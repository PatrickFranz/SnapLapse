package ca.qubeit.snaplapse.activity;


import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.d(TAG, "onCreate() ....");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        camera = CameraHelper.getCameraInstance();
        setResult(RESULT_CANCELED);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
        	projectName = extras.getString("projectName");
        }
        ivBackingImage = (ImageView)findViewById(R.id.iv_camera_preview_overlay);
        initCameraPreview();
        setBackingImage();      
    }
    
	private void setBackingImage(){
		Log.d(TAG, "setBackingImage() ....");
		Bitmap image = null;
    	if(projectName != null){
    		Project project = getProject(projectName);
    		if(project.getImagePath() != null){
    			image = MediaHelper.getLatestImageFile(project.getImagePath());    			
    			if(image != null){
    				int imgWidth = image.getWidth();
    				int imgHeight = image.getHeight();
    				int newWidth = getScreenWidth();
    				float scaleFactor = (float)newWidth / (float)imgWidth;
    				int newHeight = (int) (imgHeight * scaleFactor);
    				image = Bitmap.createScaledBitmap(image, newWidth, newHeight, true);
    				ivBackingImage.setImageBitmap(image);
    				ivBackingImage.setRotation(90);
    				ivBackingImage.setAlpha(0.5f);
    			}
    		}
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
    
    private int getScreenWidth(){
    	Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
    	Point outSize = new Point();
    	display.getSize(outSize);
    	return outSize.y;
    }
    
    private void initCameraPreview(){
    	Log.d(TAG, "initCameraPreview() ....");
    	cameraPreview = (CameraPreview)findViewById(R.id.camera_preview);
    	cameraPreview.init(camera);
    	
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
    	Log.d(TAG, "onResume() ....");
    	super.onResume();
    	if(camera == null){
    		camera = CameraHelper.getCameraInstance();
    	}
    	try {
			camera.reconnect();
		} catch (IOException e) {
			Log.d(TAG, "Unable to reconnect to camera");
		}
    	setBackingImage();
    	initCameraPreview();
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
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }	
    
}
