package oleksandr.kotyuk.orthodoxcalendar;

import java.util.ArrayList;

import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.fragments.PageFragmentViewPagerPsalturRead;
import androidx.appcompat.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import oleksandr.kotyuk.orthodoxcalendar.MyView;
import android.widget.Toast;

public class PsalturActivity extends MenuActivity {

private DatabaseHelper db;
Cursor cursor;
String sql = "";

int intent_id_psalm = 1;

float size_default_MyView = 0;
float size = 0;

static final String TAG = "my_log";

static final int PAGE_COUNT = 183;
int position=0;

ViewPager pager;
PagerAdapter pagerAdapter;

MyView tvText;

String prayers_language;
boolean description_style=false;
ArrayList <String> arr_bookmark;
int arr_bookmark_id=-1;
int prayers_id;

@Override
protected void onCreate(Bundle savedInstanceState) {
 // TODO Auto-generated method stub
 super.onCreate(savedInstanceState);
 
 ActionBar actionBar = getSupportActionBar();
 // Enabling Up / Back navigation
 actionBar.setDisplayHomeAsUpEnabled(true);

 setContentView(R.layout.activity_psaltur_read);
 
 Intent intent = getIntent();
 int psalm_id = intent.getIntExtra("psalm_id", 1);
 boolean last_read_psaltur=intent.getBooleanExtra("last_read_psaltur", false);
 
 pager = (ViewPager) findViewById(R.id.pagerPsalturRead);
    pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
    pager.setAdapter(pagerAdapter);
    if(!last_read_psaltur){
     position=psalm_id-1;
     pager.setCurrentItem(psalm_id-1);
    }else{
    position=PreferencesActivity.MyPreferenceFragment.ReadInt(this, "pref_last_read_psaltur_id", 0);
    pager.setCurrentItem(position);
    
    }
    
    prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_prayers_language", "ru");
    if (prayers_language.equals("ru")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_ru");
 } 
 if (prayers_language.equals("cs")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_cs");
 }
 prayers_id=position+1;
 SelectBookmarksOnOff();

    pager.setOnPageChangeListener(new OnPageChangeListener() {
 
 @Override
 public void onPageSelected(int arg0) {
  // TODO Auto-generated method stub
  position=arg0;
  prayers_id=position+1;
  SelectBookmarksOnOff();
  invalidateOptionsMenu();
 }
 
 @Override
 public void onPageScrolled(int arg0, float arg1, int arg2) {
  // TODO Auto-generated method stub
  
 }
 
 @Override
 public void onPageScrollStateChanged(int arg0) {
  // TODO Auto-generated method stub
  
 }
 });
 
}

private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    public MyFragmentPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return PageFragmentViewPagerPsalturRead.newInstance(position);
    }

    @Override
    public int getCount() {
      return PAGE_COUNT;
    }

  }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
 // TODO Auto-generated method stub
 prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_prayers_language", "ru");
 if (prayers_language.equals("ru")) {
 size_default_MyView = PreferencesActivity.MyPreferenceFragment
  .ReadFloat(this, "pref_prayers_text_size_ru", 0);
 } 
 if (prayers_language.equals("cs")) {
 size_default_MyView = PreferencesActivity.MyPreferenceFragment
  .ReadFloat(this, "pref_prayers_text_size_cs", 0);
 }
 tvText = (MyView) this.findViewById(R.id.MyViewDescription1);

 size = tvText.getTextSize();
 
 switch (item.getItemId()) {
 case android.R.id.home:
 // NavUtils.navigateUpFromSameTask(this);
 onBackPressed();
 return true;
 case R.id.item1_prayers_menu:
 // увеличиваем размер шрифта
 if(size<120){
  //size = size + 3;
  //tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
  if (prayers_language.equals("ru")) {
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_prayers_text_size_ru", size_default_MyView + 3);
  } else {
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_prayers_text_size_cs", size_default_MyView + 3);
  }
  
  pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
     pager.setAdapter(pagerAdapter);
     pager.setCurrentItem(position);
  
 }else Toast.makeText(this, "Размер шрифта максимальный!!!", Toast.LENGTH_SHORT)
 .show();
 return true;
 case R.id.item2_prayers_menu:
 // уменьшаем размер шрифта
 size = tvText.getTextSize();
 if(size>7){
  //size = size - 3;
  //tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
  
  if (prayers_language.equals("ru")) {
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_prayers_text_size_ru", size_default_MyView - 3);
  } else {
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_prayers_text_size_cs", size_default_MyView - 3);
  }
  
  pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
     pager.setAdapter(pagerAdapter);
     pager.setCurrentItem(position);
  
 }else Toast.makeText(this, "Размер шрифта минимальный!!!", Toast.LENGTH_SHORT)
 .show();
 return true;
 case R.id.item3_prayers_menu:
 description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style1", false);
 if(!description_style){
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style1", true);
 }else{
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style1", false);
 }
 pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
     pager.setAdapter(pagerAdapter);
     pager.setCurrentItem(position);
 return true;
 case R.id.item4_prayers_menu:
 if(flag_bookmarks_on){
  flag_bookmarks_on=false;
  BookmarksDelete();
 }
 else {
  flag_bookmarks_on=true;
BookmarksAdd();
 }
 invalidateOptionsMenu();
 return true;
 default:
 return super.onOptionsItemSelected(item);
 }
}

