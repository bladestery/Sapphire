package org.boofcv.android.tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.boofcv.android.CreateDetectorDescriptor;
import org.boofcv.android.R;

import java.util.ArrayList;
import java.util.List;

import boofcv.abst.feature.associate.AssociateDescTo2D;
import boofcv.abst.feature.associate.AssociateDescription2D;
import boofcv.abst.feature.associate.ScoreAssociation;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.tracker.PointTracker;
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
import boofcv.struct.feature.TupleDesc_B;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;

import static org.boofcv.android.CreateDetectorDescriptor.DESC_BRIEF;
import static org.boofcv.android.CreateDetectorDescriptor.DESC_NCC;
import static org.boofcv.android.CreateDetectorDescriptor.DESC_SURF;
import static org.boofcv.android.CreateDetectorDescriptor.DETECT_FAST;
import static org.boofcv.android.CreateDetectorDescriptor.DETECT_FH;
import static org.boofcv.android.CreateDetectorDescriptor.DETECT_SHITOMASI;


/**
 * Displays tracking results for DDA type tracker.  User can select which internal algorithms to use.
 *
 * @author Peter Abeles
 */
public class DdaTrackerDisplayActivity extends PointTrackerDisplayActivity
		implements AdapterView.OnItemSelectedListener
{
	private static FactoryAssociation FA;
	private static CreateDetectorDescriptor CDD;
	private static FactoryInterestPoint FIP;
	private static FactoryInterestPointAlgs FIrPA;
	private static FactoryIntensityPointAlg FIPA;
	private static FactoryFeatureExtractor FFE;
	private static FactoryDerivative FD;
	private static FactoryImageBorder FIB;
	private static GeneralizedImageOps GIO;
	private static FactoryKernelGaussian FKG;
	private static ImageType IT;
	private static FactoryBlurFilter FBF;
	int tableDet[] = new int[]{DETECT_SHITOMASI,DETECT_FAST,DETECT_FH};
	int tableDesc[] = new int[]{DESC_BRIEF,DESC_SURF,DESC_NCC};

	int selectedDetector = 0;
	int selectedDescriptor = 0;

	Spinner spinnerDet;
	Spinner spinnerDesc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getLayoutInflater();
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.associate_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		List<String> detectors = new ArrayList<String>();
		detectors.add( "Shi-Tomasi");
		detectors.add( "Fast");
		detectors.add( "Fast Hessian");

		List<String> descriptors = new ArrayList<String>();
		descriptors.add( "BRIEF");
		descriptors.add( "SURF");
		descriptors.add( "NCC");

		spinnerDet = (Spinner)controls.findViewById(R.id.spinner_detector);
		ArrayAdapter<String> spinnerAdapter =
				new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, detectors);
		spinnerDet.setAdapter(spinnerAdapter);
		spinnerDet.setOnItemSelectedListener(this);
		spinnerDet.invalidate();

		spinnerDesc = (Spinner)controls.findViewById(R.id.spinner_descriptor);
		spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, descriptors);
		spinnerDesc.setAdapter(spinnerAdapter);
		spinnerDesc.setOnItemSelectedListener(this);
		spinnerDesc.invalidate();
	}

	@Override
	protected void onResume() {
		super.onResume();

		PointTracker<GrayU8> tracker = createTracker(selectedDetector,selectedDescriptor);
		setProcessing(new PointProcessing(tracker));
	}

	private PointTracker<GrayU8> createTracker( int detector , int descriptor  )
	{
		DetectDescribePoint detDesc = CDD.create(tableDet[detector],tableDesc[descriptor],GrayU8.class, FIP, FIrPA, FIPA, FFE, FIB, FD, GIO, FKG, IT, FBF);

		ScoreAssociation score = FA.defaultScore(detDesc.getDescriptionType());

		AssociateDescription2D<TupleDesc_B> association =
				new AssociateDescTo2D<TupleDesc_B>(
						FA.greedy(score, Double.MAX_VALUE, true));

		return FactoryPointTracker.dda(detDesc,association,false);
	}

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

		PointTracker<GrayU8> tracker = createTracker(selectedDetector,selectedDescriptor);
		setProcessing(new PointProcessing(tracker));
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}
}