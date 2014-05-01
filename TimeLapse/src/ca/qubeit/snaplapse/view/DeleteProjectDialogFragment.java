package ca.qubeit.snaplapse.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.GpsStatus;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectDataSource;
import ca.qubeit.snaplapse.util.MediaHelper;
import ca.qubeit.snaplapse.util.NotificationHelper;

public class DeleteProjectDialogFragment extends DialogFragment {
	private static final String TAG = 	"DeleteProjectDialogFragment";
	private boolean isDeleted = false;
    private Listener listener;
	private Project project;
	private Context context;

    public void setListener(Listener listener){
        this.listener = listener;
    }
    static interface Listener {
        void returnData(boolean isDeleted);
    }

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//Create the view from resource
		this.context = getActivity();
		View dlgLayout = View.inflate(getActivity(), R.layout.dlg_delete_project, null);
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		final CheckBox chkIsDeleteOk = (CheckBox)dlgLayout.findViewById(R.id.isDeleteOk);
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
						MediaHelper.removeProjectFiles(project.getName());
					}
                    if(listener != null){
                        listener.returnData(isDeleted);
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
}