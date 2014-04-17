package ca.qubeit.snaplapse.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectList;
import ca.qubeit.snaplapse.view.ProjectArrayAdapter;

public class ViewProjectActivity extends Activity {

	private final String TAG = "ViewProjectActivity";
	private ListView lvProjects;
	private Button btnViewShow;
	private ArrayAdapter<Project> adapter;
	private ProjectList projects;
	private long selected;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_project);
		projects = new ProjectList(this);		
	
		//Get UI Refs
		lvProjects = (ListView)findViewById(R.id.lv_view_projects);
		btnViewShow = (Button)findViewById(R.id.btn_view_slideshow);
		
		//Add listeners
		lvProjects.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Log.d(TAG, "Clicked: " + projects.get(position).getName());
				selected = projects.get(position).get_id();
				btnViewShow.setText(getString(R.string.btn_view_slideshow) + " " + projects.get(position).getName() );
				btnViewShow.setTextColor(Color.DKGRAY);
				btnViewShow.setTextSize(30);
			}
		});
		
		btnViewShow.setOnClickListener(new OnClickListener() {	
			
			@Override
			public void onClick(View v) {				
				Log.d(TAG, "Clicked submit");
				Intent showSlides = new Intent(getBaseContext(), SlideShowActivity.class);
				showSlides.putExtra("projectId", selected);
				startActivity(showSlides);				
			}
		});
		adapter = new ProjectArrayAdapter(this, projects);
		lvProjects.setAdapter(adapter);
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_project, menu);
		return true;
	}

}
