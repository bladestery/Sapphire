/*
 * Stub for class boofcv.factory.transform.pyramid.FactoryPyramid
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.factory.transform.pyramid.stubs;


public final class FactoryPyramid_Stub extends boofcv.factory.transform.pyramid.FactoryPyramid implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public FactoryPyramid_Stub () {
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



    // Implementation of scaleSpacePyramid(double[], Class, FactoryImageBorder, FactoryInterpolation)
    public boofcv.struct.pyramid.PyramidFloat scaleSpacePyramid(double[] $param_arrayOf_double_1, java.lang.Class $param_Class_2, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_3, boofcv.factory.interpolate.FactoryInterpolation $param_FactoryInterpolation_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.scaleSpacePyramid( $param_arrayOf_double_1,  $param_Class_2,  $param_FactoryImageBorder_3,  $param_FactoryInterpolation_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.pyramid.PyramidFloat<T> boofcv.factory.transform.pyramid.FactoryPyramid.scaleSpacePyramid(double[],java.lang.Class<T>,boofcv.core.image.border.FactoryImageBorder,boofcv.factory.interpolate.FactoryInterpolation)";
                $__params.add($param_arrayOf_double_1);
                $__params.add($param_Class_2);
                $__params.add($param_FactoryImageBorder_3);
                $__params.add($param_FactoryInterpolation_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.pyramid.PyramidFloat) $__result);
    }

    // Implementation of scaleSpace(double[], Class, FactoryImageBorder, FactoryInterpolation)
    public boofcv.struct.pyramid.PyramidFloat scaleSpace(double[] $param_arrayOf_double_1, java.lang.Class $param_Class_2, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_3, boofcv.factory.interpolate.FactoryInterpolation $param_FactoryInterpolation_4) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.scaleSpace( $param_arrayOf_double_1,  $param_Class_2,  $param_FactoryImageBorder_3,  $param_FactoryInterpolation_4);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.pyramid.PyramidFloat<T> boofcv.factory.transform.pyramid.FactoryPyramid.scaleSpace(double[],java.lang.Class<T>,boofcv.core.image.border.FactoryImageBorder,boofcv.factory.interpolate.FactoryInterpolation)";
                $__params.add($param_arrayOf_double_1);
                $__params.add($param_Class_2);
                $__params.add($param_FactoryImageBorder_3);
                $__params.add($param_FactoryInterpolation_4);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.pyramid.PyramidFloat) $__result);
    }

    // Implementation of floatGaussian(double[], double[], Class, FactoryImageBorder, FactoryInterpolation)
    public boofcv.struct.pyramid.PyramidFloat floatGaussian(double[] $param_arrayOf_double_1, double[] $param_arrayOf_double_2, java.lang.Class $param_Class_3, boofcv.core.image.border.FactoryImageBorder $param_FactoryImageBorder_4, boofcv.factory.interpolate.FactoryInterpolation $param_FactoryInterpolation_5) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.floatGaussian( $param_arrayOf_double_1,  $param_arrayOf_double_2,  $param_Class_3,  $param_FactoryImageBorder_4,  $param_FactoryInterpolation_5);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.pyramid.PyramidFloat<T> boofcv.factory.transform.pyramid.FactoryPyramid.floatGaussian(double[],double[],java.lang.Class<T>,boofcv.core.image.border.FactoryImageBorder,boofcv.factory.interpolate.FactoryInterpolation)";
                $__params.add($param_arrayOf_double_1);
                $__params.add($param_arrayOf_double_2);
                $__params.add($param_Class_3);
                $__params.add($param_FactoryImageBorder_4);
                $__params.add($param_FactoryInterpolation_5);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.pyramid.PyramidFloat) $__result);
    }

    // Implementation of discreteGaussian(int[], double, int, boolean, Class, FactoryKernelGaussian)
    public boofcv.struct.pyramid.PyramidDiscrete discreteGaussian(int[] $param_arrayOf_int_1, double $param_double_2, int $param_int_3, boolean $param_boolean_4, java.lang.Class $param_Class_5, boofcv.factory.filter.kernel.FactoryKernelGaussian $param_FactoryKernelGaussian_6) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.discreteGaussian( $param_arrayOf_int_1,  $param_double_2,  $param_int_3,  $param_boolean_4,  $param_Class_5,  $param_FactoryKernelGaussian_6);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public <T> boofcv.struct.pyramid.PyramidDiscrete<T> boofcv.factory.transform.pyramid.FactoryPyramid.discreteGaussian(int[],double,int,boolean,java.lang.Class<T>,boofcv.factory.filter.kernel.FactoryKernelGaussian)";
                $__params.add($param_arrayOf_int_1);
                $__params.add($param_double_2);
                $__params.add($param_int_3);
                $__params.add($param_boolean_4);
                $__params.add($param_Class_5);
                $__params.add($param_FactoryKernelGaussian_6);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((boofcv.struct.pyramid.PyramidDiscrete) $__result);
    }
}
