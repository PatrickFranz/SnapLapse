/**
 * A project in the SnapLapse Application
 */
package ca.qubeit.timelapse.data;

import java.util.Date;

/**
 * @author Patrick Franz
 *
 */
public class Project {
	
	private int _id;
	private String name;
	private String description;
	private Date createdDate;
	private long notificationInterval;
	private String imagePath;
	
	public Project() {
		
	}

	public Project(String name, String description, long notificationInterval) {
		this.name = name;
		this.description = description;
		this.notificationInterval = notificationInterval;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate() {
		this.createdDate = new Date();
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
