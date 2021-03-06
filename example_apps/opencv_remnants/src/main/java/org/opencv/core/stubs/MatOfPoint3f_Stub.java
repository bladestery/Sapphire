/*
 * Stub for class org.opencv.core.MatOfPoint3f
 * Generated by Sapphire Compiler (sc).
 */
package org.opencv.core.stubs;


public final class MatOfPoint3f_Stub extends org.opencv.core.MatOfPoint3f implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public MatOfPoint3f_Stub () {
        super();
    }

    public MatOfPoint3f_Stub (org.opencv.core.Mat $param_Mat_1) {
        super($param_Mat_1);
    }

    public MatOfPoint3f_Stub (org.opencv.core.Point3[] $param_arrayOf_Point3_1) {
        super($param_arrayOf_Point3_1);
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
                String $__method = "public java.util.List<org.opencv.core.Point3> org.opencv.core.MatOfPoint3f.toList()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((java.util.List) $__result);
    }

    // Implementation of toArray()
    public org.opencv.core.Point3[] toArray() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.toArray();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public org.opencv.core.Point3[] org.opencv.core.MatOfPoint3f.toArray()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((org.opencv.core.Point3[]) $__result);
    }

    // Implementation of fromNativeAddr(long)
    public org.opencv.core.MatOfPoint3f fromNativeAddr(long $param_long_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                $__result = super.fromNativeAddr( $param_long_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public static org.opencv.core.MatOfPoint3f org.opencv.core.MatOfPoint3f.fromNativeAddr(long)";
                $__params.add($param_long_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((org.opencv.core.MatOfPoint3f) $__result);
    }

    // Implementation of fromList(List)
    public void fromList(java.util.List $param_List_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.fromList( $param_List_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.core.MatOfPoint3f.fromList(java.util.List<org.opencv.core.Point3>)";
                $__params.add($param_List_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of fromArray(Point3[])
    public void fromArray(org.opencv.core.Point3[] $param_arrayOf_Point3_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.fromArray( $param_arrayOf_Point3_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.core.MatOfPoint3f.fromArray(org.opencv.core.Point3...)";
                $__params.add($param_arrayOf_Point3_1);
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
                String $__method = "public void org.opencv.core.MatOfPoint3f.alloc(int)";
                $__params.add($param_int_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
