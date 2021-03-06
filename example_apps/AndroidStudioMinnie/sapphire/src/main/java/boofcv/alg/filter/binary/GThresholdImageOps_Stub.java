/*
 * Stub for class boofcv.alg.filter.binary.GThresholdImageOps
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.alg.filter.binary.stubs;


public final class GThresholdImageOps_Stub extends boofcv.alg.filter.binary.GThresholdImageOps implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public GThresholdImageOps_Stub () {
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



    // Implementation of threshold(ImageGray, GrayU8, double, boolean, ThresholdImageOps, InputSanityCheck, GeneralizedImageOps)
    public boofcv.struct.image.GrayU8 threshold(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, double $param_double_3, boolean $param_boolean_4, boofcv.alg.filter.binary.ThresholdImageOps $param_ThresholdImageOps_5, boofcv.alg.InputSanityCheck $param_InputSanityCheck_6, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_7) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.threshold( $param_ImageGray_1,  $param_GrayU8_2,  $param_double_3,  $param_boolean_4,  $param_ThresholdImageOps_5,  $param_InputSanityCheck_6,  $param_GeneralizedImageOps_7);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.GThresholdImageOps.threshold(T,boofcv.struct.image.GrayU8,double,boolean,boofcv.alg.filter.binary.ThresholdImageOps,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_double_3);
                $__params.add($param_boolean_4);
                $__params.add($param_ThresholdImageOps_5);
                $__params.add($param_InputSanityCheck_6);
                $__params.add($param_GeneralizedImageOps_7);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of localSquare(ImageGray, GrayU8, int, double, boolean, ImageGray, ImageGray, ThresholdImageOps, InputSanityCheck, GeneralizedImageOps, BlurImageOps, ConvolveImageMean, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder, ImplConvolveMean)
    public boofcv.struct.image.GrayU8 localSquare(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, double $param_double_4, boolean $param_boolean_5, boofcv.struct.image.ImageGray $param_ImageGray_6, boofcv.struct.image.ImageGray $param_ImageGray_7, boofcv.alg.filter.binary.ThresholdImageOps $param_ThresholdImageOps_8, boofcv.alg.InputSanityCheck $param_InputSanityCheck_9, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_10, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_11, boofcv.alg.filter.convolve.ConvolveImageMean $param_ConvolveImageMean_12, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_13, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_14, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_15, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_16, boofcv.alg.filter.convolve.noborder.ImplConvolveMean $param_ImplConvolveMean_17) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.localSquare( $param_ImageGray_1,  $param_GrayU8_2,  $param_int_3,  $param_double_4,  $param_boolean_5,  $param_ImageGray_6,  $param_ImageGray_7,  $param_ThresholdImageOps_8,  $param_InputSanityCheck_9,  $param_GeneralizedImageOps_10,  $param_BlurImageOps_11,  $param_ConvolveImageMean_12,  $param_ConvolveNormalized_13,  $param_ConvolveNormalizedNaive_14,  $param_ConvolveImageNoBorder_15,  $param_ConvolveNormalized_JustBorder_16,  $param_ImplConvolveMean_17);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.GThresholdImageOps.localSquare(T,boofcv.struct.image.GrayU8,int,double,boolean,T,T,boofcv.alg.filter.binary.ThresholdImageOps,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,boofcv.alg.filter.blur.BlurImageOps,boofcv.alg.filter.convolve.ConvolveImageMean,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder,boofcv.alg.filter.convolve.noborder.ImplConvolveMean)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_double_4);
                $__params.add($param_boolean_5);
                $__params.add($param_ImageGray_6);
                $__params.add($param_ImageGray_7);
                $__params.add($param_ThresholdImageOps_8);
                $__params.add($param_InputSanityCheck_9);
                $__params.add($param_GeneralizedImageOps_10);
                $__params.add($param_BlurImageOps_11);
                $__params.add($param_ConvolveImageMean_12);
                $__params.add($param_ConvolveNormalized_13);
                $__params.add($param_ConvolveNormalizedNaive_14);
                $__params.add($param_ConvolveImageNoBorder_15);
                $__params.add($param_ConvolveNormalized_JustBorder_16);
                $__params.add($param_ImplConvolveMean_17);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of localSauvola(ImageGray, GrayU8, int, float, boolean, InputSanityCheck, GeneralizedImageOps, GImageMiscOps, ImageMiscOps, ConvertImage, BlurImageOps, ConvolveImageMean, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder, ImplConvolveMean, ImageStatistics, ImageType)
    public boofcv.struct.image.GrayU8 localSauvola(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, float $param_float_4, boolean $param_boolean_5, boofcv.alg.InputSanityCheck $param_InputSanityCheck_6, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_7, boofcv.alg.misc.GImageMiscOps $param_GImageMiscOps_8, boofcv.alg.misc.ImageMiscOps $param_ImageMiscOps_9, boofcv.core.image.ConvertImage $param_ConvertImage_10, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_11, boofcv.alg.filter.convolve.ConvolveImageMean $param_ConvolveImageMean_12, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_13, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_14, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_15, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_16, boofcv.alg.filter.convolve.noborder.ImplConvolveMean $param_ImplConvolveMean_17, boofcv.alg.misc.ImageStatistics $param_ImageStatistics_18, boofcv.struct.image.ImageType $param_ImageType_19) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.localSauvola( $param_ImageGray_1,  $param_GrayU8_2,  $param_int_3,  $param_float_4,  $param_boolean_5,  $param_InputSanityCheck_6,  $param_GeneralizedImageOps_7,  $param_GImageMiscOps_8,  $param_ImageMiscOps_9,  $param_ConvertImage_10,  $param_BlurImageOps_11,  $param_ConvolveImageMean_12,  $param_ConvolveNormalized_13,  $param_ConvolveNormalizedNaive_14,  $param_ConvolveImageNoBorder_15,  $param_ConvolveNormalized_JustBorder_16,  $param_ImplConvolveMean_17,  $param_ImageStatistics_18,  $param_ImageType_19);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.GThresholdImageOps.localSauvola(T,boofcv.struct.image.GrayU8,int,float,boolean,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,boofcv.alg.misc.GImageMiscOps,boofcv.alg.misc.ImageMiscOps,boofcv.core.image.ConvertImage,boofcv.alg.filter.blur.BlurImageOps,boofcv.alg.filter.convolve.ConvolveImageMean,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder,boofcv.alg.filter.convolve.noborder.ImplConvolveMean,boofcv.alg.misc.ImageStatistics,boofcv.struct.image.ImageType)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_float_4);
                $__params.add($param_boolean_5);
                $__params.add($param_InputSanityCheck_6);
                $__params.add($param_GeneralizedImageOps_7);
                $__params.add($param_GImageMiscOps_8);
                $__params.add($param_ImageMiscOps_9);
                $__params.add($param_ConvertImage_10);
                $__params.add($param_BlurImageOps_11);
                $__params.add($param_ConvolveImageMean_12);
                $__params.add($param_ConvolveNormalized_13);
                $__params.add($param_ConvolveNormalizedNaive_14);
                $__params.add($param_ConvolveImageNoBorder_15);
                $__params.add($param_ConvolveNormalized_JustBorder_16);
                $__params.add($param_ImplConvolveMean_17);
                $__params.add($param_ImageStatistics_18);
                $__params.add($param_ImageType_19);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of localGaussian(ImageGray, GrayU8, int, double, boolean, ImageGray, ImageGray, ThresholdImageOps, InputSanityCheck, GeneralizedImageOps, BlurImageOps, FactoryKernelGaussian, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder)
    public boofcv.struct.image.GrayU8 localGaussian(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, double $param_double_4, boolean $param_boolean_5, boofcv.struct.image.ImageGray $param_ImageGray_6, boofcv.struct.image.ImageGray $param_ImageGray_7, boofcv.alg.filter.binary.ThresholdImageOps $param_ThresholdImageOps_8, boofcv.alg.InputSanityCheck $param_InputSanityCheck_9, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_10, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_11, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_12, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_13, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_14, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_15, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_16) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.localGaussian( $param_ImageGray_1,  $param_GrayU8_2,  $param_int_3,  $param_double_4,  $param_boolean_5,  $param_ImageGray_6,  $param_ImageGray_7,  $param_ThresholdImageOps_8,  $param_InputSanityCheck_9,  $param_GeneralizedImageOps_10,  $param_BlurImageOps_11,  $param_FactoryKernelGaussian_12,  $param_ConvolveNormalized_13,  $param_ConvolveNormalizedNaive_14,  $param_ConvolveImageNoBorder_15,  $param_ConvolveNormalized_JustBorder_16);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.GThresholdImageOps.localGaussian(T,boofcv.struct.image.GrayU8,int,double,boolean,T,boofcv.struct.image.ImageGray,boofcv.alg.filter.binary.ThresholdImageOps,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,boofcv.alg.filter.blur.BlurImageOps,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_double_4);
                $__params.add($param_boolean_5);
                $__params.add($param_ImageGray_6);
                $__params.add($param_ImageGray_7);
                $__params.add($param_ThresholdImageOps_8);
                $__params.add($param_InputSanityCheck_9);
                $__params.add($param_GeneralizedImageOps_10);
                $__params.add($param_BlurImageOps_11);
                $__params.add($param_FactoryKernelGaussian_12);
                $__params.add($param_ConvolveNormalized_13);
                $__params.add($param_ConvolveNormalizedNaive_14);
                $__params.add($param_ConvolveImageNoBorder_15);
                $__params.add($param_ConvolveNormalized_JustBorder_16);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of localBlockMinMax(ImageGray, GrayU8, int, double, boolean, double, GBlurImageOps, InputSanityCheck, GeneralizedImageOps, BlurImageOps, ConvolveImageMean, FactoryKernelGaussian, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder, ImplMedianHistogramInner, ImplMedianSortEdgeNaive, ImplMedianSortNaive, ImplConvolveMean, GThresholdImageOps, GImageStatistics, ImageStatistics, ThresholdImageOps, GImageMiscOps, ImageMiscOps, ConvolveJustBorder_General, ImageType, ConvertImage, UtilWavelet)
    public boofcv.struct.image.GrayU8 localBlockMinMax(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, double $param_double_4, boolean $param_boolean_5, double $param_double_6, boofcv.alg.filter.blur.GBlurImageOps $param_GBlurImageOps_7, boofcv.alg.InputSanityCheck $param_InputSanityCheck_8, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_9, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_10, boofcv.alg.filter.convolve.ConvolveImageMean $param_ConvolveImageMean_11, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_12, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_13, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_14, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_15, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_16, boofcv.alg.filter.blur.impl.ImplMedianHistogramInner $param_ImplMedianHistogramInner_17, boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive $param_ImplMedianSortEdgeNaive_18, boofcv.alg.filter.blur.impl.ImplMedianSortNaive $param_ImplMedianSortNaive_19, boofcv.alg.filter.convolve.noborder.ImplConvolveMean $param_ImplConvolveMean_20, boofcv.alg.filter.binary.GThresholdImageOps $param_GThresholdImageOps_21, boofcv.alg.misc.GImageStatistics $param_GImageStatistics_22, boofcv.alg.misc.ImageStatistics $param_ImageStatistics_23, boofcv.alg.filter.binary.ThresholdImageOps $param_ThresholdImageOps_24, boofcv.alg.misc.GImageMiscOps $param_GImageMiscOps_25, boofcv.alg.misc.ImageMiscOps $param_ImageMiscOps_26, boofcv.alg.filter.convolve.border.ConvolveJustBorder_General $param_ConvolveJustBorder_General_27, boofcv.struct.image.ImageType $param_ImageType_28, boofcv.core.image.ConvertImage $param_ConvertImage_29, boofcv.alg.transform.wavelet.UtilWavelet $param_UtilWavelet_30) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.localBlockMinMax( $param_ImageGray_1,  $param_GrayU8_2,  $param_int_3,  $param_double_4,  $param_boolean_5,  $param_double_6,  $param_GBlurImageOps_7,  $param_InputSanityCheck_8,  $param_GeneralizedImageOps_9,  $param_BlurImageOps_10,  $param_ConvolveImageMean_11,  $param_FactoryKernelGaussian_12,  $param_ConvolveNormalized_13,  $param_ConvolveNormalizedNaive_14,  $param_ConvolveImageNoBorder_15,  $param_ConvolveNormalized_JustBorder_16,  $param_ImplMedianHistogramInner_17,  $param_ImplMedianSortEdgeNaive_18,  $param_ImplMedianSortNaive_19,  $param_ImplConvolveMean_20,  $param_GThresholdImageOps_21,  $param_GImageStatistics_22,  $param_ImageStatistics_23,  $param_ThresholdImageOps_24,  $param_GImageMiscOps_25,  $param_ImageMiscOps_26,  $param_ConvolveJustBorder_General_27,  $param_ImageType_28,  $param_ConvertImage_29,  $param_UtilWavelet_30);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.GThresholdImageOps.localBlockMinMax(T,boofcv.struct.image.GrayU8,int,double,boolean,double,boofcv.alg.filter.blur.GBlurImageOps,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,boofcv.alg.filter.blur.BlurImageOps,boofcv.alg.filter.convolve.ConvolveImageMean,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder,boofcv.alg.filter.blur.impl.ImplMedianHistogramInner,boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive,boofcv.alg.filter.blur.impl.ImplMedianSortNaive,boofcv.alg.filter.convolve.noborder.ImplConvolveMean,boofcv.alg.filter.binary.GThresholdImageOps,boofcv.alg.misc.GImageStatistics,boofcv.alg.misc.ImageStatistics,boofcv.alg.filter.binary.ThresholdImageOps,boofcv.alg.misc.GImageMiscOps,boofcv.alg.misc.ImageMiscOps,boofcv.alg.filter.convolve.border.ConvolveJustBorder_General,boofcv.struct.image.ImageType,boofcv.core.image.ConvertImage,boofcv.alg.transform.wavelet.UtilWavelet)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_double_4);
                $__params.add($param_boolean_5);
                $__params.add($param_double_6);
                $__params.add($param_GBlurImageOps_7);
                $__params.add($param_InputSanityCheck_8);
                $__params.add($param_GeneralizedImageOps_9);
                $__params.add($param_BlurImageOps_10);
                $__params.add($param_ConvolveImageMean_11);
                $__params.add($param_FactoryKernelGaussian_12);
                $__params.add($param_ConvolveNormalized_13);
                $__params.add($param_ConvolveNormalizedNaive_14);
                $__params.add($param_ConvolveImageNoBorder_15);
                $__params.add($param_ConvolveNormalized_JustBorder_16);
                $__params.add($param_ImplMedianHistogramInner_17);
                $__params.add($param_ImplMedianSortEdgeNaive_18);
                $__params.add($param_ImplMedianSortNaive_19);
                $__params.add($param_ImplConvolveMean_20);
                $__params.add($param_GThresholdImageOps_21);
                $__params.add($param_GImageStatistics_22);
                $__params.add($param_ImageStatistics_23);
                $__params.add($param_ThresholdImageOps_24);
                $__params.add($param_GImageMiscOps_25);
                $__params.add($param_ImageMiscOps_26);
                $__params.add($param_ConvolveJustBorder_General_27);
                $__params.add($param_ImageType_28);
                $__params.add($param_ConvertImage_29);
                $__params.add($param_UtilWavelet_30);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of computeOtsu(int[], int, int)
    public int computeOtsu(int[] $param_arrayOf_int_1, int $param_int_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.computeOtsu( $param_arrayOf_int_1,  $param_int_2,  $param_int_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int boofcv.alg.filter.binary.GThresholdImageOps.computeOtsu(int[],int,int)";
                $__params.add($param_arrayOf_int_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }

    // Implementation of computeOtsu(ImageGray, int, int, GImageStatistics, ImageStatistics)
    public int computeOtsu(boofcv.struct.image.ImageGray $param_ImageGray_1, int $param_int_2, int $param_int_3, boofcv.alg.misc.GImageStatistics $param_GImageStatistics_4, boofcv.alg.misc.ImageStatistics $param_ImageStatistics_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.computeOtsu( $param_ImageGray_1,  $param_int_2,  $param_int_3,  $param_GImageStatistics_4,  $param_ImageStatistics_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int boofcv.alg.filter.binary.GThresholdImageOps.computeOtsu(boofcv.struct.image.ImageGray,int,int,boofcv.alg.misc.GImageStatistics,boofcv.alg.misc.ImageStatistics)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_GImageStatistics_4);
                $__params.add($param_ImageStatistics_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }

    // Implementation of computeEntropy(int[], int, int)
    public int computeEntropy(int[] $param_arrayOf_int_1, int $param_int_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.computeEntropy( $param_arrayOf_int_1,  $param_int_2,  $param_int_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int boofcv.alg.filter.binary.GThresholdImageOps.computeEntropy(int[],int,int)";
                $__params.add($param_arrayOf_int_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }

    // Implementation of computeEntropy(ImageGray, int, int, ImageStatistics, GImageStatistics)
    public int computeEntropy(boofcv.struct.image.ImageGray $param_ImageGray_1, int $param_int_2, int $param_int_3, boofcv.alg.misc.ImageStatistics $param_ImageStatistics_4, boofcv.alg.misc.GImageStatistics $param_GImageStatistics_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.computeEntropy( $param_ImageGray_1,  $param_int_2,  $param_int_3,  $param_ImageStatistics_4,  $param_GImageStatistics_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int boofcv.alg.filter.binary.GThresholdImageOps.computeEntropy(boofcv.struct.image.ImageGray,int,int,boofcv.alg.misc.ImageStatistics,boofcv.alg.misc.GImageStatistics)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_ImageStatistics_4);
                $__params.add($param_GImageStatistics_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }
}
