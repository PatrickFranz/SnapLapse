package ca.qubeit.snaplapse.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.activity.OnButtonClickListener;
import ca.qubeit.snaplapse.data.Project;
import ca.qubeit.snaplapse.data.ProjectHelper;
import ca.qubeit.snaplapse.util.NotificationHelper;

public class EditProjectDialogFragment extends DialogFragment {
	
	private Context context;
	private Project project;
	private EditText projectName;
	private EditText projectDesc;
	private Spinner interval;
	private long originalReminderInMillis;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		this.context = getActivity();
		View dlgLayout = View.inflate(context, R.layout.dlg_edit_project, null);
		projectName = (EditText)dlgLayout.findViewById(R.id.et_project_name);
		projectDesc = (EditText)dlgLayout.findViewById(R.id.et_project_description);
		interval 	= (Spinner)dlgLayout.findViewById(R.id.sp_interval);		
		originalReminderInMillis = project.getNotificationInterval();
		
		//Populate dialog with current values
		if(project != null){
			projectName.setText(project.getName());
			projectDesc.setText(project.getDescription());
			if(project.getReminderFrequencyInHuman() != -1)
			{
				interval.setSelection(project.getReminderFrequencyInHuman());
			}		
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(dlgLayout);
		builder.setTitle("Edit Project Settings");
		builder.setPositiveButton(R.string.dlg_confirm, new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				OnButtonClickListener listener = ((OnButtonClickListener)context);
				
				//Set project new values
				project.setName(projectName.getText().toString());
				project.setDescription(projectDesc.getText().toString());
				project.setNotificationInterval(ProjectHelper.getLongInterval((String)interval.getSelectedItem()));
				
				//Check if notification has changed and 
				if(project.getNotificationInterval() != originalReminderInMillis){
					NotificationHelper.cancelNotification(getActivity(), project.getNotifyId());
					NotificationHelper.createScheduledNotification(getActivity(), project);
				}
				listener.dialogButtonClick(OnButtonClickListener.CLICK_CONFIRM, project);				
			}
		});
		builder.setNegativeButton(R.string.dlg_cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
		return builder.create();
	}
	
	public void setProject(Project project){ 
		if(project != null){
			this.project = project;
		}
	}

}
