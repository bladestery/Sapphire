package org.boofcv.android.ip;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;

import boofcv.abst.filter.blur.BlurFilter;
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
import boofcv.android.ConvertBitmap;
import boofcv.android.gui.VideoImageProcessing;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;

/**
 * Blurs the input video image using different algorithms.
 *
 * @author Peter Abeles
 */
public class BlurDisplayActivity extends DemoVideoDisplayActivity
		implements AdapterView.OnItemSelectedListener
{
	private static FactoryBlurFilter FBF;
	private static GeneralizedImageOps GIO;
	private static ImageType IT;
	private static GBlurImageOps GBIO;
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

	Spinner spinnerView;

	// amount of blur applied to the image
	int radius;

	BlurProcessing processing;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getLayoutInflater();
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.blur_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		spinnerView = (Spinner)controls.findViewById(R.id.spinner_algs);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.blurs, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerView.setAdapter(adapter);
		spinnerView.setOnItemSelectedListener(this);

		SeekBar seek = (SeekBar)controls.findViewById(R.id.slider_radius);
		radius = seek.getProgress();

		seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				radius = progress;
				if( radius > 0 )
					processing.setRadius(radius);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		startBlurProcess(spinnerView.getSelectedItemPosition());
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id ) {
		startBlurProcess(pos);
	}

	private void startBlurProcess(int pos) {
		// not sure what these do if the radius is set to 0
		int radius = Math.max(1,this.radius);
		switch (pos) {
			case 0:
				processing = new BlurProcessing(FBF.mean(GrayU8.class, radius, GIO));
				break;

			case 1:
				processing = new BlurProcessing(FBF.gaussian(GrayU8.class,-1,radius, GIO));
				break;

			case 2:
				processing = new BlurProcessing(FBF.median(GrayU8.class,radius, GIO));
				break;
		}

		setProcessing(processing);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}

	protected class BlurProcessing extends VideoImageProcessing<GrayU8> {
		GrayU8 blurred;
		final BlurFilter<GrayU8> filter;

		public BlurProcessing(BlurFilter<GrayU8> filter) {
			super( IT.single(GrayU8.class));
			this.filter = filter;
		}

		@Override
		protected void declareImages( int width , int height ) {
			super.declareImages(width, height);

			blurred = new GrayU8(width,height);
		}

		@Override
		protected void process(GrayU8 input, Bitmap output, byte[] storage) {
			if( radius > 0 ) {
				synchronized ( filter ) {
					filter.process(input, blurred, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM);
				}
				ConvertBitmap.grayToBitmap(blurred, output, storage);
			} else {
				ConvertBitmap.grayToBitmap(input, output, storage);
			}
		}

		public void setRadius( int radius ) {
			synchronized ( filter ) {
				filter.setRadius(radius);
			}
		}
	}
}