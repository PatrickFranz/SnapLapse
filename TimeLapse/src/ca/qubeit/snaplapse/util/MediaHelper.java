package ca.qubeit.snaplapse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class MediaHelper {

	private final static String TAG = "MediaHelper";
	public static File getOutputImageFile(String projectName){
		File mediaFile = null;
		if(isMediaMounted()){
		   File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "SnapLapse/" + projectName);
	    // Create the storage directory if it does not exist
		    if (! mediaStorageDir.exists()){
		        if (! mediaStorageDir.mkdirs()){
		            Log.d(TAG, "failed to create directory");
		            return null;
		        }
		    }
		    // Create a media file name
		    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
		    mediaFile = new File(mediaStorageDir.getPath() + File.separator + timeStamp + ".jpg");
		}

	    return mediaFile;
	}

	public static boolean saveToFile(byte[] bytes, File file){
		boolean saved = false;
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.close();
			saved = true;
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", e.getMessage());
		} catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}
		return saved;
	}
	
	public static Bitmap rotateBitmap(Bitmap source, float angle){
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);		
	}
	
	public static Bitmap getLatestImageFile(String path, int reqWidth, int reqHeight){
		File imgFile = findMostRecentImage(path);
		if(imgFile != null){
			return getScaledImage(imgFile, reqWidth, reqHeight);
		} else {
			return null;
		}		
	}
	
	 public static Point getScreenSize(Context context){
	    	Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    	Point outSize = new Point();
	    	display.getSize(outSize);
	    	return outSize;
	    }

	public static Bitmap getScaledImage(File imgFile, int reqWidth, int reqHeight) {
		try{			
			final BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(imgFile), null, opts);
			
			opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
			if(imgFile != null){								
				opts.inJustDecodeBounds = false;
				return rotateBitmap(BitmapFactory.decodeStream(new FileInputStream(imgFile), null, opts), 90);	
				 
			}
		} catch (FileNotFoundException ex){
			Log.d(TAG, "Problem loading file for scaling...");
		}
		return null;
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
		final int height = options.outHeight;
		final int width = options.outWidth;		
		int inSampleSize = 1;
		
		if(height > reqHeight || width > reqWidth){
			final int halfHeight = height;
			final int halfWidth = width / 2;
		
			while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
		}		
		return inSampleSize;
	}

	private static File findMostRecentImage(String path) {
		File mostRecentFile = null;
		if(isMediaMounted()){
			File fileDir = new File(path);
			
			File[] files = fileDir.listFiles();
			if(files != null && files.length > 0){
				mostRecentFile = files[0];
				for(File file : files){
					if(file.lastModified() > mostRecentFile.lastModified()){
						mostRecentFile = file;
					}
				}
			}
		}
		return mostRecentFile;
	}
	
	public static File[] getProjectFilenames(String path) {
		File[] files = null;
		if(isMediaMounted()){
			
			File fileDir = new File(path);
			if(fileDir != null){
				files = fileDir.listFiles();
			}			
		}
		return files;
	}
	
	private static boolean isMediaMounted(){
		final String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);		
	}

}
