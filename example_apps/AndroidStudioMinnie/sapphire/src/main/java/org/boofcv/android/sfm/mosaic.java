package org.boofcv.android.sfm;

import org.ddogleg.struct.FastQueue;

import java.io.Serializable;

import boofcv.alg.sfm.d2.StitchingFromMotion2D;
import georegression.struct.point.Point2D_F64;

/**
 * Created by ubuntu on 17/06/13.
 */

public class mosaic implements Serializable {
    public FastQueue<Point2D_F64> inliersGui;
    public FastQueue<Point2D_F64> outliersGui;
    public StitchingFromMotion2D.Corners corners;

    public mosaic() {}
}
