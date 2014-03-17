package ca.qubeit.snaplapse.view;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{
	private static final String TAG = "CameraPreview";
	private Camera camera;
	private SurfaceHolder holder;
	private boolean isPreviewRunning;
	private Context context;
	
	public CameraPreview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	public CameraPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}


	public CameraPreview(Context context) {
		super(context);
		this.context = context;
	}

	public void init(Camera camera){
		this.camera = camera;
		initSurfaceHolder();
	}
	
	private void initSurfaceHolder(){
		holder = getHolder();
		holder.addCallback(this);
	}
	
	private void initCamera(SurfaceHolder holder){
		try{
			camera.setPreviewDisplay(holder);
			Camera.Parameters param;			
			camera.startPreview();
			isPreviewRunning = true;
			
			} catch (IOException e) {
			Log.e(TAG, "Error setting camera preview...");
			e.printStackTrace();
		}		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if(isPreviewRunning){
			camera.stopPreview();			
		}
		
		Parameters params = camera.getParameters();
		Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		
		 if(display.getRotation() == Surface.ROTATION_0)
	        {
			 	//params.setPreviewSize(height, width);                           
	            camera.setDisplayOrientation(90);
	        }

	        if(display.getRotation() == Surface.ROTATION_90)
	        {
	        //	params.setPreviewSize(width, height);                           
	        }

	        if(display.getRotation() == Surface.ROTATION_180)
	        {
	        //	params.setPreviewSize(height, width);               
	        }

	        if(display.getRotation() == Surface.ROTATION_270)
	        {
	        //	params.setPreviewSize(width, height);
	            camera.setDisplayOrientation(180);
	        }
		
		camera.setParameters(params);
		initCamera(holder);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//initCamera(holder);
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

}
