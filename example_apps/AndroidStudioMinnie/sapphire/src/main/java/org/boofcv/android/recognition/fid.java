package org.boofcv.android.recognition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import georegression.struct.se.Se3_F64;

/**
 * Created by ubuntu on 17/06/15.
 */

public class fid implements Serializable {

    public fid() {
        number = new ArrayList<>();
        targetToCameral = new ArrayList<>();
        width = new ArrayList<>();
    }
    public int totalFound;
    public List<Long> number;
    public List<Se3_F64> targetToCameral;
    public List<Double> width;
}
