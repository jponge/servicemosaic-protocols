/* 
 * CDDL HEADER START 
 * 
 * The contents of this file are subject to the terms of the 
 * Common Development and Distribution License (the "License"). 
 * You may not use this file except in compliance with the License. 
 * 
 * You can obtain a copy of the license at LICENSE.txt 
 * or at http://www.opensource.org/licenses/cddl1.php. 
 * See the License for the specific language governing permissions 
 * and limitations under the License. 
 * 
 * When distributing Covered Code, include this CDDL HEADER in each 
 * file and include the License file at LICENSE.txt. 
 * If applicable, add the following below this CDDL HEADER, with the 
 * fields enclosed by brackets "[]" replaced with your own identifying 
 * information: Portions Copyright [yyyy] [name of copyright owner] 
 * 
 * CDDL HEADER END 
 */ 

/* 
 * Copyright 2006 Julien Ponge. All rights reserved. 
 * Use is subject to license terms. 
 */ 

/* ............................................................................................. */

header
{
	package fr.isima.ponge.wsprotocol.timed.constraints.parser;

	import fr.isima.ponge.wsprotocol.timed.constraints.*;
}

/* ............................................................................................. */

class TemporalConstraintParser extends Parser;
options
{
	buildAST = true;
	k = 2;
	defaultErrorHandler = false;
}

constraint : ciConstraint | miConstraint;

miConstraint!
	:
	func:MINVOKE param:miConstraintParam
	{
		#miConstraint = #(func, #param);
	}
	;
miConstraintParam : LPAREN! miExpr (BOOLOP^ miExpr)* RPAREN!;

ciConstraint!
	:
	func:CINVOKE param:ciConstraintParam
	{
		#ciConstraint = #(func, #param);
	}
	;
ciConstraintParam : LPAREN! ciExpr (BOOLOP^ ciExpr)* RPAREN!;

miExpr : VAR COMPOP^ CONST
       | CONST COMPOP^ VAR
       | LPAREN! miExpr (BOOLOP^ miExpr)* RPAREN!
       ;

ciExpr : VAR COMPOP^ CONST
        | CONST COMPOP^ VAR
        | LPAREN! ciExpr (BOOLOP^ ciExpr)* RPAREN!
        ;

/* ............................................................................................. */

class TemporalConstraintLexer extends Lexer;
options
{
	k = 2;	
	defaultErrorHandler = false;
}

CINVOKE
options
{
	paraphrase = "a C-Invoke";
} : "C-Invoke";

MINVOKE
options
{
	paraphrase = "a M-Invoke";
} : "M-Invoke";

LPAREN
options
{
	paraphrase = "a left parenthesis";
} : "(";

RPAREN
options
{
	paraphrase = "a right parenthesis";
} : ")";

CONST
options
{
	paraphrase = "a constant";
} : ('0'..'9')+;

VAR
options
{
	paraphrase = "a variable";
} : ('a'..'z' | 'A'..'Z' | '_')
    ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;

COMPOP
options
{
	paraphrase = "a comparison operator";
} : ("=" | "!=" | "<=" | "<" | ">=" | ">");

BOOLOP
options
{
	paraphrase = "a boolean operator";
} : ("&&" | "||");

WS 
options
{
	paraphrase = "a white space";
} : (' ' | '\n' | '\r' | '\t')
{
	$setType(Token.SKIP);
};

/* ............................................................................................. */

class TemporalConstraintTreeWalker extends TreeParser;

terminal returns [IConstraintNode node]
{
	node = null;
}
	:
	  v:VAR { node = new VariableNode(v.getText()); }
	|
	  c:CONST { node = new ConstantNode(Integer.parseInt(c.getText())); }
	;

rootNode returns [IRootConstraintNode node]
{
	IConstraintNode left;
	IConstraintNode right;
	node = null;
}
	:
	  #(o1:COMPOP left=terminal right=terminal)
	  {
	  	if (left instanceof VariableNode)
	  	{
	  		node = new ComparisonNode(o1.getText(), (VariableNode) left, (ConstantNode) right);
	  	}
	  	else
	  	{
	  		node = new ComparisonNode(o1.getText(), (ConstantNode) left, (VariableNode) right);
	  	}
	  }
	|
	  #(o2:BOOLOP left=rootNode right=rootNode)
	  {
	  	node = new BooleanNode(o2.getText(), (IRootConstraintNode)left, (IRootConstraintNode)right);
	  }
	;

constraint returns [IConstraintNode constraint]
{
	constraint = null;
	IRootConstraintNode root;
}
	:
	  #(CINVOKE root=rootNode)
	  {
	  	constraint = new CInvokeNode(root);
	  }
	|
	  #(MINVOKE root=rootNode)
	  {
	  	/*if (root instanceof BooleanNode)
	  	{
	  		throw new RecognitionException("Boolean expressions are not allowed in M-Invoke constraints.");
	  	}
	  	if (root instanceof ComparisonNode)
	  	{
	  		ComparisonNode comp = (ComparisonNode) root;
	  		if (!comp.getSymbol().equals(ComparisonNode.EQ))
	  		{
	  			throw new RecognitionException("Bad comparison operator.");
	  		}
	  	}*/
	  	
	  	constraint = new MInvokeNode(root);
	  }
	;
/* ............................................................................................. */