/*
 * Stub for class boofcv.alg.filter.binary.ThresholdImageOps
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.alg.filter.binary.stubs;


public final class ThresholdImageOps_Stub extends boofcv.alg.filter.binary.ThresholdImageOps implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public ThresholdImageOps_Stub () {
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



    // Implementation of threshold(GrayU8, GrayU8, int, boolean, InputSanityCheck, GeneralizedImageOps)
    public boofcv.struct.image.GrayU8 threshold(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, boolean $param_boolean_4, boofcv.alg.InputSanityCheck $param_InputSanityCheck_5, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.threshold( $param_GrayU8_1,  $param_GrayU8_2,  $param_int_3,  $param_boolean_4,  $param_InputSanityCheck_5,  $param_GeneralizedImageOps_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.ThresholdImageOps.threshold(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,int,boolean,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_boolean_4);
                $__params.add($param_InputSanityCheck_5);
                $__params.add($param_GeneralizedImageOps_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of threshold(GrayU16, GrayU8, int, boolean, InputSanityCheck, GeneralizedImageOps)
    public boofcv.struct.image.GrayU8 threshold(boofcv.struct.image.GrayU16 $param_GrayU16_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, boolean $param_boolean_4, boofcv.alg.InputSanityCheck $param_InputSanityCheck_5, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.threshold( $param_GrayU16_1,  $param_GrayU8_2,  $param_int_3,  $param_boolean_4,  $param_InputSanityCheck_5,  $param_GeneralizedImageOps_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.ThresholdImageOps.threshold(boofcv.struct.image.GrayU16,boofcv.struct.image.GrayU8,int,boolean,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_GrayU16_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_boolean_4);
                $__params.add($param_InputSanityCheck_5);
                $__params.add($param_GeneralizedImageOps_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of threshold(GrayS32, GrayU8, int, boolean, InputSanityCheck, GeneralizedImageOps)
    public boofcv.struct.image.GrayU8 threshold(boofcv.struct.image.GrayS32 $param_GrayS32_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, boolean $param_boolean_4, boofcv.alg.InputSanityCheck $param_InputSanityCheck_5, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.threshold( $param_GrayS32_1,  $param_GrayU8_2,  $param_int_3,  $param_boolean_4,  $param_InputSanityCheck_5,  $param_GeneralizedImageOps_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.ThresholdImageOps.threshold(boofcv.struct.image.GrayS32,boofcv.struct.image.GrayU8,int,boolean,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_GrayS32_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_boolean_4);
                $__params.add($param_InputSanityCheck_5);
                $__params.add($param_GeneralizedImageOps_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of threshold(GrayS16, GrayU8, int, boolean, InputSanityCheck, GeneralizedImageOps)
    public boofcv.struct.image.GrayU8 threshold(boofcv.struct.image.GrayS16 $param_GrayS16_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, boolean $param_boolean_4, boofcv.alg.InputSanityCheck $param_InputSanityCheck_5, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.threshold( $param_GrayS16_1,  $param_GrayU8_2,  $param_int_3,  $param_boolean_4,  $param_InputSanityCheck_5,  $param_GeneralizedImageOps_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.ThresholdImageOps.threshold(boofcv.struct.image.GrayS16,boofcv.struct.image.GrayU8,int,boolean,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_GrayS16_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_boolean_4);
                $__params.add($param_InputSanityCheck_5);
                $__params.add($param_GeneralizedImageOps_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of threshold(GrayF64, GrayU8, double, boolean, InputSanityCheck, GeneralizedImageOps)
    public boofcv.struct.image.GrayU8 threshold(boofcv.struct.image.GrayF64 $param_GrayF64_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, double $param_double_3, boolean $param_boolean_4, boofcv.alg.InputSanityCheck $param_InputSanityCheck_5, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.threshold( $param_GrayF64_1,  $param_GrayU8_2,  $param_double_3,  $param_boolean_4,  $param_InputSanityCheck_5,  $param_GeneralizedImageOps_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.ThresholdImageOps.threshold(boofcv.struct.image.GrayF64,boofcv.struct.image.GrayU8,double,boolean,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_GrayF64_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_double_3);
                $__params.add($param_boolean_4);
                $__params.add($param_InputSanityCheck_5);
                $__params.add($param_GeneralizedImageOps_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of threshold(GrayF32, GrayU8, float, boolean, InputSanityCheck, GeneralizedImageOps)
    public boofcv.struct.image.GrayU8 threshold(boofcv.struct.image.GrayF32 $param_GrayF32_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, float $param_float_3, boolean $param_boolean_4, boofcv.alg.InputSanityCheck $param_InputSanityCheck_5, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.threshold( $param_GrayF32_1,  $param_GrayU8_2,  $param_float_3,  $param_boolean_4,  $param_InputSanityCheck_5,  $param_GeneralizedImageOps_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.ThresholdImageOps.threshold(boofcv.struct.image.GrayF32,boofcv.struct.image.GrayU8,float,boolean,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_GrayF32_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_float_3);
                $__params.add($param_boolean_4);
                $__params.add($param_InputSanityCheck_5);
                $__params.add($param_GeneralizedImageOps_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of localSquare(GrayU8, GrayU8, int, float, boolean, GrayU8, GrayU8, InputSanityCheck, GeneralizedImageOps, BlurImageOps, ConvolveImageMean, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder, ImplConvolveMean)
    public boofcv.struct.image.GrayU8 localSquare(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, float $param_float_4, boolean $param_boolean_5, boofcv.struct.image.GrayU8 $param_GrayU8_6, boofcv.struct.image.GrayU8 $param_GrayU8_7, boofcv.alg.InputSanityCheck $param_InputSanityCheck_8, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_9, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_10, boofcv.alg.filter.convolve.ConvolveImageMean $param_ConvolveImageMean_11, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_12, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_13, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_14, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_15, boofcv.alg.filter.convolve.noborder.ImplConvolveMean $param_ImplConvolveMean_16) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.localSquare( $param_GrayU8_1,  $param_GrayU8_2,  $param_int_3,  $param_float_4,  $param_boolean_5,  $param_GrayU8_6,  $param_GrayU8_7,  $param_InputSanityCheck_8,  $param_GeneralizedImageOps_9,  $param_BlurImageOps_10,  $param_ConvolveImageMean_11,  $param_ConvolveNormalized_12,  $param_ConvolveNormalizedNaive_13,  $param_ConvolveImageNoBorder_14,  $param_ConvolveNormalized_JustBorder_15,  $param_ImplConvolveMean_16);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.ThresholdImageOps.localSquare(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,int,float,boolean,boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,boofcv.alg.filter.blur.BlurImageOps,boofcv.alg.filter.convolve.ConvolveImageMean,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder,boofcv.alg.filter.convolve.noborder.ImplConvolveMean)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_float_4);
                $__params.add($param_boolean_5);
                $__params.add($param_GrayU8_6);
                $__params.add($param_GrayU8_7);
                $__params.add($param_InputSanityCheck_8);
                $__params.add($param_GeneralizedImageOps_9);
                $__params.add($param_BlurImageOps_10);
                $__params.add($param_ConvolveImageMean_11);
                $__params.add($param_ConvolveNormalized_12);
                $__params.add($param_ConvolveNormalizedNaive_13);
                $__params.add($param_ConvolveImageNoBorder_14);
                $__params.add($param_ConvolveNormalized_JustBorder_15);
                $__params.add($param_ImplConvolveMean_16);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of localSquare(GrayF32, GrayU8, int, float, boolean, GrayF32, GrayF32, InputSanityCheck, GeneralizedImageOps, BlurImageOps, ConvolveImageMean, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder, ImplConvolveMean)
    public boofcv.struct.image.GrayU8 localSquare(boofcv.struct.image.GrayF32 $param_GrayF32_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, float $param_float_4, boolean $param_boolean_5, boofcv.struct.image.GrayF32 $param_GrayF32_6, boofcv.struct.image.GrayF32 $param_GrayF32_7, boofcv.alg.InputSanityCheck $param_InputSanityCheck_8, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_9, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_10, boofcv.alg.filter.convolve.ConvolveImageMean $param_ConvolveImageMean_11, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_12, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_13, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_14, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_15, boofcv.alg.filter.convolve.noborder.ImplConvolveMean $param_ImplConvolveMean_16) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.localSquare( $param_GrayF32_1,  $param_GrayU8_2,  $param_int_3,  $param_float_4,  $param_boolean_5,  $param_GrayF32_6,  $param_GrayF32_7,  $param_InputSanityCheck_8,  $param_GeneralizedImageOps_9,  $param_BlurImageOps_10,  $param_ConvolveImageMean_11,  $param_ConvolveNormalized_12,  $param_ConvolveNormalizedNaive_13,  $param_ConvolveImageNoBorder_14,  $param_ConvolveNormalized_JustBorder_15,  $param_ImplConvolveMean_16);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.ThresholdImageOps.localSquare(boofcv.struct.image.GrayF32,boofcv.struct.image.GrayU8,int,float,boolean,boofcv.struct.image.GrayF32,boofcv.struct.image.GrayF32,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,boofcv.alg.filter.blur.BlurImageOps,boofcv.alg.filter.convolve.ConvolveImageMean,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder,boofcv.alg.filter.convolve.noborder.ImplConvolveMean)";
                $__params.add($param_GrayF32_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_float_4);
                $__params.add($param_boolean_5);
                $__params.add($param_GrayF32_6);
                $__params.add($param_GrayF32_7);
                $__params.add($param_InputSanityCheck_8);
                $__params.add($param_GeneralizedImageOps_9);
                $__params.add($param_BlurImageOps_10);
                $__params.add($param_ConvolveImageMean_11);
                $__params.add($param_ConvolveNormalized_12);
                $__params.add($param_ConvolveNormalizedNaive_13);
                $__params.add($param_ConvolveImageNoBorder_14);
                $__params.add($param_ConvolveNormalized_JustBorder_15);
                $__params.add($param_ImplConvolveMean_16);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of localGaussian(GrayU8, GrayU8, int, float, boolean, GrayU8, GrayU8, InputSanityCheck, GeneralizedImageOps, BlurImageOps, FactoryKernelGaussian, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder)
    public boofcv.struct.image.GrayU8 localGaussian(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, float $param_float_4, boolean $param_boolean_5, boofcv.struct.image.GrayU8 $param_GrayU8_6, boofcv.struct.image.GrayU8 $param_GrayU8_7, boofcv.alg.InputSanityCheck $param_InputSanityCheck_8, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_9, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_10, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_11, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_12, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_13, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_14, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_15) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.localGaussian( $param_GrayU8_1,  $param_GrayU8_2,  $param_int_3,  $param_float_4,  $param_boolean_5,  $param_GrayU8_6,  $param_GrayU8_7,  $param_InputSanityCheck_8,  $param_GeneralizedImageOps_9,  $param_BlurImageOps_10,  $param_FactoryKernelGaussian_11,  $param_ConvolveNormalized_12,  $param_ConvolveNormalizedNaive_13,  $param_ConvolveImageNoBorder_14,  $param_ConvolveNormalized_JustBorder_15);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.ThresholdImageOps.localGaussian(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,int,float,boolean,boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,boofcv.alg.filter.blur.BlurImageOps,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_float_4);
                $__params.add($param_boolean_5);
                $__params.add($param_GrayU8_6);
                $__params.add($param_GrayU8_7);
                $__params.add($param_InputSanityCheck_8);
                $__params.add($param_GeneralizedImageOps_9);
                $__params.add($param_BlurImageOps_10);
                $__params.add($param_FactoryKernelGaussian_11);
                $__params.add($param_ConvolveNormalized_12);
                $__params.add($param_ConvolveNormalizedNaive_13);
                $__params.add($param_ConvolveImageNoBorder_14);
                $__params.add($param_ConvolveNormalized_JustBorder_15);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of localGaussian(GrayF32, GrayU8, int, float, boolean, GrayF32, GrayF32, InputSanityCheck, GeneralizedImageOps, BlurImageOps, FactoryKernelGaussian, ConvolveNormalized, ConvolveNormalizedNaive, ConvolveImageNoBorder, ConvolveNormalized_JustBorder)
    public boofcv.struct.image.GrayU8 localGaussian(boofcv.struct.image.GrayF32 $param_GrayF32_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, float $param_float_4, boolean $param_boolean_5, boofcv.struct.image.GrayF32 $param_GrayF32_6, boofcv.struct.image.GrayF32 $param_GrayF32_7, boofcv.alg.InputSanityCheck $param_InputSanityCheck_8, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_9, boofcv.alg.filter.blur.BlurImageOps $param_BlurImageOps_10, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_11, boofcv.alg.filter.convolve.ConvolveNormalized $param_ConvolveNormalized_12, boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive $param_ConvolveNormalizedNaive_13, boofcv.alg.filter.convolve.ConvolveImageNoBorder $param_ConvolveImageNoBorder_14, boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder $param_ConvolveNormalized_JustBorder_15) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.localGaussian( $param_GrayF32_1,  $param_GrayU8_2,  $param_int_3,  $param_float_4,  $param_boolean_5,  $param_GrayF32_6,  $param_GrayF32_7,  $param_InputSanityCheck_8,  $param_GeneralizedImageOps_9,  $param_BlurImageOps_10,  $param_FactoryKernelGaussian_11,  $param_ConvolveNormalized_12,  $param_ConvolveNormalizedNaive_13,  $param_ConvolveImageNoBorder_14,  $param_ConvolveNormalized_JustBorder_15);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.ThresholdImageOps.localGaussian(boofcv.struct.image.GrayF32,boofcv.struct.image.GrayU8,int,float,boolean,boofcv.struct.image.GrayF32,boofcv.struct.image.GrayF32,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,boofcv.alg.filter.blur.BlurImageOps,boofcv.factory.filter.kernel.FactoryKernelGaussian,boofcv.alg.filter.convolve.ConvolveNormalized,boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive,boofcv.alg.filter.convolve.ConvolveImageNoBorder,boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder)";
                $__params.add($param_GrayF32_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_float_4);
                $__params.add($param_boolean_5);
                $__params.add($param_GrayF32_6);
                $__params.add($param_GrayF32_7);
                $__params.add($param_InputSanityCheck_8);
                $__params.add($param_GeneralizedImageOps_9);
                $__params.add($param_BlurImageOps_10);
                $__params.add($param_FactoryKernelGaussian_11);
                $__params.add($param_ConvolveNormalized_12);
                $__params.add($param_ConvolveNormalizedNaive_13);
                $__params.add($param_ConvolveImageNoBorder_14);
                $__params.add($param_ConvolveNormalized_JustBorder_15);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }
}
