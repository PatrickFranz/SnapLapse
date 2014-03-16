package ca.qubeit.snaplapse.data;

import java.util.ArrayList;
import java.util.List;

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
	
	public void open(){
		db = dao.getWritableDatabase();
	}
	
	public void close(){
		dao.close();
	}
	/**
	 * Adds new project to the database
	 * @param project A new project
	 * @return The row id inserts. Or -1 if error.
	 */
	public long add(Project project) {
		
		ContentValues content = new ContentValues();
		content.put("name", project.getName());
		content.put("description", project.getDescription());
		content.put("created_date", project.getCreatedDateInMillis());
		content.put("notify_interval", project.getNotificationInterval());
		content.put("image_path", project.getImagePath());
		
		return db.insert("projects", null, content);				
	}
	
	public boolean delete(long rowId){
		String where = DataAccessHelper.DB_COLUMN_ID + " = " + rowId;
		return db.delete(DataAccessHelper.DB_TABLE, where , null) > 0;
	}
	
	public List<Project> getAllProjects(){
		
		List<Project> projects = new ArrayList<Project>();
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
	
	public Project getProject(String name){		
		String selection = "name = '" + name + "'";
		Project project = new Project();
		Cursor c = db.query(DataAccessHelper.DB_TABLE, DataAccessHelper.DB_COLUMNS, selection, null, null, null, null, null);
		if(c != null && c.getCount() > 0){
			c.moveToFirst();
			project.set_id(c.getInt(c.getColumnIndex("_id")));
			project.setName(c.getString(c.getColumnIndex("name")));
			project.setDescription(c.getString(c.getColumnIndex("description")));
			project.setCreatedDate(c.getLong(c.getColumnIndex("created_date")));
			project.setNotificationInterval(c.getLong(c.getColumnIndex("notify_interval")));
			project.setImagePath(c.getString(c.getColumnIndex("image_path")));
		}		
		return project;
	}
}
