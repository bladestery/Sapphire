// 
// Decompiled by Procyon v0.5.30
// 

package java.lang.reflect;

final class VMReflection
{
    static native Class<?>[] getExceptionTypes(final long p0);
    
    static native Class<?> getFieldType(final long p0);
    
    static native Class<?> getMethodReturnType(final long p0);
    
    static native Class<?>[] getParameterTypes(final long p0);
    
    static native Object invokeMethod(final long p0, final Object p1, final Object... p2) throws InvocationTargetException;
    
    static native Object newArrayInstance(final Class<?> p0, final int[] p1);
    
    static native Object newClassInstance(final long p0, final Object... p1) throws InvocationTargetException;
}
