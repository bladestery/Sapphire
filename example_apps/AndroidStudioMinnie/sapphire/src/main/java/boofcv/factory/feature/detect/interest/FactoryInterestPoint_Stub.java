/*
 * Stub for class boofcv.factory.feature.detect.interest.FactoryInterestPoint
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.factory.feature.detect.interest.stubs;


public final class FactoryInterestPoint_Stub extends boofcv.factory.feature.detect.interest.FactoryInterestPoint implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public FactoryInterestPoint_Stub () {
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



    // Implementation of wrapPoint(GeneralFeatureDetector, double, Class, Class, FactoryDerivative, GeneralizedImageOps, FactoryImageBorder)
    public boofcv.abst.feature.detect.interest.InterestPointDetector wrapPoint(boofcv.alg.feature.detect.interest.GeneralFeatureDetector $param_GeneralFeatureDetector_1, double $param_double_2, java.lang.Class $param_Class_3, java.lang.Class $param_Class_4, boofcv.factory.filter.derivative.FactoryDerivative $param_FactoryDerivative_5, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_6, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_7) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.wrapPoint( $param_GeneralFeatureDetector_1,  $param_double_2,  $param_Class_3,  $param_Class_4,  $param_FactoryDerivative_5,  $param_GeneralizedImageOps_6,  $param_FactoryImageBorder_7);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T,D> boofcv.abst.feature.detect.interest.InterestPointDetector<T> boofcv.factory.feature.detect.interest.FactoryInterestPoint.wrapPoint(boofcv.alg.feature.detect.interest.GeneralFeatureDetector<T, D>,double,java.lang.Class<T>,java.lang.Class<D>,boofcv.factory.filter.derivative.FactoryDerivative,boofcv.core.image.GeneralizedImageOps,boofcv.core.image.border.FactoryImageBorder)";
                $__params.add($param_GeneralFeatureDetector_1);
                $__params.add($param_double_2);
                $__params.add($param_Class_3);
                $__params.add($param_Class_4);
                $__params.add($param_FactoryDerivative_5);
                $__params.add($param_GeneralizedImageOps_6);
                $__params.add($param_FactoryImageBorder_7);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.interest.InterestPointDetector) $__result);
    }

    // Implementation of wrapDetector(FeaturePyramid, double[], boolean, Class)
    public boofcv.abst.feature.detect.interest.InterestPointDetector wrapDetector(boofcv.alg.feature.detect.interest.FeaturePyramid $param_FeaturePyramid_1, double[] $param_arrayOf_double_2, boolean $param_boolean_3, java.lang.Class $param_Class_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.wrapDetector( $param_FeaturePyramid_1,  $param_arrayOf_double_2,  $param_boolean_3,  $param_Class_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T,D> boofcv.abst.feature.detect.interest.InterestPointDetector<T> boofcv.factory.feature.detect.interest.FactoryInterestPoint.wrapDetector(boofcv.alg.feature.detect.interest.FeaturePyramid<T, D>,double[],boolean,java.lang.Class<T>)";
                $__params.add($param_FeaturePyramid_1);
                $__params.add($param_arrayOf_double_2);
                $__params.add($param_boolean_3);
                $__params.add($param_Class_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.interest.InterestPointDetector) $__result);
    }

    // Implementation of wrapDetector(FeatureLaplacePyramid, double[], boolean, Class)
    public boofcv.abst.feature.detect.interest.InterestPointDetector wrapDetector(boofcv.alg.feature.detect.interest.FeatureLaplacePyramid $param_FeatureLaplacePyramid_1, double[] $param_arrayOf_double_2, boolean $param_boolean_3, java.lang.Class $param_Class_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.wrapDetector( $param_FeatureLaplacePyramid_1,  $param_arrayOf_double_2,  $param_boolean_3,  $param_Class_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T,D> boofcv.abst.feature.detect.interest.InterestPointDetector<T> boofcv.factory.feature.detect.interest.FactoryInterestPoint.wrapDetector(boofcv.alg.feature.detect.interest.FeatureLaplacePyramid<T, D>,double[],boolean,java.lang.Class<T>)";
                $__params.add($param_FeatureLaplacePyramid_1);
                $__params.add($param_arrayOf_double_2);
                $__params.add($param_boolean_3);
                $__params.add($param_Class_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.interest.InterestPointDetector) $__result);
    }

    // Implementation of sift(ConfigSiftScaleSpace, ConfigSiftDetector, Class, FactoryFeatureExtractor, FactoryImageBorder, FactoryKernelGaussian, GeneralizedImageOps)
    public boofcv.abst.feature.detect.interest.InterestPointDetector sift(boofcv.abst.feature.describe.ConfigSiftScaleSpace $param_ConfigSiftScaleSpace_1, boofcv.abst.feature.detect.interest.ConfigSiftDetector $param_ConfigSiftDetector_2, java.lang.Class $param_Class_3, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_4, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_5, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_6, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_7) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.sift( $param_ConfigSiftScaleSpace_1,  $param_ConfigSiftDetector_2,  $param_Class_3,  $param_FactoryFeatureExtractor_4,  $param_FactoryImageBorder_5,  $param_FactoryKernelGaussian_6,  $param_GeneralizedImageOps_7);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.abst.feature.detect.interest.InterestPointDetector<T> boofcv.factory.feature.detect.interest.FactoryInterestPoint.sift(boofcv.abst.feature.describe.ConfigSiftScaleSpace,boofcv.abst.feature.detect.interest.ConfigSiftDetector,java.lang.Class<T>,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor,boofcv.core.image.border.FactoryImageBorder,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_ConfigSiftScaleSpace_1);
                $__params.add($param_ConfigSiftDetector_2);
                $__params.add($param_Class_3);
                $__params.add($param_FactoryFeatureExtractor_4);
                $__params.add($param_FactoryImageBorder_5);
                $__params.add($param_FactoryKernelGaussian_6);
                $__params.add($param_GeneralizedImageOps_7);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.interest.InterestPointDetector) $__result);
    }

    // Implementation of fastHessian(ConfigFastHessian, FactoryInterestPointAlgs, FactoryFeatureExtractor)
    public boofcv.abst.feature.detect.interest.InterestPointDetector fastHessian(boofcv.abst.feature.detect.interest.ConfigFastHessian $param_ConfigFastHessian_1, boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs $param_FactoryInterestPointAlgs_2, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.fastHessian( $param_ConfigFastHessian_1,  $param_FactoryInterestPointAlgs_2,  $param_FactoryFeatureExtractor_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.abst.feature.detect.interest.InterestPointDetector<T> boofcv.factory.feature.detect.interest.FactoryInterestPoint.fastHessian(boofcv.abst.feature.detect.interest.ConfigFastHessian,boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor)";
                $__params.add($param_ConfigFastHessian_1);
                $__params.add($param_FactoryInterestPointAlgs_2);
                $__params.add($param_FactoryFeatureExtractor_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.interest.InterestPointDetector) $__result);
    }
}
