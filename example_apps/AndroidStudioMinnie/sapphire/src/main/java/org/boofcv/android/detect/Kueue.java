package org.boofcv.android.detect;

import java.io.Serializable;

import boofcv.struct.QueueCorner;

/**
 * Created by ubuntu on 17/06/05.
 */

public class Kueue implements Serializable {

    public QueueCorner maximums;
    public QueueCorner minimums;

    public Kueue() {
        maximums = new QueueCorner();
        minimums = new QueueCorner();
    }

    public Kueue(QueueCorner max, QueueCorner min) {
        maximums = max;
        minimums = min;
    }
}
