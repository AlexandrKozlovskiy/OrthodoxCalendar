package oleksandr.kotyuk.orthodoxcalendar;

import java.util.ArrayList;

import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;
import oleksandr.kotyuk.orthodoxcalendar.GlobalData;
import androidx.appcompat.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import oleksandr.kotyuk.orthodoxcalendar.MyView;
import android.widget.Toast;

public class DescriptionOtherActivity  extends AppCompatActivity{

 private DatabaseHelper db;
 Cursor cursor;
 String sql = "";

 LinearLayout llDescriptionActivity;
 MyView tvDescriptionActivity;
 MyView tvDescriptionActivityLink;
 float size_default_MyView = 0;
 float size = 0;
 String fontSizeKey="";
 String[] fontPaths;
 String prayers_fonts;
 MenuItem menu_item1;
 MenuItem menu_item2;
 String description_id="";
 String description_id_tropar_kondak="";
 String description_id_multi="";
 String description_id_one="";
 String description_id_sedmitsa="";
 String description_id_other="";
 String description_id_link_prayer_gospel="";
 String description_id_link_akafist="";
 String description_id_prayers_day_sedmits="";
 String id="";
 String top_prayer_gospel="<FONT COLOR=#aa2c2c> <b>Молитвы пред и по чтении Евангелия</b> </FONT><br>";
 int description_id_bible_prayer_gospel=173;

 boolean description_style=false;

 String black_fon_color;

 //ArrayList<String> arr_text_tropar_kondak;
 String date_calendar;
 ArrayList<String> unmovable_holiday_text = new ArrayList<String>();
 ArrayList<String> unmovable_holiday_index = new ArrayList<String>();
 ArrayList<String> unmovable_holiday_text_tmp = new ArrayList<String>();
 ArrayList<String> unmovable_holiday_index_tmp = new ArrayList<String>();
 ArrayList<String> unmovable_holiday_new = new ArrayList<String>();
 ArrayList<String> arr_prayers_tmp = new ArrayList<String>();
 String[] mas_date_calendar;
 String prayers_tmp[];
 String prayers[];
 String prayers_final[];
 String prayers_day = "#";
 int prayers_day_count = 1;

 int id_tk;
 int year_tk;
 int month_tk;
 int data_tk;
 int pageNumber_tk;

 int id_pr;
 int year_pr;
 int month_pr;
 int data_pr;
 int pageNumber_pr;

 MyCalendar cal_tk = MyCalendar.getInstance();
 MyCalendar cal_pr = MyCalendar.getInstance();
 String host="";

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  super.onCreate(savedInstanceState);
  Uri uri=null;
  Intent intent = getIntent();
  if(intent!=null) uri=intent.getData();
  if(uri!=null) host=uri.getHost();
  setContentView(R.layout.activity_description);
  ActionBar actionBar = getSupportActionBar();
  // Enabling Up / Back navigation
  actionBar.setDisplayHomeAsUpEnabled(true);

  try {
   if(host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST()) ||host.equals(GlobalData.getDESCRIPTION_LESSONS_ACTIVITY_HOST())) description_id = uri.getQueryParameter("id");
   else if(host.length()>0) {
    if (host.equals(GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())) {
     description_id_tropar_kondak = uri.getQueryParameter("id_tropar_kondak");
     description_id_multi = uri.getQueryParameter("id_multi");
     description_id_one = uri.getQueryParameter("id_one");
     description_id_sedmitsa = uri.getQueryParameter("id_sedmitsa");
     description_id_other = uri.getQueryParameter("id_other");
     description_id_link_prayer_gospel = uri.getQueryParameter("id_prayer");
     description_id_prayers_day_sedmits = uri.getQueryParameter("id_prayers_day_sedmits");
    }
    description_id_link_akafist = uri.getQueryParameter("id_akafist");
   }
  } catch (Exception e) {
   // TODO: handle exception
  }
  fontPaths =!host.equals(GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST_CS())? new String[]{"fonts/Arial.ttf","fonts/Calibri.ttf","fonts/Cambria.ttf","fonts/DroidSans.ttf","fonts/DroidSerif.ttf","fonts/Times.ttf","fonts/Verdana.ttf"}:new String[] {"fonts/Canonic.ttf","fonts/Orthodox.ttf","fonts/Triodion.ttf"};
  prayers_fonts = PreferencesActivity.MyPreferenceFragment.ReadString(this, !host.equals(GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST_CS())?"pref_prayers_fonts_ru":"pref_prayers_fonts_cs", "1");
  fontSizeKey=!host.equals(GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST_CS())?"pref_description_text_size":"pref_prayers_text_size_cs";
  description_id_bible_prayer_gospel = intent.getIntExtra("description_id_bible_prayer_gospel", 0);
  description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style2", false);
  llDescriptionActivity=(LinearLayout)this.findViewById(R.id.llViewDescription);
  tvDescriptionActivity = (MyView) this
          .findViewById(R.id.MyViewDescription1);
  tvDescriptionActivityLink = (MyView) this.findViewById(R.id.MyViewDescriptionLink);
  if(host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())) {
   tvDescriptionActivityLink.setLinksClickable(true);
   tvDescriptionActivityLink.setMovementMethod(new LinkMovementMethod());
  }
  else tvDescriptionActivityLink.setVisibility(View.GONE);
  if(description_id_link_akafist!=null){
   //if(description_id_link_akafist!=null && description_id_link_akafist.equals("63"))
   tvDescriptionActivity.setLinksClickable(true);
   tvDescriptionActivity.setMovementMethod(new LinkMovementMethod());
  }
  tvDescriptionActivity.setTypeface(FontsHelper.getTypeFace(this,
          fontPaths[Integer.parseInt(prayers_fonts)-1]));
  if(host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())) tvDescriptionActivityLink.setTypeface(tvDescriptionActivity.getTypeface());
  black_fon_color = PreferencesActivity.MyPreferenceFragment.ReadString(
         this, "pref_black_fon_color", "black");
 if(description_style){
  if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
  if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
  if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
  if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
  tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
  if(host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())) tvDescriptionActivityLink.setTextColor(getResources().getColor(R.color.WHITE2));
 }

 size_default_MyView =PreferencesActivity.MyPreferenceFragment.ReadFloat(this, fontSizeKey, 0);

 size = tvDescriptionActivity.getTextSize();
 // Log.d(TAG, "size =" + size);
 size = size + size_default_MyView;
 tvDescriptionActivity.setTextSize(
 TypedValue.COMPLEX_UNIT_PX, size);
 if(host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())) tvDescriptionActivityLink.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
 if(intent!=null &&intent.hasExtra("prayers_tk")) {
tvDescriptionActivity.setText(Html.fromHtml(intent.getStringExtra("prayers_tk").replace(
        "\r\n", "<br>")));
return;
 }
 db = DatabaseHelper.getInstance(this);
 if(description_id_tropar_kondak!=null && !description_id_tropar_kondak.equals("")){
  try{
   tvDescriptionActivity.setText(Html.fromHtml(textDescriptionOtherTroparKondak().replace("\r\n", "<br>")));
  }catch(Exception e){
   tvDescriptionActivity.setText("Произошла ошибка!!!");
  }
 }else{
  if(description_id_prayers_day_sedmits!=null && !description_id_prayers_day_sedmits.equals("")){
   try{
    tvDescriptionActivity.setText(Html.fromHtml(textDescriptionOtherPrayersSedmits().replace("\r\n", "<br>")));
   }catch(Exception e){
    tvDescriptionActivity.setText("Произошла ошибка!!!");
   }
  }
  else if(!host.equals(GlobalData.DESCRIPTION_HOLY_ACTIVITY_HOST) &&!host.equals(GlobalData.getDESCRIPTION_LESSONS_ACTIVITY_HOST())) {
   try{
    tvDescriptionActivity.setText(Html.fromHtml(textDescriptionOther().replace("\r\n", "<br>")));
   }catch(Exception e){
    tvDescriptionActivity.setText("Произошла ошибка!!!");
   }
  }
  else {
   try{
    tvDescriptionActivity.setText(Html.fromHtml(textDescription(description_id).replace("\r\n", "<br>")));
    if(host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())) tvDescriptionActivityLink.setText(Html.fromHtml(textDescriptionHolyLink(description_id).replace("\r\n", "<br>")));
   }catch(Exception e){
    tvDescriptionActivity.setText("Произошла ошибка!!!");
   }
  }
 }
}
 public String textDescription(String id){
  String text="";
  String field = host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())? "holiday_description":"name_description";
  String table=host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())? "description_holiday":"holiday_lessons";
  cursor = db.executeQuery("select "+field+" from "+table +" where _id="+id+";");
  if (cursor != null) {
   if (cursor.moveToFirst()) {
    do {
     try {
      text=cursor.getString(cursor.getColumnIndex(field));
     } catch (NumberFormatException e) {
      // Log.d(TAG, "ERROR=" + e.toString());
      text="Error!!!";
     }
    } while (cursor.moveToNext());

   }
  }
  text=text.replaceAll("<b>", "<FONT COLOR=#aa2c2c><b>");
  text=text.replaceAll("</b>", "</b></FONT>");
  return text;
 }

 public String textDescriptionHolyLink(String id){
  String text="";

  cursor = db.executeQuery("select link_source from description_holiday where _id="+id+";");
  if (cursor != null) {
   if (cursor.moveToFirst()) {
    do {
     try {
      text=cursor.getString(cursor.getColumnIndex("link_source"))+ "<br><br><br>";
     } catch (NumberFormatException e) {
      // Log.d(TAG, "ERROR=" + e.toString());
      text="Error!!!";
     }
    } while (cursor.moveToNext());
   }
  }
  return text;
 }

 public String textDescriptionOtherPrayersSedmits(){
  String text="Произошла ошибка!";
  String[] result = description_id_prayers_day_sedmits.replace(".", "#").split("#");

  try {
   id_pr = Integer.parseInt(result[0]);
   year_pr=Integer.parseInt(result[1]);
   month_pr=Integer.parseInt(result[2]);
   data_pr=Integer.parseInt(result[3]);
   pageNumber_pr=Integer.parseInt(result[4]);
  } catch (NumberFormatException e) {
   id_pr = 0;
   year_pr=0;
   month_pr=0;
   data_pr=0;
   pageNumber_pr=0;
  }
  cal_pr.AddDay(pageNumber_pr);

  // день недели (воскресенье - 1; субота - 7)
  int day_week = cal_tk.getDayWeek();
  switch (day_week) {
   case 1:
    // воскресенье
    sql = "select text from prayers_day_sedmits where _id=7";
    break;
   case 2:
    // пн
    sql = "select text from prayers_day_sedmits where _id=1";
    break;
   case 3:
    // вт
    sql = "select text from prayers_day_sedmits where _id=2";
    break;
   case 4:
    // ср
    sql = "select text from prayers_day_sedmits where _id=3";
    break;
   case 5:
    // чт
    sql = "select text from prayers_day_sedmits where _id=4";
    break;
   case 6:
    // пт
    sql = "select text from prayers_day_sedmits where _id=5";
    break;
   case 7:
    // сб
    sql = "select text from prayers_day_sedmits where _id=6";
    break;

   default:
    break;
  }

  db = DatabaseHelper.getInstance(this);
  cursor = db.executeQuery(sql);

  if (cursor != null) {
   if (cursor.moveToFirst()) {
    try {

     text = cursor.getString(cursor.getColumnIndex("text"));

    } catch (NumberFormatException e) {
     // Log.d(TAG, "ERROR=" + e.toString());
    }
   }
  }
  return text;
 }

 public String textDescriptionOtherTroparKondak(){
  String text="Произошла ошибка!";
  String[] result = description_id_tropar_kondak.replace(".", "#").split("#");

  try {
   id_tk = Integer.parseInt(result[0]);
   year_tk=Integer.parseInt(result[1]);
   month_tk=Integer.parseInt(result[2]);
   data_tk=Integer.parseInt(result[3]);
   pageNumber_tk=Integer.parseInt(result[4]);
  } catch (NumberFormatException e) {
   id_tk = 0;
   year_tk=0;
   month_tk=0;
   data_tk=0;
   pageNumber_tk=0;
  }
  cal_tk.AddDay(pageNumber_tk);

  if (year_tk == 2020) {
   if ((month_tk == 2 && data_tk == 29) || (month_tk == 3 && (data_tk > 0 && data_tk < 14))) {
    text=newVisYearPrayers();
   }else{
    text=createTroparKondakDayText();
   }
  }else{
   text=createTroparKondakDayText();
  }

  return text;
 }

 public String textDescriptionOther(){

  String column_index="";
  if(host.equals(GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST()) ||host.equals(GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST_CS())) {
   if (description_id_multi != null) {
    sql = "select description from multiday_post_years where _id=" + description_id_multi;
    column_index = "description";
    top_prayer_gospel = "";
   }
   if (description_id_one != null) {
    sql = "select description from oneday_post_years where _id=" + description_id_one;
    column_index = "description";
    top_prayer_gospel = "";
   }
   if (description_id_sedmitsa != null) {
    sql = "select description from sedmitsa_post_years where _id=" + description_id_sedmitsa;
    column_index = "description";
    top_prayer_gospel = "";
   }
   if (description_id_other != null) {
    sql = "select other_description from description_other where _id=" + description_id_other;
    column_index = "other_description";
    top_prayer_gospel = "";
   }
   if (description_id_link_prayer_gospel != null) {
    sql = "select text_prayers, name_prayers from prayers_ru_pr where id2=" + description_id_link_prayer_gospel;
    column_index = "text_prayers";
   }
  }
  if(description_id_link_akafist!=null){
   sql="select text_prayers, name_prayers from prayers_"+(host.equals(GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST_CS())?"cs":"ru")+"_ak where id2="+description_id_link_akafist;
   column_index="text_prayers";
  }
  if (description_id_bible_prayer_gospel == 173) {
   sql = "select text_prayers, name_prayers from prayers_ru_pr where id2=" + description_id_bible_prayer_gospel;
   column_index = "text_prayers";
  }
  String text="";

  cursor = db.executeQuery(sql);
  if (cursor != null) {
   if (cursor.moveToFirst()) {
    do {
     try {
      if(description_id_link_prayer_gospel!=null || description_id_link_akafist!=null){
       top_prayer_gospel="<FONT COLOR=#aa2c2c> <b>"+cursor.getString(cursor.getColumnIndex("name_prayers"))+"</b> </FONT> <br>";
      }
      text=cursor.getString(cursor.getColumnIndex(column_index))+ "<br><br>";
     } catch (NumberFormatException e) {
      // Log.d(TAG, "ERROR=" + e.toString());
      text="Error!!!";
     }
    } while (cursor.moveToNext());

   }
  }
  if(host.equals(GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST())) {
   text = text.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
   text = text.replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST());
  }
  return top_prayer_gospel+text;
 }

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  // Inflate the menu; this adds items to the action bar if it is present.
  //Log.d(TAG, "onCreateOptionsMenu");
  getMenuInflater().inflate(R.menu.main_prayers, menu);
  menu_item1 = (MenuItem) menu.findItem(R.id.item1_prayers_menu);
  menu_item2 = (MenuItem) menu.findItem(R.id.item2_prayers_menu);
  return super.onCreateOptionsMenu(menu);
 }

 // позволяет востановить данные
 protected void onRestoreInstanceState(Bundle savedInstanceState) {
  //Log.d(LOG_TAG, "onRestoreInstanceState");
  super.onRestoreInstanceState(savedInstanceState);
  //Log.d(LOG_TAG, "onRestoreInstanceState");
 }

 // позволяет сохранить данные
 protected void onSaveInstanceState(Bundle outState) {
  //Log.d(LOG_TAG, "onSaveInstanceState");
  super.onSaveInstanceState(outState);
  //Log.d(LOG_TAG, "onSaveInstanceState");
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
 protected void onStart() {
  // TODO Auto-generated method stub
  super.onStart();

  if(!PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_rotate_screen_setting", true)){
   //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

   if (Build.VERSION.SDK_INT >= 18)
   {
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
   }
   else {
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
 public void onBackPressed() {
  // TODO Auto-generated method stub
  // cursor.close();
  super.onBackPressed();
 }

 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
  switch (item.getItemId()) {
   case android.R.id.home:
    // NavUtils.navigateUpFromSameTask(this);
    onBackPressed();
    return true;
   case R.id.item1_prayers_menu:
    // увеличиваем размер шрифта

    size_default_MyView = PreferencesActivity.MyPreferenceFragment
            .ReadFloat(this, fontSizeKey, 0);

    size = tvDescriptionActivity.getTextSize();
    if(size<120){
     // Log.d(TAG, "size =" + size);
     size = size + 3;
     tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
     if(host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())) tvDescriptionActivityLink.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
     PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
             fontSizeKey, size_default_MyView + 3);

    }else Toast.makeText(this, "Размер шрифта максимальный!!!", Toast.LENGTH_SHORT)
            .show();
    return true;
   case R.id.item2_prayers_menu:
    // уменьшаем размер шрифта

    size_default_MyView = PreferencesActivity.MyPreferenceFragment
            .ReadFloat(this, fontSizeKey, 0);
    size = tvDescriptionActivity.getTextSize();
    if(size>7){
     //Log.d(TAG, "size2 =" + size);
     size = size - 3;
     tvDescriptionActivity.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
     if(host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())) tvDescriptionActivityLink.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
     PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
             fontSizeKey, size_default_MyView - 3);

    }else Toast.makeText(this, "Размер шрифта минимальный!!!", Toast.LENGTH_SHORT)
            .show();
    return true;
   case R.id.item3_prayers_menu:
    description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style2", false);
    if(!description_style){
     if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
     if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
     if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
     if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
     tvDescriptionActivity.setTextColor(getResources().getColor(R.color.WHITE2));
     if(host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())) tvDescriptionActivityLink.setTextColor(getResources().getColor(R.color.WHITE2));
     PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style2", true);
    }else{
     llDescriptionActivity.setBackgroundResource(R.drawable.rx1);
     tvDescriptionActivity.setTextColor(getResources().getColor(R.color.BLACK));
     if(host.equals(GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST())) tvDescriptionActivityLink.setTextColor(getResources().getColor(R.color.BLACK));
     PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style2", false);
    }
    return true;
   default:
    return super.onOptionsItemSelected(item);
  }
 }

 @Override
 public boolean dispatchKeyEvent(KeyEvent event) {
  // TODO Auto-generated method stub
  DisplayMetrics dm = new DisplayMetrics();
  getWindowManager().getDefaultDisplay().getMetrics(dm);
  //final int height=dm.heightPixels;

  final ScrollView scrollViewKey = (ScrollView) findViewById(R.id.scrollViewDescription1);
  if (event.getAction() == KeyEvent.ACTION_DOWN) {
   switch (event.getKeyCode()) {
    case KeyEvent.KEYCODE_VOLUME_UP:
     scrollViewKey.post(new Runnable() {
      public void run() {
       //scrollViewKey.scrollBy(0, -(height/8));
       scrollViewKey.pageScroll(View.FOCUS_UP);

       scrollViewKey.computeScroll();
      }
     });
     //scrollViewKey.pageScroll(View.FOCUS_UP);
     //scrollToPrevious();
     return true;
    case KeyEvent.KEYCODE_VOLUME_DOWN:
     scrollViewKey.post(new Runnable() {
      public void run() {
       //scrollViewKey.scrollBy(0, +(height/8));
       scrollViewKey.pageScroll(View.FOCUS_DOWN);

       scrollViewKey.computeScroll();
      }
     });
     //scrollViewKey.pageScroll(View.FOCUS_DOWN);
     //scrollToNext();
     return true;
   }
  }
  if (event.getAction() == KeyEvent.ACTION_UP
          && (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
          || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)) {
   return true;
  }
  return super.dispatchKeyEvent(event);
 }

 public String createTroparKondakDayText() {

  sql = "SELECT tropari_kondaki_day FROM data_calendar WHERE month="
          + month_tk + " AND day=" + data_tk;

  db = DatabaseHelper.getInstance(this);
  cursor = db.executeQuery(sql);

  if (cursor != null) {
   if (cursor.moveToFirst()) {
    try {

     date_calendar = cursor.getString(cursor
             .getColumnIndex("tropari_kondaki_day"));

    } catch (NumberFormatException e) {
     // Log.d(TAG, "ERROR=" + e.toString());
    }
   }
  }


  sql = "SELECT tropari_kondaki_day, tk_level" + year_tk
          + " FROM big_unmovable_holiday WHERE m" + year_tk + "=" + month_tk
          + " AND d" + year_tk + "=" + data_tk + " AND tk_level" + year_tk + "<>0";

  db = DatabaseHelper.getInstance(this);
  cursor = db.executeQuery(sql);

  if (cursor != null) {
   if (cursor.moveToFirst()) {
    do {
     try {

      unmovable_holiday_text_tmp.add(cursor.getString(cursor
              .getColumnIndex("tropari_kondaki_day")));

      unmovable_holiday_index_tmp.add(Integer.toString(cursor
              .getInt(cursor
                      .getColumnIndex("tk_level" + year_tk))));

     } catch (NumberFormatException e) {
      // Log.d(TAG, "ERROR=" + e.toString());
     }
    } while (cursor.moveToNext());
   }
  }

  sql = "SELECT tropari_kondaki_day, tk_level" + year_tk
          + " FROM unmovable_holiday WHERE m" + year_tk + "=" + month_tk
          + " AND d" + year_tk + "=" + data_tk + " AND tk_level" + year_tk + "<>0";

  db = DatabaseHelper.getInstance(this);
  cursor = db.executeQuery(sql);

  if (cursor != null) {
   if (cursor.moveToFirst()) {
    do {
     try {

      unmovable_holiday_text_tmp.add(cursor.getString(cursor
              .getColumnIndex("tropari_kondaki_day")));

      unmovable_holiday_index_tmp.add(Integer.toString(cursor
              .getInt(cursor
                      .getColumnIndex("tk_level" + year_tk))));

     } catch (NumberFormatException e) {
      // Log.d(TAG, "ERROR=" + e.toString());
     }
    } while (cursor.moveToNext());
   }
  }

  addPrayersDay();
  int i = date_calendar.indexOf("***\r\n\r\n");
  if (i != -1)
   date_calendar = date_calendar.replace("***\r\n\r\n", "###");

  i = date_calendar.indexOf("###");
  if (i != -1)
   mas_date_calendar = date_calendar.split("###");
  else {
   mas_date_calendar = new String[1];
   mas_date_calendar[0] = date_calendar;
  }

  int k = 0;
  if (!mas_date_calendar[0].equals("#")) k = mas_date_calendar.length;

  int l = k + unmovable_holiday_text_tmp.size();
  prayers_tmp = new String[l];

  if (unmovable_holiday_text_tmp.size() != 0) {
   sortLists();
   addPrayers(l);
  } else {
   prayers = (String[]) mas_date_calendar.clone();
  }
  //newPositionPrayers();// *****
  //--------------------------
  addNewPrayersUnmovable();

  int k1=unmovable_holiday_new.size();
  int k2=prayers.length;
  if(prayers[0].equals("#")) k2=0;
  int k3=0;
  if(!prayers_day.equals("#")) k3=1;
  int kk=k1+k2+k3;
  prayers_final=new String[kk];

  if(k3==0){
   for(int ii=0;ii<k1;ii++){
    prayers_final[ii]=unmovable_holiday_new.get(ii);
   }

   if(k2!=0){
    for(int j=k1;j<k2+k1;j++){
     prayers_final[j]=prayers[j-k1];
    }
   }
  }
  if(k3==1){
   prayers_final[0]=prayers_day;
   for(int ii=1;ii<k1+1;ii++){
    prayers_final[ii]=unmovable_holiday_new.get(ii-1);
   }

   if(k2!=0){
    for(int j=k1+1;j<k2+k1+1;j++){
     prayers_final[j]=prayers[j-k1-1];
    }
   }
  }

  //createTroparKondakLink();
  //--------------------------
  //createTitlePrayers();
  //replace_tk();
  //saveTextTkPreferences();

  String text="";
  if(id_tk==100){
   text=fullTroparKondakText();
  }
  else{
   text=prayers_final[id_tk];
   text=createTitlePrayers(text);
  }

  text=replace_tk(text);

  return text;
 }

 //----------------------------------------------
