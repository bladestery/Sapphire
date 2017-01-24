// 
// Decompiled by Procyon v0.5.30
// 

package compiler;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.harmony.rmi.common.RMIHashException;
import java.util.SortedMap;
import org.apache.harmony.rmi.common.RMIHash;
import java.lang.reflect.Method;
import java.util.TreeMap;
import org.apache.harmony.rmi.common.ClassList;
import java.rmi.Remote;
import org.apache.harmony.rmi.common.RMIUtil;
import org.apache.harmony.rmi.internal.nls.Messages;
import java.util.Vector;

final class ClassStub implements RmicConstants
{
    final boolean v11;
    final boolean v12;
    final boolean vCompat;
    Indenter indenter;
    private final String className;
    private final String packageName;
    private final String stubName;
    private final String skelName;
    private final Class[] interfaces;
    private final Vector methods;
    private final boolean methodsExist;
    private final long interfaceHash;
    
    ClassStub(final int n, final String s) throws RMICompilerException {
        this(n, getClass(s));
    }
    
    ClassStub(final int n, final Class clazz) throws RMICompilerException {
        if (n < 1 || n > 5) {
            throw new RMICompilerException(Messages.getString("rmi.50"));
        }
        switch (n) {
            case 1: {
                this.v11 = true;
                this.v12 = false;
                this.vCompat = false;
                break;
            }
            case 2: {
                this.v11 = false;
                this.v12 = true;
                this.vCompat = false;
                break;
            }
            case 3: {
                this.v11 = true;
                this.v12 = true;
                this.vCompat = true;
                break;
            }
            default: {
                throw new RMICompilerException(Messages.getString("rmi.51"));
            }
        }
        this.className = RMIUtil.getCanonicalName(clazz);
        if (clazz.isInterface()) {
            throw new RMICompilerException(Messages.getString("rmi.52", this.className));
        }
        if (!Remote.class.isAssignableFrom(clazz)) {
            throw new RMICompilerException(Messages.getString("rmi.53", this.className));
        }
        if (!new ClassList(clazz.getInterfaces()).contains(Remote.class)) {
            throw new RMICompilerException(Messages.getString("rmi.54", this.className));
        }
        this.packageName = RMIUtil.getPackageName(clazz);
        final String shortName = RMIUtil.getShortName(clazz);
        this.stubName = String.valueOf(shortName) + "_Stub";
        this.skelName = String.valueOf(shortName) + "_Skel";
        try {
            this.interfaces = RMIUtil.getRemoteInterfaces(clazz);
        }
        catch (IllegalArgumentException ex) {
            throw new RMICompilerException(ex);
        }
        this.methods = new Vector();
        final TreeMap<Object, Method> treeMap = new TreeMap<Object, Method>();
        for (int i = 0; i < this.interfaces.length; ++i) {
            RMIHash.getSortedMethodMap(treeMap, this.interfaces[i].getMethods());
        }
        Label_0400: {
            if (this.v11) {
                try {
                    this.interfaceHash = RMIHash.getInterfaceHash(treeMap);
                    break Label_0400;
                }
                catch (RMIHashException ex2) {
                    throw new RMICompilerException(ex2.getMessage(), ex2.getCause());
                }
            }
            this.interfaceHash = -1L;
        }
        int n2 = 0;
        final Iterator<Method> iterator = treeMap.values().iterator();
        while (iterator.hasNext()) {
            this.methods.add(new MethodStub(iterator.next(), n2++));
        }
        this.methodsExist = (n2 > 0);
    }
    
    private static Class getClass(final String s) throws RMICompilerException {
        try {
            return Class.forName(s);
        }
        catch (ClassNotFoundException ex) {
            throw new RMICompilerException(Messages.getString("rmi.55", s));
        }
    }
    
    String getStubClassName() {
        return this.stubName;
    }
    
    String getSkeletonClassName() {
        return this.skelName;
    }
    
