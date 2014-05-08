package ca.qubeit.snaplapse.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

public class CameraHelper {

	private static final String TAG = "CameraHelper";
	
	public static boolean checkCameraHardware(Context ctx){
		if(ctx.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
			return true;
		} else {
			return false;
		}
	}
	
	public static Camera getCameraInstance(){
		Camera camera = null;
		try{
			camera = Camera.open();
		} catch (Exception e) {
			Log.e(TAG, "Camera not available");
		}
		
		return camera;
	}
}
