package org.boofcv.android;

import android.graphics.Bitmap;
import android.renderscript.ScriptGroup;

import org.ddogleg.struct.FastQueue;

import java.util.List;

import boofcv.abst.feature.detect.extract.ConfigExtract;
import boofcv.abst.feature.detect.extract.NonMaxSuppression;
import boofcv.abst.feature.detect.intensity.GeneralFeatureIntensity;
import boofcv.abst.feature.detect.line.DetectLine;
import boofcv.abst.feature.detect.line.DetectLineHoughFoot;
import boofcv.abst.feature.detect.line.DetectLineHoughPolar;
import boofcv.abst.feature.detect.line.DetectLineSegment;
import boofcv.abst.feature.detect.line.DetectLineSegmentsGridRansac;
import boofcv.abst.filter.binary.GlobalOtsuBinaryFilter;
import boofcv.abst.filter.binary.InputToBinary;
import boofcv.abst.filter.binary.LocalSquareBinaryFilter;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.feature.detect.edge.GGradientToEdgeFeatures;
import boofcv.alg.feature.detect.edge.GradientToEdgeFeatures;
import boofcv.alg.feature.detect.edge.impl.ImplEdgeNonMaxSuppression;
import boofcv.alg.feature.detect.edge.impl.ImplEdgeNonMaxSuppressionCrude;
import boofcv.alg.feature.detect.intensity.HessianBlobIntensity;
import boofcv.alg.feature.detect.interest.EasyGeneralFeatureDetector;
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
import boofcv.alg.filter.derivative.GradientSobel;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.shapes.ellipse.BinaryEllipseDetector;
import boofcv.alg.shapes.polygon.BinaryPolygonDetector;
import boofcv.android.VisualizeImageData;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.factory.feature.detect.intensity.FactoryIntensityPoint;
import boofcv.factory.feature.detect.intensity.FactoryIntensityPointAlg;
import boofcv.factory.feature.detect.line.ConfigHoughFoot;
import boofcv.factory.feature.detect.line.ConfigHoughPolar;
import boofcv.factory.feature.detect.line.FactoryDetectLineAlgs;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.shape.ConfigEllipseDetector;
import boofcv.factory.shape.ConfigPolygonDetector;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.struct.ConnectRule;
import boofcv.struct.QueueCorner;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import georegression.struct.line.LineParametric2D_F32;
import georegression.struct.line.LineSegment2D_F32;
import georegression.struct.shapes.EllipseRotated_F64;
import georegression.struct.shapes.Polygon2D_F64;
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
    private FactoryShapeDetector FSD;
    private FactoryThresholdBinary FTB;
    private FactoryDetectLineAlgs FDLA;
    private FactoryFeatureExtractor FFE;
    private ConvolveJustBorder_General CJBG;
    private GradientSobel_Outer GSO;
    private GradientSobel_UnrolledOuter GSUO;
    private ImplEdgeNonMaxSuppressionCrude IENMSC;
    private FactoryIntensityPoint FIP;
    private FactoryIntensityPointAlg FIPA;
    private GImageMiscOps GIMO;

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

        contour8 = new LinearContourLabelChang2004(ConnectRule.EIGHT);
        contour4 = new LinearContourLabelChang2004(ConnectRule.FOUR);
        blackBinary = new GrayU8(1,1);
    }

    public void LatencyCheck() {}

    public <I extends ImageGray> ImageType<I> single(Class<I> imageType ) {
        return IT.single(imageType);
    }

    public void canny( int blurRadius , boolean saveTrace , boolean dynamicThreshold) {
        canny = cannyEdge(blurRadius, saveTrace, dynamicThreshold, GrayU8.class, GrayS16.class);
    }

    public <T extends ImageGray, D extends ImageGray>
    CannyEdge<T,D> cannyEdge( int blurRadius , boolean saveTrace , boolean dynamicThreshold, Class<T> imageType , Class<D> derivType ) {
        return FED.canny(blurRadius, saveTrace, dynamicThreshold, imageType, derivType, FBF, FD, GIO, IT, FIB);
    }

    public List<EdgeContour> edgeProcess(GrayU8 input , float threshLow, float threshHigh , GrayU8 output) {
        canny.process(input, threshLow, threshHigh, output,
                IMO, GBIO, IBV, GTEF, IENMS, FIBA, GGTEF, IS, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB,
                CNJB, IMHI, IMSEN, IMSN, ICM, DHF, GTIO, GIS, TIO, CJBG, GSO, GSUO, GIMO);
        return canny.getContours();
    }

    public void process(GrayU8 input , float threshLow, float threshHigh , GrayU8 output) {
        canny.process(input, threshLow, threshHigh, output,
                IMO, GBIO, IBV, GTEF, IENMS, FIBA, GGTEF, IS, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB,
                CNJB, IMHI, IMSEN, IMSN, ICM, DHF, GTIO, GIS, TIO, CJBG, GSO, GSUO, GIMO);
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
        inputToBinary = GlobalOtsu(minValue, maxValue, down, GrayU8.class);
    }

    public <T extends ImageGray>
    InputToBinary<T> GlobalOtsu(int minValue, int maxValue, boolean down, Class<T> inputType) {
        return FTB.globalOtsu(minValue, maxValue, down, inputType, IT);
    }

    public void localSquare(int radius, double scale, boolean down) {
        inputToBinary = LocalSquare(radius, scale, down, GrayU8.class);
    }

    public <T extends ImageGray>
    InputToBinary<T> LocalSquare(int radius, double scale, boolean down, Class<T> inputType) {
        return FTB.localSquare(radius, scale, down, inputType, IT);
    }

    public void inputProcess(GrayU8 image) {
        inputToBinary.process(image, blackBinary, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO);
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
        return lineDetector.detect(gray, ISC, DHF, CINB, GTEF, GIO, TIO, GGTEF, CJBG, GSO, GSUO, IMO, IENMSC, FIBA, IBV);
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
        return FDLA.lineRansac(regionSize, thresholdEdge, thresholdAngle, connectLines, imageType, derivType, FD, GIO, FIB);
    }

    public List<LineSegment2D_F32> segDetect (GrayU8 gray) {
        return segDetector.detect(gray, ISC, DHF, CINB, GTEF, TIO, GIO, GGTEF, GTIO, CJBG, GSO, GSUO);
    }

    public void general(GeneralFeatureIntensity<GrayU8, GrayS16> intensity, NonMaxSuppression nonmax) {
        general = new GeneralFeatureDetector<GrayU8, GrayS16>(intensity,nonmax);
    }ShapeFit

    public void point() {
        pointDetector = new EasyGeneralFeatureDetector<GrayU8,GrayS16>(general,GrayU8.class,GrayS16.class, FD, GIO, FIB);
    }

    public void general_point() {
        general = new GeneralFeatureDetector<GrayU8, GrayS16>(intensity,nonmax);
        pointDetector = new EasyGeneralFeatureDetector<GrayU8,GrayS16>(general,GrayU8.class,GrayS16.class, FD, GIO, FIB);
    }

    public void setMaxFeatures(int numFeatures) {
        general.setMaxFeatures(numFeatures);
    }

    public void pointDetect(GrayU8 gray) {
        pointDetector.detect(gray, null, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN);
    }

    public void set_pointDetect(GrayU8 gray) {
        nonmax.setSearchRadius( 3*gray.width/320 );
        general.setMaxFeatures(200*gray.width/320);
        pointDetector.detect(gray, null, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN);
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


}
