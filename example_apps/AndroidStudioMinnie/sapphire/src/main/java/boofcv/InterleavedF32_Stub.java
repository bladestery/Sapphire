/*
 * Stub for class boofcv.struct.image.InterleavedF32
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.struct.image.stubs;


public final class InterleavedF32_Stub extends boofcv.struct.image.InterleavedF32 implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public InterleavedF32_Stub () {
        super();
    }

    public InterleavedF32_Stub (int $param_int_1, int $param_int_2, int $param_int_3) {
        super($param_int_1, $param_int_2, $param_int_3);
    }


    public void $__initialize(sapphire.policy.SapphirePolicy.SapphireClientPolicy client) {
        $__client = client;
    }

    public void $__initialize(boolean directInvocation) {
        $__directInvocation = directInvocation;
    }

    public Object $__clone() throws CloneNotSupportedException {
        return super.clone();
    }



    // Implementation of unsafe_set(int, int, float[])
    public void unsafe_set(int $param_int_1, int $param_int_2, float[] $param_arrayOf_float_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.unsafe_set( $param_int_1,  $param_int_2,  $param_arrayOf_float_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.struct.image.InterleavedF32.unsafe_set(int,int,float[])";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_arrayOf_float_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of unsafe_get(int, int, float[])
    public void unsafe_get(int $param_int_1, int $param_int_2, float[] $param_arrayOf_float_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.unsafe_get( $param_int_1,  $param_int_2,  $param_arrayOf_float_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.struct.image.InterleavedF32.unsafe_get(int,int,float[])";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_arrayOf_float_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of setBand(int, int, int, float)
    public void setBand(int $param_int_1, int $param_int_2, int $param_int_3, float $param_float_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setBand( $param_int_1,  $param_int_2,  $param_int_3,  $param_float_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.struct.image.InterleavedF32.setBand(int,int,int,float)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_float_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of set(int, int, float[])
    public void set(int $param_int_1, int $param_int_2, float[] $param_arrayOf_float_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.set( $param_int_1,  $param_int_2,  $param_arrayOf_float_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.struct.image.InterleavedF32.set(int,int,float...)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_arrayOf_float_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of print(String)
    public void print(java.lang.String $param_String_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.print( $param_String_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.struct.image.InterleavedF32.print(java.lang.String)";
                $__params.add($param_String_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of getDataType()
    public boofcv.struct.image.ImageDataType getDataType() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getDataType();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.ImageDataType boofcv.struct.image.InterleavedF32.getDataType()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageDataType) $__result);
    }

    // Implementation of getBand(int, int, int)
    public float getBand(int $param_int_1, int $param_int_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getBand( $param_int_1,  $param_int_2,  $param_int_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public float boofcv.struct.image.InterleavedF32.getBand(int,int,int)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Float) $__result).floatValue();
    }

    // Implementation of get(int, int, float[])
    public float[] get(int $param_int_1, int $param_int_2, float[] $param_arrayOf_float_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.get( $param_int_1,  $param_int_2,  $param_arrayOf_float_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public float[] boofcv.struct.image.InterleavedF32.get(int,int,float[])";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_arrayOf_float_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((float[]) $__result);
    }

    // Implementation of createNew(int, int)
    public boofcv.struct.image.InterleavedF32 createNew(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.createNew( $param_int_1,  $param_int_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.InterleavedF32 boofcv.struct.image.InterleavedF32.createNew(int,int)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.InterleavedF32) $__result);
    }
}