package sapphire.appexamples.minnietwitter.glue;

import com.example.minnietwitter.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.os.StrictMode;
import android.widget.TextView;
import android.util.Log;

import sapphire.appexamples.minnietwitter.device.generator.TwitterWorldGeneratorAndroid;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		Log.d("output", "Starting MAIN");
		TwitterWorldGeneratorAndroid.main(null);
		//TextView minnie = (TextView) findViewById(R.id.minnie);
		//minnie.setText(TwitterWorldGeneratorAndroid.main(Input));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
