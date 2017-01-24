// 
// Decompiled by Procyon v0.5.30
// 

package compiler;

public class RMICompilerException extends Exception
{
    private static final long serialVersionUID = -7107015868027717508L;
    
    public RMICompilerException() {
    }
    
    public RMICompilerException(final String s) {
        super(s);
    }
    
    public RMICompilerException(final Throwable t) {
        super(t);
    }
    
    public RMICompilerException(final String s, final Throwable t) {
        super(s, t);
    }
}
