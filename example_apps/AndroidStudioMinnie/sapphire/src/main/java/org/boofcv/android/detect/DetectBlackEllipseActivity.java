package org.boofcv.android.detect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;
import org.ddogleg.struct.FastQueue;

import boofcv.abst.filter.binary.InputToBinary;
import boofcv.alg.InputSanityCheck;
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
import boofcv.alg.shapes.ellipse.BinaryEllipseDetector;
import boofcv.android.ConvertBitmap;
import boofcv.android.VisualizeImageData;
import boofcv.android.gui.VideoImageProcessing;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.shape.ConfigEllipseDetector;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import georegression.metric.UtilAngle;
import georegression.struct.shapes.EllipseRotated_F64;

/**
 * Detects ellipses in an image which are black.
 *
 * @author Peter Abeles
 */
public class DetectBlackEllipseActivity extends DemoVideoDisplayActivity
		implements AdapterView.OnItemSelectedListener , View.OnTouchListener
{
	private static ImageType IT;
	private static GBlurImageOps GBIO;
	private static GeneralizedImageOps GIO;
	private static InputSanityCheck ISC;
	private static BlurImageOps BIO;
	private static ConvolveImageMean CIM;
	private static FactoryKernelGaussian FKG;
	private static ConvolveNormalized CN;
	private static ConvolveNormalizedNaive CNN;
	private static ConvolveImageNoBorder CINB;
	private static ConvolveNormalized_JustBorder CNJB;
	private static ImplMedianHistogramInner IMHI;
	private static ImplMedianSortEdgeNaive IMSEN;
	private static ImplMedianSortNaive IMSN;
	private static ImplConvolveMean ICM;
	Paint paint;

	Spinner spinnerThresholder;

	// which algorithm is processing the image
	int active = -1;

	boolean showInput = true;

	BinaryEllipseDetector<GrayU8> detector;
	InputToBinary<GrayU8> inputToBinary;

	GrayU8 binary = new GrayU8(1,1);


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		paint = new Paint();
		paint.setARGB(0xFF,0xFF,0,0);
		paint.setStyle(Paint.Style.STROKE);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setStrokeWidth(3.0f);

		LayoutInflater inflater = getLayoutInflater();
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.detect_black_ellipse_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		FrameLayout iv = getViewPreview();
		iv.setOnTouchListener(this);

		spinnerThresholder = (Spinner)controls.findViewById(R.id.spinner_algs);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.threshold_styles, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerThresholder.setAdapter(adapter);
		spinnerThresholder.setOnItemSelectedListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ConfigEllipseDetector config = new ConfigEllipseDetector();
		config.maxIterations = 1;
		config.numSampleContour = 20;
		detector = FactoryShapeDetector.ellipse(config,GrayU8.class);
		setSelection(spinnerThresholder.getSelectedItemPosition());
		setProcessing(new EllipseProcessing());
	}

	@Override
	protected void onPause() {
		super.onPause();
		active = -1;
	}

	public void pressedHelp( View view ) {
		Intent intent = new Intent(this, DetectBlackPolygonHelpActivity.class);
		startActivity(intent);
	}


	private void setSelection( int which ) {
		if( which == active )
			return;
		active = which;

		switch( active ) {
			case 0 :
				inputToBinary = FactoryThresholdBinary.globalOtsu(0, 255, true, GrayU8.class);
				break;

			case 1:
				inputToBinary = FactoryThresholdBinary.localSquare(10,0.95,true,GrayU8.class);
				break;

			default:
				throw new RuntimeException("Unknown type");
		}
	}


	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
		setSelection( pos );
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if( MotionEvent.ACTION_DOWN == event.getActionMasked()) {
			showInput = !showInput;
			return true;
		}
		return false;
	}

	protected class EllipseProcessing extends VideoImageProcessing<GrayU8> {

		RectF r = new RectF();

		protected EllipseProcessing() {
			super(IT.single(GrayU8.class));
		}

		@Override
		protected void declareImages(int width, int height) {
			super.declareImages(width, height);
			binary.reshape(width,height);
		}

		@Override
		protected void process(GrayU8 image, Bitmap output, byte[] storage) {

			synchronized ( this ) {
				inputToBinary.process(image,binary, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM);
			}

			detector.process(image,binary);

			if( showInput ) {
				ConvertBitmap.boofToBitmap(image,output,storage);
			} else {
				VisualizeImageData.binaryToBitmap(binary,false,output,storage);
			}

			Canvas canvas = new Canvas(output);

			FastQueue<EllipseRotated_F64> found = detector.getFoundEllipses();

			for( EllipseRotated_F64 ellipse : found.toList() )  {

				float phi = (float) UtilAngle.degree(ellipse.phi);
				float cx =  (float)ellipse.center.x;
				float cy =  (float)ellipse.center.y;
				float w = (float)ellipse.a;
				float h = (float)ellipse.b;

				//  really skinny ones are probably just a line and not what the user wants
				if( w <= 2 || h <= 2 )
					return;

				canvas.rotate(phi, cx, cy);
				r.set(cx-w,cy-h,cx+w+1,cy+h+1);
				canvas.drawOval(r,paint);
				canvas.rotate(-phi, cx, cy);
			}
		}
	}
}