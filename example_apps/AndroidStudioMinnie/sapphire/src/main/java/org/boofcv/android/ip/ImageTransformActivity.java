package org.boofcv.android.ip;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.boofcv.android.DemoManager;
import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import boofcv.abst.transform.fft.DiscreteFourierTransform;
import boofcv.abst.transform.wavelet.WaveletTransform;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
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
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.misc.PixelMath;
import boofcv.alg.transform.fft.DiscreteFourierTransformOps;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.android.ConvertBitmap;
import boofcv.android.VisualizeImageData;
import boofcv.android.gui.VideoImageProcessing;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.transform.pyramid.FactoryPyramid;
import boofcv.factory.transform.wavelet.FactoryWaveletTransform;
import boofcv.factory.transform.wavelet.GFactoryWavelet;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageDimension;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.InterleavedF32;
import boofcv.struct.pyramid.ImagePyramid;
import boofcv.struct.wavelet.WaveletDescription;
import boofcv.struct.wavelet.WlCoef;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

/**
 * Visualizes several common image transforms
 *
 * @author Peter Abeles
 */
public class ImageTransformActivity extends DemoVideoDisplayActivity
		implements AdapterView.OnItemSelectedListener
{
	Spinner spinnerView;

	OMSServer server;
	DemoManager dm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		InetSocketAddress host, omsHost;

		try {
			Registry registry = LocateRegistry.getRegistry("157.82.159.58", 22346);
			server = (OMSServer) registry.lookup("SapphireOMS");
			System.out.println(server);

			host = new InetSocketAddress("192.168.0.7", 22346);
			omsHost = new InetSocketAddress("157.82.159.58", 22346);
			nodeServer = new KernelServerImpl(host, omsHost);
			System.out.println(nodeServer);

			System.setProperty("java.rmi.server.hostname", host.getAddress().getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			//Server is initiated with appObject to perform remote RPCs
			dm = (DemoManager) server.getAppEntryPoint();
			System.out.println("Got AppEntryPoint");
			//dm.LatencyCheck();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		LayoutInflater inflater = getLayoutInflater();
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.select_algorithm,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		spinnerView = (Spinner)controls.findViewById(R.id.spinner_algs);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.transforms, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerView.setAdapter(adapter);
		spinnerView.setOnItemSelectedListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		startTransformProcess(spinnerView.getSelectedItemPosition());
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
		startTransformProcess(pos);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}

	private void startTransformProcess(int pos) {
		switch (pos) {
			case 0:
				dm.createTransformF32();
				setProcessing(new FourierProcessing() );
				break;

			case 1:
				dm.discreteGaussian();
				setProcessing(new PyramidProcessing() );
				break;

			case 2:
				dm.haarWavelet();
				setProcessing(new WaveletProcessing() );
				break;

			case 3:
				dm.biorthogoalWavelet();
				setProcessing(new WaveletProcessing() );
				break;

			/* These wavelets not compatible with android camera I guess
			case 4:
				dm.daubJWavelet();
				setProcessing(new WaveletProcessing() );
				break;

			case 5:
				dm.coifletWavelet();
				setProcessing(new WaveletProcessing() );
				break;
			*/
		}
	}

	protected class FourierProcessing extends VideoImageProcessing<GrayU8> {
		//DiscreteFourierTransform<GrayF32,InterleavedF32> dft = DiscreteFourierTransformOps.createTransformF32();
		GrayF32 grayF;
		//InterleavedF32 transform;

		protected FourierProcessing() {
			super(dm.single(GrayU8.class));
		}

		@Override
		protected void declareImages( int width , int height ) {
			super.declareImages(width, height);
			dm.initFourier(width, height);
			//grayF = new GrayF32(width,height);
			//transform = new InterleavedF32(width,height,2);
		}

		@Override
		protected void process(GrayU8 input, Bitmap output, byte[] storage) {
			grayF = dm.fourierProcess(input);
			//CI.convert(input, grayF, ISC);
			//PixelMath.divide(grayF,255.0f,grayF, ISC);
			//dft.forward(grayF, transform);
			//DiscreteFourierTransformOps.shiftZeroFrequency(transform, true);
			//DiscreteFourierTransformOps.magnitude(transform, grayF);
			//PixelMath.log(grayF,grayF, ISC);
			//float max = IS.maxAbs(grayF);
			//PixelMath.multiply(grayF, 255f / max, grayF, ISC);
			ConvertBitmap.grayToBitmap(grayF, output, storage);
		}
	}

	protected class PyramidProcessing<C extends WlCoef>
			extends VideoImageProcessing<GrayU8>
	{
		//ImagePyramid<GrayU8> pyramid = FactoryPyramid.discreteGaussian(new int[]{2,4,8,16},-1,2,false,GrayU8.class, FKG);

		GrayU8 output;
		//GrayU8 sub = new GrayU8();

		protected PyramidProcessing() {
			super(dm.single(GrayU8.class));
		}

		@Override
		protected void declareImages( int width , int height ) {
			super.declareImages(width, height);
			dm.initImageBinary(width, height);
			//output = new GrayU8(width,height);
		}

		@Override
		protected void process(GrayU8 input, Bitmap output, byte[] storage) {
			this.output = dm.pyramidProcess(input);
			//pyramid.process(input, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI);

			//draw(0, 0, pyramid.getLayer(0));
			//int height = 0;
			//int width = pyramid.getLayer(0).getWidth();
			//for( int i = 1; i < pyramid.getNumLayers(); i++ ) {
			//	GrayU8 l = pyramid.getLayer(i);
			//	draw(width, height, l);
			//	height += l.getHeight();
			//}

			ConvertBitmap.grayToBitmap(this.output, output, storage);
		}

		//private void draw( int x0 , int y0 , GrayU8 layer ) {
		//	output.subimage(x0,y0,x0+layer.width,y0+layer.height,sub);
		//	sub.setTo(layer);
		//}
	}

	protected class WaveletProcessing<C extends WlCoef>
			extends VideoImageProcessing<GrayU8>
	{
		//WaveletDescription<C> desc = GFactoryWavelet.haar(GrayU8.class);
		//WaveletTransform<GrayU8,GrayS32,C> waveletTran = FactoryWaveletTransform.create(GrayU8.class, desc, 3, 0, 255);
		GrayS32 transform;

		protected WaveletProcessing() {
			super(dm.single(GrayU8.class));
		}

		@Override
		protected void declareImages( int width , int height ) {
			super.declareImages(width, height);
			dm.initWavelet(width, height);

			//ImageDimension d = UtilWavelet.transformDimension(width, height, waveletTran.getLevels() );
			//transform = new GrayS32(d.width,d.height);
		}

		@Override
		protected void process(GrayU8 input, Bitmap output, byte[] storage) {
			this.transform = dm.waveletProcess(input);
			//waveletTran.transform(input,transform);
			//System.out.println("BOOF: num levels " + waveletTran.getLevels());
			//System.out.println("BOOF: width "+transform.getWidth()+" "+transform.getHeight());
			//UtilWavelet.adjustForDisplay(transform, waveletTran.getLevels(), 255);

			// if needed, crop the transform for visualization
			GrayS32 transform = this.transform;
			if( transform.width != output.getWidth() || transform.height != output.getHeight() )
			    transform = transform.subimage(0,0,output.getWidth(),output.getHeight(),null);

			VisualizeImageData.grayMagnitude(transform,255,output,storage);
		}
	}
}