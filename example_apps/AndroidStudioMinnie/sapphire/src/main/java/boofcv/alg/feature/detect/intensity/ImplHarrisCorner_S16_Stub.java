/*
 * Stub for class boofcv.alg.feature.detect.intensity.impl.ImplHarrisCorner_S16
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.alg.feature.detect.intensity.impl.stubs;


public final class ImplHarrisCorner_S16_Stub extends boofcv.alg.feature.detect.intensity.impl.ImplHarrisCorner_S16 implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public ImplHarrisCorner_S16_Stub (int $param_int_1, float $param_float_2, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_3) {
        super($param_int_1, $param_float_2, $param_GeneralizedImageOps_3);
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



    // Implementation of setKappa(float)
    public void setKappa(float $param_float_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setKappa( $param_float_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.feature.detect.intensity.impl.ImplHarrisCorner_S16.setKappa(float)";
                $__params.add($param_float_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of getKappa()
    public float getKappa() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getKappa();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public float boofcv.alg.feature.detect.intensity.impl.ImplHarrisCorner_S16.getKappa()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Float) $__result).floatValue();
    }
}