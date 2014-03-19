package ca.qubeit.snaplapse.activity;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageSwitcher;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectDataSource;
import ca.qubeit.snaplapse.util.MediaHelper;

public class SlideShowActivity extends Activity {

	private ImageSwitcher switcher;	
	private ArrayList<Bitmap> images;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_show);
		images = new ArrayList<Bitmap>();
		Bundle extras = getIntent().getExtras();
		long projectId = 0;
		if(extras != null){
			projectId = extras.getLong("projectId");
		}
		images = getImages(projectId);
		
		//Get UI refs
		switcher = (ImageSwitcher)findViewById(R.id.switcher);
		
	}
	
	private ArrayList<Bitmap> getImages(long id){
		Project project = getProject(id);
		ArrayList<Bitmap> i = new ArrayList<Bitmap>();		
		File[] files = MediaHelper.getProjectFilenames(project.getImagePath());
		for(File aFile : files){
			i.add(MediaHelper.getScaledImage(aFile));
		}
		return images;		
	}
	
	private Project getProject(long id) {		
		ProjectDataSource dataSource = new ProjectDataSource(this);
		Project p;
		dataSource.open();
		p = dataSource.getProject(id);
		dataSource.close();
		return p;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.slide_show, menu);
		return true;
	}

}
