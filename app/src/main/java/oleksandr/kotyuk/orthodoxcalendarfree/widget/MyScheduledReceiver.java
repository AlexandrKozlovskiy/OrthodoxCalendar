package oleksandr.kotyuk.orthodoxcalendarfree.widget;

import oleksandr.kotyuk.orthodoxcalendarfree.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.R;
import oleksandr.kotyuk.orthodoxcalendarfree.SplashScreen;
import oleksandr.kotyuk.orthodoxcalendarfree.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendarfree.models.MyCalendar;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;

public class MyScheduledReceiver extends BroadcastReceiver {
	Boolean Noti_flag;
	SharedPreferences pref;
	public static final int NOTIFICATION_ID = 123;
	
	 String ru_great_holiday="";
	 String ru_big_holiday="";
	 String post="";
	    
	 String WIDGET_PREF = "widget_pref";
	 String NOTIFI_DATE_SAVE = "notifi_date_save";
	    
	 private DatabaseHelper db;
	 Cursor cursor;
	 MyCalendar cal=MyCalendar.getInstance();
	 
	 static final String TAG = "myLogs";
	 String time="0";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		pref = PreferenceManager.getDefaultSharedPreferences(context);

		Noti_flag = PreferencesActivity.MyPreferenceFragment.ReadBoolean(context, "pref_notifi_setting", true);
		time= PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_notifi_time", "0");

		if (Noti_flag) {
			
			cal.setTodayDate();
			SharedPreferences sp = context.getSharedPreferences(WIDGET_PREF, Context.MODE_PRIVATE);
			Log.d(TAG,"cal.getFullNameDate()= " + cal.getFullNameDate());
			String date_notifi_save = sp.getString(NOTIFI_DATE_SAVE, "");
			Log.d(TAG,"date_notifi_save= " + date_notifi_save);

			if(!cal.getFullNameDate().equals(date_notifi_save)){
				
				if((time.equals("24") && cal.getHours()==0) || (time.equals("07") && cal.getHours()==7) || (time.equals("08") && cal.getHours()==8) || (time.equals("09") && cal.getHours()==9) || (time.equals("10") && cal.getHours()==10)){
					addDataArray(context);
					mySendNotif(context);
					
					Editor editor = sp.edit();
					editor.putString(NOTIFI_DATE_SAVE, cal.getFullNameDate());
					editor.commit();
				}else{
					if(!time.equals("24")){
						switch(cal.getHours()){
							case 22:
								if(time.equals("22")){
									Editor editor = sp.edit();
									editor.putString(NOTIFI_DATE_SAVE, cal.getFullNameDate());
									editor.commit();
									
									cal.AddDayNew(1);
									addDataArray(context);
									mySendNotif(context);
									cal.AddDayNew(-1);
								}
								break;
							case 20:
								if(time.equals("20")){
									Editor editor = sp.edit();
									editor.putString(NOTIFI_DATE_SAVE, cal.getFullNameDate());
									editor.commit();
									
									cal.AddDayNew(1);
									addDataArray(context);
									mySendNotif(context);
									cal.AddDayNew(-1);
								}
								break;
							case 18:
								if(time.equals("18")){
									Editor editor = sp.edit();
									editor.putString(NOTIFI_DATE_SAVE, cal.getFullNameDate());
									editor.commit();
									
									cal.AddDayNew(1);
									addDataArray(context);
									mySendNotif(context);
									cal.AddDayNew(-1);
								}
								break;
							case 16:
								if(time.equals("16")){
									Editor editor = sp.edit();
									editor.putString(NOTIFI_DATE_SAVE, cal.getFullNameDate());
									editor.commit();
									Log.d(TAG,"cal.getFullNameDate()111= " + cal.getFullNameDate());
									cal.AddDayNew(1);
									Log.d(TAG,"cal.getFullNameDate()222= " + cal.getFullNameDate());
									addDataArray(context);
									mySendNotif(context);
									cal.AddDayNew(-1);
									Log.d(TAG,"cal.getFullNameDate()333= " + cal.getFullNameDate());
								}
								break;
							
						}
					}
				}
			}

		}
	}
	
	@SuppressLint("NewApi") @SuppressWarnings("deprecation")
	void mySendNotif(Context context) {
		
		//StringBuilder sb = new StringBuilder("<FONT COLOR=RED>"+ru_great_holiday+"</FONT>"+ru_big_holiday);
		StringBuilder sb = new StringBuilder("");
		if(!ru_great_holiday.equals("") && ru_great_holiday.length()>1){
			sb.append("<FONT COLOR=RED>"+ru_great_holiday+"</FONT>");
		}else{
			sb.append(ru_big_holiday);
		}
		
		Log.d(TAG,"mySendNotif= " + sb.toString());
		int i_tmp=0;
		int i=sb.indexOf("\r\n");

		if(i!=-1) i_tmp=i;
		i=sb.indexOf("<br>");

		if(i!=-1){
			if(i_tmp!=0){
				if(i<i_tmp) i_tmp=i;
			}else i_tmp=i;
		}
		
		try{
			if(i_tmp!=0)sb.replace(i_tmp, sb.length(), "");
		}catch(StringIndexOutOfBoundsException e){
			//Log.d(TAG,"StringIndexOutOfBoundsException1= " + i);
			//Log.d(TAG,"StringIndexOutOfBoundsException2= " + e.toString());
			//Log.d(TAG,"StringIndexOutOfBoundsException3= " + sb.toString());
		}
		//sb.replace(i, i+2, ("<br>"+values+"<br>"));
		Log.d(TAG,"mySendNotif2= " + sb.toString());
		i=sb.indexOf("<a href=");
		String str="";
		if(i==-1) str=sb.toString();
		else {
			i_tmp=sb.indexOf("\">");
			sb.replace(i, i_tmp+2, "");
			
			i=sb.indexOf("</a>");
			if(i!=-1){
				sb.replace(i, i+4, "");
			}
			
			str=sb.toString();
		}
		Log.d(TAG,"mySendNotif3= " + sb.toString());
		//Context context = getApplicationContext();

        Intent notificationIntent = new Intent(context, SplashScreen.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
        		| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //******************************************
        notificationIntent.putExtra("notifi_date_app_start", cal.getTodayDay());
        //******************************************
        
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);
        
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_stat_action_today)
                // большая картинка
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))
                // текст в строке состояния
                .setTicker("Православный календарь!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                // Заголовок уведомления
                .setContentTitle(Html.fromHtml(str))
                // Текст уведомления
                .setContentText(post)
                .setDefaults(Notification.DEFAULT_LIGHTS);
                //.setColor(context.getResources().getColor(R.color.BLACK)); 


        Notification notification;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN){
        	//меньше API Level 16
        	
        	notification = builder.getNotification();
        	//notification.defaults |= Notification.DEFAULT_LIGHTS;
        	
        }else{
        	//больше равно API Level 16
        	
        	notification = builder.build();
        	//notification.defaults |= Notification.DEFAULT_LIGHTS;
        	
        }    
        if(PreferencesActivity.MyPreferenceFragment.ReadBoolean(context, "pref_notifi_sound", true))
        	notification.defaults |=Notification.DEFAULT_SOUND;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);        
        notificationManager.notify(NOTIFICATION_ID, notification);
	}
	
	public String findTextVisYear(Context context) {
		String text_holiday_vis_year = "";
		cursor = null;
		String sql = "select holiday_month from data_calendar_leap_year where month="
				+ (cal.getMonth() + 1) + " AND day=" + cal.getDayMonth() + ";";
		// Log.d(TAG,"запросDayGR= " + sql);
		db = DatabaseHelper.getInstance(context);
		cursor = db.executeQuery(sql);

		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				try {
					text_holiday_vis_year = cursor.getString(cursor
							.getColumnIndex("holiday_month"));
				} catch (NumberFormatException e) {
					// Log.d(TAG, "ERROR=" + e.toString());
				}
			}
		}

		return text_holiday_vis_year;
	}

	//вытаскиваем из базы данные по месяцу и вносим в массивы
	void addDataArray(Context context){
		if (cal.getDateEntersPeriods()) {
			//int position=0;
			//String buffer;
			
			//db = DatabaseHelperNotification.getInstance(context);
			String sql = "SELECT ru_great_holiday, ru_big_holiday, p"+cal.getYear()+" FROM data_calendar WHERE month="
					+ (cal.getMonth() + 1) + " AND day="+cal.getDayMonth()+";";
			//Log.d(TAG,"запрос= " + sql);
			db = DatabaseHelper.getInstance(context);
			cursor = db.executeQuery(sql);
			
			if (cursor != null) {
	
				if (cursor.moveToFirst()) {
						
					ru_great_holiday=cursor.getString(cursor.getColumnIndex("ru_great_holiday"));
					if(ru_great_holiday.equals("#")) ru_great_holiday="";
						
					ru_big_holiday=cursor.getString(cursor.getColumnIndex("ru_big_holiday"));
					if(ru_big_holiday.equals("#")) ru_big_holiday="";
						
					post=cursor.getString(cursor.getColumnIndex("p"+ cal.getYear()));	
				}
			}
			
			//делаем выборку по перемещаемым GREAT праздникам
			cursor=null;
			sql = "SELECT ru_great_holiday FROM gr_unmovable_holiday WHERE m"+cal.getYear()+"="+(cal.getMonth()+1)+" and d"+cal.getYear()+"="+cal.getDayMonth()+";";
			//Log.d(TAG,"запрос1= " + sql);
			db = DatabaseHelper.getInstance(context);
			cursor = db.executeQuery(sql);
			if (cursor != null && cursor.getCount()>0) {
	
				if (cursor.moveToFirst()) {
					do{
						ru_great_holiday=ru_great_holiday+cursor.getString(cursor.getColumnIndex("ru_great_holiday"));
					}while(cursor.moveToNext());
						
				}
			}
			if(!ru_great_holiday.equals("")) ru_great_holiday=ru_great_holiday+"<br>";
			
			//делаем выборку по перемищаемим BIG праздникам
			cursor=null;
			sql = "SELECT ru_big_holiday, level FROM big_unmovable_holiday WHERE m"+cal.getYear()+"="+(cal.getMonth()+1)+" and d"+cal.getYear()+"="+cal.getDayMonth()+";";
			//Log.d(TAG,"запрос1= " + sql);
			db = DatabaseHelper.getInstance(context);
			cursor = db.executeQuery(sql);
			//position=0;
			if (cursor != null && cursor.getCount()>0) {
	
				if (cursor.moveToFirst()) {
					do{
						AddValuesBigMonth(cursor.getString(cursor.getColumnIndex("ru_big_holiday")), cursor.getInt(cursor.getColumnIndex("level")));
					}while(cursor.moveToNext());
						
				}
			}
		}else {
			ru_great_holiday="Проверьте дату на устройстве";
			post="2019г-2022гг.";
		}
		
		int y=cal.getYear();
		int m=cal.getMonth()+1;
		int d=cal.getDayMonth();
		Log.d(TAG,"cal.getYear()= " + y+"  cal.getMonth()+1="+m+"  cal.getDayMonth()="+d);
		if (y == 2020) {
			if (m == 2 && d == 29) {
				ru_great_holiday="";
				ru_big_holiday=findTextVisYear(context);
				Log.d(TAG,"ru_great_holiday=" + ru_great_holiday+"  ru_big_holiday="+ru_big_holiday);
			}
			if (m == 3 && (d > 0 && d < 14)) {
				ru_great_holiday="";
				ru_big_holiday=findTextVisYear(context);
				Log.d(TAG,"ru_great_holiday=" + ru_great_holiday+"  ru_big_holiday="+ru_big_holiday);
			}
		}
		
		//findTextVisYear(context);
		
		if (cursor != null) cursor.close();
		if(db!=null) db.closeConnecion();
	}
	
	
	//добавляем перемещаемые BIG праздники в масив
	void AddValuesBigMonth(String values, int level){
	
			switch (level) {
			case 0:
				if(ru_big_holiday.length()==0) ru_big_holiday=values;
				else ru_big_holiday=values+"<br>"+ru_big_holiday;
				break;
			case 1:
				if(ru_big_holiday.length()==0) ru_big_holiday=values;
				else{
					int i=ru_big_holiday.indexOf("\r\n");
					if(i==-1) ru_big_holiday=ru_big_holiday+"<br>"+values;
					else{
						//values_big[index].replace("\r\n", "<br>"+values+"<br>");
						
						StringBuilder sb = new StringBuilder(ru_big_holiday);
						sb.replace(i, i+2, ("<br>"+values+"<br>"));
						ru_big_holiday=sb.toString();
						}
					}
				break;
			case 2:
				if(ru_big_holiday.length()==0) ru_big_holiday=values;
				else ru_big_holiday=ru_big_holiday+"<br>"+values;
				break;

			default:
				break;
			}
		}

	public void SetAlarmR(Context context) {

		AlarmManager alarmManagerR = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		Intent myIntentR1 = new Intent(context, MyScheduledReceiver.class);

		PendingIntent pendingIntentR1 = PendingIntent.getBroadcast(context, 1, myIntentR1, PendingIntent.FLAG_UPDATE_CURRENT);

		alarmManagerR.cancel(pendingIntentR1);
		
		alarmManagerR.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 20000, 60000, pendingIntentR1);
		Log.d(TAG,"!!!SetAlarmR!!!");
	}

	public void CancelAlarmR(Context context) {

		Intent myIntentR = new Intent(context, MyScheduledReceiver.class);

		PendingIntent pendingIntentR = PendingIntent.getBroadcast(context, 1,
				myIntentR, 0);

		AlarmManager alarmManagerR = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		alarmManagerR.cancel(pendingIntentR);
		Log.d(TAG,"!!!CancelAlarmR!!!");
	}

}
