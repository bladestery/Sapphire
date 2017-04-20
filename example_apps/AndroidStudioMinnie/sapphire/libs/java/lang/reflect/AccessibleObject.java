// 
// Decompiled by Procyon v0.5.30
// 

package java.lang.reflect;

import java.security.Permission;
import org.apache.harmony.lang.reflect.ReflectPermissionCollection;
import java.lang.annotation.Annotation;
import org.apache.harmony.lang.reflect.ReflectAccessor;
import org.apache.harmony.lang.reflect.Reflection;

public class AccessibleObject implements AnnotatedElement
{
    private static final String DIMENSION_1 = "[]";
    private static final String DIMENSION_2 = "[][]";
    private static final String DIMENSION_3 = "[][][]";
    static final ReflectExporter reflectExporter;
    boolean isAccessible;
    
    static {
        Reflection.setReflectAccessor((ReflectAccessor)(reflectExporter = new ReflectExporter()));
    }
    
    public boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
        return this.getAnnotation(clazz) != null;
    }
    
    public Annotation[] getAnnotations() {
        return this.getDeclaredAnnotations();
    }
    
    public Annotation[] getDeclaredAnnotations() {
        return null;
    }
    
    public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
        return null;
    }
    
    protected AccessibleObject() {
        this.isAccessible = false;
    }
    
    public static void setAccessible(final AccessibleObject[] array, final boolean accessible0) throws SecurityException {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(ReflectPermissionCollection.SUPPRESS_ACCESS_CHECKS_PERMISSION);
        }
        for (int i = 0; i < array.length; ++i) {
            array[i].setAccessible0(accessible0);
        }
    }
    
    public boolean isAccessible() {
        return this.isAccessible;
    }
    
    public void setAccessible(final boolean accessible0) throws SecurityException {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(ReflectPermissionCollection.SUPPRESS_ACCESS_CHECKS_PERMISSION);
        }
        this.setAccessible0(accessible0);
    }
    
    Object checkObject(final Class clazz, final int n, final Object o) throws IllegalArgumentException {
        if (Modifier.isStatic(n)) {
            return null;
        }
        if (clazz.isInstance(o)) {
            return o;
        }
        if (o == null) {
            throw new NullPointerException("The specified object is null but the method is not static");
        }
        throw new IllegalArgumentException("The specified object should be an instance of " + clazz);
    }
    
    void appendArrayType(final StringBuilder sb, final Class<?> clazz) {
        if (!clazz.isArray()) {
            sb.append(clazz.getName());
            return;
        }
        int i = 1;
        Class componentType;
        final Class clazz2 = componentType = clazz.getComponentType();
        while (clazz2.isArray()) {
            componentType = clazz2;
            ++i;
        }
        sb.append(componentType.getName());
        switch (i) {
            case 1: {
                sb.append("[]");
                break;
            }
            case 2: {
                sb.append("[][]");
                break;
            }
            case 3: {
                sb.append("[][][]");
                break;
            }
            default: {
                while (i > 0) {
                    sb.append("[]");
                    --i;
                }
                break;
            }
        }
    }
    
    void appendArrayType(final StringBuilder sb, final Class[] array) {
        if (array.length > 0) {
            this.appendArrayType(sb, array[0]);
            for (int i = 1; i < array.length; ++i) {
                sb.append(',');
                this.appendArrayType(sb, array[i]);
            }
        }
    }
    
    void appendArrayGenericType(final StringBuilder sb, final Type[] array) {
        if (array.length > 0) {
            this.appendGenericType(sb, array[0]);
            for (int i = 1; i < array.length; ++i) {
                sb.append(',');
                this.appendGenericType(sb, array[i]);
            }
        }
    }
    
    void appendGenericType(final StringBuilder sb, final Type type) {
        if (type instanceof TypeVariable) {
            sb.append(((TypeVariable)type).getName());
        }
        else if (type instanceof ParameterizedType) {
            sb.append(type.toString());
        }
        else if (type instanceof GenericArrayType) {
            this.appendGenericType(sb, ((GenericArrayType)type).getGenericComponentType());
            sb.append("[]");
        }
        else if (type instanceof Class) {
            final Class clazz = (Class)type;
            if (clazz.isArray()) {
                final String[] split = clazz.getName().split("\\[");
                final int n = split.length - 1;
                if (split[n].length() > 1) {
                    sb.append(split[n].substring(1, split[n].length() - 1));
                }
                else {
                    final char char1 = split[n].charAt(0);
                    if (char1 == 'I') {
                        sb.append("int");
                    }
                    else if (char1 == 'B') {
                        sb.append("byte");
                    }
                    else if (char1 == 'J') {
                        sb.append("long");
                    }
                    else if (char1 == 'F') {
                        sb.append("float");
                    }
                    else if (char1 == 'D') {
                        sb.append("double");
                    }
                    else if (char1 == 'S') {
                        sb.append("short");
                    }
                    else if (char1 == 'C') {
                        sb.append("char");
                    }
                    else if (char1 == 'Z') {
                        sb.append("boolean");
                    }
                    else if (char1 == 'V') {
                        sb.append("void");
                    }
                }
                for (int i = 0; i < n; ++i) {
                    sb.append("[]");
                }
            }
            else {
                sb.append(clazz.getName());
            }
        }
    }
    
    void appendSimpleType(final StringBuilder sb, final Class<?>[] array) {
        if (array.length > 0) {
            sb.append(array[0].getName());
            for (int i = 1; i < array.length; ++i) {
                sb.append(',');
                sb.append(array[i].getName());
            }
        }
    }
    
    private void setAccessible0(final boolean isAccessible) throws SecurityException {
        if (isAccessible && this instanceof Constructor && ((Constructor)this).getDeclaringClass() == Class.class) {
            throw new SecurityException("Can not make the java.lang.Class class constructor accessible");
        }
        this.isAccessible = isAccessible;
    }
    
    static void checkInvokationArguments(final Class<?>[] array, final Object[] array2) {
        Label_0029: {
            if (array2 == null) {
                if (array.length == 0) {
                    break Label_0029;
                }
            }
            else if (array2.length == array.length) {
                break Label_0029;
            }
            throw new IllegalArgumentException("Invalid number of actual parameters");
        }
        for (int i = array.length - 1; i >= 0; --i) {
            if (array[i].isPrimitive()) {
                if (!(array2[i] instanceof Number) && !(array2[i] instanceof Character)) {
                    if (!(array2[i] instanceof Boolean)) {
                        throw new IllegalArgumentException("Actual parameter: " + ((array2[i] == null) ? "<null>" : array2[i].getClass().getName()) + " is incompatible with " + array[i].getName());
                    }
                }
            }
            else if (array2[i] != null) {
                if (!array[i].isInstance(array2[i])) {
                    throw new IllegalArgumentException("Actual parameter: " + ((array2[i] == null) ? "<null>" : array2[i].getClass().getName()) + " is incompatible with " + array[i].getName());
                }
            }
        }
    }
}
