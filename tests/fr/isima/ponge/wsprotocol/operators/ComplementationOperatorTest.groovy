package fr.isima.ponge.wsprotocol.operators

import fr.isima.ponge.wsprotocol.*
import fr.isima.ponge.wsprotocol.impl.*
import junit.framework.TestCase

class ComplementationOperatorTest extends TestCase
{
    BusinessProtocolFactory factory = new BusinessProtocolFactoryImpl();

    def buildSimpleProtocol(String name = "P")
    {
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

    def buildSimpleProtocolComplement(String name = "^P")
    {
        BusinessProtocol p = buildSimpleProtocol(name)

        p.states.each { it.setInitialState(!it.initialState) }
        p.operations.find { it.name == "T2" }.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, "C-Invoke(T1 >= 3)")
        p.operations.find { it.name == "T3" }.putExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT, "C-Invoke(T1 <= 4)")
    }

    void testComplement()
    {
        def protocol = buildSimpleProtocol()
        def complement = buildSimpleProtocolComplement()

        ComplementationOperator operator = new ComplementationOperator()
        def result = operator.apply(protocol)

        // TEMPORARY!!!
        def list = result.operations.collect { "${it} - ${it.getExtraProperty(StandardExtraProperties.TEMPORAL_CONSTRAINT)}" }
        println "${list.size()} operations:"
        println list.join("\n")
    }
}