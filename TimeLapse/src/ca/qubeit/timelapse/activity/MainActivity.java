package ca.qubeit.timelapse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import ca.qubeit.timelapse.CreateProjectActivity;
import ca.qubeit.timelapse.R;

public class MainActivity extends Activity {

	private LinearLayout 	btnCreate;
	private LinearLayout 	btnOpen;
	private LinearLayout	btnRemove;
	private LinearLayout 	btnView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get View references
        btnCreate = (LinearLayout)findViewById(R.id.btn_create);
        btnOpen = 	(LinearLayout)findViewById(R.id.btn_open);
        btnRemove = (LinearLayout)findViewById(R.id.btn_remove);
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
				Toast.makeText(getBaseContext(), "Open project", Toast.LENGTH_SHORT)
				.show();				
			}
		});
        
        btnRemove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				restoreButtonState();
				btnRemove.setBackgroundResource(R.drawable.menu_button_shape_down);
				Toast.makeText(getBaseContext(), "Remove a project", Toast.LENGTH_SHORT)
				.show();
			}
		});
        
        btnView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				restoreButtonState();
				btnView.setBackgroundResource(R.drawable.menu_button_shape_down);
				Toast.makeText(getBaseContext(), "View project", Toast.LENGTH_SHORT)
				.show();
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
    	btnRemove.setBackgroundResource(R.drawable.menu_button_shape);
    	btnView	 .setBackgroundResource(R.drawable.menu_button_shape);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
