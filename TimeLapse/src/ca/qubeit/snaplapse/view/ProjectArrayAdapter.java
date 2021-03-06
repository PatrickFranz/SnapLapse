package ca.qubeit.snaplapse.view;

import java.util.List;

import android.R.color;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;

public class ProjectArrayAdapter  extends ArrayAdapter<Project>{

	private Context ctx;
	private List<Project> projects;
	private int selectedItem;
	
	public ProjectArrayAdapter(Context context, List<Project> projects) {
		super(context, android.R.id.content, projects);
		this.ctx = context;
		this.projects = projects;
	}
	
	public void setSelectedItem(int position){
		selectedItem = position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.project_listview_row, null);
		Project project = projects.get(position);
		
		LinearLayout row = (LinearLayout) view;
		if(position == selectedItem){
			row.setBackgroundColor(color.holo_red_light);
		}
		
		TextView tvProjectName = (TextView)view.findViewById(R.id.tv_project_name);
		TextView tvProjectDescription = (TextView) view.findViewById(R.id.tv_project_description);
		
		tvProjectName.setText(project.getName());
		tvProjectDescription.setText(project.getDescription());
		
		
		
		return view;
	}

}
