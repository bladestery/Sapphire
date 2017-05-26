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

import org.boofcv.android.DemoManager;
import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.function.IntBinaryOperator;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.filter.binary.impl.ImplBinaryBorderOps;
import boofcv.alg.filter.binary.impl.ImplBinaryInnerOps;
import boofcv.android.VisualizeImageData;
import boofcv.android.gui.VideoImageProcessing;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

/**
 * Converts camera image into a binary image and lets the user control the threshold/filters.
 *
 * @author Peter Abeles
 */
public class BinaryDisplayActivity extends DemoVideoDisplayActivity
		implements SeekBar.OnSeekBarChangeListener ,
		CompoundButton.OnCheckedChangeListener,
		AdapterView.OnItemSelectedListener {
	boolean down;
	double threshold;
	int action;
	OMSServer server;
	DemoManager dm;
	ImageType IT = new ImageType();

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
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.binary_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		SeekBar seek = (SeekBar)controls.findViewById(R.id.slider_threshold);
		seek.setOnSeekBarChangeListener(this);

		ToggleButton toggle = (ToggleButton)controls.findViewById(R.id.toggle_threshold);
		toggle.setOnCheckedChangeListener(this);

		Spinner spinner = (Spinner)controls.findViewById(R.id.spinner_binary_ops);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.binary_filters, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);

		down = toggle.isChecked();
		threshold = seek.getProgress();
		action = spinner.getSelectedItemPosition();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setProcessing(new ThresholdProcessing() );
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		threshold = progress;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
		down = isChecked;
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id ) {
		action = pos;
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}

	protected class ThresholdProcessing extends VideoImageProcessing<GrayU8> {
		GrayU8 binary;
		GrayU8 afterOps;

		protected ThresholdProcessing() {
			super(IT.single(GrayU8.class));
		}

		@Override
		protected void declareImages( int width , int height ) {
			super.declareImages(width, height);
			dm.initImageBinary(width, height);
			binary = new GrayU8(width,height);
			//afterOps = new GrayU8(width,height);
		}

		@Override
		protected void process(GrayU8 input, Bitmap output, byte[] storage) {
			//dm.threshold(input,threshold, down);

			switch( action ) {
				case 1:
					afterOps = dm.dilate4(input,threshold,down,1);
					//BIO.dilate4(binary,1,afterOps, ISC, IBIO, IBBO, IBV);
					break;

				case 2:
					afterOps = dm.dilate8(input, threshold, down, 1);
					//BIO.dilate8(binary, 1, afterOps, ISC, IBIO, IBBO, IBV);
					break;

				case 3:
					afterOps = dm.erode4(input, threshold, down, 1);
					//BIO.erode4(binary, 1, afterOps, ISC, IBIO, IBBO, IBV);
					break;

				case 4:
					afterOps = dm.erode8(input, threshold, down, 1);
					//BIO.erode8(binary, 1, afterOps, ISC, IBIO, IBBO, IBV);
					break;

				case 5:
					afterOps = dm.edge4(input, threshold, down);
					//BIO.edge4(binary, afterOps, ISC, IBIO, IBBO, IBV);
					break;

				case 6:
					afterOps = dm.edge8(input, threshold, down);
					//BIO.edge8(binary, afterOps, ISC, IBIO, IBBO, IBV);
					break;

				case 7:
					afterOps = dm.removePointNoise(input, threshold, down);
					//BIO.removePointNoise(binary, afterOps, ISC, IBIO, IBBO, IBV);
					break;

				case 8:
					afterOps = dm.thin(input, threshold, down, 50);
					//BIO.thin(binary,50,afterOps, ISC);
					break;

				default:
					afterOps = dm.threshold(input, threshold, down);
			}

			VisualizeImageData.binaryToBitmap(afterOps, false, output, storage);
		}
	}
}