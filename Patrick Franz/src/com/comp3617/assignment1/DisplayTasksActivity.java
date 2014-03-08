package com.comp3617.assignment1;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.comp3617.assignment1.data.Task;
import com.comp3617.assignment1.data.TaskList;

public class DisplayTasksActivity extends Activity {

	private ArrayList<Task> taskList = TaskList.getTaskList();
	private TextView tvTopBar;
	private ListView list;
	private Button btnAddNew;
	private static final int REQ_CODE = 111;
	private ArrayAdapter<Task> listViewAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_displaytasks);
		// Get font
		Typeface segoeUi = Typeface.createFromAsset(this.getAssets(),"SEGUISYM.TTF");
		
		makeSampleTasks();
		tvTopBar = (TextView)findViewById(R.id.tv_topBar);
		tvTopBar.setTypeface(segoeUi);
		
		list = (ListView)findViewById(R.id.task_listview);
		btnAddNew = (Button)findViewById(R.id.btn_add_new);
		
		listViewAdapter = 
				new ArrayAdapter<Task>(this, R.layout.task_list_layout, R.id.list_due_date, taskList);
		list.setAdapter(listViewAdapter);


		list.setOnItemClickListener(new OnItemClickListener() {	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(getBaseContext(), EditTaskActivity.class);
				i.putExtra("selected", 	arg2);				
				startActivityForResult(i, REQ_CODE);
			}
		});
		
	
		
		btnAddNew.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), AddTaskActivity.class);
				startActivityForResult(intent, REQ_CODE);
			}
		});		
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQ_CODE){
			listViewAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		//Set top bar text
		if(taskList.size() < 1){
			tvTopBar.setText("No tasks found ... ");
		} else {
			tvTopBar.setText("" + taskList.size() + " tasks found.");
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@SuppressWarnings("unused")
	private void makeSampleTasks(){
		
		taskList.add(new Task("Get milk", "Get milk from store",
				new GregorianCalendar(2014, 9, 22),"grocery","Medium", false
				));
		taskList.add(new Task("laundry", "do the laundry",
				null,"home","Low", true
				));
		taskList.add(new Task("book flight", "Book flight to Vegas",
				null,"travel","High", true 
				));
	}

}
