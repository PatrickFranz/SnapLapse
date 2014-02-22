package com.comp3617.assignment1;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.comp3617.assignment1.data.Task;
import com.comp3617.assignment1.data.TaskList;

public class DisplayTasksActivity extends Activity {

	private ArrayList<Task> taskList = TaskList.getTaskList();
	private ListView list;
	private Button btnAddNew;
	private static final int REQ_CODE = 111;
	private ArrayAdapter<Task> listViewAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_displaytasks);
		makeTasks();
		list = (ListView)findViewById(R.id.lv_tasklist);
		listViewAdapter = 
				new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, taskList);
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
		btnAddNew = (Button)findViewById(R.id.btn_add_new);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void makeTasks(){
		
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
