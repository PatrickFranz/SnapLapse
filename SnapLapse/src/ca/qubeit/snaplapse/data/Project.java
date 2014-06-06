/**
 * A project in the SnapLapse Application
 */
package ca.qubeit.snaplapse.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;

/**
 * @author Patrick Franz
 * @Date Feb 1, 2014
 *
 */
public class Project {
	public static final long MINUTE_IN_MILLIS = 1000 * 60; //For testing 
	public static final long NEVER_ALERT = 0;
	public static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	public static final long WEEK_IN_MILLIS = 1000 * 60 * 60 * 24 * 7;
	public static final long MONTH_IN_MILLIS = 1000 * 60 * 60 * 24 * 7 * 30;
	
	private int _id;
	private String name;
	private String description;
	private long createdDateInMillis;
	private long notificationInterval;
	private int notifyId;
	private String imagePath;
	
	public Project() {
		setCreatedDate(System.currentTimeMillis());
		setImagePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/SnapLapse/" + name);		
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCreatedDateInMillis() {
		return createdDateInMillis;
	}
	
	public String getCreatedDateString(){
		DateFormat df = new SimpleDateFormat("MM:dd:YYYY",Locale.US);
		return df.format(new Date(getCreatedDateInMillis()));				
	}

	public void setCreatedDate(long dateInMillis) {
		this.createdDateInMillis = dateInMillis;
	}

	public long getNotificationInterval() {
		return notificationInterval;
	}

	public void setNotificationInterval(long notificationInterval) {
		this.notificationInterval = notificationInterval;
	}

	public int getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public int getReminderFrequencyInHuman(){		
		if(notificationInterval == DAY_IN_MILLIS){
			return 0;
		}
		else if(notificationInterval == WEEK_IN_MILLIS){
			return 1;
		}
		else if(notificationInterval == MONTH_IN_MILLIS){
			return 2;
		}
		else if(notificationInterval == NEVER_ALERT){
			return 3;
		} else {
			return -1;
		}
	}

}