    String getStubSource() {
        this.indenter = new Indenter();
        return String.valueOf(this.getStubHeader("stub")) + this.getPackageStatement() + ClassStub.EOLN + this.getStubClassDeclaration() + this.indenter.hIncrease() + (this.v12 ? (String.valueOf(ClassStub.EOLN) + this.getSerialVersionUID()) : "") + (this.v11 ? (String.valueOf(ClassStub.EOLN) + this.getInterfaceHash() + (this.methodsExist ? (String.valueOf(this.vCompat ? new StringBuilder(String.valueOf(ClassStub.EOLN)).append(this.getNewInvoke()).toString() : "") + ClassStub.EOLN + this.getOperationsArrayDeclaration()) : "")) : "") + ((this.v12 && this.methodsExist) ? (String.valueOf(ClassStub.EOLN) + this.getMethodVariablesDeclaration() + ClassStub.EOLN + this.getStaticInitializationBlock()) : "") + ClassStub.EOLN + this.getStubConstructors() + (this.methodsExist ? this.getMethodImplementations() : "") + this.indenter.decrease() + '}' + ClassStub.EOLN + this.indenter.assertEmpty();
    }
    
    String getSkeletonSource() {
        this.indenter = new Indenter();
        return String.valueOf(this.getStubHeader("skeleton")) + this.getPackageStatement() + ClassStub.EOLN + this.getSkeletonClassDeclaration() + this.indenter.hIncrease() + ClassStub.EOLN + this.getInterfaceHash() + ClassStub.EOLN + this.getOperationsArrayDeclaration() + ClassStub.EOLN + this.getOperationsMethod() + ClassStub.EOLN + this.getDispatchMethod() + this.indenter.decrease() + '}' + ClassStub.EOLN + this.indenter.assertEmpty();
    }
    
    private String getStubHeader(final String s) {
        return "/*" + ClassStub.EOLN + " * RMI " + s + " class" + ClassStub.EOLN + " * for class " + this.className + ClassStub.EOLN + " * Compatible with stub protocol version " + (this.v11 ? "1.1" : "") + (this.vCompat ? "/" : "") + (this.v12 ? "1.2" : "") + ClassStub.EOLN + " *" + ClassStub.EOLN + " * Generated by DRL RMI Compiler (rmic)." + ClassStub.EOLN + " *" + ClassStub.EOLN + " * DO NOT EDIT!!!" + ClassStub.EOLN + " * Contents subject to change without notice!" + ClassStub.EOLN + " */" + ClassStub.EOLN;
    }
    
    private String getPackageStatement() {
        return (this.packageName == null) ? "" : ("package " + this.packageName + ';' + ClassStub.EOLN + ClassStub.EOLN);
    }
    
    private String getStubClassDeclaration() {
        final StringBuilder sb = new StringBuilder("public final class " + this.stubName + " extends java.rmi.server.RemoteStub" + ClassStub.EOLN + this.indenter.tIncrease(2) + "implements ");
        for (int i = 0; i < this.interfaces.length; ++i) {
            sb.append(String.valueOf((i > 0) ? ", " : "") + this.interfaces[i].getName());
        }
        sb.append(" {" + ClassStub.EOLN);
        return sb.toString();
    }
    
    private String getSkeletonClassDeclaration() {
        return "public final class " + this.skelName + " implements java.rmi.server.Skeleton {" + ClassStub.EOLN;
    }
    
    private String getSerialVersionUID() {
        return String.valueOf(this.indenter.indent()) + "private static final long serialVersionUID = 2;" + ClassStub.EOLN;
    }
    
    private String getInterfaceHash() {
        return String.valueOf(this.indenter.indent()) + "private static final long " + "interfaceHash" + " = " + this.interfaceHash + "L;" + ClassStub.EOLN;
    }
    
    private String getNewInvoke() {
        return String.valueOf(this.indenter.indent()) + "private static boolean " + "useNewInvoke" + ';' + ClassStub.EOLN;
    }
    
    private String getOperationsArrayDeclaration() {
        final StringBuilder sb = new StringBuilder(String.valueOf(this.indenter.indent()) + "private static final java.rmi.server.Operation[]" + " operations = {");
        if (this.methodsExist) {
            sb.append(String.valueOf(ClassStub.EOLN) + this.indenter.hIncrease());
            final Iterator<MethodStub> iterator = (Iterator<MethodStub>)this.methods.iterator();
            while (iterator.hasNext()) {
                sb.append(String.valueOf(iterator.next().getOpsArrayElement()) + (iterator.hasNext() ? "," : "") + ClassStub.EOLN);
            }
            sb.append(this.indenter.decrease());
        }
        sb.append("};" + ClassStub.EOLN);
        return sb.toString();
    }
    
