/*
 * Stub for class boofcv.factory.feature.detect.line.FactoryDetectLineAlgs
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.factory.feature.detect.line.stubs;


public final class FactoryDetectLineAlgs_Stub extends boofcv.factory.feature.detect.line.FactoryDetectLineAlgs implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public FactoryDetectLineAlgs_Stub () {
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



    // Implementation of lineRansac(int, double, double, boolean, Class, Class, FactoryDerivative, GeneralizedImageOps, FactoryImageBorder)
    public boofcv.abst.feature.detect.line.DetectLineSegmentsGridRansac lineRansac(int $param_int_1, double $param_double_2, double $param_double_3, boolean $param_boolean_4, java.lang.Class $param_Class_5, java.lang.Class $param_Class_6, boofcv.factory.filter.derivative.FactoryDerivative $param_FactoryDerivative_7, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_8, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_9) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.lineRansac( $param_int_1,  $param_double_2,  $param_double_3,  $param_boolean_4,  $param_Class_5,  $param_Class_6,  $param_FactoryDerivative_7,  $param_GeneralizedImageOps_8,  $param_FactoryImageBorder_9);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <I,D> boofcv.abst.feature.detect.line.DetectLineSegmentsGridRansac<I, D> boofcv.factory.feature.detect.line.FactoryDetectLineAlgs.lineRansac(int,double,double,boolean,java.lang.Class<I>,java.lang.Class<D>,boofcv.factory.filter.derivative.FactoryDerivative,boofcv.core.image.GeneralizedImageOps,boofcv.core.image.border.FactoryImageBorder)";
                $__params.add($param_int_1);
                $__params.add($param_double_2);
                $__params.add($param_double_3);
                $__params.add($param_boolean_4);
                $__params.add($param_Class_5);
                $__params.add($param_Class_6);
                $__params.add($param_FactoryDerivative_7);
                $__params.add($param_GeneralizedImageOps_8);
                $__params.add($param_FactoryImageBorder_9);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.line.DetectLineSegmentsGridRansac) $__result);
    }

    // Implementation of houghPolar(ConfigHoughPolar, Class, Class, FactoryDerivative, GeneralizedImageOps, FactoryImageBorder, ImageType, FactoryFeatureExtractor)
    public boofcv.abst.feature.detect.line.DetectLineHoughPolar houghPolar(boofcv.factory.feature.detect.line.ConfigHoughPolar $param_ConfigHoughPolar_1, java.lang.Class $param_Class_2, java.lang.Class $param_Class_3, boofcv.factory.filter.derivative.FactoryDerivative $param_FactoryDerivative_4, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_5, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_6, boofcv.struct.image.ImageType $param_ImageType_7, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_8) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.houghPolar( $param_ConfigHoughPolar_1,  $param_Class_2,  $param_Class_3,  $param_FactoryDerivative_4,  $param_GeneralizedImageOps_5,  $param_FactoryImageBorder_6,  $param_ImageType_7,  $param_FactoryFeatureExtractor_8);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <I,D> boofcv.abst.feature.detect.line.DetectLineHoughPolar<I, D> boofcv.factory.feature.detect.line.FactoryDetectLineAlgs.houghPolar(boofcv.factory.feature.detect.line.ConfigHoughPolar,java.lang.Class<I>,java.lang.Class<D>,boofcv.factory.filter.derivative.FactoryDerivative,boofcv.core.image.GeneralizedImageOps,boofcv.core.image.border.FactoryImageBorder,boofcv.struct.image.ImageType,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor)";
                $__params.add($param_ConfigHoughPolar_1);
                $__params.add($param_Class_2);
                $__params.add($param_Class_3);
                $__params.add($param_FactoryDerivative_4);
                $__params.add($param_GeneralizedImageOps_5);
                $__params.add($param_FactoryImageBorder_6);
                $__params.add($param_ImageType_7);
                $__params.add($param_FactoryFeatureExtractor_8);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.line.DetectLineHoughPolar) $__result);
    }

    // Implementation of houghFootSub(ConfigHoughFootSubimage, Class, Class, FactoryDerivative, GeneralizedImageOps, FactoryImageBorder, ImageType, FactoryFeatureExtractor)
    public boofcv.abst.feature.detect.line.DetectLineHoughFootSubimage houghFootSub(boofcv.factory.feature.detect.line.ConfigHoughFootSubimage $param_ConfigHoughFootSubimage_1, java.lang.Class $param_Class_2, java.lang.Class $param_Class_3, boofcv.factory.filter.derivative.FactoryDerivative $param_FactoryDerivative_4, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_5, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_6, boofcv.struct.image.ImageType $param_ImageType_7, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_8) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.houghFootSub( $param_ConfigHoughFootSubimage_1,  $param_Class_2,  $param_Class_3,  $param_FactoryDerivative_4,  $param_GeneralizedImageOps_5,  $param_FactoryImageBorder_6,  $param_ImageType_7,  $param_FactoryFeatureExtractor_8);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <I,D> boofcv.abst.feature.detect.line.DetectLineHoughFootSubimage<I, D> boofcv.factory.feature.detect.line.FactoryDetectLineAlgs.houghFootSub(boofcv.factory.feature.detect.line.ConfigHoughFootSubimage,java.lang.Class<I>,java.lang.Class<D>,boofcv.factory.filter.derivative.FactoryDerivative,boofcv.core.image.GeneralizedImageOps,boofcv.core.image.border.FactoryImageBorder,boofcv.struct.image.ImageType,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor)";
                $__params.add($param_ConfigHoughFootSubimage_1);
                $__params.add($param_Class_2);
                $__params.add($param_Class_3);
                $__params.add($param_FactoryDerivative_4);
                $__params.add($param_GeneralizedImageOps_5);
                $__params.add($param_FactoryImageBorder_6);
                $__params.add($param_ImageType_7);
                $__params.add($param_FactoryFeatureExtractor_8);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.line.DetectLineHoughFootSubimage) $__result);
    }

    // Implementation of houghFoot(ConfigHoughFoot, Class, Class, FactoryDerivative, GeneralizedImageOps, FactoryImageBorder, ImageType, FactoryFeatureExtractor)
    public boofcv.abst.feature.detect.line.DetectLineHoughFoot houghFoot(boofcv.factory.feature.detect.line.ConfigHoughFoot $param_ConfigHoughFoot_1, java.lang.Class $param_Class_2, java.lang.Class $param_Class_3, boofcv.factory.filter.derivative.FactoryDerivative $param_FactoryDerivative_4, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_5, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_6, boofcv.struct.image.ImageType $param_ImageType_7, boofcv.factory.feature.detect.extract.FactoryFeatureExtractor $param_FactoryFeatureExtractor_8) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.houghFoot( $param_ConfigHoughFoot_1,  $param_Class_2,  $param_Class_3,  $param_FactoryDerivative_4,  $param_GeneralizedImageOps_5,  $param_FactoryImageBorder_6,  $param_ImageType_7,  $param_FactoryFeatureExtractor_8);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <I,D> boofcv.abst.feature.detect.line.DetectLineHoughFoot<I, D> boofcv.factory.feature.detect.line.FactoryDetectLineAlgs.houghFoot(boofcv.factory.feature.detect.line.ConfigHoughFoot,java.lang.Class<I>,java.lang.Class<D>,boofcv.factory.filter.derivative.FactoryDerivative,boofcv.core.image.GeneralizedImageOps,boofcv.core.image.border.FactoryImageBorder,boofcv.struct.image.ImageType,boofcv.factory.feature.detect.extract.FactoryFeatureExtractor)";
                $__params.add($param_ConfigHoughFoot_1);
                $__params.add($param_Class_2);
                $__params.add($param_Class_3);
                $__params.add($param_FactoryDerivative_4);
                $__params.add($param_GeneralizedImageOps_5);
                $__params.add($param_FactoryImageBorder_6);
                $__params.add($param_ImageType_7);
                $__params.add($param_FactoryFeatureExtractor_8);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.feature.detect.line.DetectLineHoughFoot) $__result);
    }
}
