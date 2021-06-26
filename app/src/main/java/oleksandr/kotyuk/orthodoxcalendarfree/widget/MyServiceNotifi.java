package oleksandr.kotyuk.orthodoxcalendarfree.widget;

import oleksandr.kotyuk.orthodoxcalendarfree.PreferencesActivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyServiceNotifi extends Service {

	Boolean Noti_flag;
	MyScheduledReceiver alarm = new MyScheduledReceiver();
	
	@Override
	public void onCreate() {
		//Toast.makeText(getApplicationContext(), "MyServiceNotifi-onCreate()", Toast.LENGTH_LONG).show(); 
		Noti_flag = PreferencesActivity.MyPreferenceFragment.ReadBoolean(getApplicationContext(), "pref_notifi_setting", true);
		super.onCreate();
	}

	// В onStartCommand отправляем уведомление.
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if (Noti_flag) {
			alarm.SetAlarmR(MyServiceNotifi.this);
		}

		return START_STICKY;

	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}
}
