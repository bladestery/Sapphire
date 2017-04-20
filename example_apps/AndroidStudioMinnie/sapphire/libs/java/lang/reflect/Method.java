// 
// Decompiled by Procyon v0.5.30
// 

package java.lang.reflect;

import org.apache.harmony.vm.VMStack;
import java.util.Arrays;
import org.apache.harmony.lang.reflect.parser.Parser;
import org.apache.harmony.vm.VMGenericsAndAnnotations;
import java.lang.annotation.Annotation;

import sapphire.app.SapphireObject;

public final class Method extends AccessibleObject implements Member, GenericDeclaration, SapphireObject
{
    private final MethodData data;
    
    public boolean isBridge() {
        return (this.getModifiers() & 0x40) != 0x0;
    }
    
    public boolean isVarArgs() {
        return (this.getModifiers() & 0x80) != 0x0;
    }
    
    public Annotation[][] getParameterAnnotations() {
        final Annotation[][] parameterAnnotations = this.data.getParameterAnnotations();
        final Annotation[][] array = new Annotation[parameterAnnotations.length][];
        for (int i = 0; i < parameterAnnotations.length; ++i) {
            array[i] = new Annotation[parameterAnnotations[i].length];
            System.arraycopy(parameterAnnotations[i], 0, array[i], 0, parameterAnnotations[i].length);
        }
        return array;
    }
    
    public Annotation[] getDeclaredAnnotations() {
        final Annotation[] declaredAnnotations = this.data.getDeclaredAnnotations();
        final Annotation[] array = new Annotation[declaredAnnotations.length];
        System.arraycopy(declaredAnnotations, 0, array, 0, declaredAnnotations.length);
        return array;
    }
    
    public <A extends Annotation> A getAnnotation(final Class<A> clazz) {
        if (clazz == null) {
            throw new NullPointerException();
        }
        Annotation[] declaredAnnotations;
        for (int length = (declaredAnnotations = this.data.getDeclaredAnnotations()).length, i = 0; i < length; ++i) {
            final Annotation annotation = declaredAnnotations[i];
            if (annotation.annotationType() == clazz) {
                return (A)annotation;
            }
        }
        return null;
    }
    
    public Type[] getGenericExceptionTypes() throws GenericSignatureFormatError, TypeNotPresentException, MalformedParameterizedTypeException {
        if (this.data.genericExceptionTypes == null) {
            this.data.genericExceptionTypes = Parser.getGenericExceptionTypes(this, VMGenericsAndAnnotations.getSignature(this.data.vm_member_id));
        }
        return this.data.genericExceptionTypes.clone();
    }
    
    public Type[] getGenericParameterTypes() throws GenericSignatureFormatError, TypeNotPresentException, MalformedParameterizedTypeException {
        if (this.data.genericParameterTypes == null) {
            this.data.genericParameterTypes = Parser.getGenericParameterTypes(this, VMGenericsAndAnnotations.getSignature(this.data.vm_member_id));
        }
        return this.data.genericParameterTypes.clone();
    }
    
    public Type getGenericReturnType() throws GenericSignatureFormatError, TypeNotPresentException, MalformedParameterizedTypeException {
        if (this.data.genericReturnType == null) {
            this.data.genericReturnType = Parser.getGenericReturnTypeImpl(this, VMGenericsAndAnnotations.getSignature(this.data.vm_member_id));
        }
        return this.data.genericReturnType;
    }
    
    public TypeVariable<Method>[] getTypeParameters() throws GenericSignatureFormatError {
        if (this.data.typeParameters == null) {
            this.data.typeParameters = (TypeVariable<Method>[])Parser.getTypeParameters(this, VMGenericsAndAnnotations.getSignature(this.data.vm_member_id));
        }
        return (TypeVariable<Method>[])this.data.typeParameters.clone();
    }
    
