/*
 * Stub for class boofcv.abst.filter.binary.LocalSquareBinaryFilter
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.abst.filter.binary.stubs;


public final class LocalSquareBinaryFilter_Stub extends boofcv.abst.filter.binary.LocalSquareBinaryFilter implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public LocalSquareBinaryFilter_Stub (int $param_int_1, double $param_double_2, boolean $param_boolean_3, boofcv.struct.image.ImageType $param_ImageType_4) {
        super($param_int_1, $param_double_2, $param_boolean_3, $param_ImageType_4);
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


    // Implementation of process(ImageGray, GrayU8, GBlurImageOps, InputSanityCheck, GeneralizedImageOps, BlurImageOps, ConvolveImageMean, FactoryKernelGaussian, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder, ImplMedianHistogramInner, ImplMedianSortEdgeNaive, ImplMedianSortNaive, ImplConvolveMean, GThresholdImageOps, GImageStatistics, ImageStatistics, ThresholdImageOps)
    public void process(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.alg.filter.blur.GBlurImageOps $param_GBlurImageOps_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_5, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_6, boofcv.alg.filter.convolve.ConvolveImageMean $param_ConvolveImageMean_7, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_8, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_9, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_10, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_11, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_12, boofcv.alg.filter.blur.impl.ImplMedianHistogramInner $param_ImplMedianHistogramInner_13, boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive $param_ImplMedianSortEdgeNaive_14, boofcv.alg.filter.blur.impl.ImplMedianSortNaive $param_ImplMedianSortNaive_15, boofcv.alg.filter.convolve.noborder.ImplConvolveMean $param_ImplConvolveMean_16, boofcv.alg.filter.binary.GThresholdImageOps $param_GThresholdImageOps_17, boofcv.alg.misc.GImageStatistics $param_GImageStatistics_18, boofcv.alg.misc.ImageStatistics $param_ImageStatistics_19, boofcv.alg.filter.binary.ThresholdImageOps $param_ThresholdImageOps_20) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.process( $param_ImageGray_1,  $param_GrayU8_2,  $param_GBlurImageOps_3,  $param_InputSanityCheck_4,  $param_GeneralizedImageOps_5,  $param_BlurImageOps_6,  $param_ConvolveImageMean_7,  $param_FactoryKernelGaussian_8,  $param_ConvolveNormalized_9,  $param_ConvolveNormalizedNaive_10,  $param_ConvolveImageNoBorder_11,  $param_ConvolveNormalized_JustBorder_12,  $param_ImplMedianHistogramInner_13,  $param_ImplMedianSortEdgeNaive_14,  $param_ImplMedianSortNaive_15,  $param_ImplConvolveMean_16,  $param_GThresholdImageOps_17,  $param_GImageStatistics_18,  $param_ImageStatistics_19,  $param_ThresholdImageOps_20);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.abst.filter.binary.LocalSquareBinaryFilter.process(T,boofcv.struct.image.GrayU8,boofcv.alg.filter.blur.GBlurImageOps,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,boofcv.alg.filter.blur.BlurImageOps,boofcv.alg.filter.convolve.ConvolveImageMean,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder,boofcv.alg.filter.blur.impl.ImplMedianHistogramInner,boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive,boofcv.alg.filter.blur.impl.ImplMedianSortNaive,boofcv.alg.filter.convolve.noborder.ImplConvolveMean,boofcv.alg.filter.binary.GThresholdImageOps,boofcv.alg.misc.GImageStatistics,boofcv.alg.misc.ImageStatistics,boofcv.alg.filter.binary.ThresholdImageOps)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_GBlurImageOps_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_GeneralizedImageOps_5);
                $__params.add($param_BlurImageOps_6);
                $__params.add($param_ConvolveImageMean_7);
                $__params.add($param_FactoryKernelGaussian_8);
                $__params.add($param_ConvolveNormalized_9);
                $__params.add($param_ConvolveNormalizedNaive_10);
                $__params.add($param_ConvolveImageNoBorder_11);
                $__params.add($param_ConvolveNormalized_JustBorder_12);
                $__params.add($param_ImplMedianHistogramInner_13);
                $__params.add($param_ImplMedianSortEdgeNaive_14);
                $__params.add($param_ImplMedianSortNaive_15);
                $__params.add($param_ImplConvolveMean_16);
                $__params.add($param_GThresholdImageOps_17);
                $__params.add($param_GImageStatistics_18);
                $__params.add($param_ImageStatistics_19);
                $__params.add($param_ThresholdImageOps_20);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of getVerticalBorder()
    public int getVerticalBorder() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getVerticalBorder();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int boofcv.abst.filter.binary.LocalSquareBinaryFilter.getVerticalBorder()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }

    // Implementation of getOutputType(ImageType)
    public boofcv.struct.image.ImageType getOutputType(boofcv.struct.image.ImageType $param_ImageType_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getOutputType( $param_ImageType_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.ImageType<boofcv.struct.image.GrayU8> boofcv.abst.filter.binary.LocalSquareBinaryFilter.getOutputType(boofcv.struct.image.ImageType)";
                $__params.add($param_ImageType_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageType) $__result);
    }

    // Implementation of getInputType(ImageType)
    public boofcv.struct.image.ImageType getInputType(boofcv.struct.image.ImageType $param_ImageType_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getInputType( $param_ImageType_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.ImageType<T> boofcv.abst.filter.binary.LocalSquareBinaryFilter.getInputType(boofcv.struct.image.ImageType)";
                $__params.add($param_ImageType_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageType) $__result);
    }

    // Implementation of getHorizontalBorder()
    public int getHorizontalBorder() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getHorizontalBorder();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int boofcv.abst.filter.binary.LocalSquareBinaryFilter.getHorizontalBorder()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }
}
