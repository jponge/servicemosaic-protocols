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
import junit.framework.TestCase;

import java.io.StringReader;

public class GrammarTestCase extends TestCase
{
    public void testGrammar()
    {
        String[] input = {
                "C-Invoke((((T1 < 5) && (T2 >= 10)) || (T3 = 7)))",
                "C-Invoke((T1< 3) && (T2 >=5))",
                "M-Invoke(T1 = 3)",
                "C-Invoke(T1 < 3)",
                "M-Invoke(T1 = 3)",
                "M-Invoke((T1 = 3) || (T2 = 2))",
                "C-Invoke(T1 - T2 < 3)",
                "M-Invoke((T1 = 3) && ((T1 - T0 <= 10) || (T2 - T0 >= 5)))",
                "M-Invoke((T1 = 3) && ((T2 >= 6) || (T3 < 5)))",
                "M-Invoke((T1 = 3) || ((T2 >= 6) || (T3 < 5)))"
        };
        String[] output = {
                "C-Invoke(((T1 < 5) && (T2 >= 10)) || (T3 = 7))",
                "C-Invoke((T1 < 3) && (T2 >= 5))",
                "M-Invoke(T1 = 3)",
                "C-Invoke(T1 < 3)",
                "M-Invoke(T1 = 3)",
                "M-Invoke((T1 = 3) || (T2 = 2))",
                "C-Invoke(T1 - T2 < 3)",
                "M-Invoke((T1 = 3) && ((T1 - T0 <= 10) || (T2 - T0 >= 5)))",
                "M-Invoke((T1 = 3) && ((T2 >= 6) || (T3 < 5)))",
                null
        };
        TemporalConstraintTreeWalker walker = new TemporalConstraintTreeWalker();

        for (int i = 0; i < input.length; ++i)
        {
            try
            {
                TemporalConstraintLexer lexer = new TemporalConstraintLexer(new StringReader(
                        input[i]));
                TemporalConstraintParser parser = new TemporalConstraintParser(lexer);
                parser.constraint();
                CommonAST tree = (CommonAST) parser.getAST();
                IConstraintNode constraint = walker.constraint(tree);

                TestCase.assertEquals(output[i], constraint.toString());
            }
            catch (Exception e)
            {
                if (output[i] != null)
                {
                    System.out.println(new StringBuilder().append(input[i]).append(" (").append(i).append(")").toString());
                    e.printStackTrace();
                    TestCase.fail();
                }
            }
        }

    }
}
