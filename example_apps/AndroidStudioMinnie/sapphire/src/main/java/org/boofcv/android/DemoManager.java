package org.boofcv.android;

import android.graphics.Bitmap;
import android.renderscript.ScriptGroup;

import org.ddogleg.struct.FastQueue;

import java.util.List;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.feature.detect.edge.GGradientToEdgeFeatures;
import boofcv.alg.feature.detect.edge.GradientToEdgeFeatures;
import boofcv.alg.feature.detect.edge.impl.ImplEdgeNonMaxSuppression;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.LinearContourLabelChang2004;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.filter.binary.impl.ImplBinaryBorderOps;
import boofcv.alg.filter.binary.impl.ImplBinaryInnerOps;
import boofcv.alg.filter.blur.BlurImageOps;
import boofcv.alg.filter.blur.GBlurImageOps;
import boofcv.alg.filter.blur.impl.ImplMedianHistogramInner;
import boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive;
import boofcv.alg.filter.blur.impl.ImplMedianSortNaive;
import boofcv.alg.filter.convolve.ConvolveImageMean;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.ConvolveNormalized;
import boofcv.alg.filter.convolve.noborder.ImplConvolveMean;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.android.VisualizeImageData;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.ConnectRule;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import sapphire.app.SapphireObject;

import static sapphire.runtime.Sapphire.new_;

/**
 * Created by ubuntu on 17/04/03.
 */

public class DemoManager implements SapphireObject<sapphire.policy.offload.CodeOffload>  {
    private FactoryEdgeDetectors FED;
    private ImageType IT;
    private FactoryBlurFilter FBF;
    private FactoryDerivative FD;
    private ImageMiscOps IMO;
    private GeneralizedImageOps GIO;
    private FactoryImageBorder FIB;
    private GBlurImageOps GBIO;
    private ImageBorderValue IBV;
    private GradientToEdgeFeatures GTEF;
    private ImplEdgeNonMaxSuppression IENMS;
    private FactoryImageBorderAlgs FIBA;
    private GGradientToEdgeFeatures GGTEF;
    private ImageStatistics IS;

    private GThresholdImageOps GTIO;
    private ThresholdImageOps TIO;
    private BinaryImageOps BIO;
    private LinearContourLabelChang2004 findContours;
    private GImageStatistics GIS;
    private InputSanityCheck ISC;
    private ImplBinaryInnerOps IBIO;
    private ImplBinaryBorderOps IBBO;
    private BlurImageOps BlIO;

    private ConvolveImageMean CIM;
    private FactoryKernelGaussian FKG;
    private ConvolveNormalized CN;
    private ConvolveNormalizedNaive CNN;
    private ConvolveImageNoBorder CINB;
    private ConvolveNormalized_JustBorder CNJB;
    private ImplMedianHistogramInner IMHI;
    private ImplMedianSortEdgeNaive IMSEN;
    private ImplMedianSortNaive IMSN;
    private ImplConvolveMean ICM;
    private DerivativeHelperFunctions DHF;

    CannyEdge<GrayU8,GrayS16> canny;

