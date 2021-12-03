package oleksandr.kotyuk.orthodoxcalendar;

import java.util.ArrayList;

import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import androidx.appcompat.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
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

public class PrayersBookmarksActivity extends MenuActivity {

static final String TAG = "myLogs";
int id_description = 1;

private DatabaseHelper db;
Cursor cursor;
String sql = "";

LinearLayout llDescriptionActivity;
MyView tvDescriptionActivity;

//private final String FONT_PATH1 = "fonts/TRIOD35.TTF";
//private final String FONT_PATH2 = "fonts/IrmUcs.ttf";

private final String FONT_PATH_RU1 = "fonts/Arial.ttf";
private final String FONT_PATH_RU2 = "fonts/Calibri.ttf";
private final String FONT_PATH_RU3 = "fonts/Cambria.ttf";
private final String FONT_PATH_RU4 = "fonts/DroidSans.ttf";
private final String FONT_PATH_RU5 = "fonts/DroidSerif.ttf";
private final String FONT_PATH_RU6 = "fonts/Times.ttf";
private final String FONT_PATH_RU7 = "fonts/Verdana.ttf";

private final String FONT_PATH_CS1 = "fonts/Canonic.ttf";
private final String FONT_PATH_CS2 = "fonts/Orthodox.ttf";
private final String FONT_PATH_CS3 = "fonts/Triodion.ttf";

String prayers_language;
String black_fon_color;

float size_default_MyView = 0;
float size = 0;

boolean description_style = false;

int orientation = 0;

// String[] list_bookmarks;
ArrayList<String> arr_bookmark;
int arr_bookmark_id = -1;
int prayers_group;
int prayers_position;
String prayers_name;

String text_br="<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>";

public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);

 Log.d(TAG, "DescriptionActivity onCreate");

 // получаем ID выбраного раздела(подпункта)
 Intent intent = getIntent();

 Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
  .getDefaultDisplay();
 orientation = display.getRotation();

 arr_bookmark_id = intent.getIntExtra("arr_position", 1);
 prayers_group = intent.getIntExtra("prayers_group", 1);
 prayers_position = intent.getIntExtra("prayers_position", 1);
 prayers_name = intent.getStringExtra("prayers_name");

 prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_prayers_language", "ru");

 if (prayers_language.equals("ru")) {
 size_default_MyView = PreferencesActivity.MyPreferenceFragment
  .ReadFloat(this, "pref_prayers_text_size_ru", 0);
 arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(
  this, "new_prayers_bookmarks_ru");
 }
 if (prayers_language.equals("cs")) {
 size_default_MyView = PreferencesActivity.MyPreferenceFragment
  .ReadFloat(this, "pref_prayers_text_size_cs", 0);
 arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(
  this, "new_prayers_bookmarks_cs");
 }
 description_style = PreferencesActivity.MyPreferenceFragment
  .ReadBoolean(this, "pref_description_style1", false);

 SelectBookmarksOnOff();

 if (prayers_name == null) {
 prayers_name = "";
 }

 ActionBar actionBar = getSupportActionBar();
 actionBar.setDisplayHomeAsUpEnabled(true);

 if (prayers_group == 1) {
 if (prayers_language.equals("ru"))
  sql = "select text_prayers from prayers_ru_pr where id2="
   + prayers_position;
 else
  sql = "select text_prayers from prayers_cs_pr where id2="
   + prayers_position;
 }
 
 if (prayers_group == 2) {
 if (prayers_language.equals("ru"))
  sql = "select text_prayers from prayers_ru_ak where id2="
   + prayers_position;
 else
  sql = "select text_prayers from prayers_cs_ak where id2="
   + prayers_position;
 }
 
 if (prayers_group == 3) {
 if (prayers_language.equals("ru"))
  sql = "SELECT psalom_text_ru FROM psaltur where _id="
   + prayers_position;
 else
  sql = "SELECT psalom_text_csFROM psaltur where _id="
   + prayers_position;
 }

 setContentView(R.layout.activity_description);

 llDescriptionActivity = (LinearLayout) this
  .findViewById(R.id.llViewDescription);

 tvDescriptionActivity = (MyView) this
  .findViewById(R.id.MyViewDescription1);
 
 black_fon_color = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_black_fon_color", "black");

 if (description_style) {
 if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
 if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
 if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
 if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
 tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
 }

 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);
 StringBuilder text = new StringBuilder();
 if (cursor != null) {
 if (cursor.moveToFirst()) {
  do {
  try {
   if (prayers_group == 1 || prayers_group == 2) {
   text.append("<big><FONT COLOR=#aa2c2c><b>"
     + prayers_name
     + "</b></FONT></big><br>"
     + cursor.getString(cursor
      .getColumnIndex("text_prayers"))
     + "<br><br>");
   
   } 
   if (prayers_group == 3){
   if (prayers_language.equals("ru"))
    text.append(cursor.getString(cursor
     .getColumnIndex("psalom_text_ru"))
     + "<br><br>");
   else
    text.append(cursor.getString(cursor
     .getColumnIndex("psalom_text_sc"))
     + "<br><br>");
   }
  } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
  }
  } while (cursor.moveToNext());

 }
 }

 if (prayers_language.equals("ru")){
 
 String prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_ru", "1");
 if(prayers_fonts_ru.equals("1")){
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU1));
  tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
 }
 if(prayers_fonts_ru.equals("2")){
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU2));
  tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
 }
 if(prayers_fonts_ru.equals("3")){
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU3));
  tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
 }
 if(prayers_fonts_ru.equals("4")){
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU4));
  tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
 }
 if(prayers_fonts_ru.equals("5")){
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU5));
  tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
 }
 if(prayers_fonts_ru.equals("6")){
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU6));
  tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
 }
 if(prayers_fonts_ru.equals("7")){
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_RU7));
  tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.0f,  getResources().getDisplayMetrics()), 1.0f);
 }
 
 /*tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
  FONT_PATH1));*/
 }
 else{
 String prayers_fonts_cs=PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_fonts_cs", "1");
 if(prayers_fonts_cs.equals("1")){
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_CS1));
  tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -5.0f,  getResources().getDisplayMetrics()), 1.0f);
 }
 if(prayers_fonts_cs.equals("2")){
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_CS2));
  tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -8.0f,  getResources().getDisplayMetrics()), 1.0f);
 }
 if(prayers_fonts_cs.equals("3")){
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this, FONT_PATH_CS3));
  tvDescriptionActivity.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -10.0f,  getResources().getDisplayMetrics()), 1.0f);
 }
 
 /*tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
  FONT_PATH2));*/
 }

 size = tvDescriptionActivity.getTextSize();
 size = size + size_default_MyView;
 tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

 tvDescriptionActivity.setText(Html.fromHtml(text.toString().replace(
  "\r\n", "<br>")+text_br));
}