    public String toGenericString() {
        final StringBuilder sb = new StringBuilder(80);
        if (this.data.genericParameterTypes == null) {
            this.data.genericParameterTypes = Parser.getGenericParameterTypes(this, VMGenericsAndAnnotations.getSignature(this.data.vm_member_id));
        }
        if (this.data.genericExceptionTypes == null) {
            this.data.genericExceptionTypes = Parser.getGenericExceptionTypes(this, VMGenericsAndAnnotations.getSignature(this.data.vm_member_id));
        }
        final int modifiers = this.getModifiers();
        if (modifiers != 0) {
            sb.append(Modifier.toString(modifiers & 0xFFFFFF3F)).append(' ');
        }
        if (this.data.typeParameters != null && this.data.typeParameters.length > 0) {
            sb.append('<');
            for (int i = 0; i < this.data.typeParameters.length; ++i) {
                this.appendGenericType(sb, (Type)this.data.typeParameters[i]);
                if (i < this.data.typeParameters.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("> ");
        }
        this.appendGenericType(sb, this.getGenericReturnType());
        sb.append(' ');
        this.appendArrayType(sb, (Class)this.getDeclaringClass());
        sb.append("." + this.getName());
        sb.append('(');
        this.appendArrayGenericType(sb, this.data.genericParameterTypes);
        sb.append(')');
        if (this.data.genericExceptionTypes.length > 0) {
            sb.append(" throws ");
            this.appendArrayGenericType(sb, this.data.genericExceptionTypes);
        }
        return sb.toString();
    }
    
    public boolean isSynthetic() {
        return (this.getModifiers() & 0x1000) != 0x0;
    }
    
    public Object getDefaultValue() {
        return VMGenericsAndAnnotations.getDefaultValue(this.data.vm_member_id);
    }
    
    Method(final Method method) {
        this.data = method.data;
        this.isAccessible = method.isAccessible;
    }
    
    Method(final long n, final Class clazz, final String s, final String s2, final int n2) {
        this.data = new MethodData(n, clazz, s, s2, n2);
    }
    
    long getId() {
        return this.data.vm_member_id;
    }
    
    public boolean equals(final Object o) {
        if (o instanceof Method) {
            final Method method = (Method)o;
            if (this.data.vm_member_id == method.data.vm_member_id) {
                assert this.getDeclaringClass() == method.getDeclaringClass() && this.getName() == method.getName() && this.getReturnType() == method.getReturnType() && Arrays.equals(this.getParameterTypes(), method.getParameterTypes());
                return true;
            }
        }
        return false;
    }
    
    public Class<?> getDeclaringClass() {
        return this.data.declaringClass;
    }
    
    public Class<?>[] getExceptionTypes() {
        return (Class<?>[])this.data.getExceptionTypes().clone();
    }
    
    public int getModifiers() {
        return this.data.modifiers;
    }
    
    public String getName() {
        return this.data.name;
    }
    
    public Class<?>[] getParameterTypes() {
        return (Class<?>[])this.data.getParameterTypes().clone();
    }
    
    public Class<?> getReturnType() {
        return this.data.getReturnType();
    }
    
    public int hashCode() {
        return this.getDeclaringClass().getName().hashCode() ^ this.getName().hashCode();
    }
    
    public Object invoke(Object checkObject, final Object... array) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        checkObject = this.checkObject((Class)this.getDeclaringClass(), this.getModifiers(), checkObject);
        checkInvokationArguments(this.data.getParameterTypes(), array);
        if (!this.isAccessible) {
            Method.reflectExporter.checkMemberAccess(VMStack.getCallerClass(0), this.getDeclaringClass(), (checkObject == null) ? this.getDeclaringClass() : checkObject.getClass(), this.getModifiers());
        }
        return VMReflection.invokeMethod(this.data.vm_member_id, checkObject, array);
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final int modifiers = this.getModifiers();
        if (modifiers != 0) {
            sb.append(Modifier.toString(modifiers & 0xFFFFFF3F)).append(' ');
        }
        this.appendArrayType(sb, (Class)this.getReturnType());
        sb.append(' ');
        sb.append(this.getDeclaringClass().getName()).append('.').append(this.getName());
        sb.append('(');
        this.appendArrayType(sb, this.data.getParameterTypes());
        sb.append(')');
        final Class<?>[] exceptionTypes = this.data.getExceptionTypes();
        if (exceptionTypes.length > 0) {
            sb.append(" throws ");
            this.appendSimpleType(sb, (Class[])exceptionTypes);
        }
        return sb.toString();
    }
    
    String getSignature() {
        return this.data.descriptor;
    }
    
    private class MethodData
    {
        final long vm_member_id;
        Annotation[] declaredAnnotations;
        final Class<?> declaringClass;
        private Class<?>[] exceptionTypes;
        Type[] genericExceptionTypes;
        Type[] genericParameterTypes;
        Type genericReturnType;
        String methSignature;
        final int modifiers;
        final String name;
        final String descriptor;
        Annotation[][] parameterAnnotations;
        Class<?>[] parameterTypes;
        private Class<?> returnType;
        TypeVariable<Method>[] typeParameters;
        
        public MethodData(final long vm_member_id, final Class declaringClass, final String name, final String descriptor, final int modifiers) {
            this.vm_member_id = vm_member_id;
            this.declaringClass = (Class<?>)declaringClass;
            this.name = name;
            this.modifiers = modifiers;
            this.descriptor = descriptor;
        }
        
        public Annotation[] getDeclaredAnnotations() {
            if (this.declaredAnnotations == null) {
                this.declaredAnnotations = VMGenericsAndAnnotations.getDeclaredAnnotations(this.vm_member_id);
            }
            return this.declaredAnnotations;
        }
        
        public Class<?>[] getExceptionTypes() {
            if (this.exceptionTypes == null) {
                this.exceptionTypes = VMReflection.getExceptionTypes(this.vm_member_id);
            }
            return this.exceptionTypes;
        }
        
        public Annotation[][] getParameterAnnotations() {
            if (this.parameterAnnotations == null) {
                this.parameterAnnotations = VMGenericsAndAnnotations.getParameterAnnotations(this.vm_member_id);
            }
            return this.parameterAnnotations;
        }
        
        public Class[] getParameterTypes() {
            if (this.parameterTypes == null) {
                this.parameterTypes = VMReflection.getParameterTypes(this.vm_member_id);
            }
            return this.parameterTypes;
        }
        
        public Class<?> getReturnType() {
            if (this.returnType == null) {
                this.returnType = VMReflection.getMethodReturnType(this.vm_member_id);
            }
            return this.returnType;
        }
    }
}
