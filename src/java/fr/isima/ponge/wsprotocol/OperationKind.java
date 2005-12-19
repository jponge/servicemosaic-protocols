package fr.isima.ponge.wsprotocol;

/**
 * Models an operation kind.
 * 
 * @author Julien Ponge (ponge@isima.fr)
 *
 */
public class OperationKind
{

    /** Value for an explicit operation. */
    protected static final int EXPLICIT_VALUE = 1;
    
    /** Value for an implicit operation. */
    protected static final int IMPLICIT_VALUE = 2;
    
    /** The kind value. */
    protected int kind;
    
    /** The explicit kind instance. */
    public static final OperationKind EXPLICIT = new OperationKind(EXPLICIT_VALUE);
    
    /** The implicit kind instance. */
    public static final OperationKind IMPLICIT = new OperationKind(IMPLICIT_VALUE);
    
    /**
     * Instanciates a new operation kind.
     * @param kind The kind value.
     */
    protected OperationKind(int kind)
    {
        this.kind = kind;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof OperationKind)
        {
            OperationKind kind = (OperationKind)obj;
            return kind.kind == this.kind;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return kind;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        if (kind == EXPLICIT_VALUE)
        {
            return "explicit";
        }
        else
        {
            return "implicit";
        }
    }
    
    
    
}
