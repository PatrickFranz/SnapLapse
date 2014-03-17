package ca.qubeit.snaplapse.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectDataSource;
import ca.qubeit.snaplapse.util.MediaHelper;

public class ViewProjectActivity extends Activity {

	private String projectName = "Go Jin";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_project);
		Project project = getProject(projectName);
		Bitmap bm = MediaHelper.getLatestImageFile(project.getImagePath());
		ImageView iv = (ImageView)findViewById(R.id.iv_view_project);
		
		iv.setImageBitmap(bm);
	}

    /**
     * Gets the current Project object we are working on
     * @param name The name of Project
     * @return A Project Object
     */
    private Project getProject(String name){
    	ProjectDataSource dataSource = new ProjectDataSource(this);
    	dataSource.open();
    	Project p = dataSource.getProject(name);
    	dataSource.close();
    	return p;
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_project, menu);
		return true;
	}

}
