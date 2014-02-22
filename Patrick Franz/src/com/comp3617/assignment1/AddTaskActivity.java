package com.comp3617.assignment1;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.comp3617.assignment1.data.Task;
import com.comp3617.assignment1.data.TaskList;

public class AddTaskActivity extends Activity {
	
	private final long 	ONE_DAY 	 = 1000 * 60 * 60 * 24; 	// 24 hours
	private final long 	TASK_LENGTH  = 1000 * 60 * 15; 		// 15 minutes
	private final int 	REMIND_ALARM = 60 * 60 * 8; 		// 8 hours
	
	private GregorianCalendar 	chosenDate;
	private EditText 			etTitle;
	private EditText 			etDescription;
	private TextView 			dpDueDate;
	private Spinner				spCategory;
	private Spinner 			spPriority;
	private ToggleButton 		spStatus;
	private Button				btnSubmit;
	private AlertDialog.Builder dpDialog;
	private DatePicker 			datePicker;
	
	ArrayAdapter<java.lang.CharSequence> priorityAdapter;
	ArrayAdapter<CharSequence> categoryAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_task);
		
		// Get reference to form fields
		etTitle 		= (EditText)findViewById(R.id.edit_task_title);
		etDescription 	= (EditText)findViewById(R.id.edit_task_description);
		dpDueDate 		= (TextView)findViewById(R.id.edit_task_due_date);
		spCategory 		= (Spinner)findViewById(R.id.edit_task_category);
		spPriority		= (Spinner)findViewById(R.id.edit_task_priority);
		spStatus 		= (ToggleButton)findViewById(R.id.btn_tog_status);
		btnSubmit 		= (Button)findViewById(R.id.btn_submit);
		datePicker 		= new DatePicker(this);
		//Create adapters	
		setAdapters();
		
		//Create a new task
		createTask();
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
		spStatus.setChecked(true);
	}
	
	/**
	 * Wire up submit button to create a new task. The new task is then added directly to the collection. 
	 */
	private void createTask(){
		
		//local members
		chosenDate = new GregorianCalendar();
		final Calendar now = Calendar.getInstance();
		
		//
		btnSubmit.setText(R.string.btn_create);
		dpDueDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(now.getTime()));		
		
		dpDialog = new AlertDialog.Builder(this);	
		dpDialog.setTitle("Set Task Date")				
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					chosenDate.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth()); 
					dpDueDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(chosenDate.getTime()));
					dialog.dismiss();
				}
				
			})
			.setNegativeButton("Canel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();					
				}
			});
		
		
		//Setup Listeners
		dpDueDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				//Setup date picker
				datePicker = new DatePicker(AddTaskActivity.this);
				datePicker.setMinDate(now.getTimeInMillis() - ONE_DAY);
				datePicker.init(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH),
						null);
				datePicker.setCalendarViewShown(false);		
				dpDialog.setView(datePicker).show();
			}
		});
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Create a new Task and add to the collection
				Task aTask = new Task(etTitle.getText().toString(),
									etDescription.getText().toString(),
									chosenDate,
									spCategory.getSelectedItem().toString(),
									spPriority.getSelectedItem().toString(),
									spStatus.isChecked()
									);				
			TaskList.getTaskList().add(aTask);
			
			//Add to Calendar
			AlertDialog.Builder okCalendar = new AlertDialog.Builder(AddTaskActivity.this);
			
			okCalendar.setTitle("Add to calendar?")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Intent reminder = new Intent(Intent.ACTION_EDIT)
							.setData(Events.CONTENT_URI)
							.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, chosenDate.getTimeInMillis() - REMIND_ALARM)
							.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, chosenDate.getTimeInMillis() + TASK_LENGTH)
							.putExtra(Events.DTSTART, chosenDate.getTimeInMillis())
							.putExtra(Events.DESCRIPTION, etDescription.getText().toString())
							.putExtra(Events.HAS_ALARM, true)
							.putExtra(CalendarContract.Reminders.MINUTES, REMIND_ALARM)
							.putExtra(Events.TITLE, "A Task: " + etTitle.getText().toString());
							
						startActivity(reminder);
						dialog.dismiss();
						setResult(111);			
						finish();
					}
				})
				.setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						setResult(111);			
						finish();
					}				
				}).show();
			};
		});			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_task, menu);
		return true;
	}

}
