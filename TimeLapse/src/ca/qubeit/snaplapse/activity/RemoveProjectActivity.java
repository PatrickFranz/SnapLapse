package ca.qubeit.snaplapse.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectDataSource;
import ca.qubeit.snaplapse.util.MediaHelper;
import ca.qubeit.snaplapse.view.ProjectArrayAdapter;

public class RemoveProjectActivity extends Activity {

	private static final String TAG = 	"RemoveProjectActivity";
	private ArrayAdapter<Project> 		projectAdapter;
	private ListView 	lvProjectList;
	private Button		 btnRemove;
	private CheckBox	chkIsDeleteOk;
	private ProjectDataSource 	dataSource;
	private List<Project> 		projects;
	private AlertDialog.Builder dialogBuilder;
	private Project selectedProject;
	private int selectedPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remove_project);
		
		//Get UI References
		lvProjectList = (ListView)findViewById(R.id.lv_remove_projects);
		lvProjectList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {		
				selectedProject = projects.get(position);
				selectedPosition = position;
				btnRemove.setText(getString(R.string.btn_delete_project) + " " + selectedProject.getName());				
			}
		});
		btnRemove = (Button)findViewById(R.id.btn_remove_project);
		btnRemove.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
			
				AlertDialog dlg = buildDialog();
				dlg.show();
			}				
		});
		
		//Populate projects
		getProjects();
		
		//SetAdapter for list
		projectAdapter = new ProjectArrayAdapter(this, projects);
		lvProjectList.setAdapter(projectAdapter);
		
	}
	
	private AlertDialog buildDialog(){
		//Get Dialog layout
		View dlgLayout = View.inflate(this, R.layout.dlg_delete_project, null);
		chkIsDeleteOk = (CheckBox)dlgLayout.findViewById(R.id.isDeleteOk);
		chkIsDeleteOk.setText(R.string.dlg_delete_images);
		
		dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setView(dlgLayout)
			.setTitle(R.string.dlg_remove_title)
			.setMessage(R.string.dlg_warning_msg)
			.setCancelable(true)
			.setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					projects.remove(selectedPosition);
					deleteProjectFromDatabase();
					if(chkIsDeleteOk.isChecked()){
						Log.d(TAG, "Deleting project folder...");
						MediaHelper.removeProjectFiles(selectedProject.getName());
					}
				}				
		      })
		      .setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}				    	  
		      });
		
		return dialogBuilder.create();
	}
	
	private void deleteProjectFromDatabase() {
		Log.d(TAG, "Project " + selectedProject.getName() + " removed from database");
		ProjectDataSource source = new ProjectDataSource(this);
		source.open();
		source.delete(selectedProject.get_id());
		projectAdapter.clear();		
		projectAdapter.addAll(source.getAllProjects());
		source.close();						
		projectAdapter.notifyDataSetChanged();
		btnRemove.setText(R.string.btn_delete_project);
	}
	
	
	private void getProjects() {
		if(dataSource == null){
			dataSource = new ProjectDataSource(this);
		}
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
}
