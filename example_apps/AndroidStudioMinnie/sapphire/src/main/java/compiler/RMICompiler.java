// 
// Decompiled by Procyon v0.5.30
// 

package compiler;

import java.util.StringTokenizer;
import org.apache.harmony.rmi.common.JavaCompilerException;
import org.apache.harmony.rmi.common.JavaCompiler;
import java.io.IOException;
import java.io.FileWriter;
import org.apache.harmony.rmi.common.RMIUtil;
import java.io.File;
import java.util.Iterator;
import org.apache.harmony.rmi.internal.nls.Messages;
import java.util.ArrayList;
import java.util.HashMap;

public final class RMICompiler implements RmicConstants, RmicStrings
{
    private final int version;
    private final boolean keepSources;
    private final boolean always;
    private final boolean factory;
    private final boolean valueMethods;
    private final boolean localStubs;
    private final boolean poa;
    private final boolean debug;
    private final boolean warnings;
    private final boolean writeClasses;
    private final boolean verbose;
    private final boolean depend;
    private final String destinationDir;
    private final boolean optionsPresent;
    private final String[] javacOptions;
    private HashMap warningTags;
    private Object[] classes;
    private int numClasses;
    
    public RMICompiler(final String[] array, final Class[] array2) throws RMICompilerException {
        this(array, (Object[])array2);
    }
    
    public RMICompiler(final String[] array, final String[] array2) throws RMICompilerException {
        this(array, (Object[])array2);
    }
    
    public RMICompiler(final String[] array, final String s) throws RMICompilerException {
        this(array, (Object[])stringToArray(s));
    }
    
    public RMICompiler(final String[] array) throws RMICompilerException {
        this(array, (Object[])null);
    }
    
    public RMICompiler(final String s, final Class[] array) throws RMICompilerException {
        this(stringToArray(s), (Object[])array);
    }
    
    public RMICompiler(final String s, final String[] array) throws RMICompilerException {
        this(stringToArray(s), (Object[])array);
    }
    
    public RMICompiler(final String s, final String s2) throws RMICompilerException {
        this(stringToArray(s), (Object[])stringToArray(s2));
    }
    
    public RMICompiler(final String s) throws RMICompilerException {
        this(stringToArray(s), (Object[])null);
    }
    
