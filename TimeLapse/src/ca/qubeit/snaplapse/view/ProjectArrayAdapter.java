package ca.qubeit.snaplapse.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectHelper;
import ca.qubeit.snaplapse.data.ProjectList;

public class ProjectArrayAdapter  extends ArrayAdapter<Project>{

	private Context ctx;
	private ProjectList projects;
	private int selectedProjectPosition;
	private final int DELETE 	= 0;
	private final int SETTINGS  = 1;
	
	
	public ProjectArrayAdapter(Context context, ProjectList projects) {
		super(context, android.R.id.content, projects);
		this.ctx = context;
		this.projects = projects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		selectedProjectPosition = position;
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		final View view = inflater.inflate(R.layout.project_listview_row, null);
		
		Project project = projects.get(selectedProjectPosition);
		
		TextView tvProjectName = (TextView)view.findViewById(R.id.tv_project_name);
		TextView tvProjectDescription = (TextView) view.findViewById(R.id.tv_project_description);
		ImageView ivSettings = (ImageView) view.findViewById(R.id.iv_settings);
		ivSettings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				PopupWindow popup = getPopupWindow(v);
				popup.showAsDropDown(v);
			}
		});
		
		tvProjectName.setText(project.getName());
		tvProjectDescription.setText(project.getDescription());
		
		return view;
	}
	
	private PopupWindow getPopupWindow(View view){
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
				switch(puPosition){
				
				case DELETE:
					
					ProjectHelper pHelper = new ProjectHelper(view.getContext());
					Project selectedProject = projects.get(puPosition);
					projects.remove(selectedProjectPosition);
					if(pHelper.deleteProject(selectedProject)){
						projects.refresh();
						
					}
					puw.dismiss();
					break;
				
				case SETTINGS:
					Toast.makeText(view.getContext()
							, "Setting for " + projects.get(selectedProjectPosition).getName()
							, Toast.LENGTH_SHORT).show();
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
