package ca.qubeit.snaplapse.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
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
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectDataSource;
import ca.qubeit.snaplapse.view.ProjectArrayAdapter;
import ca.qubeit.timelapse.R;

public class OpenProjectActivity extends Activity {
	
	private static final String TAG = "OpenProjectActivity";
	private ArrayAdapter<Project> projectAdapter;
	private ListView lvProjectList;
	private Button newProject;
	private ProjectDataSource dataSource;
	private List<Project> projects;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_project);
		dataSource = new ProjectDataSource(this);
		//Get UI references
		lvProjectList = (ListView)findViewById(R.id.lv_projects);
		lvProjectList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				//ArrayAdapter<Project> projectAdapter = (ProjectArrayAdapter)lvProjectList.getAdapter();
				Log.d(TAG, "onItemClick...");
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
		getProjects();
		projectAdapter = new ProjectArrayAdapter(this, projects);
		lvProjectList.setAdapter(projectAdapter);
		
		
	}

	private void getProjects() {		
		
		dataSource.open();
		projects = dataSource.getAllProjects();
		dataSource.close();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getProjects();
		projectAdapter.clear();
		projectAdapter.addAll(projects);
		projectAdapter.notifyDataSetChanged();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_project, menu);
		return true;
	}

}