    private RMICompiler(final String[] array, Object[] array2) throws RMICompilerException {
        this.warningTags = new HashMap();
        final int length = array.length;
        int version = 0;
        boolean keepSources = false;
        boolean always = false;
        boolean factory = false;
        boolean valueMethods = true;
        boolean localStubs = true;
        boolean poa = false;
        boolean debug = false;
        boolean warnings = true;
        boolean writeClasses = true;
        boolean verbose = false;
        boolean depend = false;
        boolean optionsPresent = length > 0;
        String destinationDir = ".";
        final ArrayList<String> list = new ArrayList<String>();
        list.add("-nowarn");
        for (int i = 0; i < length; ++i) {
            final String intern = array[i].intern();
            if (intern == "-v1.1") {
                if (version == 0) {
                    version = 1;
                }
                else {
                    error(RMICompiler.errorVersionText);
                }
            }
            else if (intern == "-v1.2") {
                if (version == 0) {
                    version = 2;
                }
                else {
                    error(RMICompiler.errorVersionText);
                }
            }
            else if (intern == "-vcompat") {
                if (version == 0) {
                    version = 3;
                }
                else {
                    error(RMICompiler.errorVersionText);
                }
            }
            else if (intern == "-idl") {
                if (version == 0) {
                    version = 4;
                }
                else {
                    error(RMICompiler.errorVersionText);
                }
            }
            else if (intern == "-iiop") {
                if (version == 0) {
                    version = 5;
                }
                else {
                    error(RMICompiler.errorVersionText);
                }
            }
            else if (intern == "-target") {
                if (i < length - 1) {
                    final String intern2 = array[++i].intern();
                    final String s = (intern2 == "1.1" || intern2 == "1.2") ? "1.3" : intern2;
                    list.add("-source");
                    list.add(s);
                    list.add("-target");
                    list.add(intern2);
                }
                else {
                    error(RMICompiler.errorNeedParameterText, intern);
                }
            }
            else if (intern == "-keep" || intern == "-keepgenerated") {
                keepSources = true;
            }
            else if (intern == "-always" || intern == "-alwaysgenerate") {
                always = true;
            }
            else if (intern == "-factory") {
                factory = true;
            }
            else if (intern == "-noValueMethods") {
                valueMethods = false;
            }
            else if (intern == "-nolocalstubs") {
                localStubs = false;
            }
            else if (intern == "-poa") {
                poa = true;
            }
            else if (intern == "-g" || intern.startsWith("-g:")) {
                list.add(intern);
                debug = true;
            }
            else if (intern == "-nowarn") {
                warnings = false;
            }
            else if (intern == "-nowrite") {
                writeClasses = false;
            }
            else if (intern == "-verbose") {
                list.add(intern);
                verbose = true;
            }
            else if (intern == "-depend") {
                depend = true;
            }
            else if (intern == "-idlModule" || intern == "-idlFile") {
                if (i < length - 2) {
                    i += 2;
                }
                else {
                    error(RMICompiler.errorNeedTwoParametersText, intern);
                }
            }
            else if (intern == "-classpath" || intern == "-cp" || intern == "-bootclasspath" || intern == "-extdirs" || intern == "-d") {
                if (i < length - 1) {
                    final String s2 = (intern == "-cp") ? "-classpath" : intern;
                    list.add(s2);
                    final String s3 = array[++i];
                    list.add(s3);
                    if (intern == "-d") {
                        destinationDir = s3;
                    }
                    else {
                        this.addWarning(s2.substring(1), RMICompiler.warningClassPathText);
                    }
                }
                else {
                    error(RMICompiler.errorNeedParameterText, intern);
                }
            }
            else if (intern.startsWith("-J") || intern.startsWith("-X")) {
                if (intern.length() > 2) {
                    list.add(intern);
                }
                else {
                    error(RMICompiler.errorNeedJVMParameterText, intern);
                }
            }
            else if (intern.startsWith("-")) {
                error(RMICompiler.errorUnknownOptionText, intern);
            }
            else {
                if (array2 != null) {
                    error(RMICompiler.errorUnknownOptionText, intern);
                    break;
                }
                final int n = length - i;
                array2 = new Object[n];
                System.arraycopy(array, i, array2, 0, n);
                if (i == 0) {
                    optionsPresent = false;
                    break;
                }
                break;
            }
        }
        if (warnings) {
            final Iterator<Object> iterator = this.warningTags.values().iterator();
            while (iterator.hasNext()) {
                System.err.println(Messages.getString("rmi.console.1A", iterator.next()));
            }
        }
        if (always && version != 4 && version != 5) {
            error(RMICompiler.errorUnusableExceptIDL_IIOP, "-always");
        }
        if (factory && version != 4) {
            error(RMICompiler.errorUnusableExceptIDL, "-factory");
        }
        if (!valueMethods && version != 4) {
            error(RMICompiler.errorUnusableExceptIDL, "-noValueMethods");
        }
        if (!localStubs && version != 5) {
            error(RMICompiler.errorUnusableExceptIIOP, "-nolocalstubs");
        }
        if (poa && version != 5) {
            error(RMICompiler.errorUnusableExceptIIOP, "-poa");
        }
        if (version == 0) {
            version = 2;
        }
        this.classes = ((array2 != null) ? array2 : new Object[0]);
        this.numClasses = this.classes.length;
        this.version = version;
        this.keepSources = keepSources;
        this.always = always;
        this.factory = factory;
        this.valueMethods = valueMethods;
        this.localStubs = localStubs;
        this.poa = poa;
        this.debug = debug;
        this.warnings = warnings;
        this.writeClasses = writeClasses;
        this.verbose = verbose;
        this.depend = depend;
        this.optionsPresent = optionsPresent;
        this.destinationDir = destinationDir;
        this.javacOptions = list.toArray(new String[list.size()]);
    }
    
