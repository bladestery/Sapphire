/*
 * Stub for class boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.alg.filter.derivative.impl.stubs;


public final class GradientSobel_UnrolledOuter_Stub extends boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public GradientSobel_UnrolledOuter_Stub () {
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



    // Implementation of process_I8(GrayU8, GrayS16, GrayS16)
    public void process_I8(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayS16 $param_GrayS16_2, boofcv.struct.image.GrayS16 $param_GrayS16_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.process_I8( $param_GrayU8_1,  $param_GrayS16_2,  $param_GrayS16_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter.process_I8(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayS16,boofcv.struct.image.GrayS16)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayS16_2);
                $__params.add($param_GrayS16_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of process_F32_sub(GrayF32, GrayF32, GrayF32)
    public void process_F32_sub(boofcv.struct.image.GrayF32 $param_GrayF32_1, boofcv.struct.image.GrayF32 $param_GrayF32_2, boofcv.struct.image.GrayF32 $param_GrayF32_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.process_F32_sub( $param_GrayF32_1,  $param_GrayF32_2,  $param_GrayF32_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter.process_F32_sub(boofcv.struct.image.GrayF32,boofcv.struct.image.GrayF32,boofcv.struct.image.GrayF32)";
                $__params.add($param_GrayF32_1);
                $__params.add($param_GrayF32_2);
                $__params.add($param_GrayF32_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of process_F32(GrayF32, GrayF32, GrayF32)
    public void process_F32(boofcv.struct.image.GrayF32 $param_GrayF32_1, boofcv.struct.image.GrayF32 $param_GrayF32_2, boofcv.struct.image.GrayF32 $param_GrayF32_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.process_F32( $param_GrayF32_1,  $param_GrayF32_2,  $param_GrayF32_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter.process_F32(boofcv.struct.image.GrayF32,boofcv.struct.image.GrayF32,boofcv.struct.image.GrayF32)";
                $__params.add($param_GrayF32_1);
                $__params.add($param_GrayF32_2);
                $__params.add($param_GrayF32_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
