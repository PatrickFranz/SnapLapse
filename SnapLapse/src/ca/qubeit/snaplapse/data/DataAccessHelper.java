package ca.qubeit.snaplapse.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DataAccessHelper extends SQLiteOpenHelper {

	public static final String[] DB_COLUMNS = {"_id", "name", "description", "created_date", "notify_interval", "notify_id", "image_path" };
	public static final String DB_TABLE = "projects";
	public static final String DB_COLUMN_ID = BaseColumns._ID;
	
	private static final String TAG = "DataAccessHelper";
	private static final String DB_NAME = "snap_lapse.db";
	private static final int DB_VERSION = 3;	
	
	private static final String DB_CREATE_TABLE = 
			"CREATE TABLE " + DB_TABLE + "("
					+ "_id integer primary key autoincrement, "
					+ "name text, "
					+ "description text, "
					+ "created_date integer,"
					+ "notify_interval integer, "
					+ "notify_id integer, "
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
