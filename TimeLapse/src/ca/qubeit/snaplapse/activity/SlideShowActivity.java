package ca.qubeit.snaplapse.activity;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectDataSource;
import ca.qubeit.snaplapse.util.MediaHelper;

public class SlideShowActivity extends Activity {

	private static final String TAG = "SlideShowActivity";
	private ImageView ivSlide;	
	private Point screenSize;
	private TextView tvLoading;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_show);
		screenSize = MediaHelper.getScreenSize(this);
		Bundle extras = getIntent().getExtras();
		Long projectId;
		ivSlide = (ImageView)findViewById(R.id.iv_slide);
		tvLoading = (TextView)findViewById(R.id.tv_loading);
		tvLoading.setVisibility(View.INVISIBLE);
		
		if(extras != null){
			projectId = extras.getLong("projectId");
			SlideShowTask show = new SlideShowTask(ivSlide);
			show.execute(projectId);
		}
		
	}
	
	
	public class SlideShowTask extends AsyncTask<Long, Void, ArrayList<Bitmap>>{

		ImageView iv;
		
		public SlideShowTask(ImageView iv){
			this.iv = iv;
			iv = null;
		}

		@Override
		protected ArrayList<Bitmap> doInBackground(Long... id) {
			long projectId = id[0];
			Project project = getProject(projectId);
			ArrayList<Bitmap> i = new ArrayList<Bitmap>();		
			File[] files = MediaHelper.getProjectFilenames(project.getImagePath());
			if(files != null && files.length > 0){
				for(File aFile : files){
					Bitmap bitmap = MediaHelper.getScaledImage(aFile, screenSize.y / 2, screenSize.x / 2);
					i.add(bitmap);
					Log.d(TAG, "image added...size = " + bitmap.getByteCount());
				}
			}
			return i;					
		}
		
		@Override
		protected void onPostExecute(ArrayList<Bitmap> result) {
			if(result != null && result.size() > 0){
				for(final Bitmap bitmap : result){
					ivSlide.post(new Runnable() {
						public void run(){
							try{
								ivSlide.setImageBitmap(bitmap);
								Thread.sleep(500);
							} catch(InterruptedException ex) {
								Log.d(TAG, "Thread interrupted unexpectedly.");
							}
						}
					});
				}
			}
		}
					
		
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
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
