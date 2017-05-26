package org.boofcv.android.tracker;

import java.io.Serializable;

import georegression.struct.shapes.Quadrilateral_F64;

/**
 * Created by ubuntu on 17/05/25.
 */

public class track implements Serializable {
    public boolean visible;
    public Quadrilateral_F64 location;

    public track() {
        location = new Quadrilateral_F64();
    }
}
