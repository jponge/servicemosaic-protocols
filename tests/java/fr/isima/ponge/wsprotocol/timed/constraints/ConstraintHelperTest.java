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
}
