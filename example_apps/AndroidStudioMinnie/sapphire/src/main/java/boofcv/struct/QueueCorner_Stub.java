/*
 * Stub for class boofcv.struct.QueueCorner
 * Generated by Sapphire Compiler (sc).
 */
package boofcv.struct.stubs;


public final class QueueCorner_Stub extends boofcv.struct.QueueCorner implements sapphire.common.AppObjectStub {

    sapphire.policy.SapphirePolicy.SapphireClientPolicy $__client = null;
    boolean $__directInvocation = false;

    public QueueCorner_Stub (int $param_int_1) {
        super($param_int_1);
    }

    public QueueCorner_Stub () {
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

    // Implementation of add(int, int)
    public void add(int $param_int_1, int $param_int_2) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.add( $param_int_1,  $param_int_2);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public final void boofcv.struct.QueueCorner.add(int,int)";
                $__params.add($param_int_1);
                $__params.add($param_int_2);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementation of add(Point2D_I16)
    public void add(georegression.struct.point.Point2D_I16 $param_Point2D_I16_1) {
        java.lang.Object $__result = null;
        try {
            if ($__directInvocation)
                super.add( $param_Point2D_I16_1);
            else {
                java.util.ArrayList<Object> $__params = new java.util.ArrayList<Object>();
                String $__method = "public final void boofcv.struct.QueueCorner.add(georegression.struct.point.Point2D_I16)";
                $__params.add($param_Point2D_I16_1);
                $__result = $__client.onRPC($__method, $__params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}