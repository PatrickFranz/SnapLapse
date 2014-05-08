package ca.qubeit.snaplapse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import ca.qubeit.snaplapse.R;

import com.parse.ParseAnalytics;

public class MainActivity extends Activity {

	private LinearLayout 	btnCreate;
	private LinearLayout 	btnOpen;
	private LinearLayout 	btnView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Let Parse know we've opened the App
        ParseAnalytics.trackAppOpened(getIntent());
        
        //Get View references
        btnCreate = (LinearLayout)findViewById(R.id.btn_create);
        btnOpen = 	(LinearLayout)findViewById(R.id.btn_open);
        btnView = 	(LinearLayout)findViewById(R.id.btn_view);
        
        //Set listeners on buttons
        btnCreate.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
        		restoreButtonState();
        		btnCreate.setBackgroundResource(R.drawable.menu_button_shape_down);
				Intent showCreateProjectActivity = new Intent(getBaseContext(), CreateProjectActivity.class);
				startActivity(showCreateProjectActivity);
			}
		});        
        
        btnOpen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				restoreButtonState();
				btnOpen.setBackgroundResource(R.drawable.menu_button_shape_down);
				Intent openProjectActivity = new Intent(getBaseContext(), OpenProjectActivity.class);
				startActivity(openProjectActivity);
			}
		});
        
        btnView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				restoreButtonState();
				btnView.setBackgroundResource(R.drawable.menu_button_shape_down);
				Intent viewProject = new Intent(getBaseContext(), ViewProjectActivity.class);
				startActivity(viewProject);
			}
		});
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	restoreButtonState();
    }

	private void restoreButtonState() {
		btnCreate.setBackgroundResource(R.drawable.menu_button_shape);
    	btnOpen	 .setBackgroundResource(R.drawable.menu_button_shape);
    	btnView	 .setBackgroundResource(R.drawable.menu_button_shape);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }    
}
