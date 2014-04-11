package ca.qubeit.snaplapse.util;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationHelper {

	
	public static void createScheduledNotification(Context context, int notifyId, long intervalInMillis, String projectName){
		//Get a calendar
		Calendar calendar = Calendar.getInstance();
		//Add the time to elapse before the first alarm
		calendar.setTimeInMillis(System.currentTimeMillis() + intervalInMillis);		
		
		//Get an AlarmManger from system
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		
		//Intent to launch when alarm is triggered
		Intent i = new Intent(context, NotificationReceiver.class);
		i.putExtra("projectName", projectName);
		i.putExtra("interval", intervalInMillis);		
		
		//Prepare PendingIntent
		PendingIntent pi = PendingIntent.getBroadcast(context, notifyId, i, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//Register the alert in the alarm manager service.
		alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), intervalInMillis, pi);
		//alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pi);
	}
	
	public static void cancelNotification(Context context, int notifyId){		
		
		//Create Pending intent to delete (deletion based on requestCode)
		Intent i = new Intent(context, NotificationReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, notifyId, i, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//Get the AlarmManager and cancel PendingIntent
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(pi);
		pi.cancel();
		
	}
}
