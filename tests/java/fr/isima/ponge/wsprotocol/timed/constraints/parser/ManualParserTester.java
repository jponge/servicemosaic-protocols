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

package fr.isima.ponge.wsprotocol.timed.constraints.parser;

import antlr.CommonAST;
import fr.isima.ponge.wsprotocol.timed.constraints.IConstraintNode;

public class ManualParserTester
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // Ex: C-Invoke((((T1 < 5) && (T2 >= 10)) || (T3 = 7)))
        // Ex: C-Invoke((T1< 3) && (T2 >=5))
        // Ex: M-Invoke(T1 = 3)
        // Ex: C-Invoke(T1 < 3)
        try
        {
            TemporalConstraintLexer lexer = new TemporalConstraintLexer(System.in);
            TemporalConstraintParser parser = new TemporalConstraintParser(lexer);

            parser.constraint();
            CommonAST tree = (CommonAST) parser.getAST();
            System.out.println(tree.toStringList());

            TemporalConstraintTreeWalker walker = new TemporalConstraintTreeWalker();
            IConstraintNode constraint = walker.constraint(tree);
            System.out.println(constraint);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
