/*
 * Stub for class boofcv.core.image.border.ImageBorder1D_F64
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.core.image.border.stubs;


public final class ImageBorder1D_F64_Stub extends boofcv.core.image.border.ImageBorder1D_F64 implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public ImageBorder1D_F64_Stub (java.lang.Class $param_Class_1) {
        super($param_Class_1);
    }

    public ImageBorder1D_F64_Stub (boofcv.core.image.border.BorderIndex1D $param_BorderIndex1D_1, boofcv.core.image.border.BorderIndex1D $param_BorderIndex1D_2) {
        super($param_BorderIndex1D_1, $param_BorderIndex1D_2);
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



    // Implementation of setOutside(int, int, double)
    public void setOutside(int $param_int_1, int $param_int_2, double $param_double_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setOutside( $param_int_1,  $param_int_2,  $param_double_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.core.image.border.ImageBorder1D_F64.setOutside(int,int,double)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_double_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of setImage(GrayF64)
    public void setImage(boofcv.struct.image.GrayF64 $param_GrayF64_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setImage( $param_GrayF64_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.core.image.border.ImageBorder1D_F64.setImage(boofcv.struct.image.GrayF64)";
                $__params.add($param_GrayF64_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of getRowWrap()
    public boofcv.core.image.border.BorderIndex1D getRowWrap() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getRowWrap();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.core.image.border.BorderIndex1D boofcv.core.image.border.ImageBorder1D_F64.getRowWrap()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.core.image.border.BorderIndex1D) $__result);
    }

    // Implementation of getOutside(int, int)
    public double getOutside(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getOutside( $param_int_1,  $param_int_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public double boofcv.core.image.border.ImageBorder1D_F64.getOutside(int,int)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Double) $__result).doubleValue();
    }

    // Implementation of getColWrap()
    public boofcv.core.image.border.BorderIndex1D getColWrap() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getColWrap();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.core.image.border.BorderIndex1D boofcv.core.image.border.ImageBorder1D_F64.getColWrap()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.core.image.border.BorderIndex1D) $__result);
    }
}
