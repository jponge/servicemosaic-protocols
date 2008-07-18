import fr.isima.ponge.wsprotocol.BusinessProtocol
import fr.isima.ponge.wsprotocol.BusinessProtocolFactory
import fr.isima.ponge.wsprotocol.Polarity
import fr.isima.ponge.wsprotocol.StandardExtraProperties
import fr.isima.ponge.wsprotocol.impl.BusinessProtocolFactoryImpl
import fr.isima.ponge.wsprotocol.operators.EmptinessOperator

/*
* Copyright 2005-2008 Julien Ponge <http://julien.ponge.info/>.
* Copyright 2005-2008 Universite Blaise Pascal, LIMOS, Clermont-Ferrand, France.
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


/*
 * This is not implemented as a JUnit test case as there is a dependency
 * on UPPAAL which is an external tool with licensing restrictions.
 */

def buildSimpleProtocol(String name = "P")
{
    BusinessProtocolFactory factory = new BusinessProtocolFactoryImpl()
    BusinessProtocol p = factory.createBusinessProtocol(name)

    def states = [
            factory.createState("s1", false),
            factory.createState("s2", false),
            factory.createState("s3", true)
    ].each { p.addState it }
    p.setInitialState states[0]

    def messages = [
            factory.createMessage("a", Polarity.POSITIVE),
            factory.createMessage("b", Polarity.POSITIVE)
    ]

    def operations = [
            factory.createOperation("T1", states[0], states[1], messages[0]),
            factory.createOperation("T2", states[1], states[2], messages[1]),
            factory.createOperation("T3", states[1], states[0], messages[1])
    ].each { p.addOperation it }
    operations[1].putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, "C-Invoke(T1 < 3)")
    operations[2].putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, "C-Invoke(T1 > 4)")

    return p
}

EmptinessOperator op = new EmptinessOperator()
op.isEmpty(buildSimpleProtocol())
