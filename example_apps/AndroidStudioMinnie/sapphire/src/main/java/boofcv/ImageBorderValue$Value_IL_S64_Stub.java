/*
 * Stub for class boofcv.core.image.border.ImageBorderValue.Value_IL_S64
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.core.image.border.stubs;


public final class ImageBorderValue$Value_IL_S64_Stub extends boofcv.core.image.border.ImageBorderValue.Value_IL_S64 implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public ImageBorderValue$Value_IL_S64_Stub (long $param_long_1) {
        super($param_long_1);
    }

    public ImageBorderValue$Value_IL_S64_Stub (boofcv.struct.image.InterleavedS64 $param_InterleavedS64_1, long $param_long_2) {
        super($param_InterleavedS64_1, $param_long_2);
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



    // Implementation of setOutside(int, int, long[])
    public void setOutside(int $param_int_1, int $param_int_2, long[] $param_arrayOf_long_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setOutside( $param_int_1,  $param_int_2,  $param_arrayOf_long_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.core.image.border.ImageBorderValue$Value_IL_S64.setOutside(int,int,long[])";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_arrayOf_long_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of getOutside(int, int, long[])
    public void getOutside(int $param_int_1, int $param_int_2, long[] $param_arrayOf_long_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.getOutside( $param_int_1,  $param_int_2,  $param_arrayOf_long_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.core.image.border.ImageBorderValue$Value_IL_S64.getOutside(int,int,long[])";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_arrayOf_long_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
