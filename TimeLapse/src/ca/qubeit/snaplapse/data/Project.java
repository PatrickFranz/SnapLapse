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
 *
 */
public class Project {
	
	private int _id;
	private String name;
	private String description;
	private long createdDateInMillis;
	private long notificationInterval;
	private String imagePath;
	
	public Project() {
		
	}

	public Project(String name, String description, long notificationInterval) {
		this.name = name;
		this.description = description;
		this.notificationInterval = notificationInterval;
		setCreatedDate(System.currentTimeMillis());
		setImagePath(Environment.DIRECTORY_PICTURES + "/SnapLapse/" + name);
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
