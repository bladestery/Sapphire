package org.boofcv.android;

import com.sun.org.apache.bcel.internal.generic.FADD;
import com.thoughtworks.xstream.core.util.Pool;

import org.boofcv.android.assoc.Assoc;
import org.boofcv.android.tracker.track;
import org.boofcv.android.tracker.trackInfo;
import org.boofcv.android.tracker.trackList;
import org.ddogleg.struct.FastQueue;
import org.hamcrest.Factory;

import java.util.ArrayList;
import java.util.List;

import boofcv.abst.distort.FDistort;
import boofcv.abst.feature.associate.AssociateDescTo2D;
import boofcv.abst.feature.associate.AssociateDescription;
import boofcv.abst.feature.associate.AssociateDescription2D;
import boofcv.abst.feature.associate.ScoreAssociation;
import boofcv.abst.feature.describe.ConfigSiftScaleSpace;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.detect.extract.ConfigExtract;
import boofcv.abst.feature.detect.extract.NonMaxSuppression;
import boofcv.abst.feature.detect.intensity.GeneralFeatureIntensity;
import boofcv.abst.feature.detect.interest.ConfigFastHessian;
import boofcv.abst.feature.detect.interest.ConfigGeneralDetector;
import boofcv.abst.feature.detect.interest.ConfigSiftDetector;
import boofcv.abst.feature.detect.interest.InterestPointDetector;
import boofcv.abst.feature.detect.line.DetectLine;
import boofcv.abst.feature.detect.line.DetectLineHoughFoot;
import boofcv.abst.feature.detect.line.DetectLineHoughPolar;
import boofcv.abst.feature.detect.line.DetectLineSegment;
import boofcv.abst.feature.detect.line.DetectLineSegmentsGridRansac;
import boofcv.abst.feature.tracker.PointTrack;
import boofcv.abst.feature.tracker.PointTracker;
import boofcv.abst.filter.binary.InputToBinary;
import boofcv.abst.filter.blur.BlurFilter;
import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.abst.segmentation.ImageSuperpixels;
import boofcv.abst.tracker.ConfigCirculantTracker;
import boofcv.abst.tracker.ConfigComaniciu2003;
import boofcv.abst.tracker.ConfigTld;
import boofcv.abst.tracker.MeanShiftLikelihoodType;
import boofcv.abst.tracker.TrackerObjectQuad;
import boofcv.abst.transform.fft.DiscreteFourierTransform;
import boofcv.abst.transform.wavelet.WaveletTransform;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.background.BackgroundModelStationary;
import boofcv.alg.background.stationary.BackgroundStationaryBasic;
import boofcv.alg.background.stationary.BackgroundStationaryGaussian;
import boofcv.alg.descriptor.UtilFeature;
import boofcv.alg.enhance.EnhanceImageOps;
import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.feature.detect.edge.GGradientToEdgeFeatures;
import boofcv.alg.feature.detect.edge.GradientToEdgeFeatures;
import boofcv.alg.feature.detect.edge.impl.ImplEdgeNonMaxSuppression;
import boofcv.alg.feature.detect.edge.impl.ImplEdgeNonMaxSuppressionCrude;
import boofcv.alg.feature.detect.intensity.HessianBlobIntensity;
import boofcv.alg.feature.detect.interest.EasyGeneralFeatureDetector;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
import boofcv.alg.feature.detect.interest.GeneralFeatureDetector;
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
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.convolve.noborder.ImplConvolveMean;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.misc.PixelMath;
import boofcv.alg.segmentation.ComputeRegionMeanColor;
import boofcv.alg.segmentation.ImageSegmentationOps;
import boofcv.alg.shapes.ellipse.BinaryEllipseDetector;
import boofcv.alg.shapes.polygon.BinaryPolygonDetector;
import boofcv.alg.tracker.klt.PkltConfig;
import boofcv.alg.tracker.sfot.SfotConfig;
import boofcv.alg.transform.fft.DiscreteFourierTransformOps;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.BorderType;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.background.ConfigBackgroundBasic;
import boofcv.factory.background.ConfigBackgroundGaussian;
import boofcv.factory.background.FactoryBackgroundModel;
import boofcv.factory.feature.associate.FactoryAssociation;
import boofcv.factory.feature.tracker.FactoryPointTracker;
import boofcv.factory.tracker.FactoryTrackerObjectQuad;
import boofcv.factory.transform.pyramid.FactoryPyramid;
import boofcv.factory.transform.wavelet.FactoryWaveletTransform;
import boofcv.factory.transform.wavelet.GFactoryWavelet;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.factory.feature.detect.intensity.FactoryIntensityPoint;
import boofcv.factory.feature.detect.intensity.FactoryIntensityPointAlg;
import boofcv.factory.feature.detect.interest.FactoryInterestPoint;
import boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs;
import boofcv.factory.feature.detect.line.ConfigHoughFoot;
import boofcv.factory.feature.detect.line.ConfigHoughPolar;
import boofcv.factory.feature.detect.line.FactoryDetectLineAlgs;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.segmentation.ConfigFh04;
import boofcv.factory.segmentation.ConfigSegmentMeanShift;
import boofcv.factory.segmentation.ConfigSlic;
import boofcv.factory.segmentation.ConfigWatershed;
import boofcv.factory.segmentation.FactoryImageSegmentation;
import boofcv.factory.segmentation.FactorySegmentationAlg;
import boofcv.factory.shape.ConfigEllipseDetector;
import boofcv.factory.shape.ConfigPolygonDetector;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.struct.ConnectRule;
import boofcv.struct.QueueCorner;
import boofcv.struct.feature.AssociatedIndex;
import boofcv.struct.feature.ColorQueue_F32;
import boofcv.struct.feature.ScalePoint;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.feature.TupleDesc_B;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayS16pair;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageDataType;
import boofcv.struct.image.ImageDimension;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageInterleaved;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.InterleavedF32;
import boofcv.struct.image.Planar;
import boofcv.struct.pyramid.ImagePyramid;
import boofcv.struct.wavelet.WaveletDescription;
import boofcv.struct.wavelet.WlCoef;
import georegression.struct.line.LineParametric2D_F32;
import georegression.struct.line.LineSegment2D_F32;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point2D_I32;
import georegression.struct.shapes.EllipseRotated_F64;
import georegression.struct.shapes.Polygon2D_F64;
import georegression.struct.shapes.Quadrilateral_F64;
import sapphire.app.SapphireObject;

import static org.boofcv.android.CreateDetectorDescriptor.DESC_BRIEF;
import static org.boofcv.android.CreateDetectorDescriptor.DESC_NCC;
import static org.boofcv.android.CreateDetectorDescriptor.DESC_SURF;
import static org.boofcv.android.CreateDetectorDescriptor.DETECT_FAST;
import static org.boofcv.android.CreateDetectorDescriptor.DETECT_FH;
import static org.boofcv.android.CreateDetectorDescriptor.DETECT_SHITOMASI;
import static sapphire.runtime.Sapphire.new_;

/**
 * Created by ubuntu on 17/04/03.
 */

public class DemoManager implements SapphireObject<sapphire.policy.offload.CodeOffload>  {
    //Top Level Objects which need to be created
    private FactoryEdgeDetectors FED;
    private ImageType IT;
    private FactoryBlurFilter FBF;
    private FactoryDerivative FD;
    private ImageStatistics IS;
    private GThresholdImageOps GTIO;
    private ThresholdImageOps TIO;
    private BinaryImageOps BIO;
    private InputSanityCheck ISC;
    private FactoryShapeDetector FSD;
    private FactoryThresholdBinary FTB;
    private FactoryDetectLineAlgs FDLA;
    private FactoryFeatureExtractor FFE;
    private FactoryIntensityPoint FIP;
    private FactoryInterestPoint FIrP;
    private FactoryInterestPointAlgs FIrPA;
    private FactoryImageSegmentation FIS;
    private FactorySegmentationAlg FSA;
    private ImageSegmentationOps ISO;
    private EnhanceImageOps EIO;
    private ConvertImage CI;
    private DiscreteFourierTransformOps DFTO;
    private FactoryPyramid FP;
    private GFactoryWavelet GFW;
    private FactoryWaveletTransform FWT;
    private UtilWavelet UW;
    private CreateDetectorDescriptor CDD;
    private FactoryAssociation FA;
    private FactoryTrackerObjectQuad FTOQ;
    private FactoryPointTracker FPT;
    private FactoryBackgroundModel FBM;

    //  Lower Level Objects (TODO: check if necessary??)
    /** info: Necessary when using entire Sapphire, but current
              code is incomplete, so some should be removed??   **/
    private ImageMiscOps IMO;
    private GeneralizedImageOps GIO;
    private FactoryImageBorder FIB;
    private GBlurImageOps GBIO;
    private ImageBorderValue IBV;
    private GradientToEdgeFeatures GTEF;
    private ImplEdgeNonMaxSuppression IENMS;
    private FactoryImageBorderAlgs FIBA;
    private GGradientToEdgeFeatures GGTEF;
    private GImageStatistics GIS;
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
    private ConvolveJustBorder_General CJBG;
    private GradientSobel_Outer GSO;
    private GradientSobel_UnrolledOuter GSUO;
    private ImplEdgeNonMaxSuppressionCrude IENMSC;
    private FactoryIntensityPointAlg FIPA;
    private GImageMiscOps GIMO;
    private FastHessianFeatureDetector FHFD;

    //  Objects which are used in applications
    /** info: Some are redundantly used to save space **/
    CannyEdge<GrayU8,GrayS16> canny;
    LinearContourLabelChang2004 contour8;
    LinearContourLabelChang2004 contour4;
    BinaryEllipseDetector<GrayU8> ellipseDetector;
    InputToBinary<GrayU8> inputToBinary;
    BinaryPolygonDetector<GrayU8> polyDetector;
    GrayU8 blackBinary;
    DetectLine<GrayU8> lineDetector;
    DetectLineSegment<GrayU8> segDetector;
    GeneralFeatureDetector<GrayU8,GrayS16> general;
    EasyGeneralFeatureDetector<GrayU8,GrayS16> pointDetector;
    GeneralFeatureIntensity<GrayU8, GrayS16> intensity;
    NonMaxSuppression nonmaxMax;
    NonMaxSuppression nonmaxMinMax;
    NonMaxSuppression nonmaxCandidate;
    NonMaxSuppression nonmax;
    InterestPointDetector<GrayU8> scaleDetector;
    FastQueue<ScalePoint> foundGUI;
    ImageType<Planar<GrayU8>> type;
    ImageSuperpixels<Planar<GrayU8>> segmentation;
    FastQueue<float[]> segmentColor;
    FastQueue<Integer> regionMemberCount;
    GrayS32 pixelToRegion;
    Planar<GrayU8> background;
    GrayU8 binary;
    GrayU8 afterOps;
    BlurFilter<GrayU8> filter;
    int histogram[];
    int transform[];
    ImageGradient<GrayU8,GrayS16> gradient;
    GrayS16pair deriv;
    DiscreteFourierTransform<GrayF32,InterleavedF32> dft;
    GrayF32 grayF;
    InterleavedF32 interleavedTransform;
    ImagePyramid<GrayU8> pyramid;
    WaveletDescription<WlCoef> desc;
    WaveletTransform<GrayU8,GrayS32,WlCoef> waveletTran;
    DetectDescribePoint<GrayF32,TupleDesc> detDesc;
    AssociateDescription<TupleDesc> associate;
    FastQueue<TupleDesc> listSrc;
    FastQueue<TupleDesc> listDst;
    FastQueue<Point2D_F64> locationSrc;
    FastQueue<Point2D_F64> locationDst;
    TrackerObjectQuad tracker;
    PointTracker<GrayU8> pointTracker;
    trackList tracklist;
    List<PointTrack> inactive;
    long tick;
    BackgroundModelStationary model;
    ImageBase scaled;
    ImageGray work;
    FDistort shrink;
    int tableDet[] = new int[]{DETECT_SHITOMASI,DETECT_FAST,DETECT_FH};
    int tableDesc[] = new int[]{DESC_BRIEF,DESC_SURF,DESC_NCC};

    //Initialization
    public DemoManager() {
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
        FSD = (FactoryShapeDetector) new_(FactoryShapeDetector.class);
        FTB = (FactoryThresholdBinary) new_(FactoryThresholdBinary.class);
        FDLA = (FactoryDetectLineAlgs) new_(FactoryDetectLineAlgs.class);
        FFE = (FactoryFeatureExtractor) new_(FactoryFeatureExtractor.class);
        CJBG = (ConvolveJustBorder_General) new_(ConvolveJustBorder_General.class);
        GSO = (GradientSobel_Outer) new_(GradientSobel_Outer.class);
        GSUO = (GradientSobel_UnrolledOuter) new_(GradientSobel_UnrolledOuter.class);
        IENMSC = (ImplEdgeNonMaxSuppressionCrude) new_(ImplEdgeNonMaxSuppressionCrude.class);
        FIP = (FactoryIntensityPoint) new_(FactoryIntensityPoint.class);
        FIPA = (FactoryIntensityPointAlg) new_(FactoryIntensityPointAlg.class);
        GIMO = (GImageMiscOps) new_(GImageMiscOps.class);
        FIrP = (FactoryInterestPoint) new_(FactoryInterestPoint.class);
        FIrPA = (FactoryInterestPointAlgs) new_(FactoryInterestPointAlgs.class);
        FHFD = (FastHessianFeatureDetector) new_(FastHessianFeatureDetector.class);
        FIS = (FactoryImageSegmentation) new_(FactoryImageSegmentation.class);
        FSA = (FactorySegmentationAlg) new_(FactorySegmentationAlg.class);
        ISO = (ImageSegmentationOps) new_(ImageSegmentationOps.class);
        EIO = (EnhanceImageOps) new_(EnhanceImageOps.class);
        CI = (ConvertImage) new_(ConvertImage.class);
        DFTO = (DiscreteFourierTransformOps) new_(DiscreteFourierTransformOps.class);
        FP = (FactoryPyramid) new_(FactoryPyramid.class);
        GFW = (GFactoryWavelet) new_(GFactoryWavelet.class);
        FWT = (FactoryWaveletTransform) new_(FactoryWaveletTransform.class);
        UW = (UtilWavelet) new_(UtilWavelet.class);
        CDD = (CreateDetectorDescriptor) new_(CreateDetectorDescriptor.class);
        FA = (FactoryAssociation) new_(FactoryAssociation.class);
        FTOQ = (FactoryTrackerObjectQuad) new_(FactoryTrackerObjectQuad.class);
        FPT = (FactoryPointTracker) new_(FactoryPointTracker.class);
        FBM = (FactoryBackgroundModel) new_(FactoryBackgroundModel.class); 

        //TODO: move these initializations to functions below
        contour8 = new LinearContourLabelChang2004(ConnectRule.EIGHT);
        contour4 = new LinearContourLabelChang2004(ConnectRule.FOUR);
        blackBinary = new GrayU8(1,1);
    }

    public void LatencyCheck() {}
    /*
    public <I extends ImageGray> ImageType<I> single(Class<I> imageType ) {
        return IT.single(imageType);
    }*/

    public void canny( int blurRadius , boolean saveTrace , boolean dynamicThreshold) {
        canny = cannyEdge(blurRadius, saveTrace, dynamicThreshold, GrayU8.class, GrayS16.class);
    }

    public <T extends ImageGray, D extends ImageGray>
    CannyEdge<T,D> cannyEdge( int blurRadius , boolean saveTrace , boolean dynamicThreshold,
                              Class<T> imageType , Class<D> derivType ) {
        return FED.canny(blurRadius, saveTrace, dynamicThreshold, imageType, derivType,
                FBF, FD, GIO, IT, FIB);
    }

    public List<EdgeContour> edgeProcess(GrayU8 input , float threshLow, float threshHigh , GrayU8 output) {
        canny.process(input, threshLow, threshHigh, output,
                IMO, GBIO, IBV, GTEF, IENMS, FIBA, GGTEF, IS, ISC, GIO, BlIO, CIM, FKG, CN, CNN,
                CINB, CNJB, IMHI, IMSEN, IMSN, ICM, DHF, GTIO, GIS, TIO, CJBG, GSO, GSUO, GIMO, CI,
                UW, IT);
        return canny.getContours();
    }

    public void process(GrayU8 input , float threshLow, float threshHigh , GrayU8 output) {
        canny.process(input, threshLow, threshHigh, output,
                IMO, GBIO, IBV, GTEF, IENMS, FIBA, GGTEF, IS, ISC, GIO, BlIO, CIM, FKG, CN, CNN,
                CINB, CNJB, IMHI, IMSEN, IMSN, ICM, DHF, GTIO, GIS, TIO, CJBG, GSO, GSUO, GIMO, CI,
                UW, IT);
    }

    public List<EdgeContour> getContours() {
        return canny.getContours();
    }

    public GrayU8 contourFilter(ImageGray input, int minValue, int maxValue, boolean down) {
        int mean = GTIO.computeOtsu(input, minValue, maxValue, GIS, IS);
        GrayU8 binary = TIO.threshold((GrayU8) input, null, mean, down, ISC, GIO);
        return BIO.removePointNoise(binary, null, ISC, IBIO, IBBO, IBV);
    }

    public FastQueue<Contour> contourProcess(GrayU8 binary, GrayS32 labeled) {
        contour8.process(binary, labeled, IMO);
        return contour8.getContours();
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
        contour8.process(binary, labeled, IMO);
    }

    public FastQueue<Contour> findContoursGetContours() {
        return contour8.getContours();
    }

    public void ellipse(ConfigEllipseDetector config) {
        ellipseDetector = Ellipse(config, GrayU8.class);
    }

    public <T extends ImageGray>
    BinaryEllipseDetector<T> Ellipse(ConfigEllipseDetector config , Class<T> imageType) {
        return FSD.ellipse(config, imageType);
    }

    public GrayU8 detectorProcess(GrayU8 image) {
        ellipseDetector.process(image, blackBinary, contour4, IMO);
        return blackBinary;
    }

    public FastQueue<EllipseRotated_F64> getFoundEllipses() {
        return ellipseDetector.getFoundEllipses();
    }

    public void globalOtsu(int minValue, int maxValue, boolean down) {
        inputToBinary = FTB.globalOtsu(minValue, maxValue, down, GrayU8.class, IT);
    }

    public void globalEntropy(int minValue, int maxValue, boolean down) {
        inputToBinary = FTB.globalEntropy(minValue, maxValue, down, GrayU8.class, IT);
    }

    public void localSquare(int radius, double scale, boolean down) {
        inputToBinary = FTB.localSquare(radius, scale, down, GrayU8.class, IT);
    }