//добавляем новые перемещаемые тропари и кондаки
 public void addNewPrayersUnmovable(){

  //SELECT tropari_kondaki_text FROM tropari_kondaki_holiday_multi WHERE m2016s>=1 AND d2016s>=7 AND m2016f<=1 AND d2016f<=7
  //SELECT tropari_kondaki_text, m2016s, d2016s, m2016f, d2016f FROM tropari_kondaki_holiday_multi

  //выборка из таблицы tropari_kondaki_holiday_multi
  sql="SELECT tropari_kondaki_text, m"+year_tk+"s, d"+year_tk+"s, m"+year_tk+"f, d"+year_tk+"f FROM tropari_kondaki_holiday_multi";

  db = DatabaseHelper.getInstance(this);
  cursor = db.executeQuery(sql);

  int m_year_s=0;
  int d_year_s=0;
  int m_year_f=0;
  int d_year_f=0;
  if (cursor != null) {
   if (cursor.moveToFirst()) {
    do {
     try {
      m_year_s=cursor.getInt(cursor.getColumnIndex("m"+year_tk+"s"));
      d_year_s=cursor.getInt(cursor.getColumnIndex("d"+year_tk+"s"));
      m_year_f=cursor.getInt(cursor.getColumnIndex("m"+year_tk+"f"));
      d_year_f=cursor.getInt(cursor.getColumnIndex("d"+year_tk+"f"));

      if(m_year_s!=0){
       if(m_year_s==m_year_f){
        if(m_year_s==month_tk){
         if(d_year_s<=data_tk && d_year_f>=data_tk)
          unmovable_holiday_new.add(cursor.getString(cursor.getColumnIndex("tropari_kondaki_text")));
        }
       }else{
        if(m_year_s==month_tk || m_year_f==month_tk){
         if((d_year_s<=data_tk && m_year_s==month_tk) || (d_year_f>=data_tk && m_year_f==month_tk)){
          unmovable_holiday_new.add(cursor.getString(cursor.getColumnIndex("tropari_kondaki_text")));
         }
        }
       }
      }


     } catch (NumberFormatException e) {
      // Log.d(TAG, "ERROR=" + e.toString());
     }
    } while (cursor.moveToNext());
   }
  }

  //выборка из таблицы tropari_kondaki_holiday_one
  //SELECT tropari_kondaki_text FROM tropari_kondaki_holiday_one WHERE m2016=5 AND d2016=10
  sql = "SELECT tropari_kondaki_text FROM tropari_kondaki_holiday_one WHERE m" + year_tk + "=" + month_tk
          + " AND d" + year_tk + "=" + data_tk;

  db = DatabaseHelper.getInstance(this);
  cursor = db.executeQuery(sql);

  if (cursor != null) {
   if (cursor.moveToFirst()) {
    do {
     try {

      unmovable_holiday_new.add(cursor.getString(cursor
              .getColumnIndex("tropari_kondaki_text")));

     } catch (NumberFormatException e) {
      // Log.d(TAG, "ERROR=" + e.toString());
     }
    } while (cursor.moveToNext());
   }
  }
 }
