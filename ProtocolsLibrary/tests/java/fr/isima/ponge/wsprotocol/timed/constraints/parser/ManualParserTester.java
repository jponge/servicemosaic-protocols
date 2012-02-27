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
