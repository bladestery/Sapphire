package org.boofcv.android.tracker;

import java.io.Serializable;

import georegression.struct.point.Point2D_F64;

/**
 * Created by ubuntu on 17/05/26.
 */

public class trackInfo implements Serializable {
    public long lastActive;
    public Point2D_F64 spawn = new Point2D_F64();
}