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

package fr.isima.ponge.wsprotocol.timed.constraints;

import antlr.CommonAST;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintLexer;
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintParser;
import fr.isima.ponge.wsprotocol.timed.constraints.parser.TemporalConstraintTreeWalker;
import junit.framework.TestCase;

import java.io.StringReader;
import java.util.List;

public class ConstraintHelperTest extends TestCase
{
    public void testIsDisjunctionFree() throws TokenStreamException, RecognitionException
    {
        TemporalConstraintLexer lexer = new TemporalConstraintLexer(new StringReader("C-Invoke((T1 < 3) && (T2 >= 6))"));
        TemporalConstraintParser parser = new TemporalConstraintParser(lexer);
        TemporalConstraintTreeWalker walker = new TemporalConstraintTreeWalker();
        parser.constraint();
        CommonAST tree = (CommonAST) parser.getAST();
        IConstraintNode constraint1 = walker.constraint(tree);

        lexer = new TemporalConstraintLexer(new StringReader("C-Invoke((T1 < 3) || (T2 >= 6))"));
        parser = new TemporalConstraintParser(lexer);
        parser.constraint();
        tree = (CommonAST) parser.getAST();
        IConstraintNode constraint2 = walker.constraint(tree);

        lexer = new TemporalConstraintLexer(new StringReader("C-Invoke((T1 < 2) && (((T1 < 3) || (T2 >= 6)) || (T3 = 5)))"));
        parser = new TemporalConstraintParser(lexer);
        parser.constraint();
        tree = (CommonAST) parser.getAST();
        IConstraintNode constraint3 = walker.constraint(tree);

        ConstraintHelper helper = new ConstraintHelper();
        assertTrue(helper.isDisjunctionFree(constraint1));
        assertFalse(helper.isDisjunctionFree(constraint2));
        assertFalse(helper.isDisjunctionFree(constraint3));
    }

    public void testBreakDisjunctions() throws TokenStreamException, RecognitionException
    {
        TemporalConstraintLexer lexer = new TemporalConstraintLexer(new StringReader("C-Invoke((T1 < 2) && (((T1 < 3) || (T2 >= 6)) || (T3 = 5)))"));
        TemporalConstraintParser parser = new TemporalConstraintParser(lexer);
        TemporalConstraintTreeWalker walker = new TemporalConstraintTreeWalker();
        parser.constraint();
        CommonAST tree = (CommonAST) parser.getAST();
        IConstraintNode constraint = walker.constraint(tree);

        ConstraintHelper helper = new ConstraintHelper();
        List<IConstraintNode> disjunctions = helper.makeDisjunctionFree(constraint);

        assertEquals(3, disjunctions.size());

        String[] expected = {
                "C-Invoke((T1 < 2) && (T1 < 3))",
                "C-Invoke((T1 < 2) && (T3 = 5))",
                "C-Invoke((T1 < 2) && (T2 >= 6))"
        };
        for (int i = 0; i < expected.length; ++i)
        {
            assertEquals(expected[i], disjunctions.get(i).toString());
        }
    }

    public void testIsValidMInvoke() throws TokenStreamException, RecognitionException
    {
        TemporalConstraintLexer lexer = new TemporalConstraintLexer(new StringReader("M-Invoke((T1 = 3) && ((T2 >= 6) || (T3 < 5)))"));
        TemporalConstraintParser parser = new TemporalConstraintParser(lexer);
        TemporalConstraintTreeWalker walker = new TemporalConstraintTreeWalker();
        parser.constraint();
        CommonAST tree = (CommonAST) parser.getAST();
        IConstraintNode constraintNode = walker.constraint(tree);

        ConstraintHelper helper = new ConstraintHelper();
        assertTrue(helper.isValidMInvoke(constraintNode));

        lexer = new TemporalConstraintLexer(new StringReader("M-Invoke((T1 = 3) || ((T2 >= 6) || (T3 < 5)))"));
        parser = new TemporalConstraintParser(lexer);
        parser.constraint();
        tree = (CommonAST) parser.getAST();
        try
        {
            walker.constraint(tree);
            fail("M-Invoke((T1 = 3) || ((T2 >= 6) || (T3 < 5))) should not be parsed since it is not valid.");
        }
        catch (Exception e)
        {
        }
    }
}