    public void localGaussian(int radius, double scale, boolean down) {
        inputToBinary = FTB.localGaussian(radius, scale, down, GrayU8.class, IT);
    }

    public void localSauvola(int radius, float k, boolean down) {
        inputToBinary = FTB.localSauvola(radius, k, down, GrayU8.class, IT);
    }

    public GrayU8 thresholdProcess(GrayU8 input) {
        inputToBinary.process(input, binary, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB, CNJB,
                IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT);
        return binary;
    }

    public void inputProcess(GrayU8 image) {
        inputToBinary.process(image, blackBinary, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB,
                CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT);
    }

    public void reshape(int width, int height) {
        blackBinary.reshape(width, height);
    }

    public void polygon(ConfigPolygonDetector config) {
        polyDetector = Polygon(config, GrayU8.class);
    }

    public <T extends ImageGray>
    BinaryPolygonDetector<T> Polygon(ConfigPolygonDetector config, Class<T> imageType) {
        return FSD.polygon(config,imageType);
    }

    public void setConvex(boolean convex) {
        polyDetector.setConvex(convex);
    }

    public void setNumberOfSides( int min , int max ) {
        polyDetector.setNumberOfSides(min, max);
    }

    public GrayU8 polyProcess(GrayU8 image) {
        polyDetector.process(image, blackBinary, ISC, IMO, contour4);
        return blackBinary;
    }

    public FastQueue<Polygon2D_F64> getFoundPolygons() {
        return polyDetector.getFoundPolygons();
    }

    public void houghFoot(ConfigHoughFoot config) {
        lineDetector = HoughFoot(config,GrayU8.class,GrayS16.class);
    }

    public <I extends ImageGray, D extends ImageGray>
    DetectLineHoughFoot<I,D> HoughFoot(ConfigHoughFoot config ,
                                       Class<I> imageType ,
                                       Class<D> derivType ) {
        return FDLA.houghFoot(config, imageType, derivType, FD, GIO, FIB, IT, FFE);
    }

    public void houghPolar(ConfigHoughPolar config) {
        lineDetector = HoughPolar(config,GrayU8.class,GrayS16.class);
    }

    public <I extends ImageGray, D extends ImageGray>
    DetectLineHoughPolar<I,D> HoughPolar(ConfigHoughPolar config ,
                                         Class<I> imageType ,
                                         Class<D> derivType ) {
        return FDLA.houghPolar(config, imageType, derivType, FD, GIO, FIB, IT, FFE);
    }

    public List<LineParametric2D_F32> lineDetect(GrayU8 gray) {
        return lineDetector.detect(gray, ISC, DHF, CINB, GTEF, GIO, TIO, GGTEF, CJBG, GSO, GSUO,
                IMO, IENMSC, FIBA, IBV);
    }

    public void lineRansac() {
        segDetector = LineRansac(40,30,2.36, true, GrayU8.class, GrayS16.class);
    }

    public <I extends ImageGray, D extends ImageGray>
    DetectLineSegmentsGridRansac<I,D> LineRansac(int regionSize ,
                                                 double thresholdEdge ,
                                                 double thresholdAngle ,
                                                 boolean connectLines,
                                                 Class<I> imageType ,
                                                 Class<D> derivType) {
        return FDLA.lineRansac(regionSize, thresholdEdge, thresholdAngle, connectLines, imageType,
                derivType, FD, GIO, FIB);
    }

    public List<LineSegment2D_F32> segDetect (GrayU8 gray) {
        return segDetector.detect(gray, ISC, DHF, CINB, GTEF, TIO, GIO, GGTEF, GTIO, CJBG, GSO, GSUO);
    }

    public void general(GeneralFeatureIntensity<GrayU8, GrayS16> intensity, NonMaxSuppression nonmax) {
        general = new GeneralFeatureDetector<GrayU8, GrayS16>(intensity,nonmax);
    }

    public void point() {
        pointDetector = new EasyGeneralFeatureDetector<GrayU8,GrayS16>(general,GrayU8.class,
                GrayS16.class, FD, GIO, FIB);
    }

    public void general_point() {
        general = new GeneralFeatureDetector<GrayU8, GrayS16>(intensity,nonmax);
        pointDetector = new EasyGeneralFeatureDetector<GrayU8,GrayS16>(general,GrayU8.class,
                GrayS16.class, FD, GIO, FIB);
    }

    public void setMaxFeatures(int numFeatures) {
        general.setMaxFeatures(numFeatures);
    }

    public void pointDetect(GrayU8 gray) {
        pointDetector.detect(gray, null, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
                GBIO, GIO, BlIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, CI, UW, IT);
    }

    public void set_pointDetect(GrayU8 gray) {
        nonmax.setSearchRadius( 3*gray.width/320 );
        general.setMaxFeatures(200*gray.width/320);
        pointDetector.detect(gray, null, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
                GBIO, GIO, BlIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, CI, UW, IT);
    }

    public QueueCorner pointgetMaximums() {
        return pointDetector.getMaximums();
    }
    public QueueCorner pointgetMinimums() { return pointDetector.getMinimums(); }

    public void shiTomasi(int windowRadius, boolean weighted) {
        nonmax = nonmaxMax;
        intensity = FIP.shiTomasi(windowRadius,weighted,GrayS16.class, FIPA, GIO, FKG);
    }

    public void harris(int windowRadius, float kappa, boolean weighted) {
        nonmax = nonmaxMax;
        intensity = FIP.harris(windowRadius, kappa, weighted, GrayS16.class, FIPA, GIO, FKG);
    }

    public void fast( int pixelTol, int minCont) {
        intensity = FIP.fast(pixelTol,minCont,GrayU8.class, FIPA);
        nonmax = nonmaxCandidate;
    }

    public void laplacian() {
        intensity = (GeneralFeatureIntensity)FIP.laplacian();
        nonmax = nonmaxMinMax;
    }

    public void kitros() {
        nonmax = nonmaxMax;
        intensity = FIP.kitros(GrayS16.class);
    }

    public void hessianDet() {
        nonmax = nonmaxMax;
        intensity = FIP.hessian(HessianBlobIntensity.Type.DETERMINANT,GrayS16.class);
    }

    public void hessianTrace() {
        intensity = FIP.hessian(HessianBlobIntensity.Type.TRACE,GrayS16.class);
        nonmax = nonmaxMinMax;
    }

