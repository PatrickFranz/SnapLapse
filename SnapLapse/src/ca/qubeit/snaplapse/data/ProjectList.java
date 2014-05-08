package ca.qubeit.snaplapse.data;

import java.util.ArrayList;

import android.content.Context;

public class ProjectList extends ArrayList<Project> {
	
	private static final long serialVersionUID = 1L;
	private ProjectDataSource src;
	
	public ProjectList(Context context){
		src = new ProjectDataSource(context);
		refresh();
	}
	
	public void refresh(){
		this.clear();
		src.open();
		this.addAll(src.getAllProjects());
		src.close();
	}
  
	

}
