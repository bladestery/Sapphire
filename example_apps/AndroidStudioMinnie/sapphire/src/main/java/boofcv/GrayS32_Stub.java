/*
 * Stub for class boofcv.struct.image.GrayS32
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.struct.image.stubs;


public final class GrayS32_Stub extends boofcv.struct.image.GrayS32 implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public GrayS32_Stub (int $param_int_1, int $param_int_2) {
        super($param_int_1, $param_int_2);
    }

    public GrayS32_Stub () {
        super();
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



    // Implementation of unsafe_set(int, int, int)
    public void unsafe_set(int $param_int_1, int $param_int_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.unsafe_set( $param_int_1,  $param_int_2,  $param_int_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.struct.image.GrayS32.unsafe_set(int,int,int)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of unsafe_get(int, int)
    public int unsafe_get(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.unsafe_get( $param_int_1,  $param_int_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int boofcv.struct.image.GrayS32.unsafe_get(int,int)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }

    // Implementation of setData(int[])
    public void setData(int[] $param_arrayOf_int_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setData( $param_arrayOf_int_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.struct.image.GrayS32.setData(int[])";
                $__params.add($param_arrayOf_int_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of set(int, int, int)
    public void set(int $param_int_1, int $param_int_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.set( $param_int_1,  $param_int_2,  $param_int_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.struct.image.GrayS32.set(int,int,int)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
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
                String $__method = "public boofcv.struct.image.ImageDataType boofcv.struct.image.GrayS32.getDataType()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageDataType) $__result);
    }

    // Implementation of getData()
    public int[] getData() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getData();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int[] boofcv.struct.image.GrayS32.getData()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((int[]) $__result);
    }

    // Implementation of createNew(int, int)
    public boofcv.struct.image.GrayS32 createNew(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.createNew( $param_int_1,  $param_int_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayS32 boofcv.struct.image.GrayS32.createNew(int,int)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayS32) $__result);
    }
}
