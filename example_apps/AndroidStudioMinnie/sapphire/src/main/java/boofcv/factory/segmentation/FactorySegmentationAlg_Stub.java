/*
 * Stub for class boofcv.factory.segmentation.FactorySegmentationAlg
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.factory.segmentation.stubs;


public final class FactorySegmentationAlg_Stub extends boofcv.factory.segmentation.FactorySegmentationAlg implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public FactorySegmentationAlg_Stub () {
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



    // Implementation of weightsFelzenszwalb04(ConnectRule, ImageType)
    public boofcv.alg.segmentation.fh04.FhEdgeWeights weightsFelzenszwalb04(boofcv.struct.ConnectRule $param_ConnectRule_1, boofcv.struct.image.ImageType $param_ImageType_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.weightsFelzenszwalb04( $param_ConnectRule_1,  $param_ImageType_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.alg.segmentation.fh04.FhEdgeWeights<T> boofcv.factory.segmentation.FactorySegmentationAlg.weightsFelzenszwalb04(boofcv.struct.ConnectRule,boofcv.struct.image.ImageType<T>)";
                $__params.add($param_ConnectRule_1);
                $__params.add($param_ImageType_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.segmentation.fh04.FhEdgeWeights) $__result);
    }

    // Implementation of watershed(ConnectRule)
    public boofcv.alg.segmentation.watershed.WatershedVincentSoille1991 watershed(boofcv.struct.ConnectRule $param_ConnectRule_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.watershed( $param_ConnectRule_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.alg.segmentation.watershed.WatershedVincentSoille1991 boofcv.factory.segmentation.FactorySegmentationAlg.watershed(boofcv.struct.ConnectRule)";
                $__params.add($param_ConnectRule_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.segmentation.watershed.WatershedVincentSoille1991) $__result);
    }

    // Implementation of slic(ConfigSlic, ImageType, ImageType, FactorySegmentationAlg)
    public boofcv.alg.segmentation.slic.SegmentSlic slic(boofcv.factory.segmentation.ConfigSlic $param_ConfigSlic_1, boofcv.struct.image.ImageType $param_ImageType_2, boofcv.struct.image.ImageType $param_ImageType_3, boofcv.factory.segmentation.FactorySegmentationAlg $param_FactorySegmentationAlg_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.slic( $param_ConfigSlic_1,  $param_ImageType_2,  $param_ImageType_3,  $param_FactorySegmentationAlg_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.alg.segmentation.slic.SegmentSlic<T> boofcv.factory.segmentation.FactorySegmentationAlg.slic(boofcv.factory.segmentation.ConfigSlic,boofcv.struct.image.ImageType<T>,boofcv.struct.image.ImageType,boofcv.factory.segmentation.FactorySegmentationAlg)";
                $__params.add($param_ConfigSlic_1);
                $__params.add($param_ImageType_2);
                $__params.add($param_ImageType_3);
                $__params.add($param_FactorySegmentationAlg_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.segmentation.slic.SegmentSlic) $__result);
    }

    // Implementation of regionMeanColor(ImageType)
    public boofcv.alg.segmentation.ComputeRegionMeanColor regionMeanColor(boofcv.struct.image.ImageType $param_ImageType_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.regionMeanColor( $param_ImageType_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.alg.segmentation.ComputeRegionMeanColor<T> boofcv.factory.segmentation.FactorySegmentationAlg.regionMeanColor(boofcv.struct.image.ImageType<T>)";
                $__params.add($param_ImageType_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.segmentation.ComputeRegionMeanColor) $__result);
    }

    // Implementation of meanShift(ConfigSegmentMeanShift, ImageType, FactoryImageBorder)
    public boofcv.alg.segmentation.ms.SegmentMeanShift meanShift(boofcv.factory.segmentation.ConfigSegmentMeanShift $param_ConfigSegmentMeanShift_1, boofcv.struct.image.ImageType $param_ImageType_2, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.meanShift( $param_ConfigSegmentMeanShift_1,  $param_ImageType_2,  $param_FactoryImageBorder_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.alg.segmentation.ms.SegmentMeanShift<T> boofcv.factory.segmentation.FactorySegmentationAlg.meanShift(boofcv.factory.segmentation.ConfigSegmentMeanShift,boofcv.struct.image.ImageType<T>,boofcv.core.image.border.FactoryImageBorder)";
                $__params.add($param_ConfigSegmentMeanShift_1);
                $__params.add($param_ImageType_2);
                $__params.add($param_FactoryImageBorder_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.segmentation.ms.SegmentMeanShift) $__result);
    }

    // Implementation of fh04(ConfigFh04, ImageType)
    public boofcv.alg.segmentation.fh04.SegmentFelzenszwalbHuttenlocher04 fh04(boofcv.factory.segmentation.ConfigFh04 $param_ConfigFh04_1, boofcv.struct.image.ImageType $param_ImageType_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.fh04( $param_ConfigFh04_1,  $param_ImageType_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.alg.segmentation.fh04.SegmentFelzenszwalbHuttenlocher04<T> boofcv.factory.segmentation.FactorySegmentationAlg.fh04(boofcv.factory.segmentation.ConfigFh04,boofcv.struct.image.ImageType<T>)";
                $__params.add($param_ConfigFh04_1);
                $__params.add($param_ImageType_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.segmentation.fh04.SegmentFelzenszwalbHuttenlocher04) $__result);
    }
}