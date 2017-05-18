/*
 * Stub for class boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.factory.feature.detect.interest.stubs;


public final class FactoryInterestPointAlgs_Stub extends boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public FactoryInterestPointAlgs_Stub () {
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



    // Implementation of sift(ConfigSiftScaleSpace, ConfigSiftDetector, FactoryFeatureExtractor, FactoryImageBorder, FactoryKernelGaussian, GeneralizedImageOps)
    public boofcv.alg.feature.detect.interest.SiftDetector sift(boofcv.abst.feature.describe.ConfigSiftScaleSpace $param_ConfigSiftScaleSpace_1, boofcv.abst.feature.detect.interest.ConfigSiftDetector $param_ConfigSiftDetector_2, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_3, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_4, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_5, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.sift( $param_ConfigSiftScaleSpace_1,  $param_ConfigSiftDetector_2,  $param_FactoryFeatureExtractor_3,  $param_FactoryImageBorder_4,  $param_FactoryKernelGaussian_5,  $param_GeneralizedImageOps_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.alg.feature.detect.interest.SiftDetector boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs.sift(boofcv.abst.feature.describe.ConfigSiftScaleSpace,boofcv.abst.feature.detect.interest.ConfigSiftDetector,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor,boofcv.core.image.border.FactoryImageBorder,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_ConfigSiftScaleSpace_1);
                $__params.add($param_ConfigSiftDetector_2);
                $__params.add($param_FactoryFeatureExtractor_3);
                $__params.add($param_FactoryImageBorder_4);
                $__params.add($param_FactoryKernelGaussian_5);
                $__params.add($param_GeneralizedImageOps_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.feature.detect.interest.SiftDetector) $__result);
    }

    // Implementation of hessianPyramid(int, float, int, Class, Class, FactoryFeatureExtractor, GeneralizedImageOps, FactoryImageBorder)
    public boofcv.alg.feature.detect.interest.FeaturePyramid hessianPyramid(int $param_int_1, float $param_float_2, int $param_int_3, java.lang.Class $param_Class_4, java.lang.Class $param_Class_5, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_6, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_7, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_8) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.hessianPyramid( $param_int_1,  $param_float_2,  $param_int_3,  $param_Class_4,  $param_Class_5,  $param_FactoryFeatureExtractor_6,  $param_GeneralizedImageOps_7,  $param_FactoryImageBorder_8);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T,D> boofcv.alg.feature.detect.interest.FeaturePyramid<T, D> boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs.hessianPyramid(int,float,int,java.lang.Class<T>,java.lang.Class<D>,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor,boofcv.core.image.GeneralizedImageOps,boofcv.core.image.border.FactoryImageBorder)";
                $__params.add($param_int_1);
                $__params.add($param_float_2);
                $__params.add($param_int_3);
                $__params.add($param_Class_4);
                $__params.add($param_Class_5);
                $__params.add($param_FactoryFeatureExtractor_6);
                $__params.add($param_GeneralizedImageOps_7);
                $__params.add($param_FactoryImageBorder_8);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.feature.detect.interest.FeaturePyramid) $__result);
    }

    // Implementation of hessianLaplace(int, float, int, Class, Class, FactoryFeatureExtractor, GeneralizedImageOps, FactoryImageBorder)
    public boofcv.alg.feature.detect.interest.FeatureLaplacePyramid hessianLaplace(int $param_int_1, float $param_float_2, int $param_int_3, java.lang.Class $param_Class_4, java.lang.Class $param_Class_5, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_6, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_7, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_8) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.hessianLaplace( $param_int_1,  $param_float_2,  $param_int_3,  $param_Class_4,  $param_Class_5,  $param_FactoryFeatureExtractor_6,  $param_GeneralizedImageOps_7,  $param_FactoryImageBorder_8);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T,D> boofcv.alg.feature.detect.interest.FeatureLaplacePyramid<T, D> boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs.hessianLaplace(int,float,int,java.lang.Class<T>,java.lang.Class<D>,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor,boofcv.core.image.GeneralizedImageOps,boofcv.core.image.border.FactoryImageBorder)";
                $__params.add($param_int_1);
                $__params.add($param_float_2);
                $__params.add($param_int_3);
                $__params.add($param_Class_4);
                $__params.add($param_Class_5);
                $__params.add($param_FactoryFeatureExtractor_6);
                $__params.add($param_GeneralizedImageOps_7);
                $__params.add($param_FactoryImageBorder_8);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.feature.detect.interest.FeatureLaplacePyramid) $__result);
    }

    // Implementation of harrisPyramid(int, float, int, Class, Class, FactoryIntensityPointAlg, GeneralizedImageOps, FactoryKernelGaussian, FactoryFeatureExtractor, FactoryImageBorder)
    public boofcv.alg.feature.detect.interest.FeaturePyramid harrisPyramid(int $param_int_1, float $param_float_2, int $param_int_3, java.lang.Class $param_Class_4, java.lang.Class $param_Class_5, boofcv.factory.feature.detect.intensity.FactoryIntensityPointAlg $param_FactoryIntensityPointAlg_6, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_7, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_8, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_9, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_10) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.harrisPyramid( $param_int_1,  $param_float_2,  $param_int_3,  $param_Class_4,  $param_Class_5,  $param_FactoryIntensityPointAlg_6,  $param_GeneralizedImageOps_7,  $param_FactoryKernelGaussian_8,  $param_FactoryFeatureExtractor_9,  $param_FactoryImageBorder_10);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T,D> boofcv.alg.feature.detect.interest.FeaturePyramid<T, D> boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs.harrisPyramid(int,float,int,java.lang.Class<T>,java.lang.Class<D>,boofcv.factory.feature.detect.intensity.FactoryIntensityPointAlg,boofcv.core.image.GeneralizedImageOps,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor,boofcv.core.image.border.FactoryImageBorder)";
                $__params.add($param_int_1);
                $__params.add($param_float_2);
                $__params.add($param_int_3);
                $__params.add($param_Class_4);
                $__params.add($param_Class_5);
                $__params.add($param_FactoryIntensityPointAlg_6);
                $__params.add($param_GeneralizedImageOps_7);
                $__params.add($param_FactoryKernelGaussian_8);
                $__params.add($param_FactoryFeatureExtractor_9);
                $__params.add($param_FactoryImageBorder_10);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.feature.detect.interest.FeaturePyramid) $__result);
    }

    // Implementation of harrisLaplace(int, float, int, Class, Class, FactoryIntensityPointAlg, GeneralizedImageOps, FactoryKernelGaussian, FactoryFeatureExtractor, FactoryImageBorder)
    public boofcv.alg.feature.detect.interest.FeatureLaplacePyramid harrisLaplace(int $param_int_1, float $param_float_2, int $param_int_3, java.lang.Class $param_Class_4, java.lang.Class $param_Class_5, boofcv.factory.feature.detect.intensity.FactoryIntensityPointAlg $param_FactoryIntensityPointAlg_6, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_7, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_8, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_9, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_10) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.harrisLaplace( $param_int_1,  $param_float_2,  $param_int_3,  $param_Class_4,  $param_Class_5,  $param_FactoryIntensityPointAlg_6,  $param_GeneralizedImageOps_7,  $param_FactoryKernelGaussian_8,  $param_FactoryFeatureExtractor_9,  $param_FactoryImageBorder_10);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T,D> boofcv.alg.feature.detect.interest.FeatureLaplacePyramid<T, D> boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs.harrisLaplace(int,float,int,java.lang.Class<T>,java.lang.Class<D>,boofcv.factory.feature.detect.intensity.FactoryIntensityPointAlg,boofcv.core.image.GeneralizedImageOps,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor,boofcv.core.image.border.FactoryImageBorder)";
                $__params.add($param_int_1);
                $__params.add($param_float_2);
                $__params.add($param_int_3);
                $__params.add($param_Class_4);
                $__params.add($param_Class_5);
                $__params.add($param_FactoryIntensityPointAlg_6);
                $__params.add($param_GeneralizedImageOps_7);
                $__params.add($param_FactoryKernelGaussian_8);
                $__params.add($param_FactoryFeatureExtractor_9);
                $__params.add($param_FactoryImageBorder_10);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.feature.detect.interest.FeatureLaplacePyramid) $__result);
    }

    // Implementation of fastHessian(ConfigFastHessian, FactoryFeatureExtractor)
    public boofcv.alg.feature.detect.interest.FastHessianFeatureDetector fastHessian(boofcv.abst.feature.detect.interest.ConfigFastHessian $param_ConfigFastHessian_1, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.fastHessian( $param_ConfigFastHessian_1,  $param_FactoryFeatureExtractor_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <II> boofcv.alg.feature.detect.interest.FastHessianFeatureDetector<II> boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs.fastHessian(boofcv.abst.feature.detect.interest.ConfigFastHessian,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor)";
                $__params.add($param_ConfigFastHessian_1);
                $__params.add($param_FactoryFeatureExtractor_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.feature.detect.interest.FastHessianFeatureDetector) $__result);
    }
}
