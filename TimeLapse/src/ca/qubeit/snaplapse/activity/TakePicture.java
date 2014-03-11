package ca.qubeit.snaplapse.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ca.qubeit.timelapse.R;

public class TakePicture extends Activity {
	private static final int REQ_CAMERA_IMAGE = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_picture);
		String message = "Click the button to start.";
//		if(cameraNotDetected()){
//			message = "No camera detected";
//		}
		TextView tv = (TextView) findViewById(R.id.tv_camera_topbar);
		tv.setText(message);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_picture, menu);
		return true;
	}
	/*
	public void onUseCameraClick(View button){
		Intent intent = new Intent(this, CameraActivity.class);
		startActivityForResult(intent, REQ_CAMERA_IMAGE);
	}
	
	private boolean cameraNotDetected(){
		return !getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_OK){
			String imgPath = data.getStringExtra(CameraActivity.EXTRA_IMAGE_PATH);
			Log.i("tag", "Got image path: " + imgPath);
			displayImage(imgPath);
			
		} else 
		
		if(requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_CANCELED){
			Log.i("tag", "User didn't take an image");
			
		}
	}
	
	private void displayImage(String path){
		ImageView imageView = (ImageView)findViewById(R.id.image_view_captured_image);
		imageView.setImageBitmap(BitmapHelper.decodeSampleBitmap(path, 300, 250));
	}
	
	*/

}
