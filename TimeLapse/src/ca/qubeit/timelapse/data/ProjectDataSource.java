package ca.qubeit.timelapse.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ProjectDataSource {
	
	private DataAccessHelper dao;
	
	public ProjectDataSource(Context ctx){
		dao = new DataAccessHelper(ctx);
	}
	
	
	public long addProjectToDB(Project project) {
		SQLiteDatabase db = dao.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put("name", project.getName());
		content.put("description", project.getDescription());
		content.put("created_date", project.getCreatedDate().toString());
		content.put("notify_interval", project.getNotificationInterval());
		content.put("image_path", project.getImagePath());
		
		return db.insert("projects", null, content);				
	}
}
