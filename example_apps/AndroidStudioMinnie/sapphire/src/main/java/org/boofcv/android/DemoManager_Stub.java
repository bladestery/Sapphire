/*
 * Stub for class org.boofcv.android.DemoManager
 * Generated by Sapphire Compiler (sc).
 */
package org.boofcv.android.stubs;


public final class DemoManager_Stub extends org.boofcv.android.DemoManager implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public DemoManager_Stub () {
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



    // Implementation of threshold(GrayU8, GrayU8, int, boolean)
    public boofcv.struct.image.GrayU8 threshold(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2, int $param_int_3, boolean $param_boolean_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.threshold( $param_GrayU8_1,  $param_GrayU8_2,  $param_int_3,  $param_boolean_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 org.boofcv.android.DemoManager.threshold(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8,int,boolean)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__params.add($param_int_3);
                $__params.add($param_boolean_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of single(Class)
    public boofcv.struct.image.ImageType single(java.lang.Class $param_Class_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.single( $param_Class_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <I> boofcv.struct.image.ImageType<I> org.boofcv.android.DemoManager.single(java.lang.Class<I>)";
                $__params.add($param_Class_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageType) $__result);
    }

    // Implementation of reshape(int, int)
    public void reshape(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.reshape( $param_int_1,  $param_int_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.boofcv.android.DemoManager.reshape(int,int)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of removePointNoise(GrayU8, GrayU8)
    public boofcv.struct.image.GrayU8 removePointNoise(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayU8 $param_GrayU8_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.removePointNoise( $param_GrayU8_1,  $param_GrayU8_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 org.boofcv.android.DemoManager.removePointNoise(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayU8)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayU8_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of process(GrayU8, float, float, GrayU8)
    public void process(boofcv.struct.image.GrayU8 $param_GrayU8_1, float $param_float_2, float $param_float_3, boofcv.struct.image.GrayU8 $param_GrayU8_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.process( $param_GrayU8_1,  $param_float_2,  $param_float_3,  $param_GrayU8_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.boofcv.android.DemoManager.process(boofcv.struct.image.GrayU8,float,float,boofcv.struct.image.GrayU8)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_float_2);
                $__params.add($param_float_3);
                $__params.add($param_GrayU8_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of localSquare(int, double, boolean)
    public void localSquare(int $param_int_1, double $param_double_2, boolean $param_boolean_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.localSquare( $param_int_1,  $param_double_2,  $param_boolean_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.boofcv.android.DemoManager.localSquare(int,double,boolean)";
                $__params.add($param_int_1);
                $__params.add($param_double_2);
                $__params.add($param_boolean_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of inputProcess(GrayU8)
    public void inputProcess(boofcv.struct.image.GrayU8 $param_GrayU8_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.inputProcess( $param_GrayU8_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.boofcv.android.DemoManager.inputProcess(boofcv.struct.image.GrayU8)";
                $__params.add($param_GrayU8_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of globalOtsu(int, int, boolean)
    public void globalOtsu(int $param_int_1, int $param_int_2, boolean $param_boolean_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.globalOtsu( $param_int_1,  $param_int_2,  $param_boolean_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.boofcv.android.DemoManager.globalOtsu(int,int,boolean)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_boolean_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of getFoundEllipses()
    public org.ddogleg.struct.FastQueue getFoundEllipses() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getFoundEllipses();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public org.ddogleg.struct.FastQueue<georegression.struct.shapes.EllipseRotated_F64> org.boofcv.android.DemoManager.getFoundEllipses()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((org.ddogleg.struct.FastQueue) $__result);
    }

    // Implementation of getContours()
    public java.util.List getContours() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getContours();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public java.util.List<boofcv.alg.feature.detect.edge.EdgeContour> org.boofcv.android.DemoManager.getContours()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of findContoursProcess(GrayU8, GrayS32)
    public void findContoursProcess(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayS32 $param_GrayS32_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.findContoursProcess( $param_GrayU8_1,  $param_GrayS32_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.boofcv.android.DemoManager.findContoursProcess(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayS32)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayS32_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of findContoursGetContours()
    public org.ddogleg.struct.FastQueue findContoursGetContours() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.findContoursGetContours();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public org.ddogleg.struct.FastQueue<boofcv.alg.filter.binary.Contour> org.boofcv.android.DemoManager.findContoursGetContours()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((org.ddogleg.struct.FastQueue) $__result);
    }

    // Implementation of ellipse(ConfigEllipseDetector)
    public void ellipse(boofcv.factory.shape.ConfigEllipseDetector $param_ConfigEllipseDetector_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.ellipse( $param_ConfigEllipseDetector_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.boofcv.android.DemoManager.ellipse(boofcv.factory.shape.ConfigEllipseDetector)";
                $__params.add($param_ConfigEllipseDetector_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of edgeProcess(GrayU8, float, float, GrayU8)
    public java.util.List edgeProcess(boofcv.struct.image.GrayU8 $param_GrayU8_1, float $param_float_2, float $param_float_3, boofcv.struct.image.GrayU8 $param_GrayU8_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.edgeProcess( $param_GrayU8_1,  $param_float_2,  $param_float_3,  $param_GrayU8_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public java.util.List<boofcv.alg.feature.detect.edge.EdgeContour> org.boofcv.android.DemoManager.edgeProcess(boofcv.struct.image.GrayU8,float,float,boofcv.struct.image.GrayU8)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_float_2);
                $__params.add($param_float_3);
                $__params.add($param_GrayU8_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of detectorProcess(GrayU8)
    public boofcv.struct.image.GrayU8 detectorProcess(boofcv.struct.image.GrayU8 $param_GrayU8_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.detectorProcess( $param_GrayU8_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 org.boofcv.android.DemoManager.detectorProcess(boofcv.struct.image.GrayU8)";
                $__params.add($param_GrayU8_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of contourProcess(GrayU8, GrayS32)
    public org.ddogleg.struct.FastQueue contourProcess(boofcv.struct.image.GrayU8 $param_GrayU8_1, boofcv.struct.image.GrayS32 $param_GrayS32_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.contourProcess( $param_GrayU8_1,  $param_GrayS32_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public org.ddogleg.struct.FastQueue<boofcv.alg.filter.binary.Contour> org.boofcv.android.DemoManager.contourProcess(boofcv.struct.image.GrayU8,boofcv.struct.image.GrayS32)";
                $__params.add($param_GrayU8_1);
                $__params.add($param_GrayS32_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((org.ddogleg.struct.FastQueue) $__result);
    }

    // Implementation of contourFilter(ImageGray, int, int, boolean)
    public boofcv.struct.image.GrayU8 contourFilter(boofcv.struct.image.ImageGray $param_ImageGray_1, int $param_int_2, int $param_int_3, boolean $param_boolean_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.contourFilter( $param_ImageGray_1,  $param_int_2,  $param_int_3,  $param_boolean_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boofcv.struct.image.GrayU8 org.boofcv.android.DemoManager.contourFilter(boofcv.struct.image.ImageGray,int,int,boolean)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_boolean_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.GrayU8) $__result);
    }

    // Implementation of computeOtsu(ImageGray, int, int)
    public int computeOtsu(boofcv.struct.image.ImageGray $param_ImageGray_1, int $param_int_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.computeOtsu( $param_ImageGray_1,  $param_int_2,  $param_int_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public int org.boofcv.android.DemoManager.computeOtsu(boofcv.struct.image.ImageGray,int,int)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }

    // Implementation of cannyEdge(int, boolean, boolean, Class, Class)
    public boofcv.alg.feature.detect.edge.CannyEdge cannyEdge(int $param_int_1, boolean $param_boolean_2, boolean $param_boolean_3, java.lang.Class $param_Class_4, java.lang.Class $param_Class_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.cannyEdge( $param_int_1,  $param_boolean_2,  $param_boolean_3,  $param_Class_4,  $param_Class_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T,D> boofcv.alg.feature.detect.edge.CannyEdge<T, D> org.boofcv.android.DemoManager.cannyEdge(int,boolean,boolean,java.lang.Class<T>,java.lang.Class<D>)";
                $__params.add($param_int_1);
                $__params.add($param_boolean_2);
                $__params.add($param_boolean_3);
                $__params.add($param_Class_4);
                $__params.add($param_Class_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.feature.detect.edge.CannyEdge) $__result);
    }

    // Implementation of canny(int, boolean, boolean)
    public void canny(int $param_int_1, boolean $param_boolean_2, boolean $param_boolean_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.canny( $param_int_1,  $param_boolean_2,  $param_boolean_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.boofcv.android.DemoManager.canny(int,boolean,boolean)";
                $__params.add($param_int_1);
                $__params.add($param_boolean_2);
                $__params.add($param_boolean_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of LocalSquare(int, double, boolean, Class)
    public boofcv.abst.filter.binary.InputToBinary LocalSquare(int $param_int_1, double $param_double_2, boolean $param_boolean_3, java.lang.Class $param_Class_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.LocalSquare( $param_int_1,  $param_double_2,  $param_boolean_3,  $param_Class_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.abst.filter.binary.InputToBinary<T> org.boofcv.android.DemoManager.LocalSquare(int,double,boolean,java.lang.Class<T>)";
                $__params.add($param_int_1);
                $__params.add($param_double_2);
                $__params.add($param_boolean_3);
                $__params.add($param_Class_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.filter.binary.InputToBinary) $__result);
    }

    // Implementation of LatencyCheck()
    public void LatencyCheck() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.LatencyCheck();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.boofcv.android.DemoManager.LatencyCheck()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of GlobalOtsu(int, int, boolean, Class)
    public boofcv.abst.filter.binary.InputToBinary GlobalOtsu(int $param_int_1, int $param_int_2, boolean $param_boolean_3, java.lang.Class $param_Class_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.GlobalOtsu( $param_int_1,  $param_int_2,  $param_boolean_3,  $param_Class_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.abst.filter.binary.InputToBinary<T> org.boofcv.android.DemoManager.GlobalOtsu(int,int,boolean,java.lang.Class<T>)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__params.add($param_boolean_3);
                $__params.add($param_Class_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.abst.filter.binary.InputToBinary) $__result);
    }

    // Implementation of Ellipse(ConfigEllipseDetector, Class)
    public boofcv.alg.shapes.ellipse.BinaryEllipseDetector Ellipse(boofcv.factory.shape.ConfigEllipseDetector $param_ConfigEllipseDetector_1, java.lang.Class $param_Class_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.Ellipse( $param_ConfigEllipseDetector_1,  $param_Class_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.alg.shapes.ellipse.BinaryEllipseDetector<T> org.boofcv.android.DemoManager.Ellipse(boofcv.factory.shape.ConfigEllipseDetector,java.lang.Class<T>)";
                $__params.add($param_ConfigEllipseDetector_1);
                $__params.add($param_Class_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.alg.shapes.ellipse.BinaryEllipseDetector) $__result);
    }
}
