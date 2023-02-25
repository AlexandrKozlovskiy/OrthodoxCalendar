package oleksandr.kotyuk.orthodoxcalendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

public class MyScheduledReceiver extends BroadcastReceiver {
    static interface StopServiceListener {
        void stopService();
    }

    private static String ru_great_holiday = "";
    private static String ru_big_holiday = "";
    private static String post = "";
    private static String WIDGET_PREF = "widget_pref";
    private static DatabaseHelper db;
    private static Cursor cursor;
    private static MyCalendar cal = MyCalendar.getInstance();
    private static final String TAG = "myLogs";
    private static final long wholeDayInMillis = 86400000;
    private static int time;
    private static Boolean Noti_flag;
    private static String millisKey = "millis";
    private static final int NOTIFICATION_ID = 123;
    private static ExecutorService service;
    public static StopServiceListener l;

    @Override
    public void onReceive(Context context, Intent intent) {
        Noti_flag = PreferencesActivity.MyPreferenceFragment.ReadBoolean(context, PreferencesActivity.MyPreferenceFragment.KEY_PREF_NOTIFI, true);
        if (Noti_flag) {
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                Log.i("Autostart", "Boot was completed with action " + intent.getAction() + ".");
                setAlarm(context,false);
            } else if (Intent.ACTION_DATE_CHANGED.equals(intent.getAction()) || Intent.ACTION_TIME_CHANGED.equals(intent.getAction()) || Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                long millis = Long.parseLong(PreferencesActivity.MyPreferenceFragment.ReadString(context, millisKey, "0")) - System.currentTimeMillis();
                //Проверяем разницу между временем,в которое должно поступить уведомление,и реальным системным временем. Возьмём,к примеру,10 секунд.
                if (wholeDayInMillis - millis <= 10000) {
                    Intent i = new Intent(context, notificationService.class);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) context.startService(i);
                    else context.startForegroundService(i);
                } else setAlarm(context,false);
            } else if (AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED.equals(intent.getAction()))
                setAlarm(context,false);
            //else if(wholeDayInMillis==millis) onAlarm(context);
            //else onAlarm(context);
        }
    }

    //Отображает информацию для отладки.
    private void showInfo(Context context, String info) {
        AccessibilityManager manager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        AccessibilityEvent event = new AccessibilityEvent(AccessibilityEvent.TYPE_ANNOUNCEMENT);
        event.getText().add(info);
        manager.sendAccessibilityEvent(event);
    }

    public static void onAlarm(Context context) {
        cal.setTodayDate();
        time = Integer.parseInt(PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_notifi_time", "0"));
        if (cal.getHours() == time && cal.getMinutes() == 0) {
            Log.d(TAG, "cal.getFullNameDate()= " + cal.getFullNameDate());
            SharedPreferences sp = context.getSharedPreferences(SplashScreen.WIDGET_PREF, Context.MODE_PRIVATE);
            Runnable r = () -> {
                cal.AddDayNew(time >= PreferencesActivity.MyPreferenceFragment.minTimeForNextDate ? 1 : 0);
                addDataArray(context);
                sendNotif(context);
                cal.AddDayNew(time >= PreferencesActivity.MyPreferenceFragment.minTimeForNextDate ? -1 : 0);
                //Устанавливаем alarm на следующий день,но на это же время.
                setAlarm(context, cal.getTimeInMillis() + wholeDayInMillis,true);
                if (service != null) {
                    service.shutdownNow();
                    service = null;
                }
                if (l != null) {
                    l.stopService();
                    l = null;
                }
            };
            if (DatabaseHelper.shouldUpdateDb(sp) < 1) {
                service = Executors.newSingleThreadExecutor();
                service.execute(() -> {
                    db = DatabaseHelper.getInstance(context, sp);
                    new Handler(Looper.getMainLooper()).post(r);
                });
            } else {
                db = DatabaseHelper.getInstance(context);
                r.run();
            }
        }
//alarm не правильный,значит устанавливаем его на время согласно наших настроек.
        else {
            setAlarm(context, getTimeForAlarm(),false);
            if (l != null) {
                l.stopService();
                l = null;
            }
        }
    }

    public static Notification sendNotif(Context context, CharSequence ticker, CharSequence channelTitle, CharSequence title, CharSequence text, boolean forground) {
        Intent notificationIntent = new Intent(context, SplashScreen.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //******************************************
        notificationIntent.putExtra("notifi_date_app_start", cal.getTodayDay());
        //******************************************
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                Build.VERSION.SDK_INT < Build.VERSION_CODES.S? PendingIntent.FLAG_CANCEL_CURRENT:PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        Resources res = context.getResources();
        String channelId = "OrthodoxCalendar";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId).setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_stat_action_today)
                // большая картинка
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))
                // текст в строке состояния
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(!forground)
                .setOngoing(forground)
                // Заголовок уведомления
                .setContentTitle(title)
                // Текст уведомления
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                //.setColor(context.getResources().getColor(R.color.BLACK));
                .setPriority(NotificationManagerCompat.IMPORTANCE_DEFAULT);
        Notification notification = builder.build();
        if (PreferencesActivity.MyPreferenceFragment.ReadBoolean(context, "pref_notifi_sound", true))
            notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context.getApplicationContext());
        if (notificationManager.getNotificationChannel(channelId) == null)
            notificationManager.createNotificationChannel(new NotificationChannelCompat.Builder(channelId, NotificationManagerCompat.IMPORTANCE_DEFAULT).setName(channelTitle).build());
        if (!forground) notificationManager.notify(NOTIFICATION_ID, notification);
        return notification;
    }

    public static Notification sendNotif(Context context, CharSequence ticker, CharSequence channelTitle, CharSequence title, CharSequence text) {
        return sendNotif(context, ticker, channelTitle, title, text, false);
    }

    static void sendNotif(Context context) {
        StringBuilder sb = new StringBuilder("");
        if (!ru_great_holiday.equals("") && ru_great_holiday.length() > 1) {
            sb.append("<FONT COLOR=RED>" + ru_great_holiday + "</FONT>");
        } else {
            sb.append(ru_big_holiday);
        }
        Log.d(TAG, "sendNotif= " + sb.toString());
        int i_tmp = 0;
        int i = sb.indexOf("\r\n");
        if (i != -1) i_tmp = i;
        i = sb.indexOf("<br>");
        if (i != -1) {
            if (i_tmp != 0) {
                if (i < i_tmp) i_tmp = i;
            } else i_tmp = i;
        }
        try {
            if (i_tmp != 0) sb.replace(i_tmp, sb.length(), "");
        } catch (StringIndexOutOfBoundsException e) {
            //Log.d(TAG,"StringIndexOutOfBoundsException1= " + i);
            //Log.d(TAG,"StringIndexOutOfBoundsException2= " + e.toString());
            //Log.d(TAG,"StringIndexOutOfBoundsException3= " + sb.toString());
            sendNotif(context, "Смузи", "Новое смузи", "Смузи из банана", e.getMessage());
        }
        //sb.replace(i, i+2, ("<br>"+values+"<br>"));
        Log.d(TAG, "sendNotif2= " + sb.toString());
        i = sb.indexOf("<a href=");
        String str = "";
        if (i == -1) str = sb.toString();
        else {
            i_tmp = sb.indexOf("\">");
            sb.replace(i, i_tmp + 2, "");
            i = sb.indexOf("</a>");
            if (i != -1) {
                sb.replace(i, i + 4, "");
            }
            str = sb.toString();
        }
        Log.d(TAG, "sendNotif3= " + sb.toString());
        sendNotif(context, context.getString(R.string.app_name), context.getString(R.string.app_name), Html.fromHtml(str), post);
    }

    public static void cancelNotification(Context context, int id) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context.getApplicationContext());
        notificationManager.cancel(id);
    }

    public static String findTextVisYear(Context context) {
        String text_holiday_vis_year = "";
        cursor = null;
        String sql = "select holiday_month from data_calendar_leap_year where month="
                + (cal.getMonth() + 1) + " AND day=" + cal.getDayMonth() + ";";
        // Log.d(TAG,"запросDayGR= " + sql);
        cursor = db.executeQuery(sql);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                try {
                    text_holiday_vis_year = cursor.getString(cursor
                            .getColumnIndex("holiday_month"));
                } catch (NumberFormatException e) {
                    // Log.d(TAG, "ERROR=" + e.toString());
                    sendNotif(context, "тест", "новый тест", "ещё один тест", e.toString());
                }
            }
            cursor.close();
        }
        if (db != null) db.closeConnecion();
        return text_holiday_vis_year;
    }

    //вытаскиваем из базы данные по месяцу и вносим в массивы
    private static void addDataArray(Context context) {
        if (cal.getDateEntersPeriods()) {
            //int position=0;
            //String buffer;
            //db = DatabaseHelperNotification.getInstance(context);
            String sql = "SELECT ru_great_holiday, ru_big_holiday, p" + cal.getYear() + " FROM data_calendar WHERE month="
                    + (cal.getMonth() + 1) + " AND day=" + cal.getDayMonth() + ";";
            //Log.d(TAG,"запрос= " + sql);
            cursor = db.executeQuery(sql);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    ru_great_holiday = cursor.getString(cursor.getColumnIndex("ru_great_holiday"));
                    if (ru_great_holiday.equals("#")) ru_great_holiday = "";
                    ru_big_holiday = cursor.getString(cursor.getColumnIndex("ru_big_holiday"));
                    if (ru_big_holiday.equals("#")) ru_big_holiday = "";
                    post = cursor.getString(cursor.getColumnIndex("p" + cal.getYear()));
                }
                cursor.close();
            }
