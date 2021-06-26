package oleksandr.kotyuk.orthodoxcalendar;


import androidx.appcompat.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import oleksandr.kotyuk.orthodoxcalendar.MyView;
import android.widget.Toast;

public class PrayersTroparKondarActivity extends AppCompatActivity {

static final String TAG = "myLogs";
int id_description = 1;


LinearLayout llDescriptionActivity;
MyView tvDescriptionActivity;

//private final String FONT_PATH1 = "fonts/TRIOD35.TTF";

private final String FONT_PATH_RU1 = "fonts/Arial.ttf";
private final String FONT_PATH_RU2 = "fonts/Calibri.ttf";
private final String FONT_PATH_RU3 = "fonts/Cambria.ttf";
private final String FONT_PATH_RU4 = "fonts/DroidSans.ttf";
private final String FONT_PATH_RU5 = "fonts/DroidSerif.ttf";
private final String FONT_PATH_RU6 = "fonts/Times.ttf";
private final String FONT_PATH_RU7 = "fonts/Verdana.ttf";

String prayers_fonts_ru;
String black_fon_color;

MenuItem menu_item1;
MenuItem menu_item2;
MenuItem menu_item3;

float size_default_MyView = 0;
float size = 0;

boolean description_style = false;

int orientation = 0;

String prayers_tk;

public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);

 Log.d(TAG, "DescriptionActivity onCreate");

 // получаем ID выбраного раздела(подпункта)
 Intent intent = getIntent();

 Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
  .getDefaultDisplay();
 orientation = display.getRotation();

 prayers_tk = intent.getStringExtra("prayers_tk");
 
 size_default_MyView = PreferencesActivity.MyPreferenceFragment.ReadFloat(this, "pref_prayers_text_size_ru", 0);
 
 description_style = PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style1", false);

 prayers_fonts_ru = PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_ru", "1");

 ActionBar actionBar = getSupportActionBar();
 actionBar.setDisplayHomeAsUpEnabled(true);
 

 setContentView(R.layout.activity_description);

 llDescriptionActivity = (LinearLayout) this.findViewById(R.id.llViewDescription);

 tvDescriptionActivity = (MyView) this.findViewById(R.id.MyViewDescription1);
 
 if(prayers_fonts_ru.equals("1")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
  FONT_PATH_RU1));
 if(prayers_fonts_ru.equals("2")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
  FONT_PATH_RU2));
 if(prayers_fonts_ru.equals("3")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
  FONT_PATH_RU3));
 if(prayers_fonts_ru.equals("4")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
  FONT_PATH_RU4));
 if(prayers_fonts_ru.equals("5")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
  FONT_PATH_RU5));
 if(prayers_fonts_ru.equals("6")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
  FONT_PATH_RU6));
 if(prayers_fonts_ru.equals("7")) tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
  FONT_PATH_RU7));
 
 black_fon_color = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_black_fon_color", "black");

 if (description_style) {
 if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
 if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
 if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
 if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
 tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
 }


 /*tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
  FONT_PATH1));*/

 size = tvDescriptionActivity.getTextSize();
 size = size + size_default_MyView;
 tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

 tvDescriptionActivity.setText(Html.fromHtml(prayers_tk.toString().replace(
  "\r\n", "<br>")));
}






@Override
protected void onDestroy() {
 // TODO Auto-generated method stub
 super.onDestroy();

}

@Override
public void onBackPressed() {
 // TODO Auto-generated method stub
 // cursor.close();
 super.onBackPressed();
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
 Log.d(TAG, "onOptionsItemSelected");
 switch (item.getItemId()) {
 case android.R.id.home:
 // NavUtils.navigateUpFromSameTask(this);
 onBackPressed();
 return true;
 case R.id.item1_prayers_menu:
 // увеличиваем размер шрифта

 size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_ru", 0);

 size = tvDescriptionActivity.getTextSize();
 if (size < 120) {
  // Log.d(TAG, "size =" + size);
  size = size + 3;
  tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   size);
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,"pref_prayers_text_size_ru",size_default_MyView + 3);
 } else
  Toast.makeText(this, "Размер шрифта максимальный!!!",
   Toast.LENGTH_SHORT).show();
 return true;
 case R.id.item2_prayers_menu:
 // уменьшаем размер шрифта
 size_default_MyView = PreferencesActivity.MyPreferenceFragment.ReadFloat(this, "pref_prayers_text_size_ru", 0);

 size = tvDescriptionActivity.getTextSize();
 if (size > 7) {
  // Log.d(TAG, "size2 =" + size);
  size = size - 3;
  tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   size);
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_prayers_text_size_ru",
   size_default_MyView - 3);
 } else
  Toast.makeText(this, "Размер шрифта минимальный!!!",
   Toast.LENGTH_SHORT).show();

 return true;
 case R.id.item3_prayers_menu:
 description_style = PreferencesActivity.MyPreferenceFragment
  .ReadBoolean(this, "pref_description_style1", false);

 if (!description_style) {
  if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
  if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
  if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
  if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
  tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this,
   "pref_description_style1", true);
 } else {
  llDescriptionActivity.setBackgroundResource(R.drawable.rx1);
  tvDescriptionActivity.setTextColor(getResources().getColor(
   R.color.BLACK));
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this,
   "pref_description_style1", false);
 }
 return true;
 default:
 return super.onOptionsItemSelected(item);
 }
}

