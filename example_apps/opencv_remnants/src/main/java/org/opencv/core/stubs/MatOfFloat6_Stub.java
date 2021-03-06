/*
 * Stub for class org.opencv.core.MatOfFloat6
 * Generated by Sapphire Compiler (sc).
 */
package org.opencv.core.stubs;


public final class MatOfFloat6_Stub extends org.opencv.core.MatOfFloat6 implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public MatOfFloat6_Stub () {
        super();
    }

    public MatOfFloat6_Stub (org.opencv.core.Mat $param_Mat_1) {
        super($param_Mat_1);
    }

    public MatOfFloat6_Stub (float[] $param_arrayOf_float_1) {
        super($param_arrayOf_float_1);
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



    // Implementation of toList()
    public java.util.List toList() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.toList();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public java.util.List<java.lang.Float> org.opencv.core.MatOfFloat6.toList()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of toArray()
    public float[] toArray() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.toArray();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public float[] org.opencv.core.MatOfFloat6.toArray()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((float[]) $__result);
    }

    // Implementation of fromNativeAddr(long)
    public org.opencv.core.MatOfFloat6 fromNativeAddr(long $param_long_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.fromNativeAddr( $param_long_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public static org.opencv.core.MatOfFloat6 org.opencv.core.MatOfFloat6.fromNativeAddr(long)";
                $__params.add($param_long_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((org.opencv.core.MatOfFloat6) $__result);
    }

    // Implementation of fromList(List)
    public void fromList(java.util.List $param_List_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.fromList( $param_List_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.core.MatOfFloat6.fromList(java.util.List<java.lang.Float>)";
                $__params.add($param_List_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of fromArray(float[])
    public void fromArray(float[] $param_arrayOf_float_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.fromArray( $param_arrayOf_float_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.core.MatOfFloat6.fromArray(float...)";
                $__params.add($param_arrayOf_float_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of alloc(int)
    public void alloc(int $param_int_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.alloc( $param_int_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.core.MatOfFloat6.alloc(int)";
                $__params.add($param_int_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
