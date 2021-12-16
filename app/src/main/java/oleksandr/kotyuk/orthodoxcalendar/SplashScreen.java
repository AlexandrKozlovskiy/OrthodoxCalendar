package oleksandr.kotyuk.orthodoxcalendar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

public class SplashScreen extends Activity {

 // Splash screen timer
 private int SPLASH_TIME = 1;

 String text_load = "ЗАГРУЗКА";

 private boolean flag_activity = false;
 MyCalendar cal = MyCalendar.getInstance();

 public final static String NUMBER_PROGRAM = "number_program";
 public final static String WIDGET_PREF = "widget_pref";
 private ExecutorService service =null;
 MyView tvSplashActivity;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  if (!cal.getDateEntersPeriods()) {
   setContentView(R.layout.splash_activity_error);
   flag_activity = true;
  } else {
   setContentView(R.layout.splash_activity);
   SharedPreferences sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
   byte state = DatabaseHelper.shouldUpdateDb(sp);
   if (state == -1) {
    text_load = "УСТАНОВКА БАЗЫ ДАННЫХ";
   } else if (state == 0) {
    text_load = "ОБНОВЛЕНИЕ БАЗЫ ДАННЫХ";
   } else {
    text_load = "ЗАГРУЗКА";
   }
   tvSplashActivity = (MyView) this.findViewById(R.id.MyView_splash);
   tvSplashActivity.setText(text_load);
   Runnable r = () -> {
    getIntent().setClass(SplashScreen.this, MainActivity.class);
    startActivity(getIntent());
    finish();
    if (service != null) {
service.shutdownNow();
service =null;
    }
   };
   if (state < 1) {
    service = Executors.newSingleThreadExecutor();
    service.execute(() -> {
             DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
             new Handler(getMainLooper()).post(r);
            }
    );
   } else r.run();
  }
 }
 public void onBackPressed() {
  /*if (flag_activity) {
   finish();
   android.os.Process.killProcess(android.os.Process.myPid());
  }*/
 }

}