package org.boofcv.android.sfm;

import java.io.Serializable;

import boofcv.struct.image.GrayF32;

/**
 * Created by ubuntu on 17/06/13.
 */

public class leftright implements Serializable {
    public GrayF32 Left;
    public GrayF32 Right;

    public leftright() {}
}
