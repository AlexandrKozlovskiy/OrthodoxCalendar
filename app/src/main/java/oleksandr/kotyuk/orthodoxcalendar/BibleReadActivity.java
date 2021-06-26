package oleksandr.kotyuk.orthodoxcalendar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import oleksandr.kotyuk.orthodoxcalendar.MyView;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.fragments.PageFragmentViewPagerBibleRead;

public class BibleReadActivity extends AppCompatActivity {

private DatabaseHelper db;
Cursor cursor;
String sql = "";

int intent_id_bible = 1;
public static int intent_line_text_bible_book=0;

MyView tvTitle;
MyView tvText;
float size_default_MyView = 0;
float size = 0;

MenuItem menu_item1;
MenuItem menu_item2;

static final String TAG = "my_log";

int PAGE_COUNT = 1;
//static int ID_BIBLE=1;
int active_position_bible=0;
public static int start_position_bible=-1;
int position=0;

ViewPager pager;
PagerAdapter pagerAdapter;
  
boolean description_style=false;

boolean save_page=true;

@Override
protected void onCreate(Bundle savedInstanceState) {
 // TODO Auto-generated method stub
 super.onCreate(savedInstanceState);

 ActionBar actionBar = getSupportActionBar();
 // Enabling Up / Back navigation
 actionBar.setDisplayHomeAsUpEnabled(true);

 setContentView(R.layout.activity_bible_read);

 // получаем ID выбраного раздела книги
 Intent intent = getIntent();
 intent_id_bible = intent.getIntExtra("id", 1);
 intent_line_text_bible_book = intent.getIntExtra("line_text_bible_book", -1);
save_page=intent.getBooleanExtra("save_page", true);
 //select id_bible, COUNT(_id) from bible_book where id_bible=(select id_bible from bible_book where _id=52)
 sql = "select COUNT(_id) as count_rows from bible_book where id_bible=(select id_bible from bible_book where _id=" + intent_id_bible + ");";
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);
 if (cursor != null) {
 if (cursor.moveToFirst()) {
  try {
  //ID_BIBLE = cursor.getInt(cursor.getColumnIndex("id_bible"));
  PAGE_COUNT = cursor.getInt(cursor
   .getColumnIndex("count_rows"));
  } catch (NumberFormatException e) {
  // Log.d(TAG, "ERROR=" + e.toString());
  }
 }
 }
 sql = "select chapter_id from bible_book where _id=" + intent_id_bible;
 db = DatabaseHelper.getInstance(this);
 cursor = db.executeQuery(sql);
 if (cursor != null) {
 if (cursor.moveToFirst()) {
  try {
  //ID_BIBLE = cursor.getInt(cursor.getColumnIndex("id_bible"));
  active_position_bible = cursor.getInt(cursor
   .getColumnIndex("chapter_id"));
  position=active_position_bible-1;
  } catch (NumberFormatException e) {
  // Log.d(TAG, "ERROR=" + e.toString());
  }
 }
 }
 start_position_bible=intent_id_bible-active_position_bible+1;
 pager = (ViewPager) findViewById(R.id.pagerBibleRead);
    pagerAdapter = new MyFragmentPagerAdapterBible(getSupportFragmentManager());
    pager.setAdapter(pagerAdapter);
    pager.setCurrentItem(active_position_bible-1);
    pager.setOnPageChangeListener(new OnPageChangeListener() {
 
 @Override
 public void onPageSelected(int arg0) {
  // TODO Auto-generated method stub
  Log.d(TAG, "onPageSelected, position = " + arg0);
  position=arg0;
}
 
 @Override
 public void onPageScrolled(int arg0, float arg1, int arg2) {
  // TODO Auto-generated method stub
  Log.d(TAG, "onPageScrolled, arg0 = " + arg0 + "arg1 = " + arg1 + "arg2 = " + arg2);
 }
 
 @Override
 public void onPageScrollStateChanged(int arg0) {
  // TODO Auto-generated method stub
  Log.d(TAG, "onPageScrollStateChanged, position = " + arg0);
 }
 });

}

