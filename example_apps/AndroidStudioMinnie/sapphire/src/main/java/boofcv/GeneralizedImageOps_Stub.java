/*
 * Stub for class boofcv.core.image.GeneralizedImageOps
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.core.image.stubs;


public final class GeneralizedImageOps_Stub extends boofcv.core.image.GeneralizedImageOps implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public GeneralizedImageOps_Stub () {
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



    // Implementation of setM(ImageBase, int, int, double[])
    public void setM(boofcv.struct.image.ImageBase $param_ImageBase_1, int $param_int_2, int $param_int_3, double[] $param_arrayOf_double_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setM( $param_ImageBase_1,  $param_int_2,  $param_int_3,  $param_arrayOf_double_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.core.image.GeneralizedImageOps.setM(boofcv.struct.image.ImageBase,int,int,double...)";
                $__params.add($param_ImageBase_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_arrayOf_double_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of setB(ImageBase, int, int, int, double)
    public void setB(boofcv.struct.image.ImageBase $param_ImageBase_1, int $param_int_2, int $param_int_3, int $param_int_4, double $param_double_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setB( $param_ImageBase_1,  $param_int_2,  $param_int_3,  $param_int_4,  $param_double_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.core.image.GeneralizedImageOps.setB(boofcv.struct.image.ImageBase,int,int,int,double)";
                $__params.add($param_ImageBase_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_int_4);
                $__params.add($param_double_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of set(ImageGray, int, int, double)
    public void set(boofcv.struct.image.ImageGray $param_ImageGray_1, int $param_int_2, int $param_int_3, double $param_double_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.set( $param_ImageGray_1,  $param_int_2,  $param_int_3,  $param_double_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.core.image.GeneralizedImageOps.set(boofcv.struct.image.ImageGray,int,int,double)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_double_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of isFloatingPoint(Class)
    public boolean isFloatingPoint(java.lang.Class $param_Class_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.isFloatingPoint( $param_Class_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public boolean boofcv.core.image.GeneralizedImageOps.isFloatingPoint(java.lang.Class<?>)";
                $__params.add($param_Class_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Boolean) $__result).booleanValue();
    }

    // Implementation of getNumBits(Class)
    public int getNumBits(java.lang.Class $param_Class_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.getNumBits( $param_Class_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> int boofcv.core.image.GeneralizedImageOps.getNumBits(java.lang.Class<T>)";
                $__params.add($param_Class_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Integer) $__result).intValue();
    }

    // Implementation of get(ImageInterleaved, int, int, int)
    public double get(boofcv.struct.image.ImageInterleaved $param_ImageInterleaved_1, int $param_int_2, int $param_int_3, int $param_int_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.get( $param_ImageInterleaved_1,  $param_int_2,  $param_int_3,  $param_int_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public double boofcv.core.image.GeneralizedImageOps.get(boofcv.struct.image.ImageInterleaved,int,int,int)";
                $__params.add($param_ImageInterleaved_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_int_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Double) $__result).doubleValue();
    }

    // Implementation of get(ImageGray, int, int)
    public double get(boofcv.struct.image.ImageGray $param_ImageGray_1, int $param_int_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.get( $param_ImageGray_1,  $param_int_2,  $param_int_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public double boofcv.core.image.GeneralizedImageOps.get(boofcv.struct.image.ImageGray,int,int)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Double) $__result).doubleValue();
    }

    // Implementation of get(ImageBase, int, int, int)
    public double get(boofcv.struct.image.ImageBase $param_ImageBase_1, int $param_int_2, int $param_int_3, int $param_int_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.get( $param_ImageBase_1,  $param_int_2,  $param_int_3,  $param_int_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public double boofcv.core.image.GeneralizedImageOps.get(boofcv.struct.image.ImageBase,int,int,int)";
                $__params.add($param_ImageBase_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_int_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.lang.Double) $__result).doubleValue();
    }

    // Implementation of createSingleBand(Class, int, int)
    public boofcv.struct.image.ImageGray createSingleBand(java.lang.Class $param_Class_1, int $param_int_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.createSingleBand( $param_Class_1,  $param_int_2,  $param_int_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> T boofcv.core.image.GeneralizedImageOps.createSingleBand(java.lang.Class<T>,int,int)";
                $__params.add($param_Class_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageGray) $__result);
    }

    // Implementation of createSingleBand(ImageDataType, int, int)
    public boofcv.struct.image.ImageGray createSingleBand(boofcv.struct.image.ImageDataType $param_ImageDataType_1, int $param_int_2, int $param_int_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.createSingleBand( $param_ImageDataType_1,  $param_int_2,  $param_int_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> T boofcv.core.image.GeneralizedImageOps.createSingleBand(boofcv.struct.image.ImageDataType,int,int)";
                $__params.add($param_ImageDataType_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageGray) $__result);
    }

    // Implementation of createInterleaved(Class, int, int, int)
    public boofcv.struct.image.ImageInterleaved createInterleaved(java.lang.Class $param_Class_1, int $param_int_2, int $param_int_3, int $param_int_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.createInterleaved( $param_Class_1,  $param_int_2,  $param_int_3,  $param_int_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> T boofcv.core.image.GeneralizedImageOps.createInterleaved(java.lang.Class<T>,int,int,int)";
                $__params.add($param_Class_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_int_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageInterleaved) $__result);
    }

    // Implementation of createInterleaved(ImageDataType, int, int, int)
    public boofcv.struct.image.ImageInterleaved createInterleaved(boofcv.struct.image.ImageDataType $param_ImageDataType_1, int $param_int_2, int $param_int_3, int $param_int_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.createInterleaved( $param_ImageDataType_1,  $param_int_2,  $param_int_3,  $param_int_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> T boofcv.core.image.GeneralizedImageOps.createInterleaved(boofcv.struct.image.ImageDataType,int,int,int)";
                $__params.add($param_ImageDataType_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_int_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageInterleaved) $__result);
    }

    // Implementation of createImage(Class, int, int, int)
    public boofcv.struct.image.ImageBase createImage(java.lang.Class $param_Class_1, int $param_int_2, int $param_int_3, int $param_int_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.createImage( $param_Class_1,  $param_int_2,  $param_int_3,  $param_int_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> T boofcv.core.image.GeneralizedImageOps.createImage(java.lang.Class<T>,int,int,int)";
                $__params.add($param_Class_1);
                $__params.add($param_int_2);
                $__params.add($param_int_3);
                $__params.add($param_int_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageBase) $__result);
    }

    // Implementation of convert(ImageGray, ImageGray, Class)
    public boofcv.struct.image.ImageGray convert(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.ImageGray $param_ImageGray_2, java.lang.Class $param_Class_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.convert( $param_ImageGray_1,  $param_ImageGray_2,  $param_Class_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> T boofcv.core.image.GeneralizedImageOps.convert(boofcv.struct.image.ImageGray<?>,T,java.lang.Class<T>)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_ImageGray_2);
                $__params.add($param_Class_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageGray) $__result);
    }
}