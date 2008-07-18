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

package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.*
import groovy.xml.MarkupBuilder

class EmptinessOperator
{
    boolean isEmpty(BusinessProtocol protocol)
    {
        def writer = new StringWriter()

        buildUppaalXML(writer, protocol)

        println writer.toString()
    }

    private void buildUppaalXML(writer, BusinessProtocol protocol)
    {
        def xml = new MarkupBuilder(writer)

        def clocks = []
        protocol.operations.each {
            clocks << "clock x_${it.name};"
            clocks << "bool bx_${it.name} = false;"
        }
        protocol.states.each { clocks << "clock y_${it.name};" }

        writer.println '<?xml version="1.0" encoding="UTF-8"?>'
        writer.println '<!DOCTYPE nta PUBLIC "-//Uppaal Team//DTD Flat System 1.1//EN"'
        writer.println '          "http://www.it.uu.se/research/group/darts/uppaal/flat-1_1.dtd">'
        xml.nta {
            template {
                name 'Template'
                declaration clocks.join("\n")

                protocol.states.each { state ->
                    location id:state.name, {
                        name state.name
                    }
                }
                init ref:protocol.initialState.name

                protocol.operations.each { Operation op ->
                    transition {
                        source ref: op.sourceState.name
                        target ref: op.targetState.name
                    }
                }
            }
        }
    }
}