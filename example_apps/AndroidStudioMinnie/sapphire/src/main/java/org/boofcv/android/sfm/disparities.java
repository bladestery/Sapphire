package org.boofcv.android.sfm;

import java.io.Serializable;
import java.util.List;

import boofcv.struct.geo.AssociatedPair;
import boofcv.struct.image.GrayF32;

/**
 * Created by ubuntu on 17/06/13.
 */

public class disparities implements Serializable {
    public int disparityMin;
    public int disparityMax;
    public GrayF32 disparity;
    public List<AssociatedPair> Inliers;

    public disparities() {
    }
}
