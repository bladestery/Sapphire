/*
 * Stub for class boofcv.alg.feature.detect.line.HoughTransformLineFootOfNorm
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.alg.feature.detect.line.stubs;


public final class HoughTransformLineFootOfNorm_Stub extends boofcv.alg.feature.detect.line.HoughTransformLineFootOfNorm implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public HoughTransformLineFootOfNorm_Stub (boofcv.abst.feature.detect.extract.NonMaxSuppression $param_NonMaxSuppression_1, int $param_int_2) {
        super($param_NonMaxSuppression_1, $param_int_2);
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



    // Implementation of transform(ImageGray, ImageGray, GrayU8, InputSanityCheck, ImageMiscOps)
    public void transform(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.ImageGray $param_ImageGray_2, boofcv.struct.image.GrayU8 $param_GrayU8_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.alg.misc.ImageMiscOps $param_ImageMiscOps_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.transform( $param_ImageGray_1,  $param_ImageGray_2,  $param_GrayU8_3,  $param_InputSanityCheck_4,  $param_ImageMiscOps_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <D> void boofcv.alg.feature.detect.line.HoughTransformLineFootOfNorm.transform(D,D,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.alg.misc.ImageMiscOps)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_ImageGray_2);
                $__params.add($param_GrayU8_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_ImageMiscOps_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of parameterize(int, int, float, float)
    public void parameterize(int $param_int_1, int $param_int_2, float $param_float_3, float $param_float_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.parameterize( $param_int_1,  $param_int_2,  $param_float_3,  $param_float_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.feature.detect.line.HoughTransformLineFootOfNorm.parameterize(int,int,float,float)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_float_3);
                $__params.add($param_float_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of getTransform()
    public boofcv.struct.image.GrayF32 getTransform() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getTransform();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayF32 boofcv.alg.feature.detect.line.HoughTransformLineFootOfNorm.getTransform()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayF32) $__result);
    }

    // Implementation of getFoundIntensity()
    public float[] getFoundIntensity() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getFoundIntensity();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public float[] boofcv.alg.feature.detect.line.HoughTransformLineFootOfNorm.getFoundIntensity()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((float[]) $__result);
    }

    // Implementation of extractLines()
    public org.ddogleg.struct.FastQueue extractLines() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.extractLines();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public org.ddogleg.struct.FastQueue<georegression.struct.line.LineParametric2D_F32> boofcv.alg.feature.detect.line.HoughTransformLineFootOfNorm.extractLines()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((org.ddogleg.struct.FastQueue) $__result);
    }
}