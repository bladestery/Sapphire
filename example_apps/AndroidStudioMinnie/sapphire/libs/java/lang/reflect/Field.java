// 
// Decompiled by Procyon v0.5.30
// 

package java.lang.reflect;

import org.apache.harmony.vm.VMStack;
import org.apache.harmony.lang.reflect.parser.Parser;
import org.apache.harmony.vm.VMGenericsAndAnnotations;
import java.lang.annotation.Annotation;

public final class Field extends AccessibleObject implements Member
{
    private final FieldData data;
    
    public Annotation[] getDeclaredAnnotations() {
        final Annotation[] annotations = this.data.getAnnotations();
        final Annotation[] array = new Annotation[annotations.length];
        System.arraycopy(annotations, 0, array, 0, annotations.length);
        return array;
    }
    
    public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException();
        }
        Annotation[] annotations;
        for (int length = (annotations = this.data.getAnnotations()).length, i = 0; i < length; ++i) {
            final Annotation annotation = annotations[i];
            if (annotation.annotationType() == clazz) {
                return (T)annotation;
            }
        }
        return null;
    }
    
    public Type getGenericType() throws GenericSignatureFormatError, TypeNotPresentException, MalformedParameterizedTypeException {
        if (this.data.genericType == null) {
            this.data.genericType = Parser.parseFieldGenericType(this, VMGenericsAndAnnotations.getSignature(this.data.vm_member_id));
        }
        return this.data.genericType;
    }
    
    public String toGenericString() {
        final StringBuilder sb = new StringBuilder(80);
        final int modifiers = this.getModifiers();
        if (modifiers != 0) {
            sb.append(Modifier.toString(modifiers)).append(' ');
        }
        this.appendGenericType(sb, this.getGenericType());
        sb.append(' ');
        sb.append(this.getDeclaringClass().getName()).append('.').append(this.getName());
        return sb.toString();
    }
    
    public boolean isSynthetic() {
        return (this.getModifiers() & 0x1000) != 0x0;
    }
    
    public boolean isEnumConstant() {
        return (this.getModifiers() & 0x4000) != 0x0;
    }
    
    Field(final Field field) {
        this.data = field.data;
        this.isAccessible = field.isAccessible;
    }
    
    Field(final long n, final Class clazz, final String s, final String s2, final int n2) {
        this.data = new FieldData(n, clazz, s, s2, n2);
    }
    
    long getId() {
        return this.data.vm_member_id;
    }
    
    public boolean equals(final Object o) {
        if (o instanceof Field) {
            final Field field = (Field)o;
            if (this.data.vm_member_id == field.data.vm_member_id) {
                assert this.getDeclaringClass() == field.getDeclaringClass() && this.getName() == field.getName();
                return true;
            }
        }
        return false;
    }
    
    public Object get(Object checkGet) throws IllegalArgumentException, IllegalAccessException {
        checkGet = this.checkGet(VMStack.getCallerClass(0), checkGet);
        return VMField.getObject(checkGet, this.data.vm_member_id);
    }
    
    public boolean getBoolean(Object checkGet) throws IllegalArgumentException, IllegalAccessException {
        checkGet = this.checkGet(VMStack.getCallerClass(0), checkGet);
        return VMField.getBoolean(checkGet, this.data.vm_member_id);
    }
    
    public byte getByte(Object checkGet) throws IllegalArgumentException, IllegalAccessException {
        checkGet = this.checkGet(VMStack.getCallerClass(0), checkGet);
        return VMField.getByte(checkGet, this.data.vm_member_id);
    }
    
    public char getChar(Object checkGet) throws IllegalArgumentException, IllegalAccessException {
        checkGet = this.checkGet(VMStack.getCallerClass(0), checkGet);
        return VMField.getChar(checkGet, this.data.vm_member_id);
    }
    
    public Class<?> getDeclaringClass() {
        return (Class<?>)this.data.declaringClass;
    }
    
    public double getDouble(Object checkGet) throws IllegalArgumentException, IllegalAccessException {
        checkGet = this.checkGet(VMStack.getCallerClass(0), checkGet);
        return VMField.getDouble(checkGet, this.data.vm_member_id);
    }
    
    public float getFloat(Object checkGet) throws IllegalArgumentException, IllegalAccessException {
        checkGet = this.checkGet(VMStack.getCallerClass(0), checkGet);
        return VMField.getFloat(checkGet, this.data.vm_member_id);
    }
    
    public int getInt(Object checkGet) throws IllegalArgumentException, IllegalAccessException {
        checkGet = this.checkGet(VMStack.getCallerClass(0), checkGet);
        return VMField.getInt(checkGet, this.data.vm_member_id);
    }
    
    public long getLong(Object checkGet) throws IllegalArgumentException, IllegalAccessException {
        checkGet = this.checkGet(VMStack.getCallerClass(0), checkGet);
        return VMField.getLong(checkGet, this.data.vm_member_id);
    }
    
    public int getModifiers() {
        return this.data.modifiers;
    }
    
    public String getName() {
        return this.data.name;
    }
    
    public short getShort(Object checkGet) throws IllegalArgumentException, IllegalAccessException {
        checkGet = this.checkGet(VMStack.getCallerClass(0), checkGet);
        return VMField.getShort(checkGet, this.data.vm_member_id);
    }
    
    public Class<?> getType() {
        return this.data.getType();
    }
    
    public int hashCode() {
        return this.getDeclaringClass().getName().hashCode() ^ this.getName().hashCode();
    }
    
    public void set(Object checkSet, final Object o) throws IllegalArgumentException, IllegalAccessException {
        checkSet = this.checkSet(VMStack.getCallerClass(0), checkSet);
        VMField.setObject(checkSet, this.data.vm_member_id, o);
    }
    
    public void setBoolean(Object checkSet, final boolean b) throws IllegalArgumentException, IllegalAccessException {
        checkSet = this.checkSet(VMStack.getCallerClass(0), checkSet);
        VMField.setBoolean(checkSet, this.data.vm_member_id, b);
    }
    
    public void setByte(Object checkSet, final byte b) throws IllegalArgumentException, IllegalAccessException {
        checkSet = this.checkSet(VMStack.getCallerClass(0), checkSet);
        VMField.setByte(checkSet, this.data.vm_member_id, b);
    }
    
    public void setChar(Object checkSet, final char c) throws IllegalArgumentException, IllegalAccessException {
        checkSet = this.checkSet(VMStack.getCallerClass(0), checkSet);
        VMField.setChar(checkSet, this.data.vm_member_id, c);
    }
    
    public void setDouble(Object checkSet, final double n) throws IllegalArgumentException, IllegalAccessException {
        checkSet = this.checkSet(VMStack.getCallerClass(0), checkSet);
        VMField.setDouble(checkSet, this.data.vm_member_id, n);
    }
    
    public void setFloat(Object checkSet, final float n) throws IllegalArgumentException, IllegalAccessException {
        checkSet = this.checkSet(VMStack.getCallerClass(0), checkSet);
        VMField.setFloat(checkSet, this.data.vm_member_id, n);
    }
    
    public void setInt(Object checkSet, final int n) throws IllegalArgumentException, IllegalAccessException {
        checkSet = this.checkSet(VMStack.getCallerClass(0), checkSet);
        VMField.setInt(checkSet, this.data.vm_member_id, n);
    }
    
    public void setLong(Object checkSet, final long n) throws IllegalArgumentException, IllegalAccessException {
        checkSet = this.checkSet(VMStack.getCallerClass(0), checkSet);
        VMField.setLong(checkSet, this.data.vm_member_id, n);
    }
    
    public void setShort(Object checkSet, final short n) throws IllegalArgumentException, IllegalAccessException {
        checkSet = this.checkSet(VMStack.getCallerClass(0), checkSet);
        VMField.setShort(checkSet, this.data.vm_member_id, n);
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder(80);
        final int modifiers = this.getModifiers();
        if (modifiers != 0) {
            sb.append(Modifier.toString(modifiers)).append(' ');
        }
        this.appendArrayType(sb, (Class)this.getType());
        sb.append(' ');
        sb.append(this.getDeclaringClass().getName()).append('.').append(this.getName());
        return sb.toString();
    }
    
    private Object checkGet(final Class clazz, Object checkObject) throws IllegalArgumentException, IllegalAccessException {
        checkObject = this.checkObject((Class)this.getDeclaringClass(), this.getModifiers(), checkObject);
        if (!this.isAccessible) {
            Field.reflectExporter.checkMemberAccess(clazz, this.getDeclaringClass(), (checkObject == null) ? this.getDeclaringClass() : checkObject.getClass(), this.getModifiers());
        }
        return checkObject;
    }
    
    private Object checkSet(final Class clazz, Object checkObject) throws IllegalArgumentException, IllegalAccessException {
        checkObject = this.checkObject((Class)this.getDeclaringClass(), this.getModifiers(), checkObject);
        if (Modifier.isFinal(this.getModifiers()) && (!this.isAccessible || checkObject == null)) {
            throw new IllegalAccessException("Can not assign new value to the field with final modifier");
        }
        if (!this.isAccessible) {
            Field.reflectExporter.checkMemberAccess(clazz, this.getDeclaringClass(), (checkObject == null) ? this.getDeclaringClass() : checkObject.getClass(), this.getModifiers());
        }
        return checkObject;
    }
    
    String getSignature() {
        return this.data.descriptor;
    }
    
    private static class FieldData
    {
        final String name;
        final Class declaringClass;
        final int modifiers;
        private Class<?> type;
        private Annotation[] declaredAnnotations;
        Type genericType;
        final String descriptor;
        final long vm_member_id;
        
        FieldData(final long vm_member_id, final Class declaringClass, final String name, final String descriptor, final int modifiers) {
            this.vm_member_id = vm_member_id;
            this.declaringClass = declaringClass;
            this.name = name;
            this.modifiers = modifiers;
            this.descriptor = descriptor;
        }
        
        Annotation[] getAnnotations() {
            if (this.declaredAnnotations == null) {
                this.declaredAnnotations = VMGenericsAndAnnotations.getDeclaredAnnotations(this.vm_member_id);
            }
            return this.declaredAnnotations;
        }
        
        Class<?> getType() {
            if (this.type == null) {
                this.type = VMReflection.getFieldType(this.vm_member_id);
            }
            return this.type;
        }
    }
}
