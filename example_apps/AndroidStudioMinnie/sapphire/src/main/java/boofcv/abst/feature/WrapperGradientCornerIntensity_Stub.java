/*
 * Stub for class boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.abst.feature.detect.intensity.stubs;


public final class WrapperGradientCornerIntensity_Stub extends boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public WrapperGradientCornerIntensity_Stub (boofcv.alg.feature.detect.intensity.GradientCornerIntensity $param_GradientCornerIntensity_1) {
        super($param_GradientCornerIntensity_1);
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



    // Implementation of process(ImageGray, ImageGray, ImageGray, ImageGray, ImageGray, ImageGray, GImageMiscOps, ImageMiscOps, InputSanityCheck, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder, ConvolveNormalized, GBlurImageOps, GeneralizedImageOps, BlurImageOps, ConvolveImageMean, FactoryKernelGaussian, ImplMedianHistogramInner, ImplMedianSortEdgeNaive, ImplMedianSortNaive, ImplConvolveMean, GThresholdImageOps, GImageStatistics, ImageStatistics, ThresholdImageOps)
    public void process(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.ImageGray $param_ImageGray_2, boofcv.struct.image.ImageGray $param_ImageGray_3, boofcv.struct.image.ImageGray $param_ImageGray_4, boofcv.struct.image.ImageGray $param_ImageGray_5, boofcv.struct.image.ImageGray $param_ImageGray_6, boofcv.alg.misc.GImageMiscOps $param_GImageMiscOps_7, boofcv.alg.misc.ImageMiscOps $param_ImageMiscOps_8, boofcv.alg.InputSanityCheck $param_InputSanityCheck_9, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_10, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_11, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_12, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_13, boofcv.alg.filter.blur.GBlurImageOps $param_GBlurImageOps_14, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_15, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_16, boofcv.alg.filter.convolve.ConvolveImageMean $param_ConvolveImageMean_17, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_18, boofcv.alg.filter.blur.impl.ImplMedianHistogramInner $param_ImplMedianHistogramInner_19, boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive $param_ImplMedianSortEdgeNaive_20, boofcv.alg.filter.blur.impl.ImplMedianSortNaive $param_ImplMedianSortNaive_21, boofcv.alg.filter.convolve.noborder.ImplConvolveMean $param_ImplConvolveMean_22, boofcv.alg.filter.binary.GThresholdImageOps $param_GThresholdImageOps_23, boofcv.alg.misc.GImageStatistics $param_GImageStatistics_24, boofcv.alg.misc.ImageStatistics $param_ImageStatistics_25, boofcv.alg.filter.binary.ThresholdImageOps $param_ThresholdImageOps_26) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.process( $param_ImageGray_1,  $param_ImageGray_2,  $param_ImageGray_3,  $param_ImageGray_4,  $param_ImageGray_5,  $param_ImageGray_6,  $param_GImageMiscOps_7,  $param_ImageMiscOps_8,  $param_InputSanityCheck_9,  $param_ConvolveNormalizedNaive_10,  $param_ConvolveImageNoBorder_11,  $param_ConvolveNormalized_JustBorder_12,  $param_ConvolveNormalized_13,  $param_GBlurImageOps_14,  $param_GeneralizedImageOps_15,  $param_BlurImageOps_16,  $param_ConvolveImageMean_17,  $param_FactoryKernelGaussian_18,  $param_ImplMedianHistogramInner_19,  $param_ImplMedianSortEdgeNaive_20,  $param_ImplMedianSortNaive_21,  $param_ImplConvolveMean_22,  $param_GThresholdImageOps_23,  $param_GImageStatistics_24,  $param_ImageStatistics_25,  $param_ThresholdImageOps_26);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity.process(I,D,D,D,D,D,boofcv.alg.misc.GImageMiscOps,boofcv.alg.misc.ImageMiscOps,boofcv.alg.InputSanityCheck,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.blur.GBlurImageOps,boofcv.core.image.GeneralizedImageOps,boofcv.alg.filter.blur.BlurImageOps,boofcv.alg.filter.convolve.ConvolveImageMean,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.alg.filter.blur.impl.ImplMedianHistogramInner,boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive,boofcv.alg.filter.blur.impl.ImplMedianSortNaive,boofcv.alg.filter.convolve.noborder.ImplConvolveMean,boofcv.alg.filter.binary.GThresholdImageOps,boofcv.alg.misc.GImageStatistics,boofcv.alg.misc.ImageStatistics,boofcv.alg.filter.binary.ThresholdImageOps)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_ImageGray_2);
                $__params.add($param_ImageGray_3);
                $__params.add($param_ImageGray_4);
                $__params.add($param_ImageGray_5);
                $__params.add($param_ImageGray_6);
                $__params.add($param_GImageMiscOps_7);
                $__params.add($param_ImageMiscOps_8);
                $__params.add($param_InputSanityCheck_9);
                $__params.add($param_ConvolveNormalizedNaive_10);
                $__params.add($param_ConvolveImageNoBorder_11);
                $__params.add($param_ConvolveNormalized_JustBorder_12);
                $__params.add($param_ConvolveNormalized_13);
                $__params.add($param_GBlurImageOps_14);
                $__params.add($param_GeneralizedImageOps_15);
                $__params.add($param_BlurImageOps_16);
                $__params.add($param_ConvolveImageMean_17);
                $__params.add($param_FactoryKernelGaussian_18);
                $__params.add($param_ImplMedianHistogramInner_19);
                $__params.add($param_ImplMedianSortEdgeNaive_20);
                $__params.add($param_ImplMedianSortNaive_21);
                $__params.add($param_ImplConvolveMean_22);
                $__params.add($param_GThresholdImageOps_23);
                $__params.add($param_GImageStatistics_24);
                $__params.add($param_ImageStatistics_25);
                $__params.add($param_ThresholdImageOps_26);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of localMinimums()
    public boolean localMinimums() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.localMinimums();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boolean boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity.localMinimums()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Boolean) $__result).booleanValue();
    }

    // Implementation of localMaximums()
    public boolean localMaximums() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.localMaximums();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boolean boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity.localMaximums()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Boolean) $__result).booleanValue();
    }

    // Implementation of hasCandidates()
    public boolean hasCandidates() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.hasCandidates();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boolean boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity.hasCandidates()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Boolean) $__result).booleanValue();
    }

    // Implementation of getRequiresHessian()
    public boolean getRequiresHessian() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getRequiresHessian();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boolean boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity.getRequiresHessian()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Boolean) $__result).booleanValue();
    }

    // Implementation of getRequiresGradient()
    public boolean getRequiresGradient() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getRequiresGradient();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boolean boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity.getRequiresGradient()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Boolean) $__result).booleanValue();
    }

    // Implementation of getIgnoreBorder()
    public int getIgnoreBorder() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getIgnoreBorder();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity.getIgnoreBorder()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }

    // Implementation of getCandidatesMin()
    public boofcv.struct.QueueCorner getCandidatesMin() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getCandidatesMin();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.QueueCorner boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity.getCandidatesMin()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.QueueCorner) $__result);
    }

    // Implementation of getCandidatesMax()
    public boofcv.struct.QueueCorner getCandidatesMax() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getCandidatesMax();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.QueueCorner boofcv.abst.feature.detect.intensity.WrapperGradientCornerIntensity.getCandidatesMax()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.QueueCorner) $__result);
    }
}
