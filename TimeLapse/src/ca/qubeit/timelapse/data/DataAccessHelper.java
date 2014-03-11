package ca.qubeit.timelapse.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataAccessHelper extends SQLiteOpenHelper {

	private static final String TAG = "DataAccessHelper";
	private static final String DB_NAME = "snap_lapse.db";
	private static final String DB_TABLE = "projects";
	private static final int DB_VERSION = 1;
	//private static final String[] DB_COLUMNS = {"_id", "name", "description", "created_date", "notify_interval", "image_path" };
	private static final String DB_CREATE_TABLE = 
			"CREATE TABLE " + DB_TABLE + "("
					+ "_id integer primary key autoincrement, "
					+ "name text, "
					+ "description text, "
					+ "created_date text,"
					+ "notify_interval integer, "
					+ "image_path text);";
	
	public DataAccessHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE_TABLE);
		Log.i(TAG, "Database table created");

	}

	@Override
	public String getDatabaseName() {
		// TODO Auto-generated method stub
		return super.getDatabaseName();
	}

	@Override
	public SQLiteDatabase getReadableDatabase() {
		// TODO Auto-generated method stub
		return super.getReadableDatabase();
	}

	@Override
	public SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		return super.getWritableDatabase();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
		onCreate(db);

	}

}
