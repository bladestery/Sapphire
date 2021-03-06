/*
 * Stub for class boofcv.alg.filter.blur.BlurImageOps
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.alg.filter.blur.stubs;


public final class BlurImageOps_Stub extends boofcv.alg.filter.blur.BlurImageOps implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public BlurImageOps_Stub () {
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



    // Implementation of median(GrayU8, GrayU8, int, InputSanityCheck, ImplMedianHistogramInner, ImplMedianSortEdgeNaive)
    public boofcv.struct.image.GrayU8 median(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.alg.filter.blur.impl.ImplMedianHistogramInner $param_ImplMedianHistogramInner_5, boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive $param_ImplMedianSortEdgeNaive_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.median( $param_GrayU8_1,  $param_GrayU8_2,  $param_int_3,  $param_InputSanityCheck_4,  $param_ImplMedianHistogramInner_5,  $param_ImplMedianSortEdgeNaive_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.blur.BlurImageOps.median(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,int,boofcv.alg.InputSanityCheck,boofcv.alg.filter.blur.impl.ImplMedianHistogramInner,boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_ImplMedianHistogramInner_5);
                $__params.add($param_ImplMedianSortEdgeNaive_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of median(GrayF32, GrayF32, int, InputSanityCheck, ImplMedianSortNaive)
    public boofcv.struct.image.GrayF32 median(boofcv.struct.image.GrayF32 $param_GrayF32_1, boofcv.struct.image.GrayF32 $param_GrayF32_2, int $param_int_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.alg.filter.blur.impl.ImplMedianSortNaive $param_ImplMedianSortNaive_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.median( $param_GrayF32_1,  $param_GrayF32_2,  $param_int_3,  $param_InputSanityCheck_4,  $param_ImplMedianSortNaive_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayF32 boofcv.alg.filter.blur.BlurImageOps.median(boofcv.struct.image.GrayF32,boofcv.struct.image.GrayF32,int,boofcv.alg.InputSanityCheck,boofcv.alg.filter.blur.impl.ImplMedianSortNaive)";
                $__params.add($param_GrayF32_1);
                $__params.add($param_GrayF32_2);
                $__params.add($param_int_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_ImplMedianSortNaive_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayF32) $__result);
    }

    // Implementation of median(Planar, Planar, int, GBlurImageOps, GeneralizedImageOps, InputSanityCheck, BlurImageOps, ImplMedianHistogramInner, ImplMedianSortEdgeNaive, ImplMedianSortNaive)
    public boofcv.struct.image.Planar median(boofcv.struct.image.Planar $param_Planar_1, boofcv.struct.image.Planar $param_Planar_2, int $param_int_3, boofcv.alg.filter.blur.GBlurImageOps $param_GBlurImageOps_4, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_5, boofcv.alg.InputSanityCheck $param_InputSanityCheck_6, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_7, boofcv.alg.filter.blur.impl.ImplMedianHistogramInner $param_ImplMedianHistogramInner_8, boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive $param_ImplMedianSortEdgeNaive_9, boofcv.alg.filter.blur.impl.ImplMedianSortNaive $param_ImplMedianSortNaive_10) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.median( $param_Planar_1,  $param_Planar_2,  $param_int_3,  $param_GBlurImageOps_4,  $param_GeneralizedImageOps_5,  $param_InputSanityCheck_6,  $param_BlurImageOps_7,  $param_ImplMedianHistogramInner_8,  $param_ImplMedianSortEdgeNaive_9,  $param_ImplMedianSortNaive_10);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.image.Planar<T> boofcv.alg.filter.blur.BlurImageOps.median(boofcv.struct.image.Planar<T>,boofcv.struct.image.Planar<T>,int,boofcv.alg.filter.blur.GBlurImageOps,boofcv.core.image.GeneralizedImageOps,boofcv.alg.InputSanityCheck,boofcv.alg.filter.blur.BlurImageOps,boofcv.alg.filter.blur.impl.ImplMedianHistogramInner,boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive,boofcv.alg.filter.blur.impl.ImplMedianSortNaive)";
                $__params.add($param_Planar_1);
                $__params.add($param_Planar_2);
                $__params.add($param_int_3);
                $__params.add($param_GBlurImageOps_4);
                $__params.add($param_GeneralizedImageOps_5);
                $__params.add($param_InputSanityCheck_6);
                $__params.add($param_BlurImageOps_7);
                $__params.add($param_ImplMedianHistogramInner_8);
                $__params.add($param_ImplMedianSortEdgeNaive_9);
                $__params.add($param_ImplMedianSortNaive_10);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.Planar) $__result);
    }

    // Implementation of mean(GrayU8, GrayU8, int, GrayU8, InputSanityCheck, ConvolveImageMean, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder, ImplConvolveMean)
    public boofcv.struct.image.GrayU8 mean(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, boofcv.struct.image.GrayU8 $param_GrayU8_4, boofcv.alg.InputSanityCheck $param_InputSanityCheck_5, boofcv.alg.filter.convolve.ConvolveImageMean $param_ConvolveImageMean_6, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_7, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_8, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_9, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_10, boofcv.alg.filter.convolve.noborder.ImplConvolveMean $param_ImplConvolveMean_11) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.mean( $param_GrayU8_1,  $param_GrayU8_2,  $param_int_3,  $param_GrayU8_4,  $param_InputSanityCheck_5,  $param_ConvolveImageMean_6,  $param_ConvolveNormalized_7,  $param_ConvolveNormalizedNaive_8,  $param_ConvolveImageNoBorder_9,  $param_ConvolveNormalized_JustBorder_10,  $param_ImplConvolveMean_11);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.blur.BlurImageOps.mean(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,int,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.alg.filter.convolve.ConvolveImageMean,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder,boofcv.alg.filter.convolve.noborder.ImplConvolveMean)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_GrayU8_4);
                $__params.add($param_InputSanityCheck_5);
                $__params.add($param_ConvolveImageMean_6);
                $__params.add($param_ConvolveNormalized_7);
                $__params.add($param_ConvolveNormalizedNaive_8);
                $__params.add($param_ConvolveImageNoBorder_9);
                $__params.add($param_ConvolveNormalized_JustBorder_10);
                $__params.add($param_ImplConvolveMean_11);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of mean(GrayF64, GrayF64, int, GrayF64, InputSanityCheck, ConvolveImageMean, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder, ImplConvolveMean)
    public boofcv.struct.image.GrayF64 mean(boofcv.struct.image.GrayF64 $param_GrayF64_1, boofcv.struct.image.GrayF64 $param_GrayF64_2, int $param_int_3, boofcv.struct.image.GrayF64 $param_GrayF64_4, boofcv.alg.InputSanityCheck $param_InputSanityCheck_5, boofcv.alg.filter.convolve.ConvolveImageMean $param_ConvolveImageMean_6, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_7, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_8, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_9, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_10, boofcv.alg.filter.convolve.noborder.ImplConvolveMean $param_ImplConvolveMean_11) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.mean( $param_GrayF64_1,  $param_GrayF64_2,  $param_int_3,  $param_GrayF64_4,  $param_InputSanityCheck_5,  $param_ConvolveImageMean_6,  $param_ConvolveNormalized_7,  $param_ConvolveNormalizedNaive_8,  $param_ConvolveImageNoBorder_9,  $param_ConvolveNormalized_JustBorder_10,  $param_ImplConvolveMean_11);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayF64 boofcv.alg.filter.blur.BlurImageOps.mean(boofcv.struct.image.GrayF64,boofcv.struct.image.GrayF64,int,boofcv.struct.image.GrayF64,boofcv.alg.InputSanityCheck,boofcv.alg.filter.convolve.ConvolveImageMean,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder,boofcv.alg.filter.convolve.noborder.ImplConvolveMean)";
                $__params.add($param_GrayF64_1);
                $__params.add($param_GrayF64_2);
                $__params.add($param_int_3);
                $__params.add($param_GrayF64_4);
                $__params.add($param_InputSanityCheck_5);
                $__params.add($param_ConvolveImageMean_6);
                $__params.add($param_ConvolveNormalized_7);
                $__params.add($param_ConvolveNormalizedNaive_8);
                $__params.add($param_ConvolveImageNoBorder_9);
                $__params.add($param_ConvolveNormalized_JustBorder_10);
                $__params.add($param_ImplConvolveMean_11);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayF64) $__result);
    }

    // Implementation of mean(GrayF32, GrayF32, int, GrayF32, InputSanityCheck, ConvolveImageMean, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder, ImplConvolveMean)
    public boofcv.struct.image.GrayF32 mean(boofcv.struct.image.GrayF32 $param_GrayF32_1, boofcv.struct.image.GrayF32 $param_GrayF32_2, int $param_int_3, boofcv.struct.image.GrayF32 $param_GrayF32_4, boofcv.alg.InputSanityCheck $param_InputSanityCheck_5, boofcv.alg.filter.convolve.ConvolveImageMean $param_ConvolveImageMean_6, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_7, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_8, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_9, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_10, boofcv.alg.filter.convolve.noborder.ImplConvolveMean $param_ImplConvolveMean_11) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.mean( $param_GrayF32_1,  $param_GrayF32_2,  $param_int_3,  $param_GrayF32_4,  $param_InputSanityCheck_5,  $param_ConvolveImageMean_6,  $param_ConvolveNormalized_7,  $param_ConvolveNormalizedNaive_8,  $param_ConvolveImageNoBorder_9,  $param_ConvolveNormalized_JustBorder_10,  $param_ImplConvolveMean_11);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayF32 boofcv.alg.filter.blur.BlurImageOps.mean(boofcv.struct.image.GrayF32,boofcv.struct.image.GrayF32,int,boofcv.struct.image.GrayF32,boofcv.alg.InputSanityCheck,boofcv.alg.filter.convolve.ConvolveImageMean,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder,boofcv.alg.filter.convolve.noborder.ImplConvolveMean)";
                $__params.add($param_GrayF32_1);
                $__params.add($param_GrayF32_2);
                $__params.add($param_int_3);
                $__params.add($param_GrayF32_4);
                $__params.add($param_InputSanityCheck_5);
                $__params.add($param_ConvolveImageMean_6);
                $__params.add($param_ConvolveNormalized_7);
                $__params.add($param_ConvolveNormalizedNaive_8);
                $__params.add($param_ConvolveImageNoBorder_9);
                $__params.add($param_ConvolveNormalized_JustBorder_10);
                $__params.add($param_ImplConvolveMean_11);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayF32) $__result);
    }

    // Implementation of mean(Planar, Planar, int, ImageGray, GBlurImageOps, GeneralizedImageOps, InputSanityCheck, BlurImageOps, ImplMedianHistogramInner, ImplMedianSortEdgeNaive, ImplMedianSortNaive)
    public boofcv.struct.image.Planar mean(boofcv.struct.image.Planar $param_Planar_1, boofcv.struct.image.Planar $param_Planar_2, int $param_int_3, boofcv.struct.image.ImageGray $param_ImageGray_4, boofcv.alg.filter.blur.GBlurImageOps $param_GBlurImageOps_5, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_6, boofcv.alg.InputSanityCheck $param_InputSanityCheck_7, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_8, boofcv.alg.filter.blur.impl.ImplMedianHistogramInner $param_ImplMedianHistogramInner_9, boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive $param_ImplMedianSortEdgeNaive_10, boofcv.alg.filter.blur.impl.ImplMedianSortNaive $param_ImplMedianSortNaive_11) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.mean( $param_Planar_1,  $param_Planar_2,  $param_int_3,  $param_ImageGray_4,  $param_GBlurImageOps_5,  $param_GeneralizedImageOps_6,  $param_InputSanityCheck_7,  $param_BlurImageOps_8,  $param_ImplMedianHistogramInner_9,  $param_ImplMedianSortEdgeNaive_10,  $param_ImplMedianSortNaive_11);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.image.Planar<T> boofcv.alg.filter.blur.BlurImageOps.mean(boofcv.struct.image.Planar<T>,boofcv.struct.image.Planar<T>,int,T,boofcv.alg.filter.blur.GBlurImageOps,boofcv.core.image.GeneralizedImageOps,boofcv.alg.InputSanityCheck,boofcv.alg.filter.blur.BlurImageOps,boofcv.alg.filter.blur.impl.ImplMedianHistogramInner,boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive,boofcv.alg.filter.blur.impl.ImplMedianSortNaive)";
                $__params.add($param_Planar_1);
                $__params.add($param_Planar_2);
                $__params.add($param_int_3);
                $__params.add($param_ImageGray_4);
                $__params.add($param_GBlurImageOps_5);
                $__params.add($param_GeneralizedImageOps_6);
                $__params.add($param_InputSanityCheck_7);
                $__params.add($param_BlurImageOps_8);
                $__params.add($param_ImplMedianHistogramInner_9);
                $__params.add($param_ImplMedianSortEdgeNaive_10);
                $__params.add($param_ImplMedianSortNaive_11);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.Planar) $__result);
    }

    // Implementation of gaussian(GrayU8, GrayU8, double, int, GrayU8, InputSanityCheck, GeneralizedImageOps, FactoryKernelGaussian, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder)
    public boofcv.struct.image.GrayU8 gaussian(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, double $param_double_3, int $param_int_4, boofcv.struct.image.GrayU8 $param_GrayU8_5, boofcv.alg.InputSanityCheck $param_InputSanityCheck_6, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_7, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_8, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_9, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_10, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_11, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_12) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.gaussian( $param_GrayU8_1,  $param_GrayU8_2,  $param_double_3,  $param_int_4,  $param_GrayU8_5,  $param_InputSanityCheck_6,  $param_GeneralizedImageOps_7,  $param_FactoryKernelGaussian_8,  $param_ConvolveNormalized_9,  $param_ConvolveNormalizedNaive_10,  $param_ConvolveImageNoBorder_11,  $param_ConvolveNormalized_JustBorder_12);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.blur.BlurImageOps.gaussian(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,double,int,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_double_3);
                $__params.add($param_int_4);
                $__params.add($param_GrayU8_5);
                $__params.add($param_InputSanityCheck_6);
                $__params.add($param_GeneralizedImageOps_7);
                $__params.add($param_FactoryKernelGaussian_8);
                $__params.add($param_ConvolveNormalized_9);
                $__params.add($param_ConvolveNormalizedNaive_10);
                $__params.add($param_ConvolveImageNoBorder_11);
                $__params.add($param_ConvolveNormalized_JustBorder_12);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of gaussian(GrayF64, GrayF64, double, int, GrayF64, InputSanityCheck, FactoryKernelGaussian, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder)
    public boofcv.struct.image.GrayF64 gaussian(boofcv.struct.image.GrayF64 $param_GrayF64_1, boofcv.struct.image.GrayF64 $param_GrayF64_2, double $param_double_3, int $param_int_4, boofcv.struct.image.GrayF64 $param_GrayF64_5, boofcv.alg.InputSanityCheck $param_InputSanityCheck_6, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_7, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_8, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_9, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_10, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_11) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.gaussian( $param_GrayF64_1,  $param_GrayF64_2,  $param_double_3,  $param_int_4,  $param_GrayF64_5,  $param_InputSanityCheck_6,  $param_FactoryKernelGaussian_7,  $param_ConvolveNormalized_8,  $param_ConvolveNormalizedNaive_9,  $param_ConvolveImageNoBorder_10,  $param_ConvolveNormalized_JustBorder_11);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayF64 boofcv.alg.filter.blur.BlurImageOps.gaussian(boofcv.struct.image.GrayF64,boofcv.struct.image.GrayF64,double,int,boofcv.struct.image.GrayF64,boofcv.alg.InputSanityCheck,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder)";
                $__params.add($param_GrayF64_1);
                $__params.add($param_GrayF64_2);
                $__params.add($param_double_3);
                $__params.add($param_int_4);
                $__params.add($param_GrayF64_5);
                $__params.add($param_InputSanityCheck_6);
                $__params.add($param_FactoryKernelGaussian_7);
                $__params.add($param_ConvolveNormalized_8);
                $__params.add($param_ConvolveNormalizedNaive_9);
                $__params.add($param_ConvolveImageNoBorder_10);
                $__params.add($param_ConvolveNormalized_JustBorder_11);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayF64) $__result);
    }

    // Implementation of gaussian(GrayF32, GrayF32, double, int, GrayF32, InputSanityCheck, FactoryKernelGaussian, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder)
    public boofcv.struct.image.GrayF32 gaussian(boofcv.struct.image.GrayF32 $param_GrayF32_1, boofcv.struct.image.GrayF32 $param_GrayF32_2, double $param_double_3, int $param_int_4, boofcv.struct.image.GrayF32 $param_GrayF32_5, boofcv.alg.InputSanityCheck $param_InputSanityCheck_6, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_7, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_8, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_9, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_10, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_11) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.gaussian( $param_GrayF32_1,  $param_GrayF32_2,  $param_double_3,  $param_int_4,  $param_GrayF32_5,  $param_InputSanityCheck_6,  $param_FactoryKernelGaussian_7,  $param_ConvolveNormalized_8,  $param_ConvolveNormalizedNaive_9,  $param_ConvolveImageNoBorder_10,  $param_ConvolveNormalized_JustBorder_11);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayF32 boofcv.alg.filter.blur.BlurImageOps.gaussian(boofcv.struct.image.GrayF32,boofcv.struct.image.GrayF32,double,int,boofcv.struct.image.GrayF32,boofcv.alg.InputSanityCheck,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder)";
                $__params.add($param_GrayF32_1);
                $__params.add($param_GrayF32_2);
                $__params.add($param_double_3);
                $__params.add($param_int_4);
                $__params.add($param_GrayF32_5);
                $__params.add($param_InputSanityCheck_6);
                $__params.add($param_FactoryKernelGaussian_7);
                $__params.add($param_ConvolveNormalized_8);
                $__params.add($param_ConvolveNormalizedNaive_9);
                $__params.add($param_ConvolveImageNoBorder_10);
                $__params.add($param_ConvolveNormalized_JustBorder_11);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayF32) $__result);
    }

    // Implementation of gaussian(Planar, Planar, double, int, ImageGray, GBlurImageOps, GeneralizedImageOps, InputSanityCheck, BlurImageOps, FactoryKernelGaussian, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder)
    public boofcv.struct.image.Planar gaussian(boofcv.struct.image.Planar $param_Planar_1, boofcv.struct.image.Planar $param_Planar_2, double $param_double_3, int $param_int_4, boofcv.struct.image.ImageGray $param_ImageGray_5, boofcv.alg.filter.blur.GBlurImageOps $param_GBlurImageOps_6, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_7, boofcv.alg.InputSanityCheck $param_InputSanityCheck_8, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_9, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_10, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_11, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_12, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_13, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_14) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.gaussian( $param_Planar_1,  $param_Planar_2,  $param_double_3,  $param_int_4,  $param_ImageGray_5,  $param_GBlurImageOps_6,  $param_GeneralizedImageOps_7,  $param_InputSanityCheck_8,  $param_BlurImageOps_9,  $param_FactoryKernelGaussian_10,  $param_ConvolveNormalized_11,  $param_ConvolveNormalizedNaive_12,  $param_ConvolveImageNoBorder_13,  $param_ConvolveNormalized_JustBorder_14);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.image.Planar<T> boofcv.alg.filter.blur.BlurImageOps.gaussian(boofcv.struct.image.Planar<T>,boofcv.struct.image.Planar<T>,double,int,T,boofcv.alg.filter.blur.GBlurImageOps,boofcv.core.image.GeneralizedImageOps,boofcv.alg.InputSanityCheck,boofcv.alg.filter.blur.BlurImageOps,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder)";
                $__params.add($param_Planar_1);
                $__params.add($param_Planar_2);
                $__params.add($param_double_3);
                $__params.add($param_int_4);
                $__params.add($param_ImageGray_5);
                $__params.add($param_GBlurImageOps_6);
                $__params.add($param_GeneralizedImageOps_7);
                $__params.add($param_InputSanityCheck_8);
                $__params.add($param_BlurImageOps_9);
                $__params.add($param_FactoryKernelGaussian_10);
                $__params.add($param_ConvolveNormalized_11);
                $__params.add($param_ConvolveNormalizedNaive_12);
                $__params.add($param_ConvolveImageNoBorder_13);
                $__params.add($param_ConvolveNormalized_JustBorder_14);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.Planar) $__result);
    }
}
