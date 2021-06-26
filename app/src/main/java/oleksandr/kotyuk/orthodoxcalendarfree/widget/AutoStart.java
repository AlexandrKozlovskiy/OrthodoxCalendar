package oleksandr.kotyuk.orthodoxcalendarfree.widget;

import oleksandr.kotyuk.orthodoxcalendarfree.PreferencesActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoStart extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		Log.d("Autostart",
				"BOOT_COMPLETED broadcast received. Executing starter service.");
		boolean isAutoStartEnabled = PreferencesActivity.MyPreferenceFragment.ReadBoolean(context,
				"pref_notifi_setting", true);

		if (isAutoStartEnabled) {
			if (Intent.ACTION_BOOT_COMPLETED.equals(arg1.getAction())) {
				Intent intent = new Intent(context, MyServiceNotifi.class);
				context.startService(intent);
			}
		}
	}
}