    public void nonmax(ConfigExtract configCorner, ConfigExtract configBlob) {
        nonmaxMax = FFE.nonmax(configCorner);
        nonmaxCandidate = FFE.nonmaxCandidate(configCorner);
        nonmaxMinMax = FFE.nonmax(configBlob);
    }

    public void median(int radius) {
        nonmax = nonmaxMax;
        intensity = FIP.median(radius,GrayU8.class,FBF, GIO);
    }

    public void fastHessian(ConfigFastHessian config ) {
        foundGUI = new FastQueue<ScalePoint>(ScalePoint.class,true);
        scaleDetector = FIrP.fastHessian(config, FIrPA, FFE);
    }

    public void sift(ConfigSiftScaleSpace configSS ,
                     ConfigSiftDetector configDet) {
        foundGUI = new FastQueue<ScalePoint>(ScalePoint.class,true);
        scaleDetector = FIrP.sift(configSS, configDet, GrayU8.class, FFE, FIB, FKG, GIO);
    }

    public void scaleDetect(GrayU8 gray) {
        scaleDetector.detect(gray, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN, GBIO,
                GIO, BlIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD,
                FIB, FBF, CI, UW, IT);
    }

    public int getNumberOfFeatures() {
        return scaleDetector.getNumberOfFeatures();
    }

    public Point2D_F64 getLocation(int featureIndex ) {
        return scaleDetector.getLocation(featureIndex);
    }

    public double getRadius( int featureIndex ) {
        return scaleDetector.getRadius(featureIndex);
    }

    public FastQueue<ScalePoint> getInterestPoints() {
        foundGUI.reset();
        int N = scaleDetector.getNumberOfFeatures();
        for (int i = 0; i < N; i++) {
            Point2D_F64 p = scaleDetector.getLocation(i);
            double radius = scaleDetector.getRadius(i);
            foundGUI.grow().set(p.x, p.y, radius);
        }
        return foundGUI;
    }

    public void hessianPyramid() {
        foundGUI = new FastQueue<ScalePoint>(ScalePoint.class,true);
        double[] scales = {1,2,4,8,16,32};
        scaleDetector = FIrP.wrapDetector(FIrPA.hessianPyramid(10,4,40,GrayU8.class,GrayS16.class,
                FFE, GIO, FIB), scales, false, GrayU8.class, FIB, FP);
    }

    public void hessianLaplace() {
        foundGUI = new FastQueue<ScalePoint>(ScalePoint.class,true);
        double[] scales = {1,2,4,8,16,32};
        scaleDetector = FIrP.wrapDetector(FIrPA.hessianLaplace(10,3,50,GrayU8.class,GrayS16.class,
                FFE, GIO, FIB), scales, false ,GrayU8.class, FIB, FP);
    }

    public void harrisPyramid() {
        foundGUI = new FastQueue<ScalePoint>(ScalePoint.class,true);
        double[] scales = {1,2,4,8,16,32};
        scaleDetector = FIrP.wrapDetector(FIrPA.harrisPyramid(7,3,40,GrayU8.class,GrayS16.class,
                FIPA, GIO, FKG, FFE, FIB), scales, false ,GrayU8.class, FIB, FP);
    }

    public void harrisLaplace() {
        foundGUI = new FastQueue<ScalePoint>(ScalePoint.class,true);
        double[] scales = {1,2,4,8,16,32};
        scaleDetector = FIrP.wrapDetector(FIrPA.harrisLaplace(7,3,40,GrayU8.class,GrayS16.class,
                FIPA, GIO, FKG, FFE, FIB), scales, false ,GrayU8.class, FIB, FP);
    }

    /*
    public <I extends ImageGray> ImageType<Planar<GrayU8>> pl(int numBands) {
        return IT.pl(numBands, GrayU8.class);
    }*/

    public void pL(int numBands) {
        type = IT.pl(numBands, GrayU8.class);
    }

    public void watershed(int numBands, ConfigWatershed config) {
        segmentColor = new ColorQueue_F32(3);
        regionMemberCount = new FastQueue<Integer>(Integer.class, false);
        type = IT.pl(numBands, GrayU8.class);
        segmentation = FIS.watershed(config, type, FSA);
    }

    public void fh04(int numBands, ConfigFh04 config) {
        segmentColor = new ColorQueue_F32(3);
        regionMemberCount = new FastQueue<Integer>(Integer.class, false);
        type = IT.pl(numBands, GrayU8.class);
        segmentation = FIS.fh04(config, type, FSA);
    }

    public void slic(int numBands, ConfigSlic config) {
        segmentColor = new ColorQueue_F32(3);
        regionMemberCount = new FastQueue<Integer>(Integer.class, false);
        type = IT.pl(numBands, GrayU8.class);
        segmentation = FIS.slic(config,type, IT, FSA);
    }

    public void meanShift(int numBands, ConfigSegmentMeanShift config) {
        segmentColor = new ColorQueue_F32(3);
        regionMemberCount = new FastQueue<Integer>(Integer.class, false);
        type = IT.pl(numBands, GrayU8.class);
        segmentation = FIS.meanShift(config, type, FIB, FSA);
    }

    public GrayS32 segment(Planar<GrayU8> input) {
        background.setTo(input);
        segmentation.segment(input, pixelToRegion, ISC, GIO, GIMO, IMO, ISO, BIO, CI, IT);
        return pixelToRegion;
    }

    public FastQueue<float[]> colorize(Planar<GrayU8> input) {
        ComputeRegionMeanColor colorize = FSA.regionMeanColor(input.getImageType());
        int numSegments = segmentation.getTotalSuperpixels();
        segmentColor.resize(numSegments);
        regionMemberCount.resize(numSegments);
        ISO.countRegionPixels(pixelToRegion, numSegments, regionMemberCount.data);
        colorize.process(background,pixelToRegion,regionMemberCount,segmentColor);
        return segmentColor;
    }

    public void initImage(int width, int height) {
        pixelToRegion = new GrayS32(width,height);
        background = new Planar<GrayU8>(GrayU8.class,width,height,3);
    }

    public void initImageBinary(int width, int height) {
        binary = new GrayU8(width,height);
        afterOps = new GrayU8(width,height);
    }

    public <T extends ImageGray>
    GrayU8 threshold(T input , double threshold , boolean down) {
        GTIO.threshold(input,binary,threshold, down, TIO, ISC, GIO);
        return binary;
    }

