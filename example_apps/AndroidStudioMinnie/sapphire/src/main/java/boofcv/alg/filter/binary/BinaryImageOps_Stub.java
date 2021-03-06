/*
 * Stub for class boofcv.alg.filter.binary.BinaryImageOps
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.alg.filter.binary.stubs;


public final class BinaryImageOps_Stub extends boofcv.alg.filter.binary.BinaryImageOps implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public BinaryImageOps_Stub () {
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



    // Implementation of thin(GrayU8, int, GrayU8, InputSanityCheck, ImageBorderValue)
    public boofcv.struct.image.GrayU8 thin(boofcv.struct.image.GrayU8 $param_GrayU8_1, int $param_int_2, boofcv.struct.image.GrayU8 $param_GrayU8_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.core.image.border.ImageBorderValue $param_ImageBorderValue_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.thin( $param_GrayU8_1,  $param_int_2,  $param_GrayU8_3,  $param_InputSanityCheck_4,  $param_ImageBorderValue_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.thin(boofcv.struct.image.GrayU8,int,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.core.image.border.ImageBorderValue)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_int_2);
                $__params.add($param_GrayU8_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_ImageBorderValue_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of selectRandomColors(int, Random)
    public int[] selectRandomColors(int $param_int_1, java.util.Random $param_Random_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.selectRandomColors( $param_int_1,  $param_Random_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int[] boofcv.alg.filter.binary.BinaryImageOps.selectRandomColors(int,java.util.Random)";
                $__params.add($param_int_1);
                $__params.add($param_Random_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((int[]) $__result);
    }

    // Implementation of removePointNoise(GrayU8, GrayU8, InputSanityCheck, ImplBinaryInnerOps, ImplBinaryBorderOps, ImageBorderValue)
    public boofcv.struct.image.GrayU8 removePointNoise(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.alg.InputSanityCheck $param_InputSanityCheck_3, boofcv.alg.filter.binary.impl.ImplBinaryInnerOps $param_ImplBinaryInnerOps_4, boofcv.alg.filter.binary.impl.ImplBinaryBorderOps $param_ImplBinaryBorderOps_5, boofcv.core.image.border.ImageBorderValue $param_ImageBorderValue_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.removePointNoise( $param_GrayU8_1,  $param_GrayU8_2,  $param_InputSanityCheck_3,  $param_ImplBinaryInnerOps_4,  $param_ImplBinaryBorderOps_5,  $param_ImageBorderValue_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.removePointNoise(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.alg.filter.binary.impl.ImplBinaryInnerOps,boofcv.alg.filter.binary.impl.ImplBinaryBorderOps,boofcv.core.image.border.ImageBorderValue)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_InputSanityCheck_3);
                $__params.add($param_ImplBinaryInnerOps_4);
                $__params.add($param_ImplBinaryBorderOps_5);
                $__params.add($param_ImageBorderValue_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of relabel(GrayS32, int[])
    public void relabel(boofcv.struct.image.GrayS32 $param_GrayS32_1, int[] $param_arrayOf_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.relabel( $param_GrayS32_1,  $param_arrayOf_int_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.filter.binary.BinaryImageOps.relabel(boofcv.struct.image.GrayS32,int[])";
                $__params.add($param_GrayS32_1);
                $__params.add($param_arrayOf_int_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of logicXor(GrayU8, GrayU8, GrayU8, InputSanityCheck)
    public boofcv.struct.image.GrayU8 logicXor(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.struct.image.GrayU8 $param_GrayU8_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.logicXor( $param_GrayU8_1,  $param_GrayU8_2,  $param_GrayU8_3,  $param_InputSanityCheck_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.logicXor(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_GrayU8_3);
                $__params.add($param_InputSanityCheck_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of logicOr(GrayU8, GrayU8, GrayU8, InputSanityCheck)
    public boofcv.struct.image.GrayU8 logicOr(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.struct.image.GrayU8 $param_GrayU8_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.logicOr( $param_GrayU8_1,  $param_GrayU8_2,  $param_GrayU8_3,  $param_InputSanityCheck_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.logicOr(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_GrayU8_3);
                $__params.add($param_InputSanityCheck_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of logicAnd(GrayU8, GrayU8, GrayU8, InputSanityCheck)
    public boofcv.struct.image.GrayU8 logicAnd(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.struct.image.GrayU8 $param_GrayU8_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.logicAnd( $param_GrayU8_1,  $param_GrayU8_2,  $param_GrayU8_3,  $param_InputSanityCheck_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.logicAnd(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_GrayU8_3);
                $__params.add($param_InputSanityCheck_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of labelToClusters(GrayS32, int, FastQueue)
    public java.util.List labelToClusters(boofcv.struct.image.GrayS32 $param_GrayS32_1, int $param_int_2, org.ddogleg.struct.FastQueue $param_FastQueue_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.labelToClusters( $param_GrayS32_1,  $param_int_2,  $param_FastQueue_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public java.util.List<java.util.List<georegression.struct.point.Point2D_I32>> boofcv.alg.filter.binary.BinaryImageOps.labelToClusters(boofcv.struct.image.GrayS32,int,org.ddogleg.struct.FastQueue<georegression.struct.point.Point2D_I32>)";
                $__params.add($param_GrayS32_1);
                $__params.add($param_int_2);
                $__params.add($param_FastQueue_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of labelToBinary(GrayS32, GrayU8, int, InputSanityCheck, GeneralizedImageOps, int[])
    public boofcv.struct.image.GrayU8 labelToBinary(boofcv.struct.image.GrayS32 $param_GrayS32_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_5, int[] $param_arrayOf_int_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.labelToBinary( $param_GrayS32_1,  $param_GrayU8_2,  $param_int_3,  $param_InputSanityCheck_4,  $param_GeneralizedImageOps_5,  $param_arrayOf_int_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.labelToBinary(boofcv.struct.image.GrayS32,boofcv.struct.image.GrayU8,int,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps,int...)";
                $__params.add($param_GrayS32_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_GeneralizedImageOps_5);
                $__params.add($param_arrayOf_int_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of labelToBinary(GrayS32, GrayU8, boolean[], InputSanityCheck, GeneralizedImageOps)
    public boofcv.struct.image.GrayU8 labelToBinary(boofcv.struct.image.GrayS32 $param_GrayS32_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boolean[] $param_arrayOf_boolean_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.labelToBinary( $param_GrayS32_1,  $param_GrayU8_2,  $param_arrayOf_boolean_3,  $param_InputSanityCheck_4,  $param_GeneralizedImageOps_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.labelToBinary(boofcv.struct.image.GrayS32,boofcv.struct.image.GrayU8,boolean[],boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_GrayS32_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_arrayOf_boolean_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_GeneralizedImageOps_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of labelToBinary(GrayS32, GrayU8, InputSanityCheck, GeneralizedImageOps)
    public boofcv.struct.image.GrayU8 labelToBinary(boofcv.struct.image.GrayS32 $param_GrayS32_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.alg.InputSanityCheck $param_InputSanityCheck_3, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.labelToBinary( $param_GrayS32_1,  $param_GrayU8_2,  $param_InputSanityCheck_3,  $param_GeneralizedImageOps_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.labelToBinary(boofcv.struct.image.GrayS32,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_GrayS32_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_InputSanityCheck_3);
                $__params.add($param_GeneralizedImageOps_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of invert(GrayU8, GrayU8, InputSanityCheck)
    public boofcv.struct.image.GrayU8 invert(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.alg.InputSanityCheck $param_InputSanityCheck_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.invert( $param_GrayU8_1,  $param_GrayU8_2,  $param_InputSanityCheck_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.invert(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_InputSanityCheck_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of erode8(GrayU8, int, GrayU8, InputSanityCheck, ImplBinaryInnerOps, ImplBinaryBorderOps, ImageBorderValue)
    public boofcv.struct.image.GrayU8 erode8(boofcv.struct.image.GrayU8 $param_GrayU8_1, int $param_int_2, boofcv.struct.image.GrayU8 $param_GrayU8_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.alg.filter.binary.impl.ImplBinaryInnerOps $param_ImplBinaryInnerOps_5, boofcv.alg.filter.binary.impl.ImplBinaryBorderOps $param_ImplBinaryBorderOps_6, boofcv.core.image.border.ImageBorderValue $param_ImageBorderValue_7) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.erode8( $param_GrayU8_1,  $param_int_2,  $param_GrayU8_3,  $param_InputSanityCheck_4,  $param_ImplBinaryInnerOps_5,  $param_ImplBinaryBorderOps_6,  $param_ImageBorderValue_7);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.erode8(boofcv.struct.image.GrayU8,int,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.alg.filter.binary.impl.ImplBinaryInnerOps,boofcv.alg.filter.binary.impl.ImplBinaryBorderOps,boofcv.core.image.border.ImageBorderValue)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_int_2);
                $__params.add($param_GrayU8_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_ImplBinaryInnerOps_5);
                $__params.add($param_ImplBinaryBorderOps_6);
                $__params.add($param_ImageBorderValue_7);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of erode4(GrayU8, int, GrayU8, InputSanityCheck, ImplBinaryInnerOps, ImplBinaryBorderOps, ImageBorderValue)
    public boofcv.struct.image.GrayU8 erode4(boofcv.struct.image.GrayU8 $param_GrayU8_1, int $param_int_2, boofcv.struct.image.GrayU8 $param_GrayU8_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.alg.filter.binary.impl.ImplBinaryInnerOps $param_ImplBinaryInnerOps_5, boofcv.alg.filter.binary.impl.ImplBinaryBorderOps $param_ImplBinaryBorderOps_6, boofcv.core.image.border.ImageBorderValue $param_ImageBorderValue_7) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.erode4( $param_GrayU8_1,  $param_int_2,  $param_GrayU8_3,  $param_InputSanityCheck_4,  $param_ImplBinaryInnerOps_5,  $param_ImplBinaryBorderOps_6,  $param_ImageBorderValue_7);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.erode4(boofcv.struct.image.GrayU8,int,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.alg.filter.binary.impl.ImplBinaryInnerOps,boofcv.alg.filter.binary.impl.ImplBinaryBorderOps,boofcv.core.image.border.ImageBorderValue)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_int_2);
                $__params.add($param_GrayU8_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_ImplBinaryInnerOps_5);
                $__params.add($param_ImplBinaryBorderOps_6);
                $__params.add($param_ImageBorderValue_7);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of edge8(GrayU8, GrayU8, InputSanityCheck, ImplBinaryInnerOps, ImplBinaryBorderOps, ImageBorderValue)
    public boofcv.struct.image.GrayU8 edge8(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.alg.InputSanityCheck $param_InputSanityCheck_3, boofcv.alg.filter.binary.impl.ImplBinaryInnerOps $param_ImplBinaryInnerOps_4, boofcv.alg.filter.binary.impl.ImplBinaryBorderOps $param_ImplBinaryBorderOps_5, boofcv.core.image.border.ImageBorderValue $param_ImageBorderValue_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.edge8( $param_GrayU8_1,  $param_GrayU8_2,  $param_InputSanityCheck_3,  $param_ImplBinaryInnerOps_4,  $param_ImplBinaryBorderOps_5,  $param_ImageBorderValue_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.edge8(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.alg.filter.binary.impl.ImplBinaryInnerOps,boofcv.alg.filter.binary.impl.ImplBinaryBorderOps,boofcv.core.image.border.ImageBorderValue)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_InputSanityCheck_3);
                $__params.add($param_ImplBinaryInnerOps_4);
                $__params.add($param_ImplBinaryBorderOps_5);
                $__params.add($param_ImageBorderValue_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of edge4(GrayU8, GrayU8, InputSanityCheck, ImplBinaryInnerOps, ImplBinaryBorderOps, ImageBorderValue)
    public boofcv.struct.image.GrayU8 edge4(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.alg.InputSanityCheck $param_InputSanityCheck_3, boofcv.alg.filter.binary.impl.ImplBinaryInnerOps $param_ImplBinaryInnerOps_4, boofcv.alg.filter.binary.impl.ImplBinaryBorderOps $param_ImplBinaryBorderOps_5, boofcv.core.image.border.ImageBorderValue $param_ImageBorderValue_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.edge4( $param_GrayU8_1,  $param_GrayU8_2,  $param_InputSanityCheck_3,  $param_ImplBinaryInnerOps_4,  $param_ImplBinaryBorderOps_5,  $param_ImageBorderValue_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.edge4(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.alg.filter.binary.impl.ImplBinaryInnerOps,boofcv.alg.filter.binary.impl.ImplBinaryBorderOps,boofcv.core.image.border.ImageBorderValue)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_InputSanityCheck_3);
                $__params.add($param_ImplBinaryInnerOps_4);
                $__params.add($param_ImplBinaryBorderOps_5);
                $__params.add($param_ImageBorderValue_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of dilate8(GrayU8, int, GrayU8, InputSanityCheck, ImplBinaryInnerOps, ImplBinaryBorderOps, ImageBorderValue)
    public boofcv.struct.image.GrayU8 dilate8(boofcv.struct.image.GrayU8 $param_GrayU8_1, int $param_int_2, boofcv.struct.image.GrayU8 $param_GrayU8_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.alg.filter.binary.impl.ImplBinaryInnerOps $param_ImplBinaryInnerOps_5, boofcv.alg.filter.binary.impl.ImplBinaryBorderOps $param_ImplBinaryBorderOps_6, boofcv.core.image.border.ImageBorderValue $param_ImageBorderValue_7) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.dilate8( $param_GrayU8_1,  $param_int_2,  $param_GrayU8_3,  $param_InputSanityCheck_4,  $param_ImplBinaryInnerOps_5,  $param_ImplBinaryBorderOps_6,  $param_ImageBorderValue_7);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.dilate8(boofcv.struct.image.GrayU8,int,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.alg.filter.binary.impl.ImplBinaryInnerOps,boofcv.alg.filter.binary.impl.ImplBinaryBorderOps,boofcv.core.image.border.ImageBorderValue)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_int_2);
                $__params.add($param_GrayU8_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_ImplBinaryInnerOps_5);
                $__params.add($param_ImplBinaryBorderOps_6);
                $__params.add($param_ImageBorderValue_7);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of dilate4(GrayU8, int, GrayU8, InputSanityCheck, ImplBinaryInnerOps, ImplBinaryBorderOps, ImageBorderValue)
    public boofcv.struct.image.GrayU8 dilate4(boofcv.struct.image.GrayU8 $param_GrayU8_1, int $param_int_2, boofcv.struct.image.GrayU8 $param_GrayU8_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.alg.filter.binary.impl.ImplBinaryInnerOps $param_ImplBinaryInnerOps_5, boofcv.alg.filter.binary.impl.ImplBinaryBorderOps $param_ImplBinaryBorderOps_6, boofcv.core.image.border.ImageBorderValue $param_ImageBorderValue_7) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.dilate4( $param_GrayU8_1,  $param_int_2,  $param_GrayU8_3,  $param_InputSanityCheck_4,  $param_ImplBinaryInnerOps_5,  $param_ImplBinaryBorderOps_6,  $param_ImageBorderValue_7);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 boofcv.alg.filter.binary.BinaryImageOps.dilate4(boofcv.struct.image.GrayU8,int,boofcv.struct.image.GrayU8,boofcv.alg.InputSanityCheck,boofcv.alg.filter.binary.impl.ImplBinaryInnerOps,boofcv.alg.filter.binary.impl.ImplBinaryBorderOps,boofcv.core.image.border.ImageBorderValue)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_int_2);
                $__params.add($param_GrayU8_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_ImplBinaryInnerOps_5);
                $__params.add($param_ImplBinaryBorderOps_6);
                $__params.add($param_ImageBorderValue_7);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of contour(GrayU8, ConnectRule, GrayS32, InputSanityCheck, ImageMiscOps)
    public java.util.List contour(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.ConnectRule $param_ConnectRule_2, boofcv.struct.image.GrayS32 $param_GrayS32_3, boofcv.alg.InputSanityCheck $param_InputSanityCheck_4, boofcv.alg.misc.ImageMiscOps $param_ImageMiscOps_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.contour( $param_GrayU8_1,  $param_ConnectRule_2,  $param_GrayS32_3,  $param_InputSanityCheck_4,  $param_ImageMiscOps_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public java.util.List<boofcv.alg.filter.binary.Contour> boofcv.alg.filter.binary.BinaryImageOps.contour(boofcv.struct.image.GrayU8,boofcv.struct.ConnectRule,boofcv.struct.image.GrayS32,boofcv.alg.InputSanityCheck,boofcv.alg.misc.ImageMiscOps)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_ConnectRule_2);
                $__params.add($param_GrayS32_3);
                $__params.add($param_InputSanityCheck_4);
                $__params.add($param_ImageMiscOps_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of clusterToBinary(List, GrayU8, ImageMiscOps)
    public void clusterToBinary(java.util.List $param_List_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.alg.misc.ImageMiscOps $param_ImageMiscOps_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.clusterToBinary( $param_List_1,  $param_GrayU8_2,  $param_ImageMiscOps_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.filter.binary.BinaryImageOps.clusterToBinary(java.util.List<java.util.List<georegression.struct.point.Point2D_I32>>,boofcv.struct.image.GrayU8,boofcv.alg.misc.ImageMiscOps)";
                $__params.add($param_List_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_ImageMiscOps_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
