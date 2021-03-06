/*
 * Stub for class boofcv.alg.shapes.ellipse.BinaryEllipseDetector
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.alg.shapes.ellipse.stubs;


public final class BinaryEllipseDetector_Stub extends boofcv.alg.shapes.ellipse.BinaryEllipseDetector implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public BinaryEllipseDetector_Stub (boofcv.alg.shapes.ellipse.BinaryEllipseDetectorPixel $param_BinaryEllipseDetectorPixel_1, boofcv.alg.shapes.ellipse.SnapToEllipseEdge $param_SnapToEllipseEdge_2, boofcv.alg.shapes.ellipse.EdgeIntensityEllipse $param_EdgeIntensityEllipse_3, java.lang.Class $param_Class_4) {
        super($param_BinaryEllipseDetectorPixel_1, $param_SnapToEllipseEdge_2, $param_EdgeIntensityEllipse_3, $param_Class_4);
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



    // Implementation of setVerbose(boolean)
    public void setVerbose(boolean $param_boolean_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setVerbose( $param_boolean_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.shapes.ellipse.BinaryEllipseDetector.setVerbose(boolean)";
                $__params.add($param_boolean_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of setLensDistortion(PixelTransform2_F32, PixelTransform2_F32, FactoryImageBorder, FactoryInterpolation)
    public void setLensDistortion(boofcv.struct.distort.PixelTransform2_F32 $param_PixelTransform2_F32_1, boofcv.struct.distort.PixelTransform2_F32 $param_PixelTransform2_F32_2, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_3, boofcv.factory.interpolate.FactoryInterpolation $param_FactoryInterpolation_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setLensDistortion( $param_PixelTransform2_F32_1,  $param_PixelTransform2_F32_2,  $param_FactoryImageBorder_3,  $param_FactoryInterpolation_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.shapes.ellipse.BinaryEllipseDetector.setLensDistortion(boofcv.struct.distort.PixelTransform2_F32,boofcv.struct.distort.PixelTransform2_F32,boofcv.core.image.border.FactoryImageBorder,boofcv.factory.interpolate.FactoryInterpolation)";
                $__params.add($param_PixelTransform2_F32_1);
                $__params.add($param_PixelTransform2_F32_2);
                $__params.add($param_FactoryImageBorder_3);
                $__params.add($param_FactoryInterpolation_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of setAutoRefine(boolean)
    public void setAutoRefine(boolean $param_boolean_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setAutoRefine( $param_boolean_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.shapes.ellipse.BinaryEllipseDetector.setAutoRefine(boolean)";
                $__params.add($param_boolean_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of refine(EllipseRotated_F64)
    public boolean refine(georegression.struct.shapes.EllipseRotated_F64 $param_EllipseRotated_F64_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.refine( $param_EllipseRotated_F64_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boolean boofcv.alg.shapes.ellipse.BinaryEllipseDetector.refine(georegression.struct.shapes.EllipseRotated_F64)";
                $__params.add($param_EllipseRotated_F64_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Boolean) $__result).booleanValue();
    }

    // Implementation of process(ImageGray, GrayU8, LinearContourLabelChang2004, ImageMiscOps)
    public void process(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, boofcv.alg.filter.binary.LinearContourLabelChang2004 $param_LinearContourLabelChang2004_3, boofcv.alg.misc.ImageMiscOps $param_ImageMiscOps_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.process( $param_ImageGray_1,  $param_GrayU8_2,  $param_LinearContourLabelChang2004_3,  $param_ImageMiscOps_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.shapes.ellipse.BinaryEllipseDetector.process(T,boofcv.struct.image.GrayU8,boofcv.alg.filter.binary.LinearContourLabelChang2004,boofcv.alg.misc.ImageMiscOps)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_LinearContourLabelChang2004_3);
                $__params.add($param_ImageMiscOps_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of isVerbose()
    public boolean isVerbose() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.isVerbose();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boolean boofcv.alg.shapes.ellipse.BinaryEllipseDetector.isVerbose()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Boolean) $__result).booleanValue();
    }

    // Implementation of isAutoRefine()
    public boolean isAutoRefine() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.isAutoRefine();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boolean boofcv.alg.shapes.ellipse.BinaryEllipseDetector.isAutoRefine()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Boolean) $__result).booleanValue();
    }

    // Implementation of getInputType()
    public java.lang.Class getInputType() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getInputType();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public java.lang.Class<T> boofcv.alg.shapes.ellipse.BinaryEllipseDetector.getInputType()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Class) $__result);
    }

    // Implementation of getFoundEllipses()
    public org.ddogleg.struct.FastQueue getFoundEllipses() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getFoundEllipses();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public org.ddogleg.struct.FastQueue<georegression.struct.shapes.EllipseRotated_F64> boofcv.alg.shapes.ellipse.BinaryEllipseDetector.getFoundEllipses()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((org.ddogleg.struct.FastQueue) $__result);
    }

    // Implementation of getEllipseDetector()
    public boofcv.alg.shapes.ellipse.BinaryEllipseDetectorPixel getEllipseDetector() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getEllipseDetector();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.alg.shapes.ellipse.BinaryEllipseDetectorPixel boofcv.alg.shapes.ellipse.BinaryEllipseDetector.getEllipseDetector()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.shapes.ellipse.BinaryEllipseDetectorPixel) $__result);
    }

    // Implementation of getAllContours()
    public java.util.List getAllContours() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getAllContours();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public java.util.List<boofcv.alg.filter.binary.Contour> boofcv.alg.shapes.ellipse.BinaryEllipseDetector.getAllContours()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }
}
