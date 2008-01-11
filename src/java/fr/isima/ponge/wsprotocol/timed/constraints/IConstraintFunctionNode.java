package fr.isima.ponge.wsprotocol.timed.constraints;

/**
 * Interface for a constraint function node (C-Invoke, M-Invoke, ...).
 *
 * @author Julien Ponge (ponge@isima.fr)
 */
public interface IConstraintFunctionNode extends IConstraintNode
{
    /**
     * Returns the contained node.
     *
     * @return The contained node.
     */
    public IConstraintNode getNode();
}