//делаем выборку по перемещаемым GREAT праздникам
            cursor = null;
            sql = "SELECT ru_great_holiday FROM gr_unmovable_holiday WHERE m" + cal.getYear() + "=" + (cal.getMonth() + 1) + " and d" + cal.getYear() + "=" + cal.getDayMonth() + ";";
            //Log.d(TAG,"запрос1= " + sql);
            cursor = db.executeQuery(sql);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        ru_great_holiday = ru_great_holiday + cursor.getString(cursor.getColumnIndex("ru_great_holiday"));
                    } while (cursor.moveToNext());
                }
            }
            if (!ru_great_holiday.equals("")) ru_great_holiday = ru_great_holiday + "<br>";
            cursor.close();
            //делаем выборку по перемищаемим BIG праздникам
            cursor = null;
            sql = "SELECT ru_big_holiday, level FROM big_unmovable_holiday WHERE m" + cal.getYear() + "=" + (cal.getMonth() + 1) + " and d" + cal.getYear() + "=" + cal.getDayMonth() + ";";
            //Log.d(TAG,"запрос1= " + sql);
            db = DatabaseHelper.getInstance(context);
            cursor = db.executeQuery(sql);
            //position=0;
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        AddValuesBigMonth(cursor.getString(cursor.getColumnIndex("ru_big_holiday")), cursor.getInt(cursor.getColumnIndex("level")));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
        } else {
            ru_great_holiday = "Проверьте дату на устройстве";
            post = "2020г-2023гг.";
        }
        int y = cal.getYear();
        int m = cal.getMonth() + 1;
        int d = cal.getDayMonth();
        Log.d(TAG, "cal.getYear()= " + y + "  cal.getMonth()+1=" + m + "  cal.getDayMonth()=" + d);
        if (y == 2020) {
            if (m == 2 && d == 29) {
                ru_great_holiday = "";
                ru_big_holiday = findTextVisYear(context);
                Log.d(TAG, "ru_great_holiday=" + ru_great_holiday + "  ru_big_holiday=" + ru_big_holiday);
            }
            if (m == 3 && (d > 0 && d < 14)) {
                ru_great_holiday = "";
                ru_big_holiday = findTextVisYear(context);
                Log.d(TAG, "ru_great_holiday=" + ru_great_holiday + "  ru_big_holiday=" + ru_big_holiday);
            }
        }
        //findTextVisYear(context);
        if (db != null) db.closeConnecion();
    }

    //добавляем перемещаемые BIG праздники в массив
    private static void AddValuesBigMonth(String values, int level) {
        switch (level) {
            case 0:
                if (ru_big_holiday.length() == 0) ru_big_holiday = values;
                else ru_big_holiday = values + "<br>" + ru_big_holiday;
                break;
            case 1:
                if (ru_big_holiday.length() == 0) ru_big_holiday = values;
                else {
                    int i = ru_big_holiday.indexOf("\r\n");
                    if (i == -1) ru_big_holiday = ru_big_holiday + "<br>" + values;
                    else {
                        //values_big[index].replace("\r\n", "<br>"+values+"<br>");

                        StringBuilder sb = new StringBuilder(ru_big_holiday);
                        sb.replace(i, i + 2, ("<br>" + values + "<br>"));
                        ru_big_holiday = sb.toString();
                    }
                }
                break;
            case 2:
                if (ru_big_holiday.length() == 0) ru_big_holiday = values;
                else ru_big_holiday = ru_big_holiday + "<br>" + values;
                break;
            default:
                break;
        }
    }

    public static Long getTimeForAlarm(boolean setDate) {
        if (setDate) cal.setTodayDate();
        cal.setHours(time);
        cal.setMinutes(0);
        long millis = cal.getTimeInMillis();
        if (millis < System.currentTimeMillis()) return millis + 86400000;
        else return millis;
    }

    public static Long getTimeForAlarm() {
        return getTimeForAlarm(true);
    }

    public static void setAlarm(Context context, Long time,boolean requestPermission) {
        PreferencesActivity.MyPreferenceFragment.WriteString(context, millisKey, time + "");
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent myIntentR1 = new Intent(context, notificationService.class);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            myIntentR1.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);*/
        PendingIntent pendingIntentR1 = Build.VERSION.SDK_INT < Build.VERSION_CODES.O ? PendingIntent.getService(context, 1, myIntentR1, PendingIntent.FLAG_UPDATE_CURRENT) : PendingIntent.getForegroundService(context, 1, myIntentR1, Build.VERSION.SDK_INT < Build.VERSION_CODES.S? PendingIntent.FLAG_UPDATE_CURRENT:PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        manager.cancel(pendingIntentR1);
        int type = AlarmManager.RTC_WAKEUP;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !manager.canScheduleExactAlarms() &&requestPermission) context.startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:"+ context.getPackageName())));
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT || Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !manager.canScheduleExactAlarms())
            manager.set(type, time, pendingIntentR1);
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            manager.setExact(type, time, pendingIntentR1);
        else manager.setExactAndAllowWhileIdle(type, time, pendingIntentR1);
        Log.d(TAG, "!!!setAlarm!!! " + (time - System.currentTimeMillis()) / 60000);
    }

        public static void setAlarm(Context context,boolean requestPermission) {
        time = Integer.parseInt(PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_notifi_time", "0"));
        setAlarm(context, getTimeForAlarm(),requestPermission);
    }

    public static void setAlarm(Context context, int time,boolean requestPermission) {
        MyScheduledReceiver.time = time;
        setAlarm(context, getTimeForAlarm(),requestPermission);
    }

    public static void cancelAlarm(Context context) {
        Intent myIntent = new Intent(context, notificationService.class);
        PendingIntent pendingIntent = Build.VERSION.SDK_INT < Build.VERSION_CODES.O ? PendingIntent.getService(context, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT) : PendingIntent.getForegroundService(context, 1, myIntent, Build.VERSION.SDK_INT < Build.VERSION_CODES.S? PendingIntent.FLAG_UPDATE_CURRENT:PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        Log.d(TAG, "!!!cancelAlarm!!!");
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }
}