    public <T extends ImageGray>
    GrayU8 dilate4(T input , double threshold , boolean down, int numTimes) {
        GTIO.threshold(input,binary,threshold, down, TIO, ISC, GIO);
        BIO.dilate4(binary, numTimes, afterOps, ISC, IBIO, IBBO, IBV);
        return afterOps;
    }

    public <T extends ImageGray>
    GrayU8 dilate8(T input , double threshold , boolean down, int numTimes) {
        GTIO.threshold(input,binary,threshold, down, TIO, ISC, GIO);
        BIO.dilate8(binary, numTimes, afterOps, ISC, IBIO, IBBO, IBV);
        return afterOps;
    }

    public <T extends ImageGray>
    GrayU8 erode4(T input , double threshold , boolean down, int numTimes) {
        GTIO.threshold(input,binary,threshold, down, TIO, ISC, GIO);
        BIO.erode4(binary, numTimes, afterOps, ISC, IBIO, IBBO, IBV);
        return afterOps;
    }

    public <T extends ImageGray>
    GrayU8 erode8(T input , double threshold , boolean down, int numTimes) {
        GTIO.threshold(input,binary,threshold, down, TIO, ISC, GIO);
        BIO.erode8(binary, numTimes, afterOps, ISC, IBIO, IBBO, IBV);
        return afterOps;
    }

    public <T extends ImageGray>
    GrayU8 edge4(T input , double threshold , boolean down) {
        GTIO.threshold(input,binary,threshold, down, TIO, ISC, GIO);
        BIO.edge4(binary, afterOps, ISC, IBIO, IBBO, IBV);
        return afterOps;
    }

    public <T extends ImageGray>
    GrayU8 edge8(T input , double threshold , boolean down) {
        GTIO.threshold(input,binary,threshold, down, TIO, ISC, GIO);
        BIO.edge8(binary, afterOps, ISC, IBIO, IBBO, IBV);
        return afterOps;
    }

    public <T extends ImageGray>
    GrayU8 removePointNoise(T input , double threshold , boolean down) {
        GTIO.threshold(input,binary,threshold, down, TIO, ISC, GIO);
        BIO.removePointNoise(binary, afterOps, ISC, IBIO, IBBO, IBV);
        return afterOps;
    }

    public <T extends ImageGray>
    GrayU8 thin(T input , double threshold , boolean down , int maxIterations) {
        GTIO.threshold(input,binary,threshold, down, TIO, ISC, GIO);
        BIO.thin(binary, maxIterations, afterOps, ISC, IBV);
        return afterOps;
    }

    public <T extends ImageGray> void blurMedian(int radius) {
        filter = FBF.median(GrayU8.class,radius, GIO);
    }

    public <T extends ImageGray> void gaussian(double sigma , int radius) {
        filter = FBF.gaussian(GrayU8.class,sigma,radius, GIO);
    }

    public <T extends ImageGray> void mean(int radius) {
        filter = FBF.mean(GrayU8.class, radius, GIO);
    }

    public void initBlurred(int width, int height) {
        binary = new GrayU8(width,height);
    }

    public GrayU8 blurProcess(GrayU8 input) {
        filter.process(input,binary, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI,
                IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT);
        return binary;
    }

    public void setRadius(int radius) {
        filter.setRadius(radius);
    }

    public void initEnhanced(int width, int height) {
        binary = new GrayU8(width,height);
        background = new Planar<GrayU8>(GrayU8.class,width,height,3);
    }

    public void initHistogram(int size) {
        histogram = new int[size];
        transform = new int[size];
    }

    public GrayU8 histogram(GrayU8 input) {
        IS.histogram(input, histogram);
        EIO.equalize(histogram, transform);
        EIO.applyTransform(input, transform, binary, ISC);
        return binary;
    }

    public Planar<GrayU8> average(Planar<GrayU8> input) {
        CI.average(input,binary, ISC);
        IS.histogram(binary, histogram);
        EIO.equalize(histogram, transform);
        for( int i = 0; i < 3; i++ )
            EIO.applyTransform(input.getBand(i), transform, background.getBand(i), ISC);
        return background;
    }

    public GrayU8 equalizeLocal(GrayU8 input, int radius) {
        EIO.equalizeLocal(input, radius, binary, histogram, transform, IS, ISC);
        return binary;
    }

    public Planar<GrayU8> equalizelocal(Planar<GrayU8> input, int radius) {
        for( int i = 0; i < 3; i++ )
            EIO.equalizeLocal(input.getBand(i), radius, background.getBand(i), histogram, transform,
                    IS, ISC);
        return background;
    }

    public GrayU8 sharpen(GrayU8 input, int which) {
        if( which == 4 ) {
            EIO.sharpen4(input, binary, ISC);
        }
        else {
            EIO.sharpen8(input, binary, ISC);
        }
        return binary;
    }

    public Planar<GrayU8> sharpenColor(Planar<GrayU8> input, int which) {
        for( int i = 0; i < 3; i++ ) {
            if( which == 4 )
                EIO.sharpen4(input.getBand(i), background.getBand(i), ISC);
            else
                EIO.sharpen8(input.getBand(i), background.getBand(i), ISC);
        }
        return background;
    }

    public void three() {
        gradient = FD.three(GrayU8.class, GrayS16.class, GIO, FIB);
    }

    public void sobel() {
        gradient = FD.sobel(GrayU8.class, GrayS16.class, GIO, FIB);
    }

    public void prewitt() {
        gradient = FD.prewitt(GrayU8.class, GrayS16.class, GIO, FIB);
    }

    public void two0() {
        gradient = FD.two0(GrayU8.class, GrayS16.class, GIO, FIB);
    }

    public void two1() {
        gradient = FD.two1(GrayU8.class, GrayS16.class, GIO, FIB);
    }

    public void initGradient(int width, int height) {
        deriv = new GrayS16pair(width, height);
    }

    public GrayS16pair gradientProcess(GrayU8 input) {
        gradient.process(input,deriv.derivX,deriv.derivY, ISC,DHF, CINB, CJBG, GSO, GSUO);
        return deriv;
    }

    public void initFourier(int width, int height) {
        grayF = new GrayF32(width,height);
        interleavedTransform = new InterleavedF32(width,height,2);
    }

