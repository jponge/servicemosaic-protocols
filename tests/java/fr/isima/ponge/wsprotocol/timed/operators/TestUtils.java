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

package fr.isima.ponge.wsprotocol.timed.operators;

import java.io.InputStreamReader;
import java.util.Iterator;

import org.dom4j.DocumentException;

import fr.isima.ponge.wsprotocol.BusinessProtocol;
import fr.isima.ponge.wsprotocol.Operation;
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl;
import fr.isima.ponge.wsprotocol.xml.XmlIOManager;

public class TestUtils
{
    private static final XmlIOManager xmlIOManager = new XmlIOManager(new BusinessProtocolFactoryImpl());

    public static BusinessProtocol loadProtocol(String path) throws DocumentException
    {
        return xmlIOManager.readBusinessProtocol(new InputStreamReader(TestUtils.class.getResourceAsStream(path)));
    }
    
    public static Operation getOperationNamed(BusinessProtocol protocol, String name)
    {
        Iterator iter = protocol.getOperations().iterator();
        while (iter.hasNext())
        {
            Operation operation = (Operation) iter.next();
            if (operation.getName().equals(name))
            {
                return operation;
            }
        }
        return null;
    }
    
}
