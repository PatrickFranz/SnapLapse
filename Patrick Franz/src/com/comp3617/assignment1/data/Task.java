/**
 * A Task
 */
package com.comp3617.assignment1.data;

import java.text.DateFormat;
import java.util.GregorianCalendar;

/**
 * @author Patrick Franz\
 * @version 2.5.2014
 *
 */

public class Task {
	
	private static int _id = 1;
	private String title;
	private String description;
	private GregorianCalendar dueDate;
	private String category;
	private String priority;
	private boolean status;
	
	public Task(){}

	public Task(String title, String description, GregorianCalendar dueDate,
			String category, String priority, boolean status) {
		super();
		setId();
		setTitle(title);
		setDescription(description);
		setDueDate(dueDate);
		setCategory(category);
		setPriority(priority);
		setStatus(status);
	}



	public int getId() {
		return _id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public GregorianCalendar getDueDate() {
		return dueDate;
	}

	public String getCategory() {
		return category;
	}

	public boolean isPending() {
		return status;
	}

	public String getPriority() {
		return priority;
	}

	public void setId() {
		_id++;
	}

	public void setTitle(String title) {
		if(title != null){
			this.title = title;
		}
	}

	public void setDescription(String description) {		
		if(description != null) {
			this.description = description;
		}
	}

	public void setDueDate(GregorianCalendar dueDate) {
		if(dueDate != null){
			this.dueDate = dueDate;
		} else {
			this.dueDate = new GregorianCalendar();
		}
	}
	
	public String getDueDateString() {
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(dueDate.getTime());
		
	}

	public void setCategory(String category) {
		if(category != null){
			this.category = category;
		}
	}

	public void setStatus(boolean status) {
			this.status = status;
	}

	public void setPriority(String priority) {
		if(priority != null){
			this.priority = priority;
		}
	}
	
	@Override
	public String toString(){
		String isPendingString = "Complete";
		if(isPending()){
			isPendingString = "Pending";
		}
		
		return title + " - " + isPendingString + " - " + getDueDateString();
	}

	
}
