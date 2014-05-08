package ca.qubeit.snaplapse.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectDataSource;
import ca.qubeit.snaplapse.util.NotificationHelper;

import com.parse.ParseObject;

public class CreateProjectActivity extends Activity {

	private final String TAG = "CreateProjectActivity";
	private EditText 			projectName;
	private EditText			projectDescription;
	private EditText 			intervalNumeric;
	private Spinner  			intervalUnit;
	private Button	 			submit;
	private ProjectDataSource 	dataSource;
	private int 				notifyId; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_project);
		dataSource 	= new ProjectDataSource(this);
		notifyId 	= (int) System.currentTimeMillis();		
		
		//Get our references to UI views
		projectName 		= (EditText)findViewById(R.id.et_project_name);
		projectDescription 	= (EditText)findViewById(R.id.et_project_description);
		intervalNumeric 	= (EditText)findViewById(R.id.et_interval);
		intervalUnit 		= (Spinner)findViewById(R.id.sp_interval);
		submit 				= (Button)findViewById(R.id.btn_create_project_submit);
		
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = "SnapLapse Project";
				String desc = "";
				int length 	= 1;
				
				//Validate input
				if(projectName.getText().length() > 1){
					name = projectName.getText().toString();
				}
				if(projectDescription.getText().length() > 1){
					desc = projectDescription.getText().toString();
				}
				
				if(intervalNumeric.getText().length() >= 1){
					length = Integer.parseInt(intervalNumeric.getText().toString());
				}				
				long intervalInMillis = getLongInterval(length, intervalUnit.getSelectedItem().toString());
				
				//Create project and add it to the database.
				Project project = new Project(name, desc, intervalInMillis, notifyId);
				if(project != null){
					dataSource.open();
					Log.i(TAG, "Create new project titled: " + project.getName());
					if(dataSource.add(project) != -1){
						Toast.makeText(getBaseContext()
								,"Created " + project.getName() + "."
								, Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getBaseContext()
								, "Oops! Error creating project."
								, Toast.LENGTH_SHORT).show();
					}
					dataSource.close();
					
					//Schedule a notification
					NotificationHelper.createScheduledNotification(getApplicationContext(), notifyId, intervalInMillis, name);
					
					//Parse tracks total number of projects created. Lets add 1 to that now.
					sendToParse(name, desc, intervalInMillis, intervalUnit.getSelectedItem().toString());
				}
				finish();
			}			
		});		
	}
	
	private void sendToParse(String name, String desc, long intervalInMillis, String intervalUnit) {
		ParseObject newProject = new ParseObject("Project");
		newProject.put("projectName", name);
		newProject.put("description", desc);
		newProject.put("IntervalInMillis"	, intervalInMillis);
		newProject.put("IntervalUnit", intervalUnit);
		newProject.saveInBackground();
	}
	
	private long getLongInterval(int length, String unitType){
		if(unitType.equals("minutes")){
			return length * 60 * 1000;
		}
		else if(unitType.equals("hours")){
			return length * 60 * 60 * 1000;
		} 
		else if(unitType.equals("days")){
			return length * 24 * 60 * 60 * 1000;
		} 
		else if(unitType.equals("weeks")){
			return length * 7 * 24 * 60 * 60 * 1000;
		}		
		else if(unitType.equals("months")){
			return length * 30 * 24 * 60 * 60 * 1000;
		} 
		else {
			return -1;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_project, menu);
		return true;
	}

}
