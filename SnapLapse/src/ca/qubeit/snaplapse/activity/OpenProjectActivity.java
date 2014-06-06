package ca.qubeit.snaplapse.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectDataSource;
import ca.qubeit.snaplapse.data.ProjectList;
import ca.qubeit.snaplapse.view.DeleteProjectDialogFragment;
import ca.qubeit.snaplapse.view.EditProjectDialogFragment;
import ca.qubeit.snaplapse.view.ProjectArrayAdapter;

public class OpenProjectActivity extends FragmentActivity implements OnButtonClickListener{
	
	private static final String TAG = "OpenProjectActivity";
	private ProjectArrayAdapter projectAdapter;
	private ListView lvProjectList;
	private Button newProject;	
	private ProjectList projects;
	private final int DELETE 	= 0;
	private final int SETTINGS  = 1;
	private View lastClicked;
	
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
				lastClicked = view;
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
		refreshAdapter();
		
	}

	private void refreshAdapter() {
		projectAdapter.clear();
		projects.refresh();
		projectAdapter.notifyDataSetChanged();
		if(lastClicked != null){
			lastClicked.setBackgroundColor(Color.WHITE);
		}	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_project, menu);
		return true;
	}

	@Override
	public void projectOptionsButtonClick(View view, int position) {
		PopupWindow popup = getPopupWindow(view, position);
        popup.showAsDropDown(view);
		
	}
	
	@Override
	public void dialogButtonClick(int result, Project project) {
		if(result == OnButtonClickListener.CLICK_CONFIRM){
			//Confirm was pressed and the project changed
			ProjectDataSource source = new ProjectDataSource(this);
			source.open();
			source.update(project);
			source.close();
		}
		refreshAdapter();
		
	}
	
	private PopupWindow getPopupWindow(View view, final int pos){
		final PopupWindow puw = new PopupWindow(view.getContext());
		
		ListView lvOptions = new ListView(view.getContext());
		
		String[] options = {"Delete", "Settings"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, options);
		lvOptions.setAdapter(adapter);
		lvOptions.setBackgroundColor(Color.WHITE);
		lvOptions.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int puPosition, long id) {
				
				Project selectedProject = projects.get(pos);
				
				switch(puPosition){
				case DELETE:
					DeleteProjectDialogFragment deleteDialog = new DeleteProjectDialogFragment();
					deleteDialog.setProject(selectedProject);
					FragmentManager deleteFM = getSupportFragmentManager(); 
					deleteDialog.show(deleteFM, null);
					puw.dismiss();
					break;
				
				case SETTINGS:					
					EditProjectDialogFragment editDialog = new EditProjectDialogFragment();
					FragmentManager editFM = getSupportFragmentManager();
					editDialog.setProject(selectedProject);
					editDialog.show(editFM, null);					
					puw.dismiss();
					break;
				
				default:
					puw.dismiss();
					break;
					
				}				
			}
		});
		
		puw.setContentView(lvOptions);
		puw.setFocusable(true);
		puw.setWidth(400);
		puw.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		return puw;
	}

	
	

}