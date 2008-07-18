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

import java.io.*

import fr.isima.ponge.wsprotocol.*
import fr.isima.ponge.wsprotocol.timed.constraints.*
import fr.isima.ponge.wsprotocol.timed.constraints.parser.*

import groovy.xml.MarkupBuilder


class EmptinessOperator
{
    String verifytaPath

    EmptinessOperator(String verifytaPath)
    {
        this.verifytaPath = verifytaPath
    }

    boolean isEmpty(BusinessProtocol protocol)
    {
        File xml = File.createTempFile("ServiceMosaicProtocolsEmptiness", ".xml")
        File queries = File.createTempFile("ServiceMosaicProtocolsEmptiness", ".q")

        def xmlWriter = new StringWriter()
        buildUppaalXML(xmlWriter, protocol)
        xml.withWriter { out -> out.write(xmlWriter.toString()) }

        queries.withWriter { out ->
            protocol.finalStates.each { state ->
                out.writeLine "E<>Process.${state.name}"
            }
        }

        def command = "${verifytaPath} -s \"${xml.canonicalPath}\" \"${queries.canonicalPath}\""
        def process = command.execute()
        boolean empty = true
        process.in.eachLine { line ->
            if (line.contains('-- Property is satisfied'))
            {
                empty = false
            }
        }
        return empty
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
                        label kind: 'assignment', resetsFor(op)
                        label kind: 'guard', guardFor(op)
                    }
                }
            }
            system 'Process = Template();\nsystem Process;'
        }
    }

    private String resetsFor(Operation op)
    {
        "x_${op.name} = 0, bx_${op.name} = true, y_${op.targetState.name} = 0"
    }

    private String guardFor(Operation op)
    {
        def constraint = op.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT)
        if (constraint == null || "".equals(constraint))
        {
            return ""
        }

        TemporalConstraintLexer lexer
        TemporalConstraintParser parser
        TemporalConstraintTreeWalker walker = new TemporalConstraintTreeWalker()

        lexer = new TemporalConstraintLexer(new StringReader(constraint))
        parser = new TemporalConstraintParser(lexer)
        try
        {
            parser.constraint()
            def astRoot =  walker.constraint(parser.getAST())
            return computeGuard(astRoot.node)
        }
        catch (Exception e)
        {
            return ""
        }
    }

    private String computeGuard(IConstraintNode node)
    {
        if (node instanceof BooleanNode)
        {
            def symbol = (node.symbol == BooleanNode.AND) ? 'and' : 'or'
            return "${computeGuard(node.leftChild)} ${symbol} ${computeGuard(node.rightChild)}"
        }
        else if (node instanceof ComparisonNode)
        {
            if (node.leftChild instanceof VariableNode)
            {
                def variable = node.leftChild
                def constant = node.rightChild
                if (constant.constant == -1)
                {
                    return "(bx_${variable.variableName} ${node.symbol} false)"
                }
                else
                {
                    return "(x_${variable.variableName} ${node.symbol} ${constant.constant})"
                }
            }
            else
            {
                def variable = node.rightChild
                def constant = node.leftChild
                if (constant.constant == -1)
                {
                    return "(false ${node.symbol} bx_${variable.variableName})"
                }
                else
                {
                    return "(${constant.constant} ${node.symbol} x_${variable.variableName})"
                }
            }
        }
        else if (node instanceof DiagonalNode)
        {
            return "(x_${node.firstVariable} - x_${node.secondVariable} ${node.operator} ${node.constantNode.constant})"            
        }
    }
}