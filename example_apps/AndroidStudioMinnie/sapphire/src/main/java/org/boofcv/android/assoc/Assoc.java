package org.boofcv.android.assoc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import georegression.struct.point.Point2D_F64;

/**
 * Created by ubuntu on 17/05/22.
 */

public class Assoc implements Serializable {

    public List<Point2D_F64> Src;
    public List<Point2D_F64> Dst;

    public Assoc() {
        Src = new ArrayList<Point2D_F64>();
        Dst = new ArrayList<Point2D_F64>();
    }
}
