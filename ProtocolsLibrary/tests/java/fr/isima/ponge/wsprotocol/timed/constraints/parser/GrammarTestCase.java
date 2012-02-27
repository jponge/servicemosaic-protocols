/* 
 * Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
 * Copyright 2005-2008 Université Blaise Pascal, LIMOS, Clermont-Ferrand, France.
 * Copyright 2005-2008 The University of New South Wales, Sydney, Australia.
 * 
 * This file is part of ServiceMosaic Protocols <http://servicemosaic.isima.fr/>.
 * 
 * ServiceMosaic Protocols is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ServiceMosaic Protocols is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with ServiceMosaic Protocols.  If not, see <http://www.gnu.org/licenses/>.
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
