/*
 * Stub for class boofcv.factory.feature.detect.extract.FactoryFeatureExtractor
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.factory.feature.detect.extract.stubs;


public final class FactoryFeatureExtractor_Stub extends boofcv.factory.feature.detect.extract.FactoryFeatureExtractor implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public FactoryFeatureExtractor_Stub () {
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



    // Implementation of nonmaxLimiter(ConfigExtract, int)
    public boofcv.abst.feature.detect.extract.NonMaxLimiter nonmaxLimiter(boofcv.abst.feature.detect.extract.ConfigExtract $param_ConfigExtract_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.nonmaxLimiter( $param_ConfigExtract_1,  $param_int_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.abst.feature.detect.extract.NonMaxLimiter boofcv.factory.feature.detect.extract.FactoryFeatureExtractor.nonmaxLimiter(boofcv.abst.feature.detect.extract.ConfigExtract,int)";
                $__params.add($param_ConfigExtract_1);
                $__params.add($param_int_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.extract.NonMaxLimiter) $__result);
    }

    // Implementation of nonmaxCandidate(ConfigExtract)
    public boofcv.abst.feature.detect.extract.NonMaxSuppression nonmaxCandidate(boofcv.abst.feature.detect.extract.ConfigExtract $param_ConfigExtract_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.nonmaxCandidate( $param_ConfigExtract_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.abst.feature.detect.extract.NonMaxSuppression boofcv.factory.feature.detect.extract.FactoryFeatureExtractor.nonmaxCandidate(boofcv.abst.feature.detect.extract.ConfigExtract)";
                $__params.add($param_ConfigExtract_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.extract.NonMaxSuppression) $__result);
    }

    // Implementation of nonmax(ConfigExtract)
    public boofcv.abst.feature.detect.extract.NonMaxSuppression nonmax(boofcv.abst.feature.detect.extract.ConfigExtract $param_ConfigExtract_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.nonmax( $param_ConfigExtract_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.abst.feature.detect.extract.NonMaxSuppression boofcv.factory.feature.detect.extract.FactoryFeatureExtractor.nonmax(boofcv.abst.feature.detect.extract.ConfigExtract)";
                $__params.add($param_ConfigExtract_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.extract.NonMaxSuppression) $__result);
    }

    // Implementation of general(GeneralFeatureIntensity, NonMaxSuppression, int)
    public boofcv.alg.feature.detect.interest.GeneralFeatureDetector general(boofcv.abst.feature.detect.intensity.GeneralFeatureIntensity $param_GeneralFeatureIntensity_1, boofcv.abst.feature.detect.extract.NonMaxSuppression $param_NonMaxSuppression_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.general( $param_GeneralFeatureIntensity_1,  $param_NonMaxSuppression_2,  $param_int_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <I,D> boofcv.alg.feature.detect.interest.GeneralFeatureDetector<I, D> boofcv.factory.feature.detect.extract.FactoryFeatureExtractor.general(boofcv.abst.feature.detect.intensity.GeneralFeatureIntensity<I, D>,boofcv.abst.feature.detect.extract.NonMaxSuppression,int)";
                $__params.add($param_GeneralFeatureIntensity_1);
                $__params.add($param_NonMaxSuppression_2);
                $__params.add($param_int_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.feature.detect.interest.GeneralFeatureDetector) $__result);
    }
}