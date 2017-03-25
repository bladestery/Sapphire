package org.opencv.samples.facedetect;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;

import sapphire.app.SapphireObject;
import sapphire.kernel.server.KernelServer;

import static sapphire.runtime.Sapphire.new_;

/**
 * Created by bladestery on 28/2/2560.
 */

public class CascadeManager implements SapphireObject<sapphire.policy.offload.CodeOffload> {
    static final long serialVersionUID =-507093097032853979L;
    private CascadeClassifier cc;
    private DetectionBasedTracker dbt;

    public CascadeManager() {
    }

    public void initCC(String filename) {
        cc = (CascadeClassifier) new_(CascadeClassifier.class, filename);
    }

    public void initDBT(String filename, Integer i) {
        dbt = (DetectionBasedTracker) new_(DetectionBasedTracker.class, filename, i);
    }

    public void assignCC(CascadeClassifier mJavaDetector) { cc = mJavaDetector; }
    public void assignDBT(DetectionBasedTracker mNativeDetector) {
        dbt = mNativeDetector;
    }
    public void localize() {}
    public boolean empty() {
        return cc.empty();
    }
    public void detectMultiScale(Mat image, MatOfRect objects, double scaleFactor, int minNeighbors, int flags, Size minSize, Size maxSize) {
        cc.detectMultiScale(image, objects, scaleFactor, minNeighbors, flags, minSize, maxSize);
    }

    public CascadeClassifier getClassifier() {
        return cc;
    }
    public DetectionBasedTracker getTracker() { return dbt; }
}
