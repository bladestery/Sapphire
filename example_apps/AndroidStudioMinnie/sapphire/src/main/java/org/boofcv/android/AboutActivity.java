package org.boofcv.android;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * Displays information about this application.
 *
 * @author Peter Abeles
 */
public class AboutActivity extends Activity {

	private static String text = "<center><h2>BoofCV Demonstration</h2><center><br>" +
			"<p>Offloading Computer Vision Applications " +
			"to the Edge</p>";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.about);

		TextView textView = (TextView) findViewById(R.id.text_about);

		textView.setMovementMethod(LinkMovementMethod.getInstance());
		textView.setText(Html.fromHtml(text));
	}
}