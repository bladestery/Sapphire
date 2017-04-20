// 
// Decompiled by Procyon v0.5.30
// 

package java.lang.reflect;

public final class Array
{
    public static Object get(final Object o, final int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        try {
            return ((Object[])o)[n];
        }
        catch (ClassCastException ex) {
            if (o instanceof int[]) {
                return new Integer(((int[])o)[n]);
            }
            if (o instanceof boolean[]) {
                return ((boolean[])o)[n] ? Boolean.TRUE : Boolean.FALSE;
            }
            if (o instanceof float[]) {
                return new Float(((float[])o)[n]);
            }
            if (o instanceof char[]) {
                return new Character(((char[])o)[n]);
            }
            if (o instanceof double[]) {
                return new Double(((double[])o)[n]);
            }
            if (o instanceof long[]) {
                return new Long(((long[])o)[n]);
            }
            if (o instanceof short[]) {
                return new Short(((short[])o)[n]);
            }
            if (o instanceof byte[]) {
                return new Byte(((byte[])o)[n]);
            }
            throw new IllegalArgumentException("Specified argument is not an array");
        }
    }
    
    public static boolean getBoolean(final Object o, final int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        try {
            return ((boolean[])o)[n];
        }
        catch (ClassCastException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
    
    public static byte getByte(final Object o, final int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        try {
            return ((byte[])o)[n];
        }
        catch (ClassCastException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
    
    public static char getChar(final Object o, final int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        try {
            return ((char[])o)[n];
        }
        catch (ClassCastException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
    
    public static double getDouble(final Object o, final int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof double[]) {
            return ((double[])o)[n];
        }
        return getFloat(o, n);
    }
    
    public static float getFloat(final Object o, final int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof float[]) {
            return ((float[])o)[n];
        }
        return getLong(o, n);
    }
    
    public static int getInt(final Object o, final int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof int[]) {
            return ((int[])o)[n];
        }
        if (o instanceof char[]) {
            return ((char[])o)[n];
        }
        return getShort(o, n);
    }
    
    public static int getLength(final Object o) throws IllegalArgumentException {
        try {
            return ((Object[])o).length;
        }
        catch (ClassCastException ex) {
            if (o instanceof int[]) {
                return ((int[])o).length;
            }
            if (o instanceof boolean[]) {
                return ((boolean[])o).length;
            }
            if (o instanceof float[]) {
                return ((float[])o).length;
            }
            if (o instanceof char[]) {
                return ((char[])o).length;
            }
            if (o instanceof double[]) {
                return ((double[])o).length;
            }
            if (o instanceof long[]) {
                return ((long[])o).length;
            }
            if (o instanceof short[]) {
                return ((short[])o).length;
            }
            if (o instanceof byte[]) {
                return ((byte[])o).length;
            }
            throw new IllegalArgumentException("Specified argument is not an array");
        }
    }
    
    public static long getLong(final Object o, final int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof long[]) {
            return ((long[])o)[n];
        }
        return getInt(o, n);
    }
    
    public static short getShort(final Object o, final int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof short[]) {
            return ((short[])o)[n];
        }
        return getByte(o, n);
    }
    
    public static Object newInstance(final Class<?> clazz, final int n) throws NegativeArraySizeException {
        return newInstance(clazz, new int[] { n });
    }
    
    public static Object newInstance(final Class<?> clazz, final int[] array) throws IllegalArgumentException, NegativeArraySizeException {
        if (clazz == null) {
            throw new NullPointerException();
        }
        if (clazz == Void.TYPE || array.length == 0) {
            throw new IllegalArgumentException("Can not create new array instance for the specified arguments");
        }
        return VMReflection.newArrayInstance(clazz, array);
    }
    
    public static void set(final Object o, final int n, final Object o2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o == null) {
            throw new NullPointerException();
        }
        try {
            ((Object[])o)[n] = o2;
            return;
        }
        catch (ClassCastException ex2) {
            if (o2 instanceof Number) {
                if (o2 instanceof Integer) {
                    setInt(o, n, (int)o2);
                    return;
                }
                if (o2 instanceof Float) {
                    setFloat(o, n, (float)o2);
                    return;
                }
                if (o2 instanceof Double) {
                    setDouble(o, n, (double)o2);
                    return;
                }
                if (o2 instanceof Long) {
                    setLong(o, n, (long)o2);
                    return;
                }
                if (o2 instanceof Short) {
                    setShort(o, n, (short)o2);
                    return;
                }
                if (o2 instanceof Byte) {
                    setByte(o, n, (byte)o2);
                    return;
                }
            }
            else {
                if (o2 instanceof Boolean) {
                    setBoolean(o, n, (boolean)o2);
                    return;
                }
                if (o2 instanceof Character) {
                    setChar(o, n, (char)o2);
                    return;
                }
            }
        }
        catch (ArrayStoreException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        throw new IllegalArgumentException("Can not assign the specified value to the specified array component");
    }
    
    public static void setBoolean(final Object o, final int n, final boolean b) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        try {
            ((boolean[])o)[n] = b;
        }
        catch (ClassCastException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
    
    public static void setByte(final Object o, final int n, final byte b) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof byte[]) {
            ((byte[])o)[n] = b;
            return;
        }
        setShort(o, n, b);
    }
    
    public static void setChar(final Object o, final int n, final char c) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof char[]) {
            ((char[])o)[n] = c;
            return;
        }
        setInt(o, n, c);
    }
    
    public static void setDouble(final Object o, final int n, final double n2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        try {
            ((double[])o)[n] = n2;
        }
        catch (ClassCastException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
    
    public static void setFloat(final Object o, final int n, final float n2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof float[]) {
            ((float[])o)[n] = n2;
            return;
        }
        setDouble(o, n, n2);
    }
    
    public static void setInt(final Object o, final int n, final int n2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof int[]) {
            ((int[])o)[n] = n2;
            return;
        }
        setLong(o, n, n2);
    }
    
    public static void setLong(final Object o, final int n, final long n2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof long[]) {
            ((long[])o)[n] = n2;
            return;
        }
        setFloat(o, n, n2);
    }
    
    public static void setShort(final Object o, final int n, final short n2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (o instanceof short[]) {
            ((short[])o)[n] = n2;
            return;
        }
        setInt(o, n, n2);
    }
}
