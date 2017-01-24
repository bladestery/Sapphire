// 
// Decompiled by Procyon v0.5.30
// 

package compiler;

import org.apache.harmony.rmi.internal.nls.Messages;

interface RmicStrings extends RmicConstants
{
    public static final String optionPrefix = "-";
    public static final String optionV11 = "-v1.1";
    public static final String optionV12 = "-v1.2";
    public static final String optionVCompat = "-vcompat";
    public static final String optionIDL = "-idl";
    public static final String optionIIOP = "-iiop";
    public static final String optionSource = "-source";
    public static final String optionTarget = "-target";
    public static final String optionKeep = "-keep";
    public static final String optionKeepGenerated = "-keepgenerated";
    public static final String optionAlways = "-always";
    public static final String optionAlwaysGenerate = "-alwaysgenerate";
    public static final String optionFactory = "-factory";
    public static final String optionNoValueMethods = "-noValueMethods";
    public static final String optionNoLocalStubs = "-nolocalstubs";
    public static final String optionPOA = "-poa";
    public static final String optionDebug = "-g";
    public static final String optionDebugDetails = "-g:";
    public static final String optionNoWarnings = "-nowarn";
    public static final String optionNoWrite = "-nowrite";
    public static final String optionVerbose = "-verbose";
    public static final String optionDepend = "-depend";
    public static final String optionIdlModule = "-idlModule";
    public static final String optionIdlFile = "-idlFile";
    public static final String optionDestinationDir = "-d";
    public static final String optionClassPath = "-classpath";
    public static final String optionCP = "-cp";
    public static final String optionBootClassPath = "-bootclasspath";
    public static final String optionExtDirs = "-extdirs";
    public static final String optionJava = "-J";
    public static final String optionX = "-X";
    public static final String usageText = String.valueOf(Messages.getString("rmi.console.1C")) + RmicStrings.EOLN + RmicStrings.EOLN + Messages.getString("rmi.console.1D") + RmicStrings.EOLN + "  " + "-v1.1" + "              " + Messages.getString("rmi.console.1E") + RmicStrings.EOLN + "  " + "-v1.2" + "              " + Messages.getString("rmi.console.1F") + RmicStrings.EOLN + "  " + "-vcompat" + "           " + Messages.getString("rmi.console.20") + RmicStrings.EOLN + RmicStrings.EOLN + "  " + "-target" + " <version>  " + Messages.getString("rmi.console.21") + RmicStrings.EOLN + RmicStrings.EOLN + "  " + "-keep" + "              " + Messages.getString("rmi.console.22") + RmicStrings.EOLN + "  " + "-keepgenerated" + Messages.getString("rmi.console.23") + "-keep" + "\")" + RmicStrings.EOLN + RmicStrings.EOLN + "  " + "-g" + Messages.getString("rmi.console.24") + RmicStrings.EOLN + "  " + "-nowarn" + Messages.getString("rmi.console.25") + RmicStrings.EOLN + "  " + "-nowrite" + "           " + Messages.getString("rmi.console.26") + RmicStrings.EOLN + "  " + "-verbose" + Messages.getString("rmi.console.27") + RmicStrings.EOLN + RmicStrings.EOLN + "  " + "-d" + Messages.getString("rmi.console.28") + RmicStrings.EOLN + "  " + "-classpath" + Messages.getString("rmi.console.29") + RmicStrings.EOLN + "  " + "-cp" + Messages.getString("rmi.console.2A") + "-classpath" + "\")" + RmicStrings.EOLN + "  " + "-bootclasspath" + Messages.getString("rmi.console.2B") + RmicStrings.EOLN + "  " + "-extdirs" + Messages.getString("rmi.console.2C") + RmicStrings.EOLN + RmicStrings.EOLN + "  " + "-J" + Messages.getString("rmi.console.2D") + RmicStrings.EOLN + "  " + "-X" + Messages.getString("rmi.console.2E");
    public static final String errorVersionText = String.valueOf(Messages.getString("rmi.console.2F")) + "-v1.1" + "\", \"" + "-v1.2" + Messages.getString("rmi.console.31") + "-vcompat" + "\", \"" + "-idl" + "\", \"" + "-iiop";
    public static final String errorNeedParameterText = Messages.getString("rmi.console.32");
    public static final String errorNeedJVMParameterText = Messages.getString("rmi.console.33");
    public static final String errorNeedTwoParametersText = Messages.getString("rmi.console.35");
    public static final String errorUnknownOptionText = Messages.getString("rmi.console.36");
    public static final String errorNoClassesText = Messages.getString("rmi.console.37");
    public static final String errorUnusableExceptIDL_IIOP = Messages.getString("rmi.console.38", "-idl", "-iiop");
    public static final String errorUnusableExceptIDL = Messages.getString("rmi.console.39", "-idl");
    public static final String errorUnusableExceptIIOP = Messages.getString("rmi.console.3A", "-iiop");
    public static final String warningClassPathText = Messages.getString("rmi.console.3B");
}
