package ca.qubeit.snaplapse.data;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.util.MediaHelper;
import ca.qubeit.snaplapse.util.NotificationHelper;

public class RemoveProject {	
	private static final String TAG = 	"RemoveProject";
	private ArrayAdapter<Project> 		projectAdapter;
	private Button		 btnRemove;
	private CheckBox	chkIsDeleteOk;
	private List<Project> 		projects;
	private AlertDialog.Builder dialogBuilder;
	private Project selectedProject;
	private ProjectDataSource source;
	private int selectedPosition;
	private Context context;	

	public RemoveProject(Context context) {
		this.context = context;	
		source = new ProjectDataSource(context);
		buildDialog().show();
	}
	
	private AlertDialog buildDialog(){
		//Get Dialog layout
		View dlgLayout = View.inflate(context, R.layout.dlg_delete_project, null);
		chkIsDeleteOk = (CheckBox)dlgLayout.findViewById(R.id.isDeleteOk);
		chkIsDeleteOk.setText(R.string.dlg_delete_images);
		
		dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setView(dlgLayout)
			.setTitle(R.string.dlg_remove_title)
			.setMessage(R.string.dlg_warning_msg)
			.setCancelable(true)
			.setPositiveButton(R.string.txt_yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					final int notificationId = selectedProject.getNotifyId();
					projects.remove(selectedPosition);
					deleteProjectFromDatabase();
					NotificationHelper.cancelNotification(context, notificationId);
					if(chkIsDeleteOk.isChecked()){
						Log.d(TAG, "Deleting project folder...");
						MediaHelper.removeProjectFiles(selectedProject.getName());
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
		Log.d(TAG, "Project " + selectedProject.getName() + " removed from database");
		source.open();
		source.delete(selectedProject.get_id());
		projectAdapter.clear();		
		projectAdapter.addAll(source.getAllProjects());
		source.close();						
		projectAdapter.notifyDataSetChanged();
		btnRemove.setText(R.string.btn_delete_project);
	}
}