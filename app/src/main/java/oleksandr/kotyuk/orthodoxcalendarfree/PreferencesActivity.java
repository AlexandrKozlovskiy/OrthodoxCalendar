package oleksandr.kotyuk.orthodoxcalendarfree;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import oleksandr.kotyuk.orthodoxcalendarfree.widget.MyScheduledReceiver;

public class PreferencesActivity extends AppCompatActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getSupportActionBar();

		// Enabling Up / Back navigation
		actionBar.setDisplayHomeAsUpEnabled(true);

		setTitle("Настройки");
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new MyPreferenceFragment())
				.commit();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static class MyPreferenceFragment extends PreferenceFragment
			implements OnSharedPreferenceChangeListener {

		public static final String KEY_PREF_NOTIFI = "pref_notifi_setting";
		static final String TAG = "my_log";

		Context cont;
		Preference cbp1;
		Preference cbp2;
		Preference cbp3;
		Preference cbp4;
		Preference cbp5;
		Preference cbp6;
		Preference cbp7;
		Preference cbp8;
		Preference cbp9;
		SharedPreferences prefs;
		MyScheduledReceiver mSH = new MyScheduledReceiver();

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			cont = getActivity();
			prefs = PreferenceManager.getDefaultSharedPreferences(cont);
			prefs.registerOnSharedPreferenceChangeListener(this);
			addPreferencesFromResource(R.xml.pref1);
			cbp1 = (Preference) findPreference("pref_notifi_setting");
			cbp2 = (Preference) findPreference("pref_prayers_language");
			cbp3 = (Preference) findPreference("pref_text_size");
			cbp4 = (Preference) findPreference("pref_notifi_time");
			cbp5 = (Preference) findPreference("pref_notifi_sound");
			cbp6 = (Preference) findPreference("pref_rotate_screen_setting");
			cbp7 = (Preference) findPreference("pref_prayers_fonts_ru");
			cbp8 = (Preference) findPreference("pref_prayers_fonts_cs");
			cbp9 = (Preference) findPreference("pref_black_fon_color");

			getNotifiSetting();

			cbp1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

				@Override
				public boolean onPreferenceChange(Preference preference,
						Object newValue) {
					// TODO Auto-generated method stub
					if ((Boolean) newValue) {
						// Log.d(TAG, "mSH.SetAlarmR(cont)");
						mSH.SetAlarmR(cont);

					} else {
						// Log.d(TAG, "mSH.CancelAlarmR(cont)");
						mSH.CancelAlarmR(cont);

					}
					return true;
				}
			});
			
			cbp6.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

				@Override
				public boolean onPreferenceChange(Preference preference,
						Object newValue) {
					// TODO Auto-generated method stub
					if ((Boolean) newValue) {
						Log.d(TAG, "orientation_1="+getResources().getConfiguration().orientation);
						WriteInt(cont, "pref_rotate_screen_orientation", 0);

					} else {
						Log.d(TAG, "orientation_2="+getResources().getConfiguration().orientation);
						WriteInt(cont, "pref_rotate_screen_orientation", getResources().getConfiguration().orientation);
						
					}
					return true;
				}
			});

			/*
			 * cbp4.setOnPreferenceClickListener(new OnPreferenceClickListener()
			 * {
			 * 
			 * @Override public boolean onPreferenceClick(Preference preference)
			 * { // TODO Auto-generated method stub DialogFragment newFragment =
			 * new TimePickerFragment();
			 * newFragment.show(getFragmentManager(),"TimePicker"); return
			 * false; } });
			 */
		}

		public class TimePickerFragment extends DialogFragment implements
				TimePickerDialog.OnTimeSetListener {

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the current time as the default values for the time
				// picker

				/*
				 * final Calendar c = Calendar.getInstance(); int hour =
				 * c.get(Calendar.HOUR_OF_DAY); int minute =
				 * c.get(Calendar.MINUTE);
				 */

				int hour = prefs.getInt("pref_notifi_time_h", 0);
				int minute = prefs.getInt("pref_notifi_time_m", 0);

				// Create and return a new instance of TimePickerDialog
				/*
				 * public constructor..... TimePickerDialog(Context context, int
				 * theme, TimePickerDialog.OnTimeSetListener callBack, int
				 * hourOfDay, int minute, boolean is24HourView)
				 * 
				 * The 'theme' parameter allow us to specify the theme of
				 * TimePickerDialog
				 * 
				 * .......List of Themes....... THEME_DEVICE_DEFAULT_DARK
				 * THEME_DEVICE_DEFAULT_LIGHT THEME_HOLO_DARK THEME_HOLO_LIGHT
				 * THEME_TRADITIONAL
				 */
				TimePickerDialog tpd = new TimePickerDialog(getActivity(),
						AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour,
						minute, DateFormat.is24HourFormat(getActivity()));

				// You can set a simple text title for TimePickerDialog
				// tpd.setTitle("Title Of Time Picker Dialog");

				/* .........Set a custom title for picker........ */
				/*
				 * MyView tvTitle = new MyView(getActivity());
				 * tvTitle.setText("Время уведомления");
				 * 
				 * //tvTitle.setBackgroundColor(Color.parseColor("#EEE8AA"));
				 * tvTitle.setPadding(15, 15, 15, 15);
				 * tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
				 * tpd.setCustomTitle(tvTitle);
				 */
				/* .........End custom title section........ */
				return tpd;
			}

			// onTimeSet() callback method
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				Log.d(TAG, "hourOfDay=" + hourOfDay + " minute=" + minute);
				WriteInt(cont, "pref_notifi_time_h", hourOfDay);
				WriteInt(cont, "pref_notifi_time_m", minute);
			}
		}

		private void getNotifiSetting() {
			// TODO Auto-generated method stub
			/*
			 * if (cbp1 != null) Log.d(TAG, "getNotifiSetting() not NULL"); else
			 * Log.d(TAG, "getNotifiSetting() NULL");
			 */
			if (cbp1 != null) {
				if (prefs.getBoolean("pref_notifi_setting", true)) {
					cbp1.setSummary("Включено");
					cbp4.setEnabled(true);
					cbp5.setEnabled(true);
				} else {
					cbp1.setSummary("Выключено");
					cbp4.setEnabled(false);
					cbp5.setEnabled(false);
				}
			}

			if (prefs.getString("pref_prayers_language", "ru").equals("ru")) {
				if (cbp2 != null)
					cbp2.setSummary(getResources().getString(
							R.string.list2_summary1));
			} else {
				if (cbp2 != null)
					cbp2.setSummary(getResources().getString(
							R.string.list2_summary2));
			}
			if (cbp3 != null) {
				String tmp = prefs.getString("pref_text_size", "0");
				if (tmp.equals("0"))
					tmp = "";
				else
					tmp = " " + tmp;
				cbp3.setSummary(getResources().getString(
						R.string.list3_summary1)
						+ tmp);
			}
			if (cbp4 != null) {
				String time = prefs.getString("pref_notifi_time","24");
				if(time.equals("24")) cbp4.setSummary("00:00");
				if(time.equals("22")) cbp4.setSummary("22:00");
				if(time.equals("20")) cbp4.setSummary("20:00");
				if(time.equals("18")) cbp4.setSummary("18:00");
				if(time.equals("16")) cbp4.setSummary("16:00");
				if(time.equals("07")) cbp4.setSummary("07:00");
				if(time.equals("08")) cbp4.setSummary("08:00");
				if(time.equals("09")) cbp4.setSummary("09:00");
				if(time.equals("10")) cbp4.setSummary("10:00");
				
			}
			if (cbp5 != null) {
				if (prefs.getBoolean("pref_notifi_sound", true))
					cbp5.setSummary("Включен");
				else
					cbp5.setSummary("Отключен");
			}
			if (cbp6 != null) {
				if (prefs.getBoolean("pref_rotate_screen_setting", true))
					cbp6.setSummary("Включен");
				else
					cbp6.setSummary("Отключен");
			}
			
			if (cbp7 != null) {
				String fonts_ru = prefs.getString("pref_prayers_fonts_ru","1");
				if(fonts_ru.equals("1")) cbp7.setSummary("Arial");
				if(fonts_ru.equals("2")) cbp7.setSummary("Calibri");
				if(fonts_ru.equals("3")) cbp7.setSummary("Cambria");
				if(fonts_ru.equals("4")) cbp7.setSummary("DroidSans");
				if(fonts_ru.equals("5")) cbp7.setSummary("DroidSerif");
				if(fonts_ru.equals("6")) cbp7.setSummary("Times");
				if(fonts_ru.equals("7")) cbp7.setSummary("Verdana");
			}
			
			if (cbp8 != null) {
				String fonts_cs = prefs.getString("pref_prayers_fonts_cs","1");
				if(fonts_cs.equals("1")) cbp8.setSummary("Canonic");
				if(fonts_cs.equals("2")) cbp8.setSummary("Orthodox");
				if(fonts_cs.equals("3")) cbp8.setSummary("Triodion");
			}
			
			if (cbp9 != null) {
				String black_fon_color = prefs.getString("pref_black_fon_color","black");
				if(black_fon_color.equals("black")) cbp9.setSummary("Черный");
				if(black_fon_color.equals("dark_green")) cbp9.setSummary("Темно-зеленый");
				if(black_fon_color.equals("blue")) cbp9.setSummary("Синий");
				if(black_fon_color.equals("dark_blue")) cbp9.setSummary("Темно-синий");
			}

		}

		public static boolean ReadBoolean(Context context, final String key,
				final boolean defaultValue) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			return settings.getBoolean(key, defaultValue);
		}
		
		public static void WriteBoolean(Context context, final String key,
				final boolean value) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean(key, value);
			editor.commit();
		}

		public static String ReadString(Context context, final String key,
				final String defaultValue) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			return settings.getString(key, defaultValue);
		}
		
		public static void WriteString(Context context, final String key,
				final String value) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(key, value);
			editor.commit();
		}

		public static int ReadInt(Context context, final String key,
				final int defaultValue) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			return settings.getInt(key, defaultValue);
		}

		public static void WriteInt(Context context, final String key,
				final int value) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt(key, value);
			editor.commit();
		}

		public static float ReadFloat(Context context, final String key,
				final float defaultValue) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			return settings.getFloat(key, defaultValue);
		}

		public static void WriteFloat(Context context, final String key,
				final float value) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = settings.edit();
			editor.putFloat(key, value);
			editor.commit();
		}

		public static void putList(Context context, String key,
				ArrayList<String> marray) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = settings.edit();
			String[] mystringlist = marray.toArray(new String[marray.size()]);
			// the comma like character used below is not a comma it is the
			// SINGLE
			// LOW-9 QUOTATION MARK unicode 201A and unicode 2017 they are used
			// for
			// seprating the items in the list
			editor.putString(key, TextUtils.join("‚#‚", mystringlist));
			editor.apply();
		}

		public static ArrayList<String> getList(Context context, String key) {
			// the comma like character used below is not a comma it is the
			// SINGLE
			// LOW-9 QUOTATION MARK unicode 201A and unicode 2017 they are used
			// for
			// seprating the items in the list
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			String tmp = settings.getString(key, "");
			if (tmp.equals(""))
				return null;
			String[] mylist = TextUtils.split(tmp, "‚#‚");
			return new ArrayList<String>(Arrays.asList(mylist));
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			// TODO Auto-generated method stub

			Log.d(TAG, "KEY==" + key);

			getNotifiSetting();

			Editor editor = PreferenceManager.getDefaultSharedPreferences(cont)
					.edit();
			editor.putBoolean("pref_notifi_setting",
					prefs.getBoolean("pref_notifi_setting", true));
			editor.putString("pref_prayers_language",
					prefs.getString("pref_prayers_language", "ru"));
			editor.putString("pref_text_size",
					prefs.getString("pref_text_size", "0"));
			editor.commit();

			/*
			 * if (key.equals(KEY_PREF_NOTIFI)) { Log.d(TAG,
			 * "KEY_PREF_NOTIFI1="+key); boolean
			 * visible_notifi=sharedPreferences.getBoolean(key, true);
			 * if(visible_notifi){ Log.d(TAG,"onSharedPreferenceChanged=true");
			 * mSH.SetAlarmR(cont); }else{
			 * Log.d(TAG,"onSharedPreferenceChanged=false");
			 * mSH.CancelAlarmR(cont);
			 * 
			 * } }
			 */
			/*
			 * if (key.equals(KEY_PREF_NOTIFI)) { Log.d(TAG,
			 * "KEY_PREF_NOTIFI1="+key); boolean
			 * visible_notifi=sharedPreferences.getBoolean(key, true);
			 * if(visible_notifi){ //включаем отображение уведомлений Intent
			 * myIntent = new Intent(getApplicationContext(),
			 * MyReceiverNotifi.class); PendingIntent pendingIntent =
			 * PendingIntent.getBroadcast(getApplicationContext(), 0,
			 * myIntent,0);
			 * 
			 * AlarmManager alarmManager =
			 * (AlarmManager)getSystemService(ALARM_SERVICE);
			 * //alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(),
			 * pendingIntent);
			 * alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
			 * SystemClock.elapsedRealtime() + 3000, 12000, pendingIntent);
			 * //startService(new Intent(getApplicationContext(),
			 * MyServiceNotifi.class)); }else{ Log.d(TAG,
			 * "KEY_PREF_NOTIFI2="+key); //выключаем отображение уведомлений
			 * Intent myIntent = new Intent(getApplicationContext(),
			 * MyReceiverNotifi.class); PendingIntent pendingIntent =
			 * PendingIntent.getBroadcast(getApplicationContext(), 0,
			 * myIntent,0);
			 * 
			 * AlarmManager alarmManager =
			 * (AlarmManager)getSystemService(ALARM_SERVICE);
			 * alarmManager.cancel(pendingIntent); //stopService(new
			 * Intent(getApplicationContext(), MyServiceNotifi.class)); } }
			 */
		}

		@Override
		public void onResume() {
			super.onResume();
			// Set up a listener whenever a key changes
			getPreferenceScreen().getSharedPreferences()
					.registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onPause() {
			super.onPause();
			// Set up a listener whenever a key changes
			getPreferenceScreen().getSharedPreferences()
					.unregisterOnSharedPreferenceChangeListener(this);
		}

	}

	// позволяет востановить данные
	/*
	 * protected void onRestoreInstanceState(Bundle savedInstanceState) {
	 * super.onRestoreInstanceState(savedInstanceState); //Log.d(LOG_TAG,
	 * "onRestoreInstanceState"); }
	 * 
	 * // позволяет сохранить данные protected void onSaveInstanceState(Bundle
	 * outState) { super.onSaveInstanceState(outState); //Log.d(LOG_TAG,
	 * "onSaveInstanceState"); }
	 */
}
