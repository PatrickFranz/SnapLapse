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

public class CreateProjectActivity extends Activity {

	private final String TAG = "CreateProjectActivity";
	private EditText projectName;
	private EditText projectDescription;
	private EditText intervalNumeric;
	private Spinner  intervalUnit;
	private Button	 submit;
	private ProjectDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_project);
		dataSource = new ProjectDataSource(this);
		
		
		//Get our references to UI views
		projectName = (EditText)findViewById(R.id.et_project_name);
		projectDescription = (EditText)findViewById(R.id.et_project_description);
		intervalNumeric = (EditText)findViewById(R.id.et_interval);
		intervalUnit = (Spinner)findViewById(R.id.sp_interval);
		submit = (Button)findViewById(R.id.btn_create_project_submit);
		
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
				Project project = new Project(name, desc, intervalInMillis);
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
				}
				finish();
			}


		});		
	}
	
	private long getLongInterval(int length, String unitType){
		if(unitType.equals("day")){
			return length * 24 * 60 * 60 * 1000;
		} 
		else if(unitType.equals("week")){
			return length * 7 * 24 * 60 * 60 * 1000;
		}		
		else if(unitType.equals("month")){
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
