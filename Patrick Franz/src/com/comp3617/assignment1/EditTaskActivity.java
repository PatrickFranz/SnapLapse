package com.comp3617.assignment1;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.comp3617.assignment1.data.Task;
import com.comp3617.assignment1.data.TaskList;

public class EditTaskActivity extends Activity{
	
	private EditText 		etTitle;
	private EditText 		etDescription;
	private TextView 		dpDueDate;
	private Spinner			spCategory;
	private Spinner 		spPriority;
	private ToggleButton 	spStatus;
	private Button			btnSubmit;
	private CheckBox		chk_sendEmail;
	private AlertDialog.Builder dpDialog;
	private DatePicker 		datePicker;
	ArrayAdapter<CharSequence> priorityAdapter;
	ArrayAdapter<CharSequence> categoryAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_task);
		
		// Get reference to form fields
		etTitle = 		(EditText)findViewById(R.id.edit_task_title);
		etDescription = (EditText)findViewById(R.id.edit_task_description);
		dpDueDate = 	(TextView)findViewById(R.id.edit_task_due_date);
		spCategory = 	(Spinner)findViewById(R.id.edit_task_category);
		spPriority = 	(Spinner)findViewById(R.id.edit_task_priority);
		spStatus = 		(ToggleButton)findViewById(R.id.btn_tog_status);
		btnSubmit = 	(Button)findViewById(R.id.btn_submit);
		chk_sendEmail = (CheckBox)findViewById(R.id.sendEmail);
		datePicker = 	new DatePicker(this);
		
		//Create adapters	
		setAdapters();
		
		//Edit the selected task
		editTask(getIntent().getIntExtra("selected", -1));
	}

	private void setAdapters() {
		categoryAdapter = ArrayAdapter.createFromResource(this,
		        R.array.task_categories, android.R.layout.simple_spinner_item);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		priorityAdapter =
				ArrayAdapter.createFromResource(this, R.array.task_priorities, android.R.layout.simple_spinner_dropdown_item);
		//Populate Spinners
		spCategory.setAdapter(categoryAdapter);
		spPriority.setAdapter(priorityAdapter);
		spStatus.setText(R.string.btn_status_pending);
	}
	
	private void editTask(int selectedIndex) {
			
			if(selectedIndex == -1){
				setResult(111);
				finish();
			} else {			
				//Setup data members
				final Task selected = TaskList.getTaskList().get(selectedIndex);
				final Calendar date = selected.getDueDate();
				
				//Populate form with data.
				btnSubmit.setText(R.string.btn_edit);
				etTitle.setText(selected.getTitle());
				etDescription.setText(selected.getDescription());				
				dpDueDate.setText(selected.getDueDateString());		
				spCategory.setSelection(categoryAdapter.getPosition(selected.getCategory()), true);
				spPriority.setSelection(priorityAdapter.getPosition(selected.getPriority()), true);
				if(selected.isPending()){
					spStatus.setChecked(true);
					setIsEditable(true);
				} else {
					spStatus.setChecked(false);
					setIsEditable(false);				
				}
				
				//Setup date picker dialog
				dpDialog = new AlertDialog.Builder(this);	
				dpDialog.setTitle("Set Task Date")
					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							selected.setDueDate(
									new GregorianCalendar(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth()));
							
							dpDueDate.setText(selected.getDueDateString());							
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							
						}
					});				
				
				
				//Setup Listeners
				dpDueDate.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						datePicker = new DatePicker(EditTaskActivity.this);
						datePicker.init(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH),
								null);
						datePicker.setCalendarViewShown(false);
						datePicker.setMinDate(Calendar.getInstance().getTimeInMillis() - 86400000);
						dpDialog.setView(datePicker).show();
						
					}
				});
				
				spStatus.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton spStatus, boolean isChecked) {
						if(isChecked){
							setIsEditable(true);
						} else {
							setIsEditable(false);
						}
						
					}
				});				
				
				btnSubmit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//Save all changes (whether changed or not)
						selected.setTitle(etTitle.getText().toString());
						selected.setDescription(etDescription.getText().toString());
						//selected.setDueDate(new GregorianCalendar(dpDueDate.getYear(), dpDueDate.getMonth(), dpDueDate.getDayOfMonth()));
						selected.setCategory(spCategory.getSelectedItem().toString());
						selected.setPriority(spPriority.getSelectedItem().toString());
						selected.setStatus(spStatus.isChecked());
						
						if(chk_sendEmail.isChecked()){							
							Intent sendEmail = new Intent(Intent.ACTION_SEND);
							sendEmail.setData(Uri.parse("mailto:"));
							sendEmail.setType("message/rfc822");
							
							sendEmail.putExtra(Intent.EXTRA_SUBJECT, "A task for you!");
							sendEmail.putExtra(Intent.EXTRA_TEXT,("Title: " 		+ etTitle.getText().toString() 
																+ "\nDescription: " 	+ etDescription.getText().toString() 
																+ "\nDate: " 			+ dpDueDate.getText().toString()
																+ "\nCategory: " 		+ spCategory.getSelectedItem().toString()));
							startActivity(sendEmail);
						}
						setResult(111);
						finish();
					}
				});
			}
		}
	
	/**
	 * Sets the ability to focus on the fields.
	 * @param isEditable True allow editing of fields. False; not editable.
	 */
	private void setIsEditable(boolean isEditable) {
		etTitle.setFocusable(isEditable);
		etDescription.setFocusable(isEditable);
		dpDueDate.setClickable(isEditable);
		spCategory.setClickable(isEditable); 
		spPriority.setClickable(isEditable);
	}

}