    private String getOperationsMethod() {
        return String.valueOf(this.indenter.indent()) + "public java.rmi.server.Operation[] getOperations() {" + ClassStub.EOLN + this.indenter.tIncrease() + "return (java.rmi.server.Operation[]) operations.clone();" + ClassStub.EOLN + this.indenter.indent() + '}' + ClassStub.EOLN;
    }
    
    private String getDispatchMethod() {
        final StringBuilder sb = new StringBuilder(String.valueOf(this.indenter.indent()) + "public void dispatch(java.rmi.Remote obj, " + "java.rmi.server.RemoteCall call, int opnum, long hash) " + "throws java.lang.Exception {" + ClassStub.EOLN + this.indenter.hIncrease());
        if (this.vCompat) {
            sb.append(String.valueOf(this.indenter.indent()) + "if (opnum < 0) {" + ClassStub.EOLN + this.indenter.increase());
            if (this.methodsExist) {
                final Iterator<MethodStub> iterator = this.methods.iterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next().getHashCheck());
                }
                sb.append(String.valueOf('{') + ClassStub.EOLN + this.indenter.increase());
            }
            sb.append("throw new java.rmi.UnmarshalException(\"Invalid method hash: \" + hash);" + ClassStub.EOLN + this.indenter.decrease() + '}' + ClassStub.EOLN + (this.methodsExist ? (String.valueOf(this.indenter.tDecrease()) + "} else {") : "") + ClassStub.EOLN);
        }
        sb.append(String.valueOf(this.indenter.indent()) + "if (hash != interfaceHash) {" + ClassStub.EOLN + this.indenter.increase() + "throw new java.rmi.server.SkeletonMismatchException(" + ClassStub.EOLN + this.indenter.tIncrease(2) + "\"Interface hash mismatch, expected: \" + interfaceHash" + " + \", received: \" + hash);" + ClassStub.EOLN + this.indenter.decrease() + '}' + ClassStub.EOLN + ((this.vCompat && this.methodsExist) ? (String.valueOf(this.indenter.decrease()) + '}' + ClassStub.EOLN) : "") + ClassStub.EOLN + this.indenter.indent() + this.className + " server = " + '(' + this.className + ") obj;" + ClassStub.EOLN + ClassStub.EOLN);
        if (this.methodsExist) {
            sb.append(String.valueOf(this.indenter.indent()) + "switch (opnum) {" + ClassStub.EOLN);
            final Iterator<MethodStub> iterator2 = this.methods.iterator();
            while (iterator2.hasNext()) {
                sb.append(String.valueOf(ClassStub.EOLN) + iterator2.next().getDispatchCase());
            }
            sb.append(String.valueOf(ClassStub.EOLN) + this.indenter.indent() + "default:" + ClassStub.EOLN);
            this.indenter.increase();
        }
        sb.append(String.valueOf(this.indenter.indent()) + "throw new java.rmi.UnmarshalException(" + "\"Invalid method number: \" + opnum);" + ClassStub.EOLN + (this.methodsExist ? (String.valueOf(this.indenter.decrease()) + '}' + ClassStub.EOLN) : "") + this.indenter.decrease() + '}' + ClassStub.EOLN);
        return sb.toString();
    }
    
    private String getMethodVariablesDeclaration() {
        final StringBuilder sb = new StringBuilder();
        final Iterator<MethodStub> iterator = this.methods.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().getVariableDecl());
        }
        return sb.toString();
    }
    
    private String getStaticInitializationBlock() {
        final StringBuilder sb = new StringBuilder(String.valueOf(this.indenter.indent()) + "static {" + ClassStub.EOLN + this.indenter.increase() + "try {" + ClassStub.EOLN + this.indenter.hIncrease());
        if (this.vCompat) {
            sb.append(String.valueOf(this.indenter.indent()) + "java.rmi.server.RemoteRef.class.getMethod(\"invoke\", " + "new java.lang.Class[] {java.rmi.Remote.class, " + "java.lang.reflect.Method.class, java.lang.Object[].class" + ", long.class});" + ClassStub.EOLN + ClassStub.EOLN);
        }
        final Iterator<MethodStub> iterator = this.methods.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().getVariableInit());
        }
        sb.append(String.valueOf(this.vCompat ? new StringBuilder(String.valueOf(ClassStub.EOLN)).append(this.indenter.indent()).append("useNewInvoke").append(" = true;").append(ClassStub.EOLN).toString() : "") + this.indenter.decrease() + "} catch (java.lang.NoSuchMethodException e) {" + ClassStub.EOLN + this.indenter.increase() + (this.vCompat ? "useNewInvoke = false;" : ("throw new java.lang.NoSuchMethodError(" + ClassStub.EOLN + this.indenter.tIncrease(2) + "\"Stub class initialization failed: " + ((this.packageName != null) ? (String.valueOf(this.packageName) + '.') : "") + this.stubName + "\");")) + ClassStub.EOLN + this.indenter.decrease() + '}' + ClassStub.EOLN + this.indenter.decrease() + '}' + ClassStub.EOLN);
        return sb.toString();
    }
    
    private String getStubConstructors() {
        final StringBuilder sb = new StringBuilder();
        if (this.v11) {
            sb.append(String.valueOf(this.indenter.indent()) + "public " + this.stubName + "() {" + ClassStub.EOLN + this.indenter.tIncrease() + "super();" + ClassStub.EOLN + this.indenter.indent() + '}' + ClassStub.EOLN + ClassStub.EOLN);
        }
        sb.append(String.valueOf(this.indenter.indent()) + "public " + this.stubName + "(java.rmi.server.RemoteRef ref) {" + ClassStub.EOLN + this.indenter.tIncrease() + "super(ref);" + ClassStub.EOLN + this.indenter.indent() + '}' + ClassStub.EOLN);
        return sb.toString();
    }
    
    private String getMethodImplementations() {
        final StringBuilder sb = new StringBuilder();
        final Iterator<MethodStub> iterator = this.methods.iterator();
        while (iterator.hasNext()) {
            sb.append(String.valueOf(ClassStub.EOLN) + iterator.next().getStubImpl());
        }
        return sb.toString();
    }
    
    private final class MethodStub
    {
        private final String name;
        private final String interfaceName;
        private final Class[] parameters;
        private final String[] paramClassNames;
        private final String[] paramNames;
        private final int numParams;
        private final Class retType;
        private final String retTypeName;
        private final Vector exceptions;
        private final ClassList catches;
        private final String longSign;
        private final String shortSign;
        private final long hash;
        private final int number;
        private final String varName;
        private final boolean throwsException;
        
        MethodStub(final Method method, final int number) throws RMICompilerException {
            this.name = method.getName();
            this.interfaceName = method.getDeclaringClass().getName();
            this.parameters = method.getParameterTypes();
            this.numParams = this.parameters.length;
            this.retType = method.getReturnType();
            this.retTypeName = RMIUtil.getCanonicalName(this.retType);
            this.longSign = RMIUtil.getLongMethodSignature(method);
            this.shortSign = RMIUtil.getShortMethodSignature(method);
            this.number = number;
            this.varName = "$method_" + this.name + '_' + number;
            try {
                this.hash = RMIHash.getMethodHash(method);
            }
            catch (RMIHashException ex) {
                throw new RMICompilerException(ex.getMessage(), ex);
            }
            this.paramClassNames = new String[this.numParams];
            this.paramNames = new String[this.numParams];
            for (int i = 0; i < this.numParams; ++i) {
                final Class clazz = this.parameters[i];
                this.paramClassNames[i] = RMIUtil.getCanonicalName(clazz);
                this.paramNames[i] = RmicUtil.getParameterName(clazz, i + 1);
            }
            final Class<?>[] exceptionTypes = method.getExceptionTypes();
            (this.exceptions = new Vector(exceptionTypes.length)).addAll(Arrays.asList(exceptionTypes));
            (this.catches = new ClassList(true)).add(RuntimeException.class);
            this.catches.add(RemoteException.class);
            this.catches.addAll(this.exceptions);
            this.throwsException = this.catches.contains(Exception.class);
        }

        String getOpsArrayElement() {
            return String.valueOf(ClassStub.this.indenter.indent()) + "new java.rmi.server.Operation(\"" + this.longSign + "\")";
        }
        
        String getHashCheck() {
            return "if (hash == " + this.hash + "L) {" + ClassStub.EOLN + ClassStub.this.indenter.tIncrease() + "opnum = " + this.number + ';' + ClassStub.EOLN + ClassStub.this.indenter.indent() + "} else ";
        }
        
        String getDispatchCase() {
            final StringBuilder sb = new StringBuilder(String.valueOf(ClassStub.this.indenter.indent()) + "case " + this.number + ": {    // " + this.shortSign + ClassStub.EOLN + ClassStub.EOLN + ClassStub.this.indenter.hIncrease());
            if (this.numParams > 0) {
                for (int i = 0; i < this.numParams; ++i) {
                    sb.append(String.valueOf(ClassStub.this.indenter.indent()) + this.paramClassNames[i] + ' ' + this.paramNames[i] + ';' + ClassStub.EOLN);
                }
                sb.append(String.valueOf(ClassStub.EOLN) + ClassStub.this.indenter.indent() + "try {" + ClassStub.EOLN + ClassStub.this.indenter.increase() + "java.io.ObjectInput " + "in" + " = call.getInputStream();" + ClassStub.EOLN);
                boolean b = false;
                for (int j = 0; j < this.numParams; ++j) {
                    sb.append(String.valueOf(ClassStub.this.indenter.indent()) + this.paramNames[j] + " = " + RmicUtil.getReadObjectString(this.parameters[j], "in") + ';' + ClassStub.EOLN);
                    if (!this.parameters[j].isPrimitive()) {
                        b = true;
                    }
                }
                sb.append(String.valueOf(ClassStub.this.indenter.tDecrease()) + "} catch (java.io.IOException e) {" + ClassStub.EOLN + ClassStub.this.indenter.indent() + "throw new java.rmi.UnmarshalException(" + "\"Error unmarshalling arguments\", e);" + ClassStub.EOLN + (b ? (String.valueOf(ClassStub.this.indenter.tDecrease()) + "} catch (java.lang.ClassNotFoundException e) {" + ClassStub.EOLN + ClassStub.this.indenter.indent() + "throw new java.rmi.UnmarshalException(" + "\"Error unmarshalling arguments\", e);" + ClassStub.EOLN) : "") + ClassStub.this.indenter.tDecrease() + "} finally {" + ClassStub.EOLN);
            }
            sb.append(String.valueOf(ClassStub.this.indenter.indent()) + "call.releaseInputStream();" + ClassStub.EOLN);
            if (this.numParams > 0) {
                sb.append(String.valueOf(ClassStub.this.indenter.decrease()) + '}' + ClassStub.EOLN);
            }
            sb.append(String.valueOf(ClassStub.EOLN) + ClassStub.this.indenter.indent() + ((this.retType != Void.TYPE) ? (String.valueOf(this.retTypeName) + ' ' + "$result" + " = ") : "") + "server." + this.name + '(');
            for (int k = 0; k < this.numParams; ++k) {
                sb.append(String.valueOf((k > 0) ? ", " : "") + this.paramNames[k]);
            }
            sb.append(");" + ClassStub.EOLN + ClassStub.EOLN + ClassStub.this.indenter.indent() + "try {" + ClassStub.EOLN + ClassStub.this.indenter.increase() + ((this.retType != Void.TYPE) ? "java.io.ObjectOutput out = " : "") + "call.getResultStream(true);" + ClassStub.EOLN + ((this.retType != Void.TYPE) ? (String.valueOf(ClassStub.this.indenter.indent()) + RmicUtil.getWriteObjectString(this.retType, "$result", "out") + ';' + ClassStub.EOLN) : "") + ClassStub.this.indenter.decrease() + "} catch (java.io.IOException e) {" + ClassStub.EOLN + ClassStub.this.indenter.tIncrease() + "throw new java.rmi.MarshalException(" + "\"Error marshalling return\", e);" + ClassStub.EOLN + ClassStub.this.indenter.indent() + '}' + ClassStub.EOLN + ClassStub.EOLN + ClassStub.this.indenter.indent() + "break;" + ClassStub.EOLN + ClassStub.this.indenter.decrease() + '}' + ClassStub.EOLN);
            return sb.toString();
        }
        
        String getVariableDecl() {
            return String.valueOf(ClassStub.this.indenter.indent()) + "private static java.lang.reflect.Method" + ' ' + this.varName + ';' + ClassStub.EOLN;
        }
        
        String getVariableInit() {
            final StringBuilder sb = new StringBuilder(String.valueOf(ClassStub.this.indenter.indent()) + this.varName + " = " + this.interfaceName + ".class.getMethod(\"" + this.name + "\", new java.lang.Class[] {");
            if (this.numParams > 0) {
                for (int i = 0; i < this.numParams; ++i) {
                    sb.append(String.valueOf((i > 0) ? ", " : "") + this.paramClassNames[i] + ".class");
                }
            }
            sb.append("});" + ClassStub.EOLN);
            return sb.toString();
        }
        
        String getStubImpl() {
            return String.valueOf(this.getStubImplHeader()) + (ClassStub.this.vCompat ? (String.valueOf(ClassStub.this.indenter.indent()) + "if (" + "useNewInvoke" + ") {" + ClassStub.EOLN + ClassStub.this.indenter.hIncrease()) : "") + (ClassStub.this.v12 ? this.getStubImplCodeV12() : "") + (ClassStub.this.vCompat ? (String.valueOf(ClassStub.this.indenter.tDecrease()) + "} else {" + ClassStub.EOLN) : "") + (ClassStub.this.v11 ? this.getStubImplCodeV11() : "") + (ClassStub.this.vCompat ? (String.valueOf(ClassStub.this.indenter.decrease()) + '}' + ClassStub.EOLN) : "") + (this.throwsException ? "" : (String.valueOf(ClassStub.this.indenter.hDecrease()) + this.getStubImplCatchBlock())) + ClassStub.this.indenter.decrease() + '}' + ClassStub.EOLN;
        }
        
        private String getStubImplHeader() {
            final StringBuilder sb = new StringBuilder(String.valueOf(ClassStub.this.indenter.indent()) + "// Implementation of " + this.shortSign + ClassStub.EOLN + ClassStub.this.indenter.indent() + "public " + this.retTypeName + ' ' + this.name + '(');
            for (int i = 0; i < this.numParams; ++i) {
                sb.append(String.valueOf((i > 0) ? ", " : "") + this.paramClassNames[i] + ' ' + this.paramNames[i]);
            }
            sb.append(String.valueOf(')') + ClassStub.EOLN + ClassStub.this.indenter.tIncrease(2) + "throws ");
            final Iterator<Class> iterator = (Iterator<Class>)this.exceptions.iterator();
            while (iterator.hasNext()) {
                sb.append(String.valueOf(iterator.next().getName()) + (iterator.hasNext() ? ", " : ""));
            }
            sb.append(" {" + ClassStub.EOLN + ClassStub.this.indenter.hIncrease() + (this.throwsException ? "" : (String.valueOf(ClassStub.this.indenter.indent()) + "try {" + ClassStub.EOLN + ClassStub.this.indenter.hIncrease())));
            return sb.toString();
        }
        
        private String getStubImplCodeV11() {
            final StringBuilder sb = new StringBuilder(String.valueOf(ClassStub.this.indenter.indent()) + "java.rmi.server.RemoteCall call = " + "ref.newCall((java.rmi.server.RemoteObject) this, " + "operations, " + this.number + ", " + "interfaceHash" + ");" + ClassStub.EOLN);
            if (this.numParams > 0) {
                sb.append(String.valueOf(ClassStub.EOLN) + ClassStub.this.indenter.indent() + "try {" + ClassStub.EOLN + ClassStub.this.indenter.increase() + "java.io.ObjectOutput " + "out" + " = call.getOutputStream();" + ClassStub.EOLN);
                for (int i = 0; i < this.numParams; ++i) {
                    sb.append(String.valueOf(ClassStub.this.indenter.indent()) + RmicUtil.getWriteObjectString(this.parameters[i], this.paramNames[i], "out") + ';' + ClassStub.EOLN);
                }
                sb.append(String.valueOf(ClassStub.this.indenter.decrease()) + "} catch (java.io.IOException e) {" + ClassStub.EOLN + ClassStub.this.indenter.tIncrease() + "throw new java.rmi.MarshalException(" + "\"Error marshalling arguments\", e);" + ClassStub.EOLN + ClassStub.this.indenter.indent() + '}' + ClassStub.EOLN);
            }
            sb.append(String.valueOf(ClassStub.EOLN) + ClassStub.this.indenter.indent() + "ref.invoke(call);" + ClassStub.EOLN);
            if (this.retType != Void.TYPE) {
                sb.append(String.valueOf(ClassStub.EOLN) + ClassStub.this.indenter.indent() + this.retTypeName + ' ' + "$result" + ';' + ClassStub.EOLN + ClassStub.EOLN + ClassStub.this.indenter.indent() + "try {" + ClassStub.EOLN + ClassStub.this.indenter.increase() + "java.io.ObjectInput " + "in" + " = call.getInputStream();" + ClassStub.EOLN + ClassStub.this.indenter.indent() + "$result" + " = " + RmicUtil.getReadObjectString(this.retType, "in") + ';' + ClassStub.EOLN + ClassStub.this.indenter.decrease() + "} catch (java.io.IOException e) {" + ClassStub.EOLN + ClassStub.this.indenter.tIncrease() + "throw new java.rmi.UnmarshalException(" + "\"Error unmarshalling return value\", e);" + ClassStub.EOLN + (this.retType.isPrimitive() ? "" : (String.valueOf(ClassStub.this.indenter.indent()) + "} catch (java.lang.ClassNotFoundException e) {" + ClassStub.EOLN + ClassStub.this.indenter.tIncrease() + "throw new java.rmi.UnmarshalException(" + "\"Error unmarshalling return value\", e);" + ClassStub.EOLN)) + ClassStub.this.indenter.indent() + "} finally {" + ClassStub.EOLN + ClassStub.this.indenter.tIncrease() + "ref.done(call);" + ClassStub.EOLN + ClassStub.this.indenter.indent() + '}' + ClassStub.EOLN + ClassStub.EOLN + ClassStub.this.indenter.indent() + "return " + "$result" + ';' + ClassStub.EOLN);
            }
            else {
                sb.append(String.valueOf(ClassStub.EOLN) + ClassStub.this.indenter.indent() + "ref.done(call);" + ClassStub.EOLN);
            }
            return sb.toString();
        }
        
        private String getStubImplCodeV12() {
            final StringBuilder sb = new StringBuilder(ClassStub.this.indenter.indent());
            if (this.retType != Void.TYPE) {
                sb.append("java.lang.Object $result = ");
            }
            sb.append("ref.invoke(this, " + this.varName + ", ");
            if (this.numParams > 0) {
                sb.append("new java.lang.Object[] {");
                for (int i = 0; i < this.numParams; ++i) {
                    sb.append(String.valueOf((i > 0) ? ", " : "") + RmicUtil.getObjectParameterString(this.parameters[i], this.paramNames[i]));
                }
                sb.append('}');
            }
            else {
                sb.append("null");
            }
            sb.append(", " + this.hash + "L);" + ClassStub.EOLN);
            if (this.retType != Void.TYPE) {
                sb.append(String.valueOf(ClassStub.this.indenter.indent()) + "return " + RmicUtil.getReturnObjectString(this.retType, "$result") + ';' + ClassStub.EOLN);
            }
            return sb.toString();
        }
        
        private String getStubImplCatchBlock() {
            final StringBuilder sb = new StringBuilder();
            final Iterator iterator = this.catches.iterator();
            while (iterator.hasNext()) {
                sb.append(String.valueOf(ClassStub.this.indenter.indent()) + "} catch (" + iterator.next().toString() + " e) {" + ClassStub.EOLN + ClassStub.this.indenter.tIncrease() + "throw e;" + ClassStub.EOLN);
            }
            sb.append(String.valueOf(ClassStub.this.indenter.indent()) + "} catch (java.lang.Exception e) {" + ClassStub.EOLN + ClassStub.this.indenter.tIncrease() + "throw new java.rmi.UnexpectedException(" + "\"Undeclared checked exception\", e);" + ClassStub.EOLN + ClassStub.this.indenter.indent() + '}' + ClassStub.EOLN);
            return sb.toString();
        }
    }
}