    public DemoManager() {    //private VisualizeImageData VID;
        FED = (FactoryEdgeDetectors) new_(FactoryEdgeDetectors.class);
        IT = (ImageType) new_(ImageType.class);
        FBF = (FactoryBlurFilter) new_(FactoryBlurFilter.class);
        FD = (FactoryDerivative) new_(FactoryDerivative.class);
        IMO = (ImageMiscOps) new_(ImageMiscOps.class);
        GIO = (GeneralizedImageOps) new_(GeneralizedImageOps.class);
        FIB = (FactoryImageBorder) new_(FactoryImageBorder.class);
        GBIO = (GBlurImageOps) new_(GBlurImageOps.class);
        IBV = (ImageBorderValue) new_(ImageBorderValue.class);
        GTEF = (GradientToEdgeFeatures) new_(GradientToEdgeFeatures.class);
        IENMS = (ImplEdgeNonMaxSuppression) new_(ImplEdgeNonMaxSuppression.class);
        FIBA = (FactoryImageBorderAlgs) new_(FactoryImageBorderAlgs.class);
        GGTEF = (GGradientToEdgeFeatures) new_(GGradientToEdgeFeatures.class);
        IS = (ImageStatistics) new_(ImageStatistics.class);
        GTIO = (GThresholdImageOps) new_(GThresholdImageOps.class);
        TIO = (ThresholdImageOps) new_(ThresholdImageOps.class);
        BIO = (BinaryImageOps) new_(BinaryImageOps.class);
        findContours = (LinearContourLabelChang2004) new_(LinearContourLabelChang2004.class, ConnectRule.EIGHT);
        GIS = (GImageStatistics) new_(GImageStatistics.class);
        ISC = (InputSanityCheck) new_(InputSanityCheck.class);
        IBIO = (ImplBinaryInnerOps) new_(ImplBinaryInnerOps.class);
        IBBO = (ImplBinaryBorderOps) new_(ImplBinaryBorderOps.class);
        BlIO = (BlurImageOps) new_(BlurImageOps.class);
        CIM = (ConvolveImageMean) new_(ConvolveImageMean.class);
        FKG = (FactoryKernelGaussian) new_(FactoryKernelGaussian.class);
        CN = (ConvolveNormalized) new_(ConvolveNormalized.class);
        CNN = (ConvolveNormalizedNaive) new_(ConvolveNormalizedNaive.class);
        CINB = (ConvolveImageNoBorder) new_(ConvolveImageNoBorder.class);
        CNJB = (ConvolveNormalized_JustBorder) new_(ConvolveNormalized_JustBorder.class);
        IMHI = (ImplMedianHistogramInner) new_(ImplMedianHistogramInner.class);
        IMSEN = (ImplMedianSortEdgeNaive) new_(ImplMedianSortEdgeNaive.class);
        IMSN = (ImplMedianSortNaive) new_(ImplMedianSortNaive.class);
        ICM = (ImplConvolveMean) new_(ImplConvolveMean.class);
        DHF = (DerivativeHelperFunctions) new_(DerivativeHelperFunctions.class);
    }

    public void canny( int blurRadius , boolean saveTrace , boolean dynamicThreshold) {
        canny = cannyEdge(blurRadius, saveTrace, dynamicThreshold, GrayU8.class, GrayS16.class);
    }

    public <T extends ImageGray, D extends ImageGray>
    CannyEdge<T,D> cannyEdge( int blurRadius , boolean saveTrace , boolean dynamicThreshold, Class<T> imageType , Class<D> derivType ) {
        return FED.canny(blurRadius, saveTrace, dynamicThreshold, imageType, derivType, FBF, FD, GIO, IT, FIB);
    }

    public void process(GrayU8 input , float threshLow, float threshHigh , GrayU8 output) {
        canny.process(input, threshLow, threshHigh, output, IMO, GBIO, IBV, GTEF, IENMS, FIBA, GGTEF, IS, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, DHF);
    }

    public int computeOtsu(ImageGray input , int minValue , int maxValue ) {
        return GTIO.computeOtsu(input,minValue,maxValue, GIS, IS);
    }

    public GrayU8 threshold(GrayU8 input , GrayU8 output ,
                            int threshold , boolean down) {
        return TIO.threshold(input, output, threshold, down, ISC, GIO);
    }

    public GrayU8 removePointNoise(GrayU8 input, GrayU8 output) {
        return BIO.removePointNoise(input, output, ISC, IBIO, IBBO, IBV);
    }

    public void findContoursProcess(GrayU8 binary , GrayS32 labeled ) {
        findContours.process(binary, labeled, IMO);
    }

    public FastQueue<Contour> findContoursGetContours() {
        return findContours.getContours();
    }

    public List<EdgeContour> getContours() {
        return canny.getContours();
    }

    public <I extends ImageGray> ImageType<I> single(Class<I> imageType ) {
        return IT.single(imageType);
    }

}