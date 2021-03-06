package org.boofcv.android.tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.boofcv.android.DemoManager;
import org.boofcv.android.R;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import boofcv.abst.feature.associate.AssociateDescription;
import boofcv.abst.feature.associate.ScoreAssociation;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.tracker.PointTracker;
import boofcv.alg.tracker.klt.PkltConfig;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.feature.associate.FactoryAssociation;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.factory.feature.detect.intensity.FactoryIntensityPointAlg;
import boofcv.factory.feature.detect.interest.FactoryInterestPoint;
import boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs;
import boofcv.factory.feature.tracker.FactoryPointTracker;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.transform.pyramid.FactoryPyramid;
import boofcv.struct.feature.TupleDesc_B;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import static org.boofcv.android.CreateDetectorDescriptor.DESC_BRIEF;
import static org.boofcv.android.CreateDetectorDescriptor.DESC_NCC;
import static org.boofcv.android.CreateDetectorDescriptor.DESC_SURF;
import static org.boofcv.android.CreateDetectorDescriptor.DETECT_FAST;
import static org.boofcv.android.CreateDetectorDescriptor.DETECT_FH;
import static org.boofcv.android.CreateDetectorDescriptor.DETECT_SHITOMASI;
import static org.boofcv.android.DemoMain.client;
import static org.boofcv.android.DemoMain.edge;
import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

/**
 * Displays tracking results from the combined tracker.
 *
 * @author Peter Abeles
 */
public class CombinedTrackerDisplayActivity extends PointTrackerDisplayActivity
		implements AdapterView.OnItemSelectedListener
{
	//int tableDet[] = new int[]{DETECT_SHITOMASI,DETECT_FAST,DETECT_FH};
	//int tableDesc[] = new int[]{DESC_BRIEF,DESC_SURF,DESC_NCC};

	int selectedDetector = 0;
	int selectedDescriptor = 0;

	Spinner spinnerDet;
	Spinner spinnerDesc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		InetSocketAddress host, omsHost;

		try {
			Registry registry = LocateRegistry.getRegistry(edge, 22346);
			server = (OMSServer) registry.lookup("SapphireOMS");
			System.out.println(server);

			host = new InetSocketAddress(client, 22346);
			omsHost = new InetSocketAddress(edge, 22346);
			nodeServer = new KernelServerImpl(host, omsHost);
			System.out.println(nodeServer);

			System.setProperty("java.rmi.server.hostname", host.getAddress().getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			//Server is initiated with appObject to perform remote RPCs
			dm = (DemoManager) server.getAppEntryPoint();
			//dm.LatencyCheck();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		LayoutInflater inflater = getLayoutInflater();
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.combined_tracker_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		List<String> local = new ArrayList<String>();
		local.add( "KLT");

		List<String> detectors = new ArrayList<String>();
		detectors.add( "Shi-Tomasi");
		detectors.add( "Fast");
		detectors.add( "Fast Hessian");

		List<String> descriptors = new ArrayList<String>();
		descriptors.add( "BRIEF");
		descriptors.add( "SURF");
		descriptors.add( "NCC");

		Spinner spinnerLocal = (Spinner)controls.findViewById(R.id.spinner_local);
		ArrayAdapter<String> spinnerAdapter =
				new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, local);
		spinnerLocal.setAdapter(spinnerAdapter);

		spinnerDet = (Spinner)controls.findViewById(R.id.spinner_detector);
		spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, detectors);
		spinnerDet.setAdapter(spinnerAdapter);
		spinnerDet.setOnItemSelectedListener(this);

		spinnerDesc = (Spinner)controls.findViewById(R.id.spinner_descriptor);
		spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, descriptors);
		spinnerDesc.setAdapter(spinnerAdapter);
		spinnerDesc.setOnItemSelectedListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		PkltConfig config = new PkltConfig();
		config.templateRadius = 3;
		config.pyramidScaling = new int[]{1,2,4};

		dm.combinedTracker(selectedDetector,selectedDescriptor, config);

		//PointTracker<GrayU8> tracker = createTracker(selectedDetector,selectedDescriptor);
		setProcessing(new PointProcessing());
	}
	/*
	private PointTracker<GrayU8> createTracker( int detector , int descriptor  )
	{
		DetectDescribePoint detDesc = CDD.create(tableDet[detector],tableDesc[descriptor],GrayU8.class, FIP, FIrPA, FIPA, FFE, FIB, FD, GIO, FKG, IT, FBF);

		ScoreAssociation score = FA.defaultScore(detDesc.getDescriptionType());

		AssociateDescription<TupleDesc_B> association = FA.greedy(score, Double.MAX_VALUE, true);

		PkltConfig config = new PkltConfig();
		config.templateRadius = 3;
		config.pyramidScaling = new int[]{1,2,4};

		return FPT.combined(detDesc,association,config,75,GrayU8.class, FP, FKG, FD, GIO, FIB);
	}*/

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id ) {
		if( adapterView == spinnerDesc ) {
			if( selectedDescriptor == spinnerDesc.getSelectedItemPosition() )
				return;
			selectedDescriptor = spinnerDesc.getSelectedItemPosition();
		} else if( adapterView == spinnerDet ) {
			if( selectedDetector == spinnerDet.getSelectedItemPosition() )
				return;
			selectedDetector = spinnerDet.getSelectedItemPosition();
		}
		PkltConfig config = new PkltConfig();
		config.templateRadius = 3;
		config.pyramidScaling = new int[]{1,2,4};
		//PointTracker<GrayU8> tracker = createTracker(selectedDetector,selectedDescriptor);
		dm.combinedTracker(selectedDetector,selectedDescriptor, config);
		setProcessing(new PointProcessing());
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}
}