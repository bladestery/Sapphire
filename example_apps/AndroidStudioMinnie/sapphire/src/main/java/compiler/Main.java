// 
// Decompiled by Procyon v0.5.30
// 

package compiler;

import org.apache.harmony.rmi.internal.nls.Messages;

public final class Main implements RmicConstants
{
    public static void main(final String[] array) {
        try {
            new RMICompiler(array).run();
        }
        catch (RMICompilerException ex) {
            final String message = ex.getMessage();
            if (message != null && message.startsWith(Main.EOLN)) {
                System.out.println(message);
            }
            else {
                System.out.println(Messages.getString("rmi.console.18", (message != null) ? message : ""));
                ex.printStackTrace(System.out);
                System.exit(-1);
            }
        }
        catch (Throwable t) {
            System.out.println(Messages.getString("rmi.console.19", t));
            t.printStackTrace(System.out);
            System.exit(-1);
        }
    }
}
