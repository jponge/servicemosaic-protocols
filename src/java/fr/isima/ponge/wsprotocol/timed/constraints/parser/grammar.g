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

diffExpr : VAR MINUSOP^ VAR;

miExpr : VAR COMPOP^ CONST
       | CONST COMPOP^ VAR
       | diffExpr COMPOP^ CONST
       | LPAREN! miExpr (BOOLOP^ miExpr)* RPAREN!
       ;

ciExpr : VAR COMPOP^ CONST
        | CONST COMPOP^ VAR
        | diffExpr COMPOP^ CONST
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

MINUSOP
options
{
    paraphrase = "a minus operator";
} : "-";

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

diagonal returns [IConstraintNode diag]
{
    diag = null;
    IConstraintNode var1;
    IConstraintNode var2;
}
    :
      #(MINUSOP var1=terminal var2=terminal)
      {
          if (!(var1 instanceof VariableNode) || !(var2 instanceof VariableNode))
          {
              throw new RecognitionException("Difference can only be made between variables.");
          }
          diag = new DiagonalVariablesPair((VariableNode) var1, (VariableNode) var2);
      }
    ;

rootNode returns [IRootConstraintNode node]
{
	IConstraintNode left;
	IConstraintNode right;
	node = null;
}
	:
      (#(COMPOP MINUSOP)) => #(o2:COMPOP left=diagonal right=terminal)
	  {
	    if (!(right instanceof ConstantNode))
	    {
	        throw new RecognitionException("Diagonal constraints require a right-hand constant.");
	    }
	    node = new DiagonalNode((DiagonalVariablesPair) left, o2.getText(), (ConstantNode) right);
	  }
	|
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
	  #(o3:BOOLOP left=rootNode right=rootNode)
	  {
	  	node = new BooleanNode(o3.getText(), (IRootConstraintNode)left, (IRootConstraintNode)right);
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