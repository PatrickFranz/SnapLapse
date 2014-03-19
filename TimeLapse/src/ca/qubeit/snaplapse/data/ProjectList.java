package ca.qubeit.snaplapse.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

class ProjectList extends ArrayList<Project> {
	
	private static final long serialVersionUID = 1L;
	private Context context;
	private List<Project> projects;
	
	public ProjectList(Context context){
		this.context = context;
		projects = new ArrayList<Project>();
		populateList();
	}
	
	private void populateList(){
		ProjectDataSource src = new ProjectDataSource(context);
		src.open();
		projects = src.getAllProjects();
		src.close();
	}
	
  
	

}
