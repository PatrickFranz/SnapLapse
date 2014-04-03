package ca.qubeit.snaplapse.util;

import com.parse.Parse;

import android.app.Application;

public class SnapLapseApplication extends Application {

	
	@Override
	public void onCreate() {
		 Parse.initialize(this, "Tb3JEdgZiaBtZ8YtVS05OLEZ2Tx6skpViVGcNOfV", "X9ivahLAVCJU9BTP0iTTVdmvYwiskbxPkjD0XfgU");
	}
}