//-----------------------------------------------------

 // создаем тропари для високосного года
 public String newVisYearPrayers() {
  String tmp_text_vis_years = "";
  String text="";
  cursor = null;
  sql = "select tropari_kondaki_day from data_calendar_leap_year where month="
          + month_tk + " AND day=" + data_tk + ";";
  // Log.d(TAG,"запросDayGR= " + sql);
  db = DatabaseHelper.getInstance(this);
  cursor = db.executeQuery(sql);

  if (cursor != null && cursor.getCount() > 0) {
   if (cursor.moveToFirst()) {
    try {
     tmp_text_vis_years = cursor.getString(cursor
             .getColumnIndex("tropari_kondaki_day"));
    } catch (NumberFormatException e) {
     // Log.d(TAG, "ERROR=" + e.toString());
    }
   }
  }

  if(tmp_text_vis_years.length()>0){
   tmp_text_vis_years = tmp_text_vis_years.replace("***\r\n\r\n", "###");

   prayers_final=null;

   int i = tmp_text_vis_years.indexOf("###");
   if (i != -1){
    prayers_final = tmp_text_vis_years.split("###");
    if(id_tk==100) {
     text=fullTroparKondakText();
    }
    else {
     text=prayers_final[id_tk];
     text=createTitlePrayers(text);
    }
   }else {
    //prayers_final = new String[1];
    //prayers_final[0] = tmp_text_vis_years;
    text=tmp_text_vis_years;
    text=createTitlePrayers(text);
   }

   text=replace_tk(text);
   //prayers_title=null;
   //createTroparKondakLink();
   //createTitlePrayers();
   //replace_tk();
   //saveTextTkPreferences();
  }
  return text;
 }

 public String fullTroparKondakText(){
  String full_text="";
  for(int i=0;i<prayers_final.length;i++){
   full_text=full_text+createTitlePrayers(prayers_final[i])+"<br>";
  }
  return full_text;
 }

 public void newPositionPrayers(){//******
  if(prayers.length>1){
   String prayers_new_position[]=new String[prayers.length];
   prayers_new_position=(String[]) prayers.clone();
   prayers[0]=prayers_new_position[prayers_new_position.length-1];
   for(int i=1;i<prayers.length;i++){
    prayers[i]=prayers_new_position[i-1];
   }
  }
 }//*****

 public void addPrayersDay() {

  // день недели (воскресенье - 1; субота - 7)
  int day_week = cal_tk.getDayWeek();
  switch (day_week) {
   case 1:
    // воскресенье

    String sedmitsa = "";
    sql = "SELECT _id, r" + year_tk + " FROM data_calendar WHERE month="
            + month_tk + " AND day=" + data_tk + ";";

    db = DatabaseHelper.getInstance(this);
    cursor = db.executeQuery(sql);

    if (cursor != null) {
     if (cursor.moveToFirst()) {
      try {

       sedmitsa = cursor.getString(cursor.getColumnIndex("r"
               + year_tk));

      } catch (NumberFormatException e) {
       // Log.d(TAG, "ERROR=" + e.toString());
      }
     }
    }

    if (!sedmitsa.equals("#") && sedmitsa.indexOf("Глас") != -1) {
     if (sedmitsa.indexOf("Глас 1") != -1)
      sql = "select text from tropari_kondaki_day_sedmits where _id=7";
     if (sedmitsa.indexOf("Глас 2") != -1)
      sql = "select text from tropari_kondaki_day_sedmits where _id=8";
     if (sedmitsa.indexOf("Глас 3") != -1)
      sql = "select text from tropari_kondaki_day_sedmits where _id=9";
     if (sedmitsa.indexOf("Глас 4") != -1)
      sql = "select text from tropari_kondaki_day_sedmits where _id=10";
     if (sedmitsa.indexOf("Глас 5") != -1)
      sql = "select text from tropari_kondaki_day_sedmits where _id=11";
     if (sedmitsa.indexOf("Глас 6") != -1)
      sql = "select text from tropari_kondaki_day_sedmits where _id=12";
     if (sedmitsa.indexOf("Глас 7") != -1)
      sql = "select text from tropari_kondaki_day_sedmits where _id=13";
     if (sedmitsa.indexOf("Глас 8") != -1)
      sql = "select text from tropari_kondaki_day_sedmits where _id=14";
    } else {
     prayers_day_count = 0;
    }
    break;
   case 2:
    // пн
    sql = "select text from tropari_kondaki_day_sedmits where _id=1";
    break;
   case 3:
    // вт
    sql = "select text from tropari_kondaki_day_sedmits where _id=2";
    break;
   case 4:
    // ср
    sql = "select text from tropari_kondaki_day_sedmits where _id=3";
    break;
   case 5:
    // чт
    sql = "select text from tropari_kondaki_day_sedmits where _id=4";
    break;
   case 6:
    // пт
    sql = "select text from tropari_kondaki_day_sedmits where _id=5";
    break;
   case 7:
    // сб
    sql = "select text from tropari_kondaki_day_sedmits where _id=6";
    break;

   default:
    break;
  }
  if (prayers_day_count != 0) {
   db = DatabaseHelper.getInstance(this);
   cursor = db.executeQuery(sql);

   if (cursor != null) {
    if (cursor.moveToFirst()) {
     try {

      prayers_day = cursor.getString(cursor
              .getColumnIndex("text"));

     } catch (NumberFormatException e) {
      // Log.d(TAG, "ERROR=" + e.toString());
     }
    }
   }
  }
 }

 public void addPrayers(int count) {
  int l = unmovable_holiday_index.size();
  int index = 0;
  for (int i = 0; i < l; i++) {
   index = Integer.parseInt(unmovable_holiday_index.get(i));
   prayers_tmp[index - 1] = unmovable_holiday_text.get(i);
  }

  index = 0;
  for (int i = 0; i < prayers_tmp.length; i++) {
   if (prayers_tmp[i] == null) {
    prayers_tmp[i] = mas_date_calendar[index];
    index++;
    if (index == mas_date_calendar.length)
     break;
   }
  }
  index = 0;
  for (int i = 0; i < prayers_tmp.length; i++) {
   index = prayers_tmp[i].indexOf("***");
   if (index != -1) {
    prayers_tmp[i] = prayers_tmp[i].replace("***\r\n\r\n", "###");
    String[] result = prayers_tmp[i].split("###");
    for (int j = 0; j < result.length; j++) {
     arr_prayers_tmp.add(result[j]);
    }
   } else {
    arr_prayers_tmp.add(prayers_tmp[i]);
   }
  }

  prayers = (String[]) (arr_prayers_tmp
          .toArray(new String[arr_prayers_tmp.size()])).clone();
 }

 public void sortLists() {
  int index = 0;
  for (int i = 0; i < 14; i++) {
   index = unmovable_holiday_index_tmp.indexOf(Integer.toString(i));
   if (index != -1) {
    unmovable_holiday_index.add(unmovable_holiday_index_tmp
            .get(index));
    unmovable_holiday_text.add(unmovable_holiday_text_tmp
            .get(index));
   }
  }
 }

 public String replace_tk(String text) {

  String tmp="";

  tmp = text.replace("Тропарь", "<FONT COLOR=#aa2c2c>Тропарь</FONT>");
  tmp = tmp.replace("Ин тропарь", "<FONT COLOR=#aa2c2c>Ин тропарь</FONT>");
  tmp = tmp.replace("Иной тропарь", "<FONT COLOR=#aa2c2c>Ин тропарь</FONT>");

  tmp = tmp.replace("Кондак", "<FONT COLOR=#aa2c2c>Кондак</FONT>");
  tmp = tmp.replace("Ин кондак", "<FONT COLOR=#aa2c2c>Ин кондак</FONT>");
  tmp = tmp.replace("Иной кондак", "<FONT COLOR=#aa2c2c>Ин кондак</FONT>");

  tmp = tmp.replace("Величание", "<FONT COLOR=#aa2c2c>Величание</FONT>");
  tmp = tmp.replace("Величания", "<FONT COLOR=#aa2c2c>Величания</FONT>");
  tmp = tmp.replace("Ин величание", "<FONT COLOR=#aa2c2c>Ин величание</FONT>");

  tmp = tmp.replace("Вместо же Трисвятаго",
          "<FONT COLOR=#aa2c2c>Вместо же Трисвятаго</FONT>");

  tmp = tmp.replace("Задостойник", "<FONT COLOR=#aa2c2c>Задостойник</FONT>");

  tmp = tmp.replace("Стихира", "<FONT COLOR=#aa2c2c>Стихира</FONT>");

  tmp = tmp.replace("Ирмос", "<FONT COLOR=#aa2c2c>Ирмос</FONT>");

  tmp = tmp.replace("Ипакои", "<FONT COLOR=#aa2c2c>Ипакои</FONT>");

  tmp = tmp.replace("Богородичен", "<FONT COLOR=#aa2c2c>Богородичен</FONT>");

  tmp = tmp.replace("Богородичен отпустительный",
          "<FONT COLOR=#aa2c2c>Богородичен отпустительный</FONT>");

  tmp = tmp.replace("Богородичен Догматик",
          "<FONT COLOR=#aa2c2c>Богородичен Догматик</FONT>");

  tmp = tmp.replace("Псалом 136","<FONT COLOR=#aa2c2c>Псалом 136</FONT>");
  tmp = tmp.replace("По 50-м псалме","<FONT COLOR=#aa2c2c>По 50-м псалме</FONT>");
  return tmp;

 }

 public String createTitlePrayers(String text) {

  String prayers_title = "";
  int end = 0;
  end = text.indexOf("\r\n");
  prayers_title = text.substring(0, end);
  prayers_title = text.replace(prayers_title, ("<FONT COLOR=#aa2c2c><b>"+ prayers_title + "</b></FONT>"));

  return prayers_title;
 }
}