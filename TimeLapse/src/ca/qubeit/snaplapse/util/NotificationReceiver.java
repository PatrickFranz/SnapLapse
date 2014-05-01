package ca.qubeit.snaplapse.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import ca.qubeit.snaplapse.R;
import ca.qubeit.snaplapse.activity.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Bundle extras = intent.getExtras();
		String projectName = extras.getString("projectName");
		
		//Create a new intent that will fire if the notification is clicked.
		Intent i = new Intent(context, MainActivity.class);
		//Set the Notification Intent Flags
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		
		//Attach the intent to a pending intent
		NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//Build the notification
		NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
		nBuilder.setContentTitle("SnapLapse reminder")
			.setContentText("Your project, " + projectName + " is ready for a new image!")
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(pi)
			.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		
		//Send the notification
		nManager.notify(1, nBuilder.build());

	}

}
