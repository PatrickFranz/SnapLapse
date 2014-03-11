package ca.qubeit.timelapse.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProjectDataSource {
	
	private DataAccessHelper dao;
	private SQLiteDatabase db;
	
	public ProjectDataSource(Context ctx){
		dao = new DataAccessHelper(ctx);		
	}
	
	/**
	 * Adds new project to the database
	 * @param project A new project
	 * @return The row id inserts. Or -1 if error.
	 */
	public long add(Project project) {
		SQLiteDatabase db = dao.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put("name", project.getName());
		content.put("description", project.getDescription());
		content.put("created_date", project.getCreatedDateInMillis());
		content.put("notify_interval", project.getNotificationInterval());
		content.put("image_path", project.getImagePath());
		
		return db.insert("projects", null, content);				
	}
	
	public ArrayList<Project> getAllProjects(){
		db = dao.getWritableDatabase();
		ArrayList<Project> projects = new ArrayList<Project>();
		Cursor c = db.query(DataAccessHelper.DB_TABLE, DataAccessHelper.DB_COLUMNS, null, null, null, null, null);
		
		if(c != null && c.getCount() > 0){
			while(c.moveToNext()){
				Project p = new Project();
				p.set_id(c.getInt(c.getColumnIndex("_id")));
				p.setName(c.getString(c.getColumnIndex("name")));
				p.setDescription(c.getString(c.getColumnIndex("description")));
				p.setCreatedDate(c.getLong(c.getColumnIndex("created_date")));
				p.setNotificationInterval(c.getLong(c.getColumnIndex("notify_interval")));
				p.setImagePath(c.getString(c.getColumnIndex("image_path")));
				projects.add(p);
			}
			
		}
		return projects;
		
	}
}
