package org.boofcv.android.calib;

import java.io.Serializable;
import java.util.List;

import boofcv.abst.geo.calibration.ImageResults;
import boofcv.struct.calib.CameraPinholeRadial;

/**
 * Created by ubuntu on 17/06/08.
 */

public class Calib implements Serializable {

    public Calib(List<ImageResults> res, CameraPinholeRadial intrin) {
        results = res;
        intrinsic = intrin;
    }

    public List<ImageResults> results;
    public CameraPinholeRadial intrinsic;
}
