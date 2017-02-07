package sapphire.appexamples.minnietwitter.glue;

import com.example.minnietwitter.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.os.StrictMode;
import android.widget.TextView;
import android.os.AsyncTask;

import sapphire.appexamples.minnietwitter.device.generator.TwitterWorldGeneratorAndroid;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new AccessRemoteObject().execute();
		setContentView(R.layout.activity_main);
	}

	private class AccessRemoteObject extends AsyncTask<String, Void, String>{
		protected String doInBackground(String... params) {
			TwitterWorldGeneratorAndroid.main(null);
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
