package ca.qubeit.snaplapse.activity;


import java.io.File;
import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectDataSource;
import ca.qubeit.snaplapse.util.CameraHelper;
import ca.qubeit.snaplapse.util.MediaHelper;
import ca.qubeit.snaplapse.view.CameraPreview;

public class CameraActivity extends Activity implements PictureCallback{

	protected static final String EXTRA_IMAGE_PATH = "ca.qubeit.snaplapse.activity.CameraActivity.EXTRA_IMAGE_PATH";
	private final String TAG = "CameraActivity";
	private Camera camera;
	private CameraPreview cameraPreview;
	private String projectName;
	private ImageView ivBackingImage;
	private Point screenSize;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.d(TAG, "onCreate() ....");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        camera = CameraHelper.getCameraInstance();
        screenSize = MediaHelper.getScreenSize(this);
        setupActionBar();
        setResult(RESULT_CANCELED);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
        	projectName = extras.getString("projectName");
        }
        ivBackingImage = (ImageView)findViewById(R.id.iv_camera_preview_overlay);
        initCameraPreview();
        setBackingImage(false);
    }
    

	private void setBackingImage(boolean isOn){
		Log.d(TAG, "setBackingImage() ....");
		Bitmap image = null;
    	if(projectName != null && isOn){
    		Project project = getProject(projectName);
    		if(project.getImagePath() != null){
    			image = MediaHelper.getLatestImageFile(project.getImagePath(), screenSize.x, screenSize.y); 			
    			if(image != null){
    				float width = (float)image.getWidth();
    				float height =image.getHeight();
    				ivBackingImage.setVisibility(View.VISIBLE);
    				ivBackingImage.setLayoutParams(new RelativeLayout.LayoutParams((int)width, (int)height));
    				ivBackingImage.setAlpha(0.5f);
    				ivBackingImage.setImageBitmap(image);
    			} 
    		}
    	} else {
    		ivBackingImage.setVisibility(View.INVISIBLE);
    	}
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
    
    private void initCameraPreview(){
    	Log.d(TAG, "initCameraPreview() ....");
    	cameraPreview = (CameraPreview)findViewById(R.id.camera_preview);
    	cameraPreview.init(camera);
    	
    }
    
    class DecodeImagesRunnable implements Runnable{

		@Override
		public void run() {
			android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
			
		}
    	
    }
    
    //FromXML
    public void onCaptureClick(View button){
    	camera.takePicture(null, null, this);
    }
    
    @Override
	public void onPictureTaken(byte[] data, Camera camera) {
		String path = savePictureToFileSystem(data);
		setResult(path);
		finish();
	}
    
    private String savePictureToFileSystem(byte[] data){
    	File file = MediaHelper.getOutputImageFile(projectName);
    	MediaHelper.saveToFile(data, file);
    	return file.getAbsolutePath();
    }
    
    private void setResult(String path){
    	Intent intent = new Intent();
    	intent.putExtra(EXTRA_IMAGE_PATH, path);
    	setResult(RESULT_OK, intent);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	releaseCamera();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Log.d(TAG, "onResume() ....");
    	if(camera == null){
    		camera = CameraHelper.getCameraInstance();
    	}
    	try {
			camera.reconnect();
		} catch (IOException e) {
			Log.d(TAG, "Unable to reconnect to camera");
		}
    }
    
    private void releaseCamera(){
    	if(camera != null){
    		camera.release();
    		camera = null;
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
    
    private void setupActionBar(){
    	//Setup ActionBar
    	ActionBar bar = getActionBar();
    	bar.setDisplayHomeAsUpEnabled(true);
    	bar.setDisplayShowCustomEnabled(true);
    	
    	//Get the custom view
    	View customBar = getLayoutInflater().inflate(R.layout.actionbar_camera, null);
    	ToggleButton togglePreview = (ToggleButton) customBar.findViewById(R.id.switch_tog_preview);    	
    	togglePreview.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				setBackingImage(isChecked);				
			}
		});
    	
    //	ImageButton switchCamera = (ImageButton) customBar.findViewById(R.id.action_switch_camera);
    	
    	bar.setCustomView(customBar);
    	
    	
    }
    
}
