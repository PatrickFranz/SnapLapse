package ca.qubeit.snaplapse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.ProjectList;
import ca.qubeit.snaplapse.view.DeleteProjectDialogFragment;
import ca.qubeit.snaplapse.view.ProjectArrayAdapter;

public class OpenProjectActivity extends Activity {
	
	private static final String TAG = "OpenProjectActivity";
	private ProjectArrayAdapter projectAdapter;
	private ListView lvProjectList;
	private Button newProject;	
	private ProjectList projects;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_project);
		
		//Get UI references
		lvProjectList = (ListView)findViewById(R.id.lv_open_projects);
		lvProjectList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				Log.d(TAG, "ProjectList onItemClick...");
				view.setBackgroundColor(getResources().getColor(R.color.snaplapse_blue));
				Intent takePicture = new Intent(getBaseContext(), CameraActivity.class);
				takePicture.putExtra("projectName", projects.get(position).getName());
				startActivity(takePicture);		
			}
		});		
		
		newProject = (Button)findViewById(R.id.btn_create_project);
		newProject.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), CreateProjectActivity.class);
				startActivity(i);				
			}
		});		
		
		//Get Data
		projects = new ProjectList(this);
		projectAdapter = new ProjectArrayAdapter(this, projects);
		lvProjectList.setAdapter(projectAdapter);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume...");
		projectAdapter.clear();
		projects.refresh();
		projectAdapter.notifyDataSetChanged();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_project, menu);
		return true;
	}


}
