/**
 * A list of tasks
 */
package com.comp3617.assignment1.data;

import java.util.ArrayList;

/**
 * @author Patrick Franz
 * @version 02.05.2014
 *
 */
public class TaskList extends ArrayList<Task>{
	private static final long serialVersionUID = 1L;
	private static TaskList instance;
	
	//Singleton constructor
	private TaskList(){		
		
	}
	
	public static TaskList getTaskList(){
		if(instance == null){
			instance = new TaskList();
		} 
		return instance;
	}
}
