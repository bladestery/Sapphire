// 
// Decompiled by Procyon v0.5.30
// 

package compiler;

import org.apache.harmony.rmi.internal.nls.Messages;

public final class Indenter
{
    private final String stepString = "    ";
    private final int STEP_LENGTH;
    private String currentIndent;

    public Indenter() {
        this.STEP_LENGTH = "    ".length();
        this.currentIndent = new String();
    }

    public Indenter(final int n) {
        this();
        if (n > 0) {
            this.increase(n);
        }
    }
    
    public String indent() {
        return this.currentIndent;
    }

    public String increase() {
        return this.increase(1);
    }
    
    public String increase(final int n) {
        return this.currentIndent = this.tIncrease(n);
    }

    public String decrease() throws IndexOutOfBoundsException {
        return this.decrease(1);
    }
    
    public String decrease(final int n) throws IndexOutOfBoundsException {
        return this.currentIndent = this.tDecrease(n);
    }
    
    public String hIncrease() {
        return this.hIncrease(1);
    }
    
    public String hIncrease(final int n) {
        this.increase(n);
        return "";
    }

    public String hDecrease() throws IndexOutOfBoundsException {
        return this.hDecrease(1);
    }
    
    public String hDecrease(final int n) throws IndexOutOfBoundsException {
        this.decrease(n);
        return "";
    }
    
    public String tIncrease() {
        return this.tIncrease(1);
    }
    
    public String tIncrease(final int n) {
        final StringBuilder sb = new StringBuilder(this.currentIndent);
        for (int i = 0; i < n; ++i) {
            sb.append("    ");
        }
        return sb.toString();
    }
    
    public String tDecrease() throws IndexOutOfBoundsException {
        return this.tDecrease(1);
    }
    
    public String tDecrease(final int n) throws IndexOutOfBoundsException {
        return this.currentIndent.substring(0, this.currentIndent.length() - n * this.STEP_LENGTH);
    }
    
    public String assertEmpty() throws IllegalStateException {
        if (this.currentIndent.length() != 0) {
            throw new IllegalStateException(Messages.getString("rmi.56"));
        }
        return "";
    }
}