    public void run() throws RMICompilerException {
        if (this.numClasses < 1) {
            if (this.optionsPresent) {
                error(RMICompiler.errorNoClassesText);
            }
            else {
                usage();
            }
        }
        final File[] array = new File[this.numClasses];
        final File[] array2 = new File[this.numClasses];
        final File[] array3 = new File[this.numClasses];
        int n = 0;
        try {
            for (int i = 0; i < this.numClasses; ++i) {
                final Object o = this.classes[i];
                Class<?> forName;
                if (o instanceof Class) {
                    forName = (Class<?>)o;
                }
                else {
                    final String s = (String)o;
                    try {
                        forName = Class.forName(s);
                        this.classes[i] = forName;
                    }
                    catch (ClassNotFoundException ex) {
                        throw new RMICompilerException(Messages.getString("rmi.55", ex.getMessage()), ex);
                    }
                    catch (LinkageError linkageError) {
                        throw new RMICompilerException(Messages.getString("rmi.57", linkageError.getMessage()), linkageError);
                    }
                }
                final ClassStub classStub = new ClassStub(this.version, forName);
                String s2 = RMIUtil.getPackageName(forName);
                if (s2 != null) {
                    s2 = s2.replace('.', File.separatorChar);
                }
                final String stubClassName = classStub.getStubClassName();
                final String skeletonClassName = classStub.getSkeletonClassName();
                final File packageDir = RmicUtil.getPackageDir(this.destinationDir, s2);
                final File packageFile = RmicUtil.getPackageFile(packageDir, String.valueOf(stubClassName) + ".java");
                array[n] = packageFile;
                final File packageFile2 = RmicUtil.getPackageFile(packageDir, String.valueOf(skeletonClassName) + ".java");
                array2[n] = packageFile2;
                array3[n] = RmicUtil.getPackageFile(packageDir, String.valueOf(skeletonClassName) + ".class");
                ++n;
                try {
                    final FileWriter fileWriter = new FileWriter(packageFile);
                    fileWriter.write(classStub.getStubSource());
                    fileWriter.close();
                }
                catch (IOException ex2) {
                    throw new RMICompilerException(Messages.getString("rmi.58", packageFile.getName()), ex2);
                }
                if (this.version != 2) {
                    try {
                        final FileWriter fileWriter2 = new FileWriter(packageFile2);
                        fileWriter2.write(classStub.getSkeletonSource());
                        fileWriter2.close();
                    }
                    catch (IOException ex3) {
                        throw new RMICompilerException(Messages.getString("rmi.58", packageFile2.getName()), ex3);
                    }
                }
            }
            File[] array4;
            if (this.version == 2) {
                array4 = array;
            }
            else {
                array4 = new File[2 * this.numClasses];
                int n2 = 0;
                for (int j = 0; j < this.numClasses; ++j) {
                    array4[n2++] = array[j];
                    array4[n2++] = array2[j];
                }
            }
            try {
                final int compile = JavaCompiler.locateJavaCompiler(this.verbose).compile(this.javacOptions, array4);
                if (compile != 0) {
                    throw new RMICompilerException(Messages.getString("rmi.59", compile));
                }
            }
            catch (JavaCompilerException ex4) {
                throw new RMICompilerException(Messages.getString("rmi.5A", ex4), ex4);
            }
        }
        finally {
            if (!this.keepSources) {
                for (int k = 0; k < n; ++k) {
                    array[k].delete();
                }
            }
            if (!this.keepSources || this.version == 2) {
                for (int l = 0; l < n; ++l) {
                    array2[l].delete();
                }
            }
            if (this.version == 2) {
                for (int n3 = 0; n3 < n; ++n3) {
                    array3[n3].delete();
                }
            }
        }
        if (!this.keepSources) {
            for (int n4 = 0; n4 < n; ++n4) {
                array[n4].delete();
            }
        }
        if (!this.keepSources || this.version == 2) {
            for (int n5 = 0; n5 < n; ++n5) {
                array2[n5].delete();
            }
        }
        if (this.version == 2) {
            for (int n6 = 0; n6 < n; ++n6) {
                array3[n6].delete();
            }
        }
    }
    
    public void run(final Class[] classes) throws RMICompilerException {
        this.classes = classes;
        this.run();
    }
    
    public void run(final String[] classes) throws RMICompilerException {
        this.classes = classes;
        this.run();
    }
    
    private static void usage() throws RMICompilerException {
        throw new RMICompilerException(String.valueOf(RMICompiler.EOLN) + RMICompiler.usageText);
    }
    
    private static void error(final String s) throws RMICompilerException {
        throw new RMICompilerException(String.valueOf(s) + RMICompiler.EOLN);
    }
    
    private static void error(final String s, final String s2) throws RMICompilerException {
        error(s.replaceAll("%s", s2));
    }
    
    private void addWarning(final String s, final String s2) {
        this.warningTags.put(s, s2.replaceAll("%s", s));
    }
    
    private static String[] stringToArray(final String s) {
        final StringTokenizer stringTokenizer = new StringTokenizer(s);
        final int countTokens = stringTokenizer.countTokens();
        final String[] array = new String[countTokens];
        for (int i = 0; i < countTokens; ++i) {
            array[i] = stringTokenizer.nextToken();
        }
        return array;
    }
}
