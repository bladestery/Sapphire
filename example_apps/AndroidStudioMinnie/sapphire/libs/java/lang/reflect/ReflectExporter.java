// 
// Decompiled by Procyon v0.5.30
// 

package java.lang.reflect;

import org.apache.harmony.lang.reflect.ReflectAccessor;

class ReflectExporter implements ReflectAccessor
{
    public <T> Constructor<T> copyConstructor(final Constructor<T> constructor) {
        return new Constructor<T>((Constructor)constructor);
    }
    
    public Field copyField(final Field field) {
        return new Field(field);
    }
    
    public Method copyMethod(final Method method) {
        return new Method(method);
    }
    
    public Method[] mergePublicMethods(final Method[] array, final Method[] array2, final Method[][] array3, final int n) {
        final Method[] array4 = new Method[n];
        int n2 = 0;
        for (final Method method : array) {
            if (Modifier.isPublic(method.getModifiers())) {
                array4[n2++] = method;
            }
        }
        if (array2 != null) {
            final int length2 = array2.length;
            int j = 0;
        Label_0149:
            while (j < length2) {
                final Method method2 = array2[j];
                while (true) {
                    for (int k = 0; k < n2; ++k) {
                        if (method2.getName() == array4[k].getName() && method2.getSignature() == array4[k].getSignature()) {
                            ++j;
                            continue Label_0149;
                        }
                    }
                    array4[n2++] = method2;
                    continue;
                }
            }
        }
        if (array3 != null) {
            for (int length3 = array3.length, l = 0; l < length3; ++l) {
                final Method[] array5;
                final int length4 = (array5 = array3[l]).length;
                int n3 = 0;
            Label_0265:
                while (n3 < length4) {
                    final Method method3 = array5[n3];
                    while (true) {
                        for (int n4 = 0; n4 < n2; ++n4) {
                            if (method3.getName() == array4[n4].getName() && method3.getSignature() == array4[n4].getSignature()) {
                                ++n3;
                                continue Label_0265;
                            }
                        }
                        array4[n2++] = method3;
                        continue;
                    }
                }
            }
        }
        final Method[] array6 = new Method[n2];
        System.arraycopy(array4, 0, array6, 0, n2);
        return array6;
    }
    
    public void checkMemberAccess(final Class clazz, final Class clazz2, final Class clazz3, final int n) throws IllegalAccessException {
        if (!this.allowAccess(clazz, clazz2, clazz3, n)) {
            throw new IllegalAccessException("A member of the \"" + clazz2 + "\" with \"" + Modifier.toString(n) + "\" modifiers can not be accessed from the \"" + clazz + "\"");
        }
    }
    
    private boolean allowAccess(final Class<?> clazz, final Class<?> clazz2, Class<?> superclass, final int n) {
        if (this.hasSameTopLevelClass(clazz2, clazz)) {
            return true;
        }
        if (Modifier.isPrivate(n)) {
            return false;
        }
        if (Modifier.isPublic(n)) {
            if (this.allowClassAccess(clazz2, clazz)) {
                return true;
            }
            if (superclass != clazz2) {
                while (!this.allowClassAccess(superclass, clazz) && !this.hasSameTopLevelClass(superclass, clazz)) {
                    if ((superclass = superclass.getSuperclass()) == clazz2) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        else {
            if (this.hasSamePackage(clazz2, clazz) && this.allowClassAccess(clazz2, clazz)) {
                return true;
            }
            if (Modifier.isProtected(n)) {
                Class<?> clazz3 = clazz;
                while (true) {
                    if (clazz2.isAssignableFrom(clazz3)) {
                        if (clazz3.isAssignableFrom(superclass)) {
                            return true;
                        }
                        clazz3 = (Class<?>)clazz3.getDeclaringClass();
                        if (clazz3 == null) {
                            break;
                        }
                        continue;
                    }
                    else {
                        clazz3 = clazz3.getDeclaringClass();
                        if (clazz3 == null) {
                            return false;
                        }
                        continue;
                    }
                }
            }
            return false;
        }
    }
    
    private boolean allowClassAccess(final Class<?> clazz, final Class<?> clazz2) {
        if (clazz == null || clazz == clazz2) {
            return true;
        }
        final int modifiers = clazz.getModifiers();
        final Class<?> declaringClass = clazz.getDeclaringClass();
        if (declaringClass == null) {
            return Modifier.isPublic(modifiers) || this.hasSamePackage(clazz, clazz2);
        }
        return this.allowAccess(clazz2, declaringClass, declaringClass, modifiers);
    }
    
    private boolean hasSameTopLevelClass(Class<?> clazz, Class<?> clazz2) {
        Class enclosingClass;
        while ((enclosingClass = clazz.getEnclosingClass()) != null) {
            clazz = enclosingClass;
        }
        Class enclosingClass2;
        while ((enclosingClass2 = clazz2.getEnclosingClass()) != null) {
            clazz2 = enclosingClass2;
        }
        return clazz == clazz2;
    }
    
    private boolean hasSamePackage(final Class<?> clazz, final Class<?> clazz2) {
        if (clazz.getClassLoader() != clazz2.getClassLoader()) {
            return false;
        }
        final String name = clazz.getName();
        final String name2 = clazz2.getName();
        final int lastIndex = name.lastIndexOf(46);
        return lastIndex == name2.lastIndexOf(46) && name.regionMatches(0, name2, 0, lastIndex);
    }
}
