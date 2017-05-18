package org.boofcv.android.ip;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.ToggleButton;

import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;

import boofcv.abst.filter.binary.InputToBinary;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
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
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.android.VisualizeImageData;
import boofcv.android.gui.VideoImageProcessing;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;

/**
 * Automatic thresholding
 *
 * @author Peter Abeles
 */
public class ThresholdDisplayActivity extends DemoVideoDisplayActivity
{
	private static GBlurImageOps GBIO;
	private static ImageType IT;
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
	private static FactoryThresholdBinary FTB;
	private static GThresholdImageOps GTIO;
	private static GImageStatistics GIS;
	private static ImageStatistics IS;
	private static ThresholdImageOps TIO;
	private static GImageMiscOps GIMO;
	private static ImageMiscOps IMO;
	private static ConvolveJustBorder_General CJBG;
	Spinner spinnerView;

	final Object lock = new Object();
	boolean changed = false;
	boolean down;
	int radius;
	int selectedAlg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getLayoutInflater();
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.thresholding_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		spinnerView = (Spinner)controls.findViewById(R.id.spinner_algs);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.thresholding, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerView.setAdapter(adapter);

		ToggleButton toggle = (ToggleButton)controls.findViewById(R.id.toggle_threshold);
		final SeekBar seek = (SeekBar)controls.findViewById(R.id.slider_radius);

		changed = true;
		selectedAlg = spinnerView.getSelectedItemPosition();
		adjustSeekEnabled(seek);
		down = toggle.isChecked();
		radius = seek.getProgress();

		seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				synchronized (lock) {
					changed = true;
					radius = progress;
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				synchronized (lock) {
					changed = true;
					selectedAlg = position;
					adjustSeekEnabled(seek);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});

		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				synchronized (lock) {
					changed = true;
					down = isChecked;
				}
			}
		});
	}

	private void adjustSeekEnabled(SeekBar seek) {
		if( selectedAlg <= 1 ) {
			seek.setEnabled(false);
		} else {
			seek.setEnabled(true);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setProcessing(new ThresholdingProcessing());
	}

	private InputToBinary<GrayU8> createFilter() {

		int radius = this.radius + 1;

		switch (selectedAlg) {
			case 0:
				return FTB.globalOtsu(0,255,down,GrayU8.class, IT);

			case 1:
				return FTB.globalEntropy(0, 255, down, GrayU8.class, IT);

			case 2:
				return FTB.localSquare(radius,0.95,down,GrayU8.class, IT);

			case 3:
				return FTB.localGaussian(radius,0.95,down,GrayU8.class, IT);

			case 4:
				return FTB.localSauvola(radius,0.3f,down,GrayU8.class, IT);
		}

		throw new RuntimeException("Unknown selection "+selectedAlg);
	}

	protected class ThresholdingProcessing extends VideoImageProcessing<GrayU8> {
		GrayU8 binary;
		InputToBinary<GrayU8> filter;

		public ThresholdingProcessing() {
			super(IT.single(GrayU8.class));
		}

		@Override
		protected void declareImages( int width , int height ) {
			super.declareImages(width, height);

			binary = new GrayU8(width,height);
		}

		@Override
		protected void process(GrayU8 input, Bitmap output, byte[] storage) {

			synchronized ( lock ) {
				if (changed) {
					changed = false;
					filter = createFilter();
				}
			}

			if( filter != null ) {
				filter.process(input, binary, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG);
			}
			VisualizeImageData.binaryToBitmap(binary,false,  output, storage);
		}
	}
}