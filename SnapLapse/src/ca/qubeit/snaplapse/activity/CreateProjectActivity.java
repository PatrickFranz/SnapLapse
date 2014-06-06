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
import ca.qubeit.snaplapse.data.ProjectHelper;
import ca.qubeit.snaplapse.util.NotificationHelper;

public class CreateProjectActivity extends Activity {

	private final String TAG = "CreateProjectActivity";
	private EditText 			projectName;
	private EditText			projectDescription;
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
		intervalUnit 		= (Spinner)findViewById(R.id.sp_interval);
		submit 				= (Button)findViewById(R.id.btn_create_project_submit);
		
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = "SnapLapse Project";
				String desc = "";
				long intervalInMillis = 0;
				
				//Validate input
				if(projectName.getText().length() > 1){
					name = projectName.getText().toString();
				}
				if(projectDescription.getText().length() > 1){
					desc = projectDescription.getText().toString();
				}
				
				//Create project
				Project project = new Project();
				if(project != null){
					intervalInMillis = ProjectHelper.getLongInterval(intervalUnit.getSelectedItem().toString());
						
					project.setName(name);
					project.setDescription(desc);
					project.setNotificationInterval(intervalInMillis);
					project.setNotifyId(notifyId);
					
					//Schedule a notification
					if(intervalInMillis == Project.NEVER_ALERT){
						//Do nothing
					} else {						
						NotificationHelper.createScheduledNotification(getApplicationContext(), project);						
					}

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
						
					//Parse tracks total number of projects created. Lets add 1 to that now.
					ProjectHelper.sendToParse(name, desc, intervalInMillis, intervalUnit.getSelectedItem().toString());
				}
				finish();
			}			
		});		
	}
	
	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_project, menu);
		return true;
	}

}
