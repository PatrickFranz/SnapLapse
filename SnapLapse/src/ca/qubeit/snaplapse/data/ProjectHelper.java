package ca.qubeit.snaplapse.data;

import com.parse.ParseObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.util.MediaHelper;
import ca.qubeit.snaplapse.util.NotificationHelper;

public class ProjectHelper {	
	private static final String TAG = 	"ProjectHelper";
	private Context 		context;
	private Project  project;
	private boolean isDeleted;
	
	public ProjectHelper(Context context){
		this.context = context;
		isDeleted = false;
	}

	public boolean deleteProject(Project project){
		this.project = project;
		buildDeleteDialog().show();
		return isDeleted;
	}
	
	private AlertDialog buildDeleteDialog(){
		//Get Dialog layout
		View dlgLayout = View.inflate(context, R.layout.dlg_delete_project, null);
		final CheckBox chkIsDeleteOk = (CheckBox)dlgLayout.findViewById(R.id.isDeleteOk);
		chkIsDeleteOk.setText(R.string.dlg_delete_images);
		
		//Build the dialog
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setView(dlgLayout)
			.setTitle(R.string.dlg_remove_title)
			.setMessage(R.string.dlg_warning_msg)
			.setCancelable(true)
			.setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					isDeleted = true;
					final int notificationId = project.getNotifyId();
					NotificationHelper.cancelNotification(context, notificationId);
					deleteProjectFromDatabase();
					if(chkIsDeleteOk.isChecked()){
						Log.d(TAG, "Deleting project folder...");
						MediaHelper.removeProjectFiles(project.getName());
					}
				}				
		      })
		      .setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}				    	  
		      });
		
		return dialogBuilder.create();
	}
	
	private void deleteProjectFromDatabase() {
		Log.d(TAG, "Project " + project.getName() + " removed from database");
		ProjectDataSource source = new ProjectDataSource(context);
		source.open();
		source.delete(project.get_id());
		source.close();						
	}
	
	public static long getLongInterval(String unitType){
		if(unitType.equals("Minute")){  //Minute is for testing notifications
			return Project.MINUTE_IN_MILLIS;
		}	
		else if(unitType.equals("Daily")){
			return Project.DAY_IN_MILLIS;
		} 
		else if(unitType.equals("Weekly")){
			return Project.WEEK_IN_MILLIS;
		}		
		else if(unitType.equals("Monthly")){
			return Project.MONTH_IN_MILLIS;
		}
		else if(unitType.equals("Never")){
			return Project.NEVER_ALERT;
		} 
		else {
			return -1;
		}
	}
	
	public static void sendToParse(String name, String desc, long intervalInMillis, String intervalUnit) {
		ParseObject newProject = new ParseObject("Project");
		newProject.put("projectName", name);
		newProject.put("description", desc);
		newProject.put("IntervalInMillis"	, intervalInMillis);
		newProject.put("IntervalUnit", intervalUnit);
		newProject.saveInBackground();
	}
}