/*
 * Stub for class boofcv.alg.InputSanityCheck
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.alg.stubs;


public final class InputSanityCheck_Stub extends boofcv.alg.InputSanityCheck implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public InputSanityCheck_Stub () {
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



    // Implementation of checkSubimage(ImageBase)
    public void checkSubimage(boofcv.struct.image.ImageBase $param_ImageBase_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.checkSubimage( $param_ImageBase_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.InputSanityCheck.checkSubimage(boofcv.struct.image.ImageBase)";
                $__params.add($param_ImageBase_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of checkSameShapeB(ImageMultiBand, ImageMultiBand)
    public void checkSameShapeB(boofcv.struct.image.ImageMultiBand $param_ImageMultiBand_1, boofcv.struct.image.ImageMultiBand $param_ImageMultiBand_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.checkSameShapeB( $param_ImageMultiBand_1,  $param_ImageMultiBand_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.InputSanityCheck.checkSameShapeB(boofcv.struct.image.ImageMultiBand<?>,boofcv.struct.image.ImageMultiBand<?>)";
                $__params.add($param_ImageMultiBand_1);
                $__params.add($param_ImageMultiBand_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of checkSameShape(ImagePyramid, ImagePyramid)
    public void checkSameShape(boofcv.struct.pyramid.ImagePyramid $param_ImagePyramid_1, boofcv.struct.pyramid.ImagePyramid $param_ImagePyramid_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.checkSameShape( $param_ImagePyramid_1,  $param_ImagePyramid_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.InputSanityCheck.checkSameShape(boofcv.struct.pyramid.ImagePyramid<?>,boofcv.struct.pyramid.ImagePyramid<?>)";
                $__params.add($param_ImagePyramid_1);
                $__params.add($param_ImagePyramid_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of checkSameShape(ImageBase, ImageBase, ImageBase, ImageBase, ImageBase)
    public void checkSameShape(boofcv.struct.image.ImageBase $param_ImageBase_1, boofcv.struct.image.ImageBase $param_ImageBase_2, boofcv.struct.image.ImageBase $param_ImageBase_3, boofcv.struct.image.ImageBase $param_ImageBase_4, boofcv.struct.image.ImageBase $param_ImageBase_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.checkSameShape( $param_ImageBase_1,  $param_ImageBase_2,  $param_ImageBase_3,  $param_ImageBase_4,  $param_ImageBase_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.InputSanityCheck.checkSameShape(boofcv.struct.image.ImageBase<?>,boofcv.struct.image.ImageBase<?>,boofcv.struct.image.ImageBase<?>,boofcv.struct.image.ImageBase<?>,boofcv.struct.image.ImageBase<?>)";
                $__params.add($param_ImageBase_1);
                $__params.add($param_ImageBase_2);
                $__params.add($param_ImageBase_3);
                $__params.add($param_ImageBase_4);
                $__params.add($param_ImageBase_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of checkSameShape(ImageBase, ImageBase, ImageBase, ImageBase)
    public void checkSameShape(boofcv.struct.image.ImageBase $param_ImageBase_1, boofcv.struct.image.ImageBase $param_ImageBase_2, boofcv.struct.image.ImageBase $param_ImageBase_3, boofcv.struct.image.ImageBase $param_ImageBase_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.checkSameShape( $param_ImageBase_1,  $param_ImageBase_2,  $param_ImageBase_3,  $param_ImageBase_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.InputSanityCheck.checkSameShape(boofcv.struct.image.ImageBase<?>,boofcv.struct.image.ImageBase<?>,boofcv.struct.image.ImageBase<?>,boofcv.struct.image.ImageBase<?>)";
                $__params.add($param_ImageBase_1);
                $__params.add($param_ImageBase_2);
                $__params.add($param_ImageBase_3);
                $__params.add($param_ImageBase_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of checkSameShape(ImageBase, ImageBase, ImageBase)
    public void checkSameShape(boofcv.struct.image.ImageBase $param_ImageBase_1, boofcv.struct.image.ImageBase $param_ImageBase_2, boofcv.struct.image.ImageBase $param_ImageBase_3) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.checkSameShape( $param_ImageBase_1,  $param_ImageBase_2,  $param_ImageBase_3);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.InputSanityCheck.checkSameShape(boofcv.struct.image.ImageBase<?>,boofcv.struct.image.ImageBase<?>,boofcv.struct.image.ImageBase<?>)";
                $__params.add($param_ImageBase_1);
                $__params.add($param_ImageBase_2);
                $__params.add($param_ImageBase_3);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of checkSameShape(ImageBase, ImageBase)
    public void checkSameShape(boofcv.struct.image.ImageBase $param_ImageBase_1, boofcv.struct.image.ImageBase $param_ImageBase_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.checkSameShape( $param_ImageBase_1,  $param_ImageBase_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.InputSanityCheck.checkSameShape(boofcv.struct.image.ImageBase<?>,boofcv.struct.image.ImageBase<?>)";
                $__params.add($param_ImageBase_1);
                $__params.add($param_ImageBase_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of checkReshape(ImageGray, ImageGray, Class, GeneralizedImageOps)
    public boofcv.struct.image.ImageGray checkReshape(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.ImageGray $param_ImageGray_2, java.lang.Class $param_Class_3, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.checkReshape( $param_ImageGray_1,  $param_ImageGray_2,  $param_Class_3,  $param_GeneralizedImageOps_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> T boofcv.alg.InputSanityCheck.checkReshape(T,boofcv.struct.image.ImageGray,java.lang.Class<T>,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_ImageGray_2);
                $__params.add($param_Class_3);
                $__params.add($param_GeneralizedImageOps_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageGray) $__result);
    }

    // Implementation of checkIndexing(ImageBase, ImageBase)
    public void checkIndexing(boofcv.struct.image.ImageBase $param_ImageBase_1, boofcv.struct.image.ImageBase $param_ImageBase_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.checkIndexing( $param_ImageBase_1,  $param_ImageBase_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void boofcv.alg.InputSanityCheck.checkIndexing(boofcv.struct.image.ImageBase,boofcv.struct.image.ImageBase)";
                $__params.add($param_ImageBase_1);
                $__params.add($param_ImageBase_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of checkDeclare(ImageGray, ImageGray)
    public boofcv.struct.image.ImageGray checkDeclare(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.ImageGray $param_ImageGray_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.checkDeclare( $param_ImageGray_1,  $param_ImageGray_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> T boofcv.alg.InputSanityCheck.checkDeclare(T,T)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_ImageGray_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageGray) $__result);
    }

    // Implementation of checkDeclare(ImageGray, ImageGray, Class, GeneralizedImageOps)
    public boofcv.struct.image.ImageGray checkDeclare(boofcv.struct.image.ImageGray $param_ImageGray_1, boofcv.struct.image.ImageGray $param_ImageGray_2, java.lang.Class $param_Class_3, boofcv.core.image.GeneralizedImageOps $param_GeneralizedImageOps_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.checkDeclare( $param_ImageGray_1,  $param_ImageGray_2,  $param_Class_3,  $param_GeneralizedImageOps_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <In,Out> Out boofcv.alg.InputSanityCheck.checkDeclare(In,Out,java.lang.Class<Out>,boofcv.core.image.GeneralizedImageOps)";
                $__params.add($param_ImageGray_1);
                $__params.add($param_ImageGray_2);
                $__params.add($param_Class_3);
                $__params.add($param_GeneralizedImageOps_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.image.ImageGray) $__result);
    }
}