public void SelectBookmarksOnOff(){
 if(arr_bookmark!=null){
 for(int i=0; i<arr_bookmark.size();i++){
  String [] text_line_bookmark=arr_bookmark.get(i).split("###");
     int id_prayers_group_bookmarks=Integer.parseInt(text_line_bookmark[0]);
     int id_prayers_bookmarks=Integer.parseInt(text_line_bookmark[1]);
     if(id_prayers_group_bookmarks==3 && id_prayers_bookmarks==prayers_id){
      flag_bookmarks_on=true;
      arr_bookmark_id=i;
      return;
     }
 }
 
 flag_bookmarks_on=false;
 }
}

public void BookmarksAdd(){
 String text="";
 if (prayers_language.equals("ru")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_ru");
 if(arr_bookmark==null){
  arr_bookmark=new ArrayList<String>();
 }
 
 cursor = null;
 sql = "SELECT psalom_name_ru FROM psaltur where _id="+prayers_id;
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  do {
   try {
    text=cursor.getString(cursor.getColumnIndex("psalom_name_ru"));
   } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
   }
  } while (cursor.moveToNext());

  }
 }
cursor.close();
  arr_bookmark.add("3###"+prayers_id+"###"+text);
 arr_bookmark_id=arr_bookmark.size()-1;
 PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "new_prayers_bookmarks_ru", arr_bookmark);
 } 
 if (prayers_language.equals("cs")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_cs");
 if(arr_bookmark==null){
  arr_bookmark=new ArrayList<String>();
 }
 
 cursor = null;
 sql = "SELECT psalom_name_cs FROM psaltur where _id="+prayers_id;
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  do {
   try {
    text=cursor.getString(cursor.getColumnIndex("psalom_name_cs"));
   } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
   }
  } while (cursor.moveToNext());
  }
cursor.close();
 }
 arr_bookmark.add("3###"+prayers_id+"###"+text);
 arr_bookmark_id=arr_bookmark.size()-1;
 PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "new_prayers_bookmarks_cs", arr_bookmark);
 }
 
 
}
public void BookmarksDelete(){
 
 if (prayers_language.equals("ru")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_ru");
 arr_bookmark.remove(arr_bookmark_id);
 PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "new_prayers_bookmarks_ru", arr_bookmark);
 } 
 if (prayers_language.equals("cs")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_cs");
 arr_bookmark.remove(arr_bookmark_id);
 PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "new_prayers_bookmarks_cs", arr_bookmark);
 }
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
 // Inflate the menu; this adds items to the action bar if it is present.
 //Log.d(TAG, "onCreateOptionsMenu");
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
/*if (id_description == 5) {
  menu_item1.setVisible(true);
  menu_item2.setVisible(true);
 } else {
  menu_item1.setVisible(false);
  menu_item2.setVisible(false);
 }*/
 return super.onPrepareOptionsMenu(menu);
 }

@Override
  protected void onStart() {
    super.onStart();

 if(!PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_rotate_screen_setting", true)){
  //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
 
 if (Build.VERSION.SDK_INT >= 18)
 {
  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
 }else{
 
  int orientation = getResources().getConfiguration().orientation;
     int rotation = getWindowManager().getDefaultDisplay().getOrientation();
     
  int SCREEN_ORIENTATION_REVERSE_LANDSCAPE = 8;
     int SCREEN_ORIENTATION_REVERSE_PORTRAIT = 9;
     
     if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
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
  /*int orientation = getResources().getConfiguration().orientation;
  int rotation = getWindowManager().getDefaultDisplay().getRotation();
  
  if (orientation == Configuration.ORIENTATION_PORTRAIT)
  {
  if (rotation == Surface.ROTATION_180)
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
  else
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }
  else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
  {
  if (rotation == Surface.ROTATION_180)
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
  else
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
  }*/
 }
 }
  }

// позволяет востановить данные
protected void onRestoreInstanceState(Bundle savedInstanceState) {
 //Log.d(TAG, "onRestoreInstanceState");
 super.onRestoreInstanceState(savedInstanceState);
 //Log.d(TAG, "onRestoreInstanceState");
}

// позволяет сохранить данные
protected void onSaveInstanceState(Bundle outState) {
 //Log.d(TAG, "onSaveInstanceState");
 super.onSaveInstanceState(outState);
 //Log.d(TAG, "onSaveInstanceState");
}

@Override
public void onBackPressed() {
 // TODO Auto-generated method stub
 // cursor.close();
 super.onBackPressed();
 
 SavePageReadPsaltur();
}

//сохраняем данные страницы для возобновления чтений
public void SavePageReadPsaltur(){
 
 PreferencesActivity.MyPreferenceFragment.WriteInt(this, "pref_last_read_psaltur_id", position);
}
}
