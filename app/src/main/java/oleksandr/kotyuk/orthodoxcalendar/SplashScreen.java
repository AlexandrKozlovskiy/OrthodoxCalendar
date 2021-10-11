package oleksandr.kotyuk.orthodoxcalendar;

import java.util.concurrent.TimeUnit;

import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import oleksandr.kotyuk.orthodoxcalendar.MyView;

public class SplashScreen extends Activity {

// Splash screen timer
private int SPLASH_TIME = 1;

String text_load="ЗАГРУЗКА";

private boolean flag_activity=false;
MyCalendar cal = MyCalendar.getInstance();

public final static String NUMBER_PROGRAM = "number_program";
public final static String WIDGET_PREF = "widget_pref";

MyView tvSplashActivity;

@Override
protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 if (!cal.getDateEntersPeriods()) {
 setContentView(R.layout.splash_activity_error);
 flag_activity=true;
 } else {
 setContentView(R.layout.splash_activity);
 
 SharedPreferences sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
 int num_prog = sp.getInt(NUMBER_PROGRAM, 0);
 if(num_prog==0){
  text_load="УСТАНОВКА БАЗЫ ДАННЫХ";
 }else{
  if(Integer.parseInt(BuildConfig.VERSION_NAME.replace(".",""))>num_prog){
  text_load="ОБНОВЛЕНИЕ БАЗЫ ДАННЫХ";
  }else{
  text_load="ЗАГРУЗКА";
  }
 }
 tvSplashActivity = (MyView) this.findViewById(R.id.MyView_splash);
 tvSplashActivity.setText(text_load);

 /*SharedPreferences sPref = PreferenceManager
  .getDefaultSharedPreferences(this);
 String str = sPref.getString("calendar_list", "");
 if (!str.equals(""))
  GlobalData.setData_style(str);*/

 /**
  * Showing splashscreen while making network calls to download
  * necessary data before launching the app Will use AsyncTask to
  * make http call
  */
 new PrefetchData().execute();
 }

}

public void onBackPressed() {
 if(flag_activity){
 finish();
 android.os.Process.killProcess(android.os.Process.myPid());
 }
}

/**
 * Async Task to make http call
 */
private class PrefetchData extends AsyncTask<Void, Void, Void> {

 // вызываетс¤ в потоке пользовательского интерфейса, прежде чем задача
 // будет выполнена. Ётот шаг, как правило, используетс¤ дл¤ настройки
 // задач, например, показыва¤ прогресс-бар в пользовательском
 // интерфейсе.
 @Override
 protected void onPreExecute() {
 super.onPreExecute();
 // before making http calls

 }

 // вызываетс¤ в фоновом потоке сразу после onPreExecute (). Ётот шаг
 // используетс¤ дл¤ выполнени¤ вычислений в фоновом режиме, который
 // может зан¤ть много времени.
 @Override
 protected Void doInBackground(Void... arg0) {
 /*
  * Will make http call here This call will download required data
  * before launching the app example: 1. Downloading and storing in
  * SQLite 2. Downloading images 3. Fetching and parsing the xml /
  * json 4. Sending device information to server 5. etc.,
  */

 DatabaseHelper db=DatabaseHelper.getInstance(getApplicationContext());
 //db.closeConnecion();

 try {
  TimeUnit.SECONDS.sleep(SPLASH_TIME);
 } catch (InterruptedException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
 }

 return null;
 }

 /*
  * private SharedPreferences getPreferences(int modePrivate) { // TODO
  * Auto-generated method stub return null; }
  */

 // вызываетс¤ в потоке пользовательского интерфейса после вызова
 // publishProgress(ProgressЕ). Ётот метод используетс¤ дл¤ отображени¤
 // любых форм прогресса в пользовательском интерфейсе, пока идут
 // вычислени¤ в фоновом режиме. Ќапример, он может быть использован дл¤
 // анимации индикатор.
 @Override
 protected void onProgressUpdate(Void... values) {
 // TODO Auto-generated method stub
 super.onProgressUpdate(values);
 // onProgressUpdate();
 }

 // вызываетс¤ в потоке пользовательского интерфейса после выполнени¤
 // процесса вычислений в фоновом режиме. –езультат вычислений передаетс¤
 // на этот шаг в качестве параметра.
 @Override
 protected void onPostExecute(Void result) {
 super.onPostExecute(result);
 // After completing http call
 // will close this activity and lauch main activity
 // получаем ID выбраного раздела
 Intent intent = getIntent();
 int notifi_date_app_start = intent.getIntExtra("notifi_date_app_start", 0);
 
 Intent i = new Intent(SplashScreen.this, MainActivity.class);
 i.putExtra("notifi_date_app_start", notifi_date_app_start);
 startActivity(i);

 // close this activity
 finish();
 }

}

}
