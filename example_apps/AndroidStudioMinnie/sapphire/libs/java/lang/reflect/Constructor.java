// 
// Decompiled by Procyon v0.5.30
// 

package java.lang.reflect;

import org.apache.harmony.vm.VMStack;
import java.util.Arrays;
import org.apache.harmony.lang.reflect.parser.Parser;
import org.apache.harmony.vm.VMGenericsAndAnnotations;
import java.lang.annotation.Annotation;

public final class Constructor<T> extends AccessibleObject implements Member, GenericDeclaration
{
    private final ConstructorData data;
    
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
    
    public TypeVariable<Constructor<T>>[] getTypeParameters() throws GenericSignatureFormatError {
        if (this.data.typeParameters == null) {
            this.data.typeParameters = (TypeVariable<Constructor<T>>[])Parser.getTypeParameters(this, VMGenericsAndAnnotations.getSignature(this.data.vm_member_id));
        }
        return (TypeVariable<Constructor<T>>[])this.data.typeParameters.clone();
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
            sb.append(Modifier.toString(modifiers & 0xFFFFFF7F)).append(' ');
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
        this.appendArrayType(sb, (Class)this.getDeclaringClass());
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
    
    Constructor(final Constructor<T> constructor) {
        this.data = constructor.data;
        this.isAccessible = constructor.isAccessible;
    }
    
    Constructor(final long n, final Class<T> clazz, final String s, final String s2, final int n2) {
        this.data = new ConstructorData(n, clazz, s, s2, n2);
    }
    
    long getId() {
        return this.data.vm_member_id;
    }
    
    public boolean equals(final Object o) {
        if (o instanceof Constructor) {
            final Constructor constructor = (Constructor)o;
            if (this.data.vm_member_id == constructor.data.vm_member_id) {
                assert this.getDeclaringClass() == constructor.getDeclaringClass() && Arrays.equals(this.getParameterTypes(), constructor.getParameterTypes());
                return true;
            }
        }
        return false;
    }
    
    public Class<T> getDeclaringClass() {
        return this.data.declaringClass;
    }
    
    public Class<?>[] getExceptionTypes() {
        return (Class<?>[])this.data.getExceptionTypes().clone();
    }
    
    public int getModifiers() {
        return this.data.modifiers;
    }
    
    public String getName() {
        return this.data.getName();
    }
    
    public Class<?>[] getParameterTypes() {
        return (Class<?>[])this.data.getParameterTypes().clone();
    }
    
    public int hashCode() {
        return this.getDeclaringClass().getName().hashCode();
    }
    
    public T newInstance(final Object... array) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (Modifier.isAbstract(this.getDeclaringClass().getModifiers())) {
            throw new InstantiationException("Can not instantiate abstract " + this.getDeclaringClass());
        }
        checkInvokationArguments(this.data.getParameterTypes(), array);
        if (!this.isAccessible) {
            Constructor.reflectExporter.checkMemberAccess(VMStack.getCallerClass(0), this.getDeclaringClass(), this.getDeclaringClass(), this.getModifiers());
        }
        return (T)VMReflection.newClassInstance(this.data.vm_member_id, array);
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder(80);
        final int modifiers = this.getModifiers();
        if (modifiers != 0) {
            sb.append(Modifier.toString(modifiers & 0xFFFFFF7F)).append(' ');
        }
        this.appendArrayType(sb, (Class)this.getDeclaringClass());
        sb.append('(');
        this.appendArrayType(sb, this.data.getParameterTypes());
        sb.append(')');
        final Class[] exceptionTypes = this.data.getExceptionTypes();
        if (exceptionTypes.length > 0) {
            sb.append(" throws ");
            this.appendSimpleType(sb, exceptionTypes);
        }
        return sb.toString();
    }
    
    String getSignature() {
        return this.data.descriptor;
    }
    
    private class ConstructorData
    {
        final long vm_member_id;
        Annotation[] declaredAnnotations;
        final Class<T> declaringClass;
        Class<?>[] exceptionTypes;
        Type[] genericExceptionTypes;
        Type[] genericParameterTypes;
        final int modifiers;
        String name;
        Annotation[][] parameterAnnotations;
        Class<?>[] parameterTypes;
        TypeVariable<Constructor<T>>[] typeParameters;
        final String descriptor;
        
        public ConstructorData(final long vm_member_id, final Class<T> declaringClass, final String s, final String descriptor, final int modifiers) {
            this.vm_member_id = vm_member_id;
            this.declaringClass = declaringClass;
            this.name = null;
            this.modifiers = modifiers;
            this.descriptor = descriptor;
        }
        
        String getName() {
            if (this.name == null) {
                this.name = this.declaringClass.getName();
            }
            return this.name;
        }
        
        public Annotation[] getDeclaredAnnotations() {
            if (this.declaredAnnotations == null) {
                this.declaredAnnotations = VMGenericsAndAnnotations.getDeclaredAnnotations(this.vm_member_id);
            }
            return this.declaredAnnotations;
        }
        
        public Class[] getExceptionTypes() {
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
    }
}