    public GrayF32 fourierProcess(GrayU8 input) {
        CI.convert(input, grayF, ISC);
        PixelMath.divide(grayF,255.0f,grayF, ISC);
        dft.forward(grayF, interleavedTransform, DFTO, ISC);
        DFTO.shiftZeroFrequency(interleavedTransform, true);
        DFTO.magnitude(interleavedTransform, grayF, ISC);
        PixelMath.log(grayF,grayF, ISC);
        float max = IS.maxAbs(grayF);
        PixelMath.multiply(grayF, 255f / max, grayF, ISC);
        return grayF;
    }

    public GrayU8 pyramidProcess(GrayU8 input) {
        pyramid.process(input, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN,
                IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI, UW, IT);
        binary.subimage(0,0,pyramid.getLayer(0).width,pyramid.getLayer(0).height,afterOps);
        afterOps.setTo(pyramid.getLayer(0));
        int height = 0;
        int width = pyramid.getLayer(0).getWidth();
        for( int i = 1; i < pyramid.getNumLayers(); i++ ) {
            GrayU8 l = pyramid.getLayer(i);
            binary.subimage(width,height,width+l.width,height+l.height,afterOps);
            afterOps.setTo(l);
            height += l.getHeight();
        }
        return binary;
    }

    public void createTransformF32() {
        dft = DFTO.createTransformF32();
    }

    public void discreteGaussian() {
        pyramid = FP.discreteGaussian(new int[]{2,4,8,16},-1,2,false,GrayU8.class, FKG);
    }

    public void haarWavelet() {
        desc = GFW.haar(GrayU8.class);
        waveletTran = FWT.create(GrayU8.class, desc, 3, 0, 255);
    }
    /*
    public void daubJWavelet() {
        desc = GFW.daubJ(GrayU8.class, 4);
        waveletTran = FWT.create(GrayU8.class, desc, 3, 0, 255);
    }*/

    public void biorthogoalWavelet() {
        desc = GFW.biorthogoal(GrayU8.class, 5, BorderType.REFLECT, UW);
        waveletTran = FWT.create(GrayU8.class, desc, 3, 0, 255);
    }
    /*
    public void coifletWavelet() {
        desc = GFW.coiflet(GrayU8.class, 6);
        waveletTran = FWT.create(GrayU8.class, desc, 3, 0, 255);
    }*/

    public void initWavelet(int width, int height) {
        ImageDimension d = UW.transformDimension(width, height, waveletTran.getLevels());
        pixelToRegion = new GrayS32(d.width,d.height);
    }

    public GrayS32 waveletProcess(GrayU8 input) {
        waveletTran.transform(input,pixelToRegion, ISC, GIO, GIMO, IMO, CI, UW, IT);
        //System.out.println("BOOF: num levels " + waveletTran.getLevels());
        //System.out.println("BOOF: width "+pixelToRegion.getWidth()+" "+pixelToRegion.getHeight());
        UW.adjustForDisplay(pixelToRegion, waveletTran.getLevels(), 255);
        return pixelToRegion;
    }

    public void startAssoc(int selectedDet, int selectedDesc) {
        detDesc = CDD.create(selectedDet, selectedDesc, GrayF32.class, FIrP, FIrPA, FIPA, FFE,FIB,
                FD, GIO, FKG, IT, FBF);
        ScoreAssociation score = FA.defaultScore(detDesc.getDescriptionType());
        associate = FA.greedy(score,Double.MAX_VALUE,true);
        listSrc = UtilFeature.createQueue(detDesc,10);
        listDst = UtilFeature.createQueue(detDesc,10);
        locationSrc = new FastQueue<>(Point2D_F64.class, true);
        locationDst = new FastQueue<Point2D_F64>(Point2D_F64.class,true);
    }

    public void assocDetectleft(GrayF32 graySrc) {
        detDesc.detect(graySrc, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN, GBIO,
                GIO, BlIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD,
                FIB, FBF, CI, UW, IT);

        listSrc.reset();
        locationSrc.reset();
        int N = detDesc.getNumberOfFeatures();
        for( int i = 0; i < N; i++ ) {
            locationSrc.grow().set(detDesc.getLocation(i));
            listSrc.grow().setTo(detDesc.getDescription(i));
        }
    }

    public void assocDetectright(GrayF32 graySrc) {
        detDesc.detect(graySrc, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN, GBIO,
                GIO, BlIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD,
                FIB, FBF, CI, UW, IT);

        listDst.reset();
        locationDst.reset();
        int N = detDesc.getNumberOfFeatures();
        for( int i = 0; i < N; i++ ) {
            locationDst.grow().set(detDesc.getLocation(i));
            listDst.grow().setTo(detDesc.getDescription(i));
        }
    }

    public Assoc associate() {
        associate.setSource(listSrc);
        associate.setDestination(listDst);
        associate.associate();

        Assoc points = new Assoc();
        FastQueue<AssociatedIndex> matches = associate.getMatches();
        for( int i = 0; i < matches.size; i++ ) {
            AssociatedIndex m = matches.get(i);
            points.Src.add(locationSrc.get(m.src));
            points.Dst.add(locationDst.get(m.dst));
        }

        return points;
    }

    public void circulant(ConfigCirculantTracker config) {
        tracker = FTOQ.circulant(config, GrayU8.class, FIB, IT, DFTO, ISC);
    }

    public void meanShiftComaniciu2003(ConfigComaniciu2003 config, ImageType imageType) {
        tracker = FTOQ.meanShiftComaniciu2003(config, imageType, FIB);
    }

    public void meanShiftLikelihood(int maxIterations,
                                    int numBins,
                                    double maxPixelValue,
                                    MeanShiftLikelihoodType modelType, ImageType imageType) {
        tracker = FTOQ.meanShiftLikelihood(maxIterations,numBins,maxPixelValue, modelType, imageType);
    }

    public void sparseFlow(SfotConfig config) {
        tracker = FTOQ.sparseFlow(config,GrayU8.class,null, FD, FIB, GIO, IT);
    }

    public void tld(ConfigTld config) {
        tracker = FTOQ.tld(config,GrayU8.class, FD, FIB, GIO, IT);
    }

    public<T extends ImageBase> track trackerProcess(T input , Quadrilateral_F64 location) {
        track ret = new track();
        ret.visible = tracker.process(input, location, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN,
                CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI,
                UW, DHF, GSO, GSUO, FP, DFTO, IT);
        ret.location = location;
        return ret;
    }