public void SelectBookmarksOnOff() {

 flag_bookmarks_on = true;
}

public void BookmarksAdd() {
 if (prayers_language.equals("ru")) {
 arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(
  this, "new_prayers_bookmarks_ru");
 if (arr_bookmark == null) {
  arr_bookmark = new ArrayList<String>();
 }
 arr_bookmark.add(prayers_group + "###" + prayers_position + "###"
  + prayers_name);
 arr_bookmark_id=arr_bookmark.size()-1;
 PreferencesActivity.MyPreferenceFragment.putList(
  getApplicationContext(), "new_prayers_bookmarks_ru",
  arr_bookmark);
 }
 
 if (prayers_language.equals("cs")) {
 arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(
  this, "new_prayers_bookmarks_cs");
 if (arr_bookmark == null) {
  arr_bookmark = new ArrayList<String>();
 }
 arr_bookmark.add(prayers_group + "###" + prayers_position + "###"
  + prayers_name);
 arr_bookmark_id=arr_bookmark.size()-1;
 PreferencesActivity.MyPreferenceFragment.putList(
  getApplicationContext(), "new_prayers_bookmarks_cs",
  arr_bookmark);
 }
}

public void BookmarksDelete() {

 if (prayers_language.equals("ru")) {
 arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(
  this, "new_prayers_bookmarks_ru");
 arr_bookmark.remove(arr_bookmark_id);
 PreferencesActivity.MyPreferenceFragment.putList(
  getApplicationContext(), "new_prayers_bookmarks_ru",
  arr_bookmark);
 }
 if (prayers_language.equals("cs")) {
 arr_bookmark = PreferencesActivity.MyPreferenceFragment.getList(
  this, "new_prayers_bookmarks_cs");
 arr_bookmark.remove(arr_bookmark_id);
 PreferencesActivity.MyPreferenceFragment.putList(
  getApplicationContext(), "new_prayers_bookmarks_cs",arr_bookmark);
 }
}

