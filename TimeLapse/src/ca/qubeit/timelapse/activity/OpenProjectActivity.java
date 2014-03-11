package ca.qubeit.timelapse.activity;

import ca.qubeit.timelapse.R;
import ca.qubeit.timelapse.R.layout;
import ca.qubeit.timelapse.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class OpenProjectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_project);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_project, menu);
		return true;
	}

}
