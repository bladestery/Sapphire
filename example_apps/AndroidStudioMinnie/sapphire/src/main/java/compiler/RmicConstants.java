// 
// Decompiled by Procyon v0.5.30
// 

package compiler;

public interface RmicConstants
{
    public static final String EOLN = System.getProperty("line.separator");
    public static final String stubSuffix = "_Stub";
    public static final String skelSuffix = "_Skel";
    public static final String javaSuffix = ".java";
    public static final String classSuffix = ".class";
    public static final String methodVarPrefix = "$method_";
    public static final String paramPrefix = "$param_";
    public static final String arrayPrefix = "arrayOf_";
    public static final String retVarName = "$result";
    public static final String interfaceHashVarName = "interfaceHash";
    public static final String useNewInvoke = "useNewInvoke";
    public static final String inputStreamName = "in";
    public static final String outputStreamName = "out";
    public static final int VERSION_NOT_SET = 0;
    public static final int VERSION_V11 = 1;
    public static final int VERSION_V12 = 2;
    public static final int VERSION_VCOMPAT = 3;
    public static final int VERSION_IDL = 4;
    public static final int VERSION_IIOP = 5;
    public static final int MIN_VERSION = 1;
    public static final int MAX_VERSION = 5;
}
