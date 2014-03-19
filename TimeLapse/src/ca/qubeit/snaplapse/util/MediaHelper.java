package ca.qubeit.snaplapse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;


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
	
	public static Bitmap getLatestImageFile(String path){
		File imgFile = findMostRecentImage(path);
		getScaledImage(imgFile);
		return null;
	}

	public static Bitmap getScaledImage(File imgFile) {
		try{			
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			if(imgFile != null){
				BitmapFactory.decodeStream(new FileInputStream(imgFile), null, opts);
				//Find proper scale value
				final int REQ_SIZE = 70;
				int width = opts.outWidth;
				int height = opts.outHeight;
				int scale = 1;
				while(true){
					if(width / 2 < REQ_SIZE || height / 2 < REQ_SIZE){
						break;
					}
					width  /= 2;
					height /= 2;
					scale++;
				}
				BitmapFactory.Options opts2 = new BitmapFactory.Options();
				opts2.inSampleSize = scale;
				return BitmapFactory.decodeStream(new FileInputStream(imgFile), null, opts2);
			}
		} catch (FileNotFoundException ex){
			Log.d(TAG, "Problem loading file for scaling...");
		}
		return null;
	}

	private static File findMostRecentImage(String path) {
		File mostRecentFile = null;
		if(isMediaMounted()){
			File fileDir = new File(path);
			
			File[] files = fileDir.listFiles();
			if(files != null){
				long newest = files[0].lastModified();
				for(File file : files){
					if(file.lastModified() > newest){
						newest = file.lastModified();
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
