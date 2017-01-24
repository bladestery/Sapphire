// 
// Decompiled by Procyon v0.5.30
// 

package compiler;

import org.apache.harmony.rmi.internal.nls.Messages;
import java.io.File;
import org.apache.harmony.rmi.common.RMIUtil;

public final class RmicUtil implements RmicConstants
{
    public static String getParameterName(Class componentType, final int n) {
        final StringBuilder sb = new StringBuilder("$param_");
        while (componentType.isArray()) {
            sb.append("arrayOf_");
            componentType = componentType.getComponentType();
        }
        sb.append(String.valueOf(RMIUtil.getShortName(componentType)) + '_' + n);
        return sb.toString();
    }

    public static String getObjectParameterString(final Class clazz, final String s) {
        return clazz.isPrimitive() ? ("new " + RMIUtil.getWrappingClass(clazz).getName() + '(' + s + ')') : s;
    }

    public static String firstLetterToUpperCase(final String s) {
        final int length = s.length();
        if (length < 1) {
            return s;
        }
        final char[] array = new char[length];
        s.getChars(0, length, array, 0);
        array[0] = Character.toUpperCase(array[0]);
        return String.copyValueOf(array);
    }

    public static String getHandlingType(final Class clazz) {
        return clazz.isPrimitive() ? firstLetterToUpperCase(clazz.getName()) : "Object";
    }

    public static String getReadObjectString(final Class clazz, final String s) {
        return String.valueOf((!clazz.isPrimitive() && clazz != Object.class) ? new StringBuilder(String.valueOf('(')).append(RMIUtil.getCanonicalName(clazz)).append(") ").toString() : "") + s + ".read" + getHandlingType(clazz) + "()";
    }

    public static String getWriteObjectString(final Class clazz, final String s, final String s2) {
        return String.valueOf(s2) + ".write" + getHandlingType(clazz) + '(' + s + ')';
    }

    public static String getReturnObjectString(final Class clazz, final String s) {
        if (clazz == Object.class) {
            return s;
        }
        final StringBuilder sb = new StringBuilder("((");
        sb.append(RMIUtil.getCanonicalName(RMIUtil.getWrappingClass(clazz)));
        sb.append(") " + s + ')');
        if (clazz.isPrimitive()) {
            sb.append(String.valueOf('.') + clazz.getName() + "Value()");
        }
        return sb.toString();
    }

    public static File getPackageDir(final String s, final String s2) throws RMICompilerException {
        final File file = new File(s, (s2 != null) ? s2 : "");
        if (file.exists()) {
            if (file.isDirectory()) {
                return file;
            }
        }
        else if (file.mkdirs()) {
            return file;
        }
        throw new RMICompilerException(Messages.getString("rmi.4E", file));
    }

    public static File getPackageFile(final File file, final String s) throws RMICompilerException {
        final File file2 = new File(file, s);
        if (file2.exists()) {
            if (file2.isFile()) {
                if (file2.canWrite()) {
                    return file2;
                }
            }
        }
        else if (file.canWrite()) {
            return file2;
        }
        throw new RMICompilerException(Messages.getString("rmi.4F", file2));
    }
}