    public<T extends ImageBase> Quadrilateral_F64 trackerMoved(T input, Quadrilateral_F64 location) {
        tracker.initialize(input, location, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB, CNJB,
                IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI, UW, DHF, GSO,
                GSUO, FP, DFTO, IT);
        return location;
    }

    public GrayU8 convertAverage(Planar<GrayU8> input) {
        return CI.average(input, null, ISC);
    }

    public void klt(int scaling[], ConfigGeneralDetector configExtract, int featureRadius) {
        pointTracker = FPT.klt(scaling,configExtract,featureRadius,GrayU8.class, GrayS16.class, FD,
                GIO, FIB, FKG, FP, FFE, FIPA);
    }

    public void initPoint() {
        tracklist = new trackList();
        inactive = new ArrayList<PointTrack>();
    }

    public trackList pointProcess(GrayU8 gray) {
        pointTracker.process(gray, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN, GBIO,
                GIO, BlIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD,
                FIB, FBF, CI, UW, IT);

        // drop tracks which are no longer being used
        inactive.clear();
        pointTracker.getInactiveTracks(inactive);
        for( int i = 0; i < inactive.size(); i++ ) {
            PointTrack t = inactive.get(i);
            trackInfo info = t.getCookie();
            if( tick - info.lastActive > 2 ) {
                pointTracker.dropTrack(t);
            }
        }

        tracklist.active.clear();
        pointTracker.getActiveTracks(tracklist.active);
        for( int i = 0; i < tracklist.active.size(); i++ ) {
            PointTrack t = tracklist.active.get(i);
            trackInfo info = t.getCookie();
            info.lastActive = tick;
        }

        tracklist.spawned.clear();
        if( tracklist.active.size() < 50 )  {
            pointTracker.spawnTracks(ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
                    GBIO, GIO, BlIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA,
                    IBV, FHFD, FIB, FBF, CI, UW, IT);

            // update the track's initial position
            for( int i = 0; i < tracklist.active.size(); i++ ) {
                PointTrack t = tracklist.active.get(i);
                trackInfo info = t.getCookie();
                info.spawn.set(t);
            }

            pointTracker.getNewTracks(tracklist.spawned);
            for( int i = 0; i < tracklist.spawned.size(); i++ ) {
                PointTrack t = tracklist.spawned.get(i);
                if( t.cookie == null ) {
                    t.cookie = new trackInfo();
                }
                trackInfo info = t.getCookie();
                info.lastActive = tick;
                info.spawn.set(t);
            }
        }

        tick++;
        return tracklist;
    }

    public void combinedTracker( int detector , int descriptor , PkltConfig config ) {
        DetectDescribePoint detDesc = CDD.create(tableDet[detector],tableDesc[descriptor],GrayU8.class, FIrP, FIrPA, FIPA, FFE, FIB, FD, GIO, FKG, IT, FBF);
        ScoreAssociation score = FA.defaultScore(detDesc.getDescriptionType());
        AssociateDescription<TupleDesc_B> association = FA.greedy(score, Double.MAX_VALUE, true);
        pointTracker = FPT.combined(detDesc,association,config,75,GrayU8.class, FP, FKG, FD, GIO, FIB);
    }

    public void ddaTracker(int detector , int descriptor) {
        DetectDescribePoint detDesc = CDD.create(tableDet[detector],tableDesc[descriptor],GrayU8.class, FIrP, FIrPA, FIPA, FFE, FIB, FD, GIO, FKG, IT, FBF);
        ScoreAssociation score = FA.defaultScore(detDesc.getDescriptionType());
        AssociateDescription2D<TupleDesc_B> association =
                new AssociateDescTo2D<TupleDesc_B>(
                        FA.greedy(score, Double.MAX_VALUE, true));
        pointTracker = FPT.dda(detDesc,association,false);
    }

    public void stationaryBasic(ConfigBackgroundBasic configBasic) {
        model = FBM.stationaryBasic(configBasic,IT.single(GrayU8.class), IT);
    }

    public void stationaryBasicColor(ConfigBackgroundBasic configBasic) {
        model = FBM.stationaryBasic(configBasic,IT.il(3, ImageDataType.U8), IT);
    }

    public void stationaryGaussian(ConfigBackgroundGaussian configGaussian) {
        model = FBM.stationaryGaussian(configGaussian, IT.single(GrayU8.class), IT);
    }

    public void stationaryGaussianColor(ConfigBackgroundGaussian configGaussian) {
        model = FBM.stationaryGaussian(configGaussian, IT.il(3, ImageDataType.U8), IT);
    }

    public ImageType getImageType() {
        return model.getImageType();
    }

    public void motionInit() {
        scaled = model.getImageType().createImage(1, 1);
        work = GIO.createSingleBand(model.getImageType().getDataType(),1,1, IT);
    }

    public void initbg(int width, int height) {
        blackBinary.reshape(width, height);
        work.reshape(width, height);
        scaled.reshape(width/3,height/3);
        shrink = new FDistort();
    }

    public void setThreshold(float progress) {
        if( model instanceof BackgroundStationaryBasic) {
            ((BackgroundStationaryBasic)model).setThreshold(progress);
        } else if( model instanceof BackgroundStationaryGaussian) {
            ((BackgroundStationaryGaussian)model).setThreshold(progress);
        }
    }

    public void reset() {
        model.reset();
        IMO.fill(blackBinary, 0);
    }

    public<T extends ImageBase> GrayU8 set(T image) {
        model.segment(image, blackBinary, ISC, IMO);
        model.updateBackground(image, ISC, GIO, GIMO, IMO, CI, IT);
        return blackBinary;
    }

    public<T extends ImageBase> T pip(T image) {
        // shrink the input image
        shrink.init(image, scaled, FIB).scaleExt(FIB);
        shrink.apply();

        // rescale the binary image so that it can be viewed
        PixelMath.multiply(blackBinary, 255, blackBinary, ISC);

        // overwrite the original image with the binary one
        GConvertImage.convert(blackBinary, work, ISC, GIO, GIMO, IMO, CI, IT);
        // if the input image is color then it needs a gray scale image of the same type
        GConvertImage.convert(work, image, ISC, GIO, GIMO, IMO, CI, IT);

        // render the shrunk image inside the original
        int x0 = image.width  - scaled.width;
        int y0 = image.height - scaled.height;

        image.subimage(x0,y0,blackBinary.width,blackBinary.height).setTo(scaled);
        return image;
    }

}