@Override
protected void onStart() {
 super.onStart();

 Log.d(TAG, "DescriptionActivity onStart");

 if (!PreferencesActivity.MyPreferenceFragment.ReadBoolean(this,
  "pref_rotate_screen_setting", true)) {
 // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

 if (Build.VERSION.SDK_INT >= 18) {
  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
 } else {

  int orientation = getResources().getConfiguration().orientation;
  @SuppressWarnings("deprecation")
  int rotation = getWindowManager().getDefaultDisplay()
   .getOrientation();

  int SCREEN_ORIENTATION_REVERSE_LANDSCAPE = 8;
  int SCREEN_ORIENTATION_REVERSE_PORTRAIT = 9;

  if (rotation == Surface.ROTATION_0
   || rotation == Surface.ROTATION_90) {
  if (orientation == Configuration.ORIENTATION_PORTRAIT) {
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
  }
  } else if (rotation == Surface.ROTATION_180
   || rotation == Surface.ROTATION_270) {
  if (orientation == Configuration.ORIENTATION_PORTRAIT) {
   setRequestedOrientation(SCREEN_ORIENTATION_REVERSE_PORTRAIT);
  } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
   setRequestedOrientation(SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
  }
  }
 }
 }

}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
 // Inflate the menu; this adds items to the action bar if it is present.
 Log.d(TAG, "onCreateOptionsMenu");
 getMenuInflater().inflate(R.menu.main_prayers, menu);
 menu_item1 = (MenuItem) menu.findItem(R.id.item1_prayers_menu);
 menu_item2 = (MenuItem) menu.findItem(R.id.item2_prayers_menu);
 menu_item3 = (MenuItem) menu.findItem(R.id.item3_prayers_menu);

 return super.onCreateOptionsMenu(menu);
}

// вызывается каждый раз перед отображением меню
@Override
public boolean onPrepareOptionsMenu(Menu menu) {
 // TODO Auto-generated method stub
 Log.d(TAG, "onPrepareOptionsMenu");
 
 return super.onPrepareOptionsMenu(menu);
}

// позволяет востановить данные
protected void onRestoreInstanceState(Bundle savedInstanceState) {
 // Log.d(TAG, "onRestoreInstanceState");
 super.onRestoreInstanceState(savedInstanceState);
 // Log.d(TAG, "onRestoreInstanceState");
}

// позволяет сохранить данные
protected void onSaveInstanceState(Bundle outState) {
 // Log.d(TAG, "onSaveInstanceState");
 super.onSaveInstanceState(outState);
 // Log.d(TAG, "onSaveInstanceState");
}

@Override
public boolean dispatchKeyEvent(KeyEvent event) {
 // TODO Auto-generated method stub
 DisplayMetrics dm = new DisplayMetrics();
 getWindowManager().getDefaultDisplay().getMetrics(dm);
 //final int height = dm.heightPixels;

 final ScrollView scrollViewKey = (ScrollView) findViewById(R.id.scrollViewDescription1);
 if (event.getAction() == KeyEvent.ACTION_DOWN) {
 switch (event.getKeyCode()) {
 case KeyEvent.KEYCODE_VOLUME_UP:
  scrollViewKey.post(new Runnable() {
  public void run() {
   // scrollViewKey.scrollBy(0, -(height/8));
   scrollViewKey.pageScroll(View.FOCUS_UP);

   scrollViewKey.computeScroll();
  }
  });
  // scrollViewKey.pageScroll(View.FOCUS_UP);
  // scrollToPrevious();
  return true;
 case KeyEvent.KEYCODE_VOLUME_DOWN:
  scrollViewKey.post(new Runnable() {
  public void run() {
   // scrollViewKey.scrollBy(0, +(height/8));
   scrollViewKey.pageScroll(View.FOCUS_DOWN);

   scrollViewKey.computeScroll();
  }
  });
  // scrollViewKey.pageScroll(View.FOCUS_DOWN);
  // scrollToNext();
  return true;
 }
 }
 if (event.getAction() == KeyEvent.ACTION_UP
  && (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP || event
   .getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)) {
 return true;
 }
 return super.dispatchKeyEvent(event);
}
}
