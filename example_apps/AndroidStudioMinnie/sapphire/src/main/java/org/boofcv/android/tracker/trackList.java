package org.boofcv.android.tracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import boofcv.abst.feature.tracker.PointTrack;

/**
 * Created by ubuntu on 17/05/26.
 */

public class trackList implements Serializable {
    public List<PointTrack> active;
    public List<PointTrack> spawned;

    public trackList() {
        active = new ArrayList<PointTrack>();
        spawned = new ArrayList<PointTrack>();
    }
}
