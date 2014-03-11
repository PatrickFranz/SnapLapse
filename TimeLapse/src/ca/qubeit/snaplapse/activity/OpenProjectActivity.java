package ca.qubeit.snaplapse.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectDataSource;
import ca.qubeit.snaplapse.view.ProjectArrayAdapter;
import ca.qubeit.timelapse.R;

public class OpenProjectActivity extends Activity {
	
	private static final String TAG = "OpenProjectActivity";

	private ListView lvProjectList;
	
	private ProjectDataSource dataSource;
	private List<Project> projects;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_project);
		//Get UI references
		lvProjectList = (ListView)findViewById(R.id.lv_projects);
		lvProjectList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				//ArrayAdapter<Project> projectAdapter = (ProjectArrayAdapter)lvProjectList.getAdapter();
				Log.d(TAG, "onItemClick...");
				Toast.makeText(getBaseContext(), "Click", Toast.LENGTH_SHORT).show();
				
			}
		});
		//Get Data
		dataSource = new ProjectDataSource(this);
		dataSource.open();
		projects = dataSource.getAllProjects();
		dataSource.close();
		ArrayAdapter<Project> projectAdapter = new ProjectArrayAdapter(this, projects);
		lvProjectList.setAdapter(projectAdapter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_project, menu);
		return true;
	}

}
