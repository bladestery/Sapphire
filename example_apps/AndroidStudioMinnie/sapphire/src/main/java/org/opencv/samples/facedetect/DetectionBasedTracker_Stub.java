/*
 * Stub for class org.opencv.samples.facedetect.DetectionBasedTracker
 * Generated by Sapphire Compiler (sc).
 */
package org.opencv.samples.facedetect.stubs;


public final class DetectionBasedTracker_Stub extends org.opencv.samples.facedetect.DetectionBasedTracker implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public DetectionBasedTracker_Stub (java.lang.String $param_String_1, int $param_int_2) {
        super($param_String_1, $param_int_2);
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



    // Implementation of stop()
    public void stop() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.stop();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.samples.facedetect.DetectionBasedTracker.stop()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of start()
    public void start() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.start();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.samples.facedetect.DetectionBasedTracker.start()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of setMinFaceSize(int)
    public void setMinFaceSize(int $param_int_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.setMinFaceSize( $param_int_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.samples.facedetect.DetectionBasedTracker.setMinFaceSize(int)";
                $__params.add($param_int_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of release()
    public void release() {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.release();
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.samples.facedetect.DetectionBasedTracker.release()";
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of detect(Mat, MatOfRect)
    public void detect(org.opencv.core.Mat $param_Mat_1, org.opencv.core.MatOfRect $param_MatOfRect_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.detect( $param_Mat_1,  $param_MatOfRect_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.samples.facedetect.DetectionBasedTracker.detect(org.opencv.core.Mat,org.opencv.core.MatOfRect)";
                $__params.add($param_Mat_1);
                $__params.add($param_MatOfRect_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of create(String, int)
    public void create(java.lang.String $param_String_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.create( $param_String_1,  $param_int_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public void org.opencv.samples.facedetect.DetectionBasedTracker.create(java.lang.String,int)";
                $__params.add($param_String_1);
                $__params.add($param_int_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