private class MyFragmentPagerAdapterBible extends FragmentStatePagerAdapter {

    public MyFragmentPagerAdapterBible(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
return PageFragmentViewPagerBibleRead.newInstance(position);
    }

    @Override
    public int getCount() {
      return PAGE_COUNT;
    }

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
 // Log.d(LOG_TAG, "onRestoreInstanceState");
 super.onRestoreInstanceState(savedInstanceState);
 // Log.d(LOG_TAG, "onRestoreInstanceState");
}

// позволяет сохранить данные
protected void onSaveInstanceState(Bundle outState) {
 // Log.d(LOG_TAG, "onSaveInstanceState");
 super.onSaveInstanceState(outState);
 // Log.d(LOG_TAG, "onSaveInstanceState");
}

@Override
protected void onDestroy() {
 // TODO Auto-generated method stub
 super.onDestroy();
 intent_id_bible = 1;
 if (cursor != null)
 cursor.close();
 if (db != null)
 db.closeConnecion();
}

//сохраняем данные страницы для возобновления чтений
public void SavePageReadBible(){
 
 int intent_id_number=1;
 int id_bible_book=start_position_bible+position;
 if(id_bible_book>0 && id_bible_book<1362){
 if(id_bible_book<1102) intent_id_number=1;
 else intent_id_number=2;
 }else{
 id_bible_book=1;
 intent_id_number=1;
 }
 
 PreferencesActivity.MyPreferenceFragment.WriteInt(this, "pref_last_read_bible_id", intent_id_number);
 PreferencesActivity.MyPreferenceFragment.WriteInt(this, "pref_last_read_bible_id_book", id_bible_book);
 PreferencesActivity.MyPreferenceFragment.WriteInt(this, "pref_last_read_bible_id_line_text", 1);
}

@Override
public void onBackPressed() {
 // TODO Auto-generated method stub
 // cursor.close();
 super.onBackPressed();
 if(save_page) SavePageReadBible();
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
 
 tvTitle = (MyView) this
 .findViewById(R.id.tvPageBibleRead);
 
 size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_bible_text_gr_size", 0);

 size = tvTitle.getTextSize();
 if(size<120){
  //size = size + 3;
  //tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
  
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_bible_text_gr_size", size_default_MyView + 3);
  
  pagerAdapter = new MyFragmentPagerAdapterBible(getSupportFragmentManager());
     pager.setAdapter(pagerAdapter);
     pager.setCurrentItem(position);
  
 }else Toast.makeText(this, "Размер шрифта максимальный!!!", Toast.LENGTH_SHORT)
 .show();
 return true;
 case R.id.item2_prayers_menu:
 // уменьшаем размер шрифта
 tvTitle = (MyView) this
 .findViewById(R.id.tvPageBibleRead);
 
 size_default_MyView = PreferencesActivity.MyPreferenceFragment
   .ReadFloat(this, "pref_bible_text_gr_size", 0);
 size = tvTitle.getTextSize();
 if(size>7){
  //size = size - 3;
  //tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
  
  PreferencesActivity.MyPreferenceFragment.WriteFloat(this,
   "pref_bible_text_gr_size", size_default_MyView - 3);
  
  pagerAdapter = new MyFragmentPagerAdapterBible(getSupportFragmentManager());
     pager.setAdapter(pagerAdapter);
     pager.setCurrentItem(position);
  
 }else Toast.makeText(this, "Размер шрифта минимальный!!!", Toast.LENGTH_SHORT)
 .show();
 return true;
 case R.id.item3_prayers_menu:
 description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(this, "pref_description_style4", false);
 if(!description_style){
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style4", true);
 }else{
  PreferencesActivity.MyPreferenceFragment.WriteBoolean(this, "pref_description_style4", false);
 }
 pagerAdapter = new MyFragmentPagerAdapterBible(getSupportFragmentManager());
     pager.setAdapter(pagerAdapter);
     pager.setCurrentItem(position);
 return true;
 default:
 return super.onOptionsItemSelected(item);
 }
}

}