@Override
protected void onDestroy() {
 // TODO Auto-generated method stub
 super.onDestroy();
 if (cursor != null)
 cursor.close();
 if (db != null)
 db.closeConnecion();
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

 if (prayers_language.equals("ru")) {
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_ru", 0);
 }
 if (prayers_language.equals("cs")) {
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_cs", 0);
 }
 size = tvDescriptionActivity.getTextSize();
 if (size < 120) {
  // Log.d(TAG, "size =" + size);
  size = size + 3;
  tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   size);
  if (prayers_language.equals("ru")) {
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_prayers_text_size_ru",
   size_default_MyView + 3);
  } else {
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_prayers_text_size_cs",
   size_default_MyView + 3);
  }
 } else
  Toast.makeText(this, "Размер шрифта максимальный!!!",
   Toast.LENGTH_SHORT).show();
 return true;
 case R.id.item2_prayers_menu:
 // уменьшаем размер шрифта
 if (prayers_language.equals("ru")) {
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_ru", 0);
 }
 if (prayers_language.equals("cs")) {
  size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_prayers_text_size_cs", 0);
 }
 size = tvDescriptionActivity.getTextSize();
 if (size > 7) {
  // Log.d(TAG, "size2 =" + size);
  size = size - 3;
  tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   size);
  if (prayers_language.equals("ru")) {
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_prayers_text_size_ru",
   size_default_MyView - 3);
  } else {
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_prayers_text_size_cs",
   size_default_MyView - 3);
  }
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
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this,"pref_description_style1", true);
 } else {
  llDescriptionActivity.setBackgroundResource(R.drawable.rx1);
  tvDescriptionActivity.setTextColor(getResources().getColor(R.color.BLACK));
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this,"pref_description_style1", false);
 }
 return true;
 case R.id.item4_prayers_menu:
 if (flag_bookmarks_on) {
  flag_bookmarks_on = false;
  BookmarksDelete();
 } else {
  flag_bookmarks_on = true;
  BookmarksAdd();
 }
 invalidateOptionsMenu();
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
 getMenuInflater().inflate(R.menu.main_prayers_bookmarks, menu);
menu_item1 = (MenuItem) menu.findItem(R.id.item1_prayers_menu);
 menu_item2 = (MenuItem) menu.findItem(R.id.item2_prayers_menu);
 menu_item3 = (MenuItem) menu.findItem(R.id.item3_prayers_menu);
 menu_item4 = (MenuItem) menu.findItem(R.id.item4_prayers_menu);

 return super.onCreateOptionsMenu(menu);
}

// вызывается каждый раз перед отображением меню
@Override
public boolean onPrepareOptionsMenu(Menu menu) {
 // TODO Auto-generated method stub
 Log.d(TAG, "onPrepareOptionsMenu");
workWithFavouriteItem(menu);
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
