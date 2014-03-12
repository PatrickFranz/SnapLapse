package ca.qubeit.snaplapse.activity;


import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import ca.qubeit.snaplapse.util.CameraHelper;
import ca.qubeit.snaplapse.util.MediaHelper;
import ca.qubeit.snaplapse.view.CameraPreview;
import ca.qubeit.timelapse.R;

public class CameraActivity extends Activity implements PictureCallback{

	protected static final String EXTRA_IMAGE_PATH = "ca.qubeit.snaplapse.activity.CameraActivity.EXTRA_IMAGE_PATH";
	private Camera camera;
	private CameraPreview cameraPreview;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setResult(RESULT_CANCELED);
        camera = CameraHelper.getCameraInstance();
        initCameraPreview();
       
        
    }
    
    private void initCameraPreview(){
   
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
    	File file = MediaHelper.getOutputMediaFile();
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
