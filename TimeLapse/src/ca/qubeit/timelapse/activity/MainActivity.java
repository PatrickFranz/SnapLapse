package ca.qubeit.timelapse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ca.qubeit.timelapse.R;

public class MainActivity extends Activity {

	private Button btnCreate;
	private Button btnOpen;
	private Button btnRemove;
	private Button btnView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get View references
        btnCreate = (Button)findViewById(R.id.btn_create);
        btnOpen = (Button)findViewById(R.id.btn_open);
        btnRemove = (Button)findViewById(R.id.btn_remove);
        btnView = (Button)findViewById(R.id.btn_view);
        
        //Set listeners on buttons
        btnCreate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Create a project", Toast.LENGTH_SHORT)
				.show();				
			}
		});
        
        btnOpen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Open project", Toast.LENGTH_SHORT)
				.show();				
			}
		});
        
        btnRemove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Remove a project", Toast.LENGTH_SHORT)
				.show();
			}
		});
        
        btnView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "View project", Toast.LENGTH_SHORT)
				.show();				
			}
		});
        
        Intent i = new Intent(this, TakePicture.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
