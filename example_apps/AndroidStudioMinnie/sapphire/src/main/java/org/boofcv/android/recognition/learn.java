package org.boofcv.android.recognition;

import org.ddogleg.struct.FastQueue;
import org.ddogleg.struct.GrowQueue_F64;
import org.ddogleg.struct.GrowQueue_I32;

import java.io.Serializable;

/**
 * Created by ubuntu on 17/06/15.
 */

public class learn implements Serializable {
    public int numDetected;
    public FiducialDetector.Detected detected[];

    public learn() {
        detected = new org.boofcv.android.recognition.FiducialDetector.Detected[3];
    }
}
