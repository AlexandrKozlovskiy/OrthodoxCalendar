package oleksandr.kotyuk.orthodoxcalendar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import oleksandr.kotyuk.orthodoxcalendar.adapters.CustomDrawerAdapter;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.fragments.DatePickerFragment1;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentAbout;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentBible;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentDirectory;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentDirectory2;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentEaster;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentHelp;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentHolidays;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentPosts;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentPrayers;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentPrayersBookmarks2;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentPrayersTroparKondakDay;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentSouls;
import oleksandr.kotyuk.orthodoxcalendar.fragments.FragmentViewPager;
import oleksandr.kotyuk.orthodoxcalendar.fragments.LastReadPsalturDialogFragment;
import oleksandr.kotyuk.orthodoxcalendar.fragments.UpdateNewsDialogFragment;
import oleksandr.kotyuk.orthodoxcalendar.models.DrawerItem;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements
 ActionBar.OnNavigationListener, ActionBar.TabListener {

public final static String NUMBER_PROGRAM = "number_program";
public final static String WIDGET_PREF = "widget_pref";
public final static String requestedPermissionsKey="requested_permissions_key";
//final int DIALOG_UPDATE_NEWS = 1;

final String LOG_TAG = "myLogs";

private DrawerLayout mDrawerLayout;
private ListView mDrawerList;
private ActionBarDrawerToggle mDrawerToggle;

private CharSequence mDrawerTitle;
private CharSequence mTitle;
CustomDrawerAdapter adapter;


List<DrawerItem> dataList;

MenuItem menu_item1;
MenuItem menu_item2;
MenuItem menu_item3;
MenuItem menu_item4;
MenuItem menu_item5;
MenuItem menu_item6;
MenuItem menu_item7;
MenuItem menu_item8;
MenuItem menu_item9;
MenuItem menu_item10;

static int list_position_st = -1;
static boolean menu_activ_st = true;
static boolean menu_activ_psaltur_st = false;
static boolean menu_activ_bookmark_st = false;
static boolean menu_activ_prayers_st = false;
static boolean menu_activ_prayers_language_ru_st = false;
static boolean menu_activ_prayers_language_cs_st = false;
static boolean menu_activ_prayers_fonts_st = false;
static boolean menu_flag_group_st=false;
static boolean menu_flag_tab_st=false;
static boolean menu_activ_psaltur_view1=false;
static boolean menu_activ_psaltur_view2=false;
static int menu_flag_tab_index_st=-1;
public static int list_navigation_item = -1;

private ActionBar bar;
private String[] data_list;


DatabaseHelper db;

MyCalendar cal = MyCalendar.getInstance();

private long back_pressed;

Boolean Noti_flag;
boolean notifi_date_app_start_flag=true;

@Override
protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);
 //старт уведомления
 /*Noti_flag = PreferencesActivity.MyPreferenceFragment.ReadBoolean(getApplicationContext(), "pref_notifi_setting", true);
 Log.d(LOG_TAG, "Noti_flag="+Noti_flag.toString());
 if(Noti_flag){
MyScheduledReceiver.setAlarm(this);
 }*/

 data_list = new String[] { getString(R.string.menu_list1),
  getString(R.string.menu_list2) };

 bar = getSupportActionBar();
 // getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
 bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

 ArrayAdapter<String> list_adapter = new ArrayAdapter<String>(this,
  R.layout.my_simple_spinner_item, data_list);
 list_adapter
  .setDropDownViewResource(R.layout.my_simple_spinner_dropdown_item);
 bar.setListNavigationCallbacks(list_adapter, this);

 db = DatabaseHelper.getInstance(getApplicationContext());

 // инициализация бокового меню
 dataList = new ArrayList<DrawerItem>();
mTitle = mDrawerTitle = getTitle();
 mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
 mDrawerList = (ListView) findViewById(R.id.left_drawer);

 mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
  GravityCompat.START);

 // Add Drawer Item to dataList

 dataList.add(new DrawerItem(getString(R.string.main_options)));// добавляємо заголовок в
       // список
 dataList.add(new DrawerItem(getString(R.string.calendar),
  R.drawable.ic_action_crucifixion));
 dataList.add(new DrawerItem(getString(R.string.prayers), R.drawable.ic_action_crucifixion));
 dataList.add(new DrawerItem(getString(R.string.bible), R.drawable.ic_action_crucifixion));
 dataList.add(new DrawerItem(getString(R.string.easter),
  R.drawable.ic_action_crucifixion));
 dataList.add(new DrawerItem(getString(R.string.holidays),
  R.drawable.ic_action_crucifixion));
 dataList.add(new DrawerItem(getString(R.string.souls),
  R.drawable.ic_action_crucifixion));
 dataList.add(new DrawerItem(getString(R.string.posts), R.drawable.ic_action_crucifixion));
 dataList.add(new DrawerItem(getString(R.string.help), R.drawable.ic_action_crucifixion));

 // добавляємо заголовок в список
 dataList.add(new DrawerItem(getString(R.string.other_options)));
  dataList.add(new DrawerItem(getString(R.string.settings), R.drawable.ic_action_settings));
 dataList.add(new DrawerItem(getString(R.string.help_to_project), R.drawable.ic_action_help));
 dataList.add(new DrawerItem(getString(R.string.rate_project), R.drawable.ic_action_rating));
 dataList.add(new DrawerItem(getString(R.string.about), R.drawable.ic_action_about));

 adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
  dataList);

 mDrawerList.setAdapter(adapter);

 mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

 // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 // getSupportActionBar().setHomeButtonEnabled(true);
 bar.setDisplayHomeAsUpEnabled(true);
 bar.setHomeButtonEnabled(true);

 bar.setLogo(R.drawable.ic_launcher);
 bar.setDisplayUseLogoEnabled(true);


 mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.drawer_open,
  R.string.drawer_close) {

 public void onDrawerClosed(View view) {
  // getSupportActionBar().setTitle(mTitle);
  // getSupportActionBar().setTitle(mTitle);
  bar.setTitle(mTitle);
  invalidateOptionsMenu(); // вызывает метод
     // onPrepareOptionsMenu()
  if(getDrawerToggleDelegate() !=null) getDrawerToggleDelegate().setActionBarDescription(R.string.drawer_open);
 }

 public void onDrawerOpened(View drawerView) {
  // getSupportActionBar().setTitle(mDrawerTitle);
  getSupportActionBar().setTitle(mDrawerTitle);
 if(getDrawerToggleDelegate() !=null) getDrawerToggleDelegate().setActionBarDescription(R.string.drawer_close);
invalidateOptionsMenu(); // creates call to
// onPrepareOptionsMenu()

}
};
mDrawerToggle.setDrawerIndicatorEnabled(true);
 mDrawerLayout.setDrawerListener(mDrawerToggle);

 ////////////////////////////////////////////////////
 /*if (savedInstanceState == null) {

 if (dataList.get(0).getTitle() != null) {
  SelectItem(1);
 } else {
  SelectItem(0);
 }
 }*/
 /////////////////////////////////////////////////
 if (savedInstanceState == null) {
 list_position_st=-1;
 if (dataList.get(0).getTitle() != null) {
  SelectItem(1);
 } else {
  SelectItem(0);
 }
 }else{
 int gg=list_position_st;
 list_position_st=-1;
 SelectItem(gg);
 }
 //////////////////////////////////////////////

 SharedPreferences sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
 int num_prog = sp.getInt(NUMBER_PROGRAM, 0);
 int correct_num_prog=Integer.parseInt(BuildConfig.VERSION_NAME.replace(".",""));
if(correct_num_prog>num_prog) {
 //открываем диалоговое окно
 UpdateNewsDialogFragment undf = new UpdateNewsDialogFragment();
undf.show(getSupportFragmentManager(), "dialog_update_news");
 Editor editor = sp.edit();
 editor.putInt(NUMBER_PROGRAM, correct_num_prog);
 editor.commit();
}
if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED &&!sp.getBoolean(requestedPermissionsKey,false) /*&&shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)*/) requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS},1);
}


 @Override
 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(grantResults!=null &&grantResults.length>0) {
     Editor e = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE).edit();
     if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
      //startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getApplicationContext().getPackageName())));
      alert(getString(R.string.permission_error), getString(R.string.dialog_alert_title));
     }
     //Если разрешение было дано,то мы должны поместить значение false,чтобы,если разрешение будет отозвано каким-либо образом,этот диалог появился у нас снова один раз.
     e.putBoolean(requestedPermissionsKey,grantResults[0] != PackageManager.PERMISSION_GRANTED).commit();
    }
 }

 void complain(String message) {
 alert("Ошибка",message);
}

void alert(String message,String title) {
 AlertDialog.Builder bld = new AlertDialog.Builder(this);
 bld.setTitle(title);
 bld.setMessage(message);
 bld.setNeutralButton("OK", null);
 //Log.d(TAG, "Showing alert dialog: " + message);
 bld.create().show();
}

// действия при нажати на кнопку
public void onClickButton(View v) {
 switch (v.getId()) {
 /*case R.id.button_help2:
  Intent intent = new Intent(Intent.ACTION_VIEW);
  intent.setData(Uri
   .parse("https://play.google.com/store/apps/details?id=oleksandr.kotyuk.orthodoxcalendarpaid"));
  startActivity(intent);
  break;*/
 case R.id.button_Bible1:
  Intent intent_bible1=new Intent(this, BibleListActivity.class);
  intent_bible1.putExtra("id", 1);
  startActivity(intent_bible1);
  break;
 case R.id.button_Bible2:
  Intent intent_bible2=new Intent(this, BibleListActivity.class);
  intent_bible2.putExtra("id", 2);
  startActivity(intent_bible2);
  break;
 case R.id.button_Bible3:
  Intent intent_bible3=new Intent(this, BibleListActivity.class);
  intent_bible3.putExtra("id", 3);
  startActivity(intent_bible3);
  break;
 case R.id.button_Bible4:
  int intent_id_number=PreferencesActivity.MyPreferenceFragment.ReadInt(this, "pref_last_read_bible_id", -1);
  int id_bible_book=PreferencesActivity.MyPreferenceFragment.ReadInt(this, "pref_last_read_bible_id_book", -1);
  int line_text_bible_book=PreferencesActivity.MyPreferenceFragment.ReadInt(this, "pref_last_read_bible_id_line_text", -1);
  if(intent_id_number!=-1 && id_bible_book!=-1 && line_text_bible_book!=-1){
  Intent intent_bible4=new Intent(this, BibleListActivity.class);
  intent_bible4.putExtra("id", intent_id_number);
  intent_bible4.putExtra("id_bible_book", id_bible_book);
  intent_bible4.putExtra("line_text_bible_book", line_text_bible_book);
  startActivity(intent_bible4);
  }else alert("Сохраненной страницы еще нет, поскольку Вы еще не читали!!!", "Внимание");
  break;
 case R.id.button_Bible5:
  Intent intent_bible5=new Intent(this, BibleSearchActivity.class);
  startActivity(intent_bible5);
  break;
 case R.id.button_Bible6:
  Intent intent_bible6=new Intent(this, DescriptionOtherActivity.class);
  intent_bible6.putExtra("description_id_bible_prayer_gospel", 173);
  startActivity(intent_bible6);
  break;
 }
 }
@Override
public boolean onCreateOptionsMenu(Menu menu) {
 // Inflate the menu; this adds items to the action bar if it is present.
 getMenuInflater().inflate(R.menu.main, menu);
 menu_item1 = (MenuItem) menu.findItem(R.id.item1);
 menu_item2 = (MenuItem) menu.findItem(R.id.item2);
 menu_item3 = (MenuItem) menu.findItem(R.id.item3);
 menu_item4 = (MenuItem) menu.findItem(R.id.item1_bookmarks_menu);
 menu_item5 = (MenuItem) menu.findItem(R.id.item5);
 menu_item6 = (MenuItem) menu.findItem(R.id.item6);
 menu_item7 = (MenuItem) menu.findItem(R.id.item7);
 menu_item8 = (MenuItem) menu.findItem(R.id.item8);
 menu_item9 = (MenuItem) menu.findItem(R.id.item9);
 menu_item10 = (MenuItem) menu.findItem(R.id.item10);
 return super.onCreateOptionsMenu(menu);
}

// вызывается каждый раз перед отображением меню
@Override
public boolean onPrepareOptionsMenu(Menu menu) {
 // TODO Auto-generated method stub
 menu_item1.setVisible(menu_activ_st);
 menu_item2.setVisible(menu_activ_st);
 menu_item3.setVisible(menu_activ_psaltur_st);
 //menu_item4.setVisible(menu_activ_bookmark);
 menu_item4.setVisible(false);
 menu_item5.setVisible(menu_activ_prayers_st);
 menu_item6.setVisible(menu_activ_prayers_language_ru_st);
 menu_item7.setVisible(menu_activ_prayers_language_cs_st);
 menu_item8.setVisible(menu_activ_prayers_fonts_st);
 menu_item9.setVisible(menu_activ_psaltur_view1);
 menu_item10.setVisible(menu_activ_psaltur_view2);
 return super.onPrepareOptionsMenu(menu);
}

// перерисовка меню/ActionBar
/*
 * @Override public void invalidateOptionsMenu() { // TODO Auto-generated
 * method stub super.invalidateOptionsMenu(); }
 */

@Override
public boolean onOptionsItemSelected(MenuItem item) {
 // The action bar home/up action should open or close the drawer.
 // ActionBarDrawerToggle will take care of this.
 if (mDrawerToggle.onOptionsItemSelected(item)) {
 return true;
 }

 String  prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_prayers_language", "ru");

 //ps-Псалтирь
 //ys-Псалтирь по усопшим
 String  psaltur_view = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_psaltur_view", "ps");

 // обработка кнопок ActionBar
 switch (item.getItemId()) {
 case R.id.item1:
 // выставляем на сегодня
 cal.setTodayDate();
 SelectItemDayMonth();
 return true;
 case R.id.item2:
 // окрываем Date Picker и выбираем дату
DatePickerFragment1 picker = new DatePickerFragment1();
picker.show(getSupportFragmentManager(), "datePicker");
 //Log.d(LOG_TAG, "MainActivity: Date Picker");
 return true;
 case R.id.item3:
 int position=PreferencesActivity.MyPreferenceFragment.ReadInt(this, "pref_last_read_psaltur_id", -1);
 if(position!=-1){
  Intent i = new Intent(this, PsalturActivity.class);
  i.putExtra("last_read_psaltur", true);
  startActivity(i);
 }else{
  LastReadPsalturDialogFragment last_read_dial = new LastReadPsalturDialogFragment();
  last_read_dial.show(getSupportFragmentManager(), "dialog_last_read_psaltur");
 }
 return true;
 case R.id.item1_bookmarks_menu:
 if (prayers_language.equals("ru")) {
  ArrayList <String> arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_ru");
  if(arr_bookmark!=null){
  arr_bookmark.clear();
  PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "new_prayers_bookmarks_ru", arr_bookmark);
  menu_activ_prayers_language_ru_st=true;
  menu_activ_prayers_language_cs_st=false;
  }
 }
 if (prayers_language.equals("cs")) {
  ArrayList <String> arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(this, "new_prayers_bookmarks_cs");
  if(arr_bookmark!=null){
  arr_bookmark.clear();
  PreferencesActivity.MyPreferenceFragment.putList(getApplicationContext(), "new_prayers_bookmarks_cs", arr_bookmark);
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=true;
  }
 }
  Fragment fragment = null;
  Bundle args = new Bundle();

  menu_activ_st = false;
  //menu_activ_akafist=false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=true;
  menu_activ_prayers_st=false;
  menu_activ_prayers_fonts_st=true;
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment = new FragmentPrayersBookmarks2();
  args.putString(FragmentPrayersBookmarks2.ITEM_NAME,
   dataList.get(2).getItemName());
  args.putInt(FragmentPrayersBookmarks2.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());

  fragment.setArguments(args);
  // FragmentManager frgManager = getFragmentManager();
  FragmentManager frgManager = getSupportFragmentManager();
  frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

  mDrawerList.setItemChecked(2, true);
  setTitle(dataList.get(2).getItemName());
  mDrawerLayout.closeDrawer(mDrawerList);
 return true;
 case R.id.item5:
 // открываем окно поиска
 Intent intent_prayers_search=new Intent(this, PrayersSearchActivity.class);
 startActivity(intent_prayers_search);
 return true;
 case R.id.item6:
 if(menu_flag_group_st && menu_flag_tab_st){
  PreferencesActivity.MyPreferenceFragment.WriteString(this,
   "pref_prayers_language", "cs");

  Fragment fragment2 = null;
  Bundle args2 = new Bundle();

  switch(menu_flag_tab_index_st){
  case 0:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=true;
  menu_activ_prayers_fonts_st=true;

  //menu_activ_prayers=true;
  menuActivPrayers();
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment2 = new FragmentPrayers();
  args2.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args2.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  case 1:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=true;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=true;
  menu_activ_prayers_fonts_st=true;

  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment2 = new FragmentPrayersBookmarks2();
  args2.putString(FragmentPrayersBookmarks2.ITEM_NAME,
   dataList.get(2).getItemName());
  args2.putInt(FragmentPrayersBookmarks2.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  case 2:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=true;
  menu_activ_prayers_fonts_st=true;

  //menu_activ_prayers=true;
  menuActivPrayers();
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment2 = new FragmentPrayers();
args2.putString("prayers_type","ak");
  args2.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args2.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  case 3:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=true;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=true;
  menu_activ_prayers_fonts_st=true;

  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment2 = new FragmentPrayers();
   args2.putString("prayers_type","kafisma");
  args2.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args2.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  default:

  break;
  }
  fragment2.setArguments(args2);
  // FragmentManager frgManager = getFragmentManager();
  FragmentManager frgManager2 = getSupportFragmentManager();
  frgManager2.beginTransaction()
   .replace(R.id.content_frame, fragment2).commit();

  mDrawerList.setItemChecked(2, true);
  setTitle(dataList.get(2).getItemName());
  mDrawerLayout.closeDrawer(mDrawerList);

 }
 return true;
 case R.id.item7:
 if(menu_flag_group_st && menu_flag_tab_st){
  PreferencesActivity.MyPreferenceFragment.WriteString(this,
   "pref_prayers_language", "ru");

  Fragment fragment3 = null;
  Bundle args3 = new Bundle();

  switch(menu_flag_tab_index_st){
  case 0:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=true;
  menu_activ_prayers_language_ru_st=true;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=true;

  //menu_activ_prayers=true;
  menuActivPrayers();
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment3 = new FragmentPrayers();
  args3.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args3.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  case 1:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=true;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=true;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=true;

  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment3 = new FragmentPrayersBookmarks2();
  args3.putString(FragmentPrayersBookmarks2.ITEM_NAME,
   dataList.get(2).getItemName());
  args3.putInt(FragmentPrayersBookmarks2.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  case 2:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=true;
  menu_activ_prayers_language_ru_st=true;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=true;

  //menu_activ_prayers=true;
  menuActivPrayers();
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment3 = new FragmentPrayers();
   args3.putString("prayers_type","ak");
  args3.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args3.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  case 3:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=true;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=true;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=true;

  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment3 = new FragmentPrayers();
  args3.putString("prayers_type","kafisma");
  args3.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args3.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  default:

  break;
  }
  fragment3.setArguments(args3);
  // FragmentManager frgManager = getFragmentManager();
  FragmentManager frgManager3 = getSupportFragmentManager();
  frgManager3.beginTransaction()
   .replace(R.id.content_frame, fragment3).commit();

  mDrawerList.setItemChecked(2, true);
  setTitle(dataList.get(2).getItemName());
  mDrawerLayout.closeDrawer(mDrawerList);

 }
 return true;
 case R.id.item8:
 if(menu_flag_group_st && menu_flag_tab_st){
  if (prayers_language.equals("ru")) {
  String prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(this,
   "pref_prayers_fonts_ru", "1");
  if(prayers_fonts_ru.equals("1")){
   PreferencesActivity.MyPreferenceFragment.WriteString(this,
    "pref_prayers_fonts_ru", "2");
  }

  if(prayers_fonts_ru.equals("2")){
   PreferencesActivity.MyPreferenceFragment.WriteString(this,
    "pref_prayers_fonts_ru", "3");
  }

  if(prayers_fonts_ru.equals("3")){
   PreferencesActivity.MyPreferenceFragment.WriteString(this,
    "pref_prayers_fonts_ru", "4");
  }

  if(prayers_fonts_ru.equals("4")){
   PreferencesActivity.MyPreferenceFragment.WriteString(this,
    "pref_prayers_fonts_ru", "5");
  }
  if(prayers_fonts_ru.equals("5")){
   PreferencesActivity.MyPreferenceFragment.WriteString(this,
    "pref_prayers_fonts_ru", "6");
  }
  if(prayers_fonts_ru.equals("6")){
   PreferencesActivity.MyPreferenceFragment.WriteString(this,
    "pref_prayers_fonts_ru", "7");
  }
  if(prayers_fonts_ru.equals("7")){
   PreferencesActivity.MyPreferenceFragment.WriteString(this,
    "pref_prayers_fonts_ru", "1");
  }
  }

  if (prayers_language.equals("cs")) {
  String prayers_fonts_cs=PreferencesActivity.MyPreferenceFragment.ReadString(this,
   "pref_prayers_fonts_cs", "1");
  if(prayers_fonts_cs.equals("1")){
   PreferencesActivity.MyPreferenceFragment.WriteString(this,
    "pref_prayers_fonts_cs", "2");
  }

  if(prayers_fonts_cs.equals("2")){
   PreferencesActivity.MyPreferenceFragment.WriteString(this,
    "pref_prayers_fonts_cs", "3");
  }

  if(prayers_fonts_cs.equals("3")){
   PreferencesActivity.MyPreferenceFragment.WriteString(this,
    "pref_prayers_fonts_cs", "1");
  }

  }

  Fragment fragment4 = null;
  Bundle args4 = new Bundle();

  switch(menu_flag_tab_index_st){
  case 0:
  /*menu_flag_tab=true;
  menu_activ = false;
  menu_activ_psaltur=false;
  menu_activ_bookmark=false;
  menu_activ_prayers=true;
  menu_activ_prayers_language_ru=true;
  menu_activ_prayers_language_cs=false;
  menu_activ_prayers_fonts=true;*/

  menuActivPrayers();
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment4 = new FragmentPrayers();
  args4.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args4.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  case 1:
  /*menu_flag_tab=true;
  menu_activ = false;
  menu_activ_psaltur=false;
  menu_activ_bookmark=true;
  menu_activ_prayers=false;
  menu_activ_prayers_language_ru=true;
  menu_activ_prayers_language_cs=false;
  menu_activ_prayers_fonts=true;*/

  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment4 = new FragmentPrayersBookmarks2();
  args4.putString(FragmentPrayersBookmarks2.ITEM_NAME,
   dataList.get(2).getItemName());
  args4.putInt(FragmentPrayersBookmarks2.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  case 2:
  /*menu_flag_tab=true;
  menu_activ = false;
  menu_activ_psaltur=false;
  menu_activ_bookmark=false;
  menu_activ_prayers=true;
  menu_activ_prayers_language_ru=true;
  menu_activ_prayers_language_cs=false;
  menu_activ_prayers_fonts=true;*/

  menuActivPrayers();
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment4 = new FragmentPrayers();
   args4.putString("prayers_type","ak");
  args4.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args4.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  case 3:
  /*menu_flag_tab=true;
  menu_activ = false;
  menu_activ_psaltur=true;
  menu_activ_bookmark=false;
  menu_activ_prayers=false;
  menu_activ_prayers_language_ru=true;
  menu_activ_prayers_language_cs=false;
  menu_activ_prayers_fonts=true;*/

  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment4 = new FragmentPrayers();
   args4.putString("prayers_type","kafisma");
  args4.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args4.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  default:

  break;
  }
  fragment4.setArguments(args4);
  // FragmentManager frgManager = getFragmentManager();
  FragmentManager frgManager4 = getSupportFragmentManager();
  frgManager4.beginTransaction()
   .replace(R.id.content_frame, fragment4).commit();

  mDrawerList.setItemChecked(2, true);
  setTitle(dataList.get(2).getItemName());
  mDrawerLayout.closeDrawer(mDrawerList);

 }
 return true;
 case R.id.item9:
 if(menu_flag_group_st && menu_flag_tab_st){
  if (psaltur_view.equals("ps")) {
  PreferencesActivity.MyPreferenceFragment.WriteString(this,
   "pref_psaltur_view", "ys");
  menu_activ_psaltur_view1=true;
  menu_activ_psaltur_view2=false;
  }
  if (psaltur_view.equals("ys")) {
  PreferencesActivity.MyPreferenceFragment.WriteString(this,
   "pref_psaltur_view", "ps");
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=true;
  }

  Fragment fragment5 = null;
  Bundle args5 = new Bundle();

  switch(menu_flag_tab_index_st){
  case 3:
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment5 = new FragmentPrayers();
   args5.putString("prayers_type","kafisma");
  args5.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args5.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  default:

  break;
  }
  fragment5.setArguments(args5);
  // FragmentManager frgManager = getFragmentManager();
  FragmentManager frgManager5 = getSupportFragmentManager();
  frgManager5.beginTransaction()
   .replace(R.id.content_frame, fragment5).commit();

  mDrawerList.setItemChecked(2, true);
  setTitle(dataList.get(2).getItemName());
  mDrawerLayout.closeDrawer(mDrawerList);
 }
 return true;

 case R.id.item10:
 if(menu_flag_group_st && menu_flag_tab_st){
  if (psaltur_view.equals("ps")) {
  PreferencesActivity.MyPreferenceFragment.WriteString(this,
   "pref_psaltur_view", "ys");
  menu_activ_psaltur_view1=true;
  menu_activ_psaltur_view2=false;
  }
  if (psaltur_view.equals("ys")) {
  PreferencesActivity.MyPreferenceFragment.WriteString(this,
   "pref_psaltur_view", "ps");
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=true;
  }

  Fragment fragment6 = null;
  Bundle args6 = new Bundle();

  switch(menu_flag_tab_index_st){
  case 3:
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment6 = new FragmentPrayers();
   args6.putString("prayers_type","kafisma");
  args6.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args6.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
  default:

  break;
  }
  fragment6.setArguments(args6);
  // FragmentManager frgManager = getFragmentManager();
  FragmentManager frgManager6 = getSupportFragmentManager();
  frgManager6.beginTransaction()
   .replace(R.id.content_frame, fragment6).commit();

  mDrawerList.setItemChecked(2, true);
  setTitle(dataList.get(2).getItemName());
  mDrawerLayout.closeDrawer(mDrawerList);
 }
 return true;

 default:
 return super.onOptionsItemSelected(item);

 }
 // return false;
}

// обновляем activity после смены даты
public void UpdateActivityDate() {
 if (list_navigation_item != -1) {
 Bundle args = new Bundle();
 Fragment fragment = new FragmentViewPager();
 args.putString(FragmentViewPager.ITEM_NAME, dataList.get(1).getItemName());
 args.putInt(FragmentViewPager.IMAGE_RESOURCE_ID, dataList.get(1)
  .getImgResID());

 fragment.setArguments(args);
 // FragmentManager frgManager = getFragmentManager();
 FragmentManager frgManager = getSupportFragmentManager();
 frgManager.beginTransaction().replace(R.id.content_frame, fragment)
  .commit();
 }
}

// обработчик выпадающего списка ActionBar
@Override
public boolean onNavigationItemSelected(int itemPosition, long itemId) {
 /*
  * Log.d(LOG_TAG, "selected: position = " + itemPosition + ", id = " +
  * itemId + ", " + data_list[itemPosition]);
  */
 int list_navigation_item_old=list_navigation_item;
 if (list_navigation_item != itemPosition) {
 list_navigation_item = itemPosition;
 }
 if (list_navigation_item >= 0 && list_navigation_item_old!=-1) {
 cal.setTodayDate();
 SelectItemDayMonth();
 }
 return false;
}

// обработчик выбора День или Месяц и вывод на экран
public void SelectItemDayMonth() {
 if (list_navigation_item != -1) {
 Bundle args = new Bundle();
 Fragment fragment = new FragmentViewPager();
 args.putString(FragmentViewPager.ITEM_NAME, dataList.get(1).getItemName());
 args.putInt(FragmentViewPager.IMAGE_RESOURCE_ID, dataList.get(1)
  .getImgResID());
 fragment.setArguments(args);
 // FragmentManager frgManager = getFragmentManager();
 FragmentManager frgManager = getSupportFragmentManager();
 frgManager.beginTransaction().replace(R.id.content_frame, fragment)
  .commit();
 }

}

public void SelectItem(int position) {
 if (list_position_st != position) {

 if (position ==10 || position == 12) {
  //menu_flag_group=false;
  mDrawerList.setItemChecked(list_position_st, true);
  mDrawerLayout.closeDrawer(mDrawerList);
  if (position == 10) {
  // вызываем активити Preferences
  Intent intent_p = new Intent(this, PreferencesActivity.class);
  startActivity(intent_p);
  }

  else {
  Intent intent_m = new Intent(Intent.ACTION_VIEW);
  intent_m.setData(Uri
   .parse(getString(R.string.link_rating)));
  startActivity(intent_m);
  }
 /*if(position==10){
  mDrawerList.setItemChecked(list_position, true);
  mDrawerLayout.closeDrawer(mDrawerList);

  Intent intent_m = new Intent(Intent.ACTION_VIEW);
  intent_m.setData(Uri
   .parse("market://details?id=oleksandr.kotyuk.orthodoxcalendar"));
  startActivity(intent_m);*/
 } else {
  list_position_st = position;
  Fragment fragment = null;
  Bundle args = new Bundle();
  switch (position) {

  case 1:
  menu_flag_group_st=false;
  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
  menu_activ_st = true;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();

  fragment = new FragmentViewPager();
  args.putString(FragmentViewPager.ITEM_NAME,
   dataList.get(position).getItemName());
  args.putInt(FragmentViewPager.IMAGE_RESOURCE_ID,
   dataList.get(position).getImgResID());
  //**************************************
  /*if(notifi_date_app_start_flag){
   Intent intent = getIntent();
   int notifi_date_app_start = intent.getIntExtra("notifi_date_app_start", 0);
   args.putInt("notifi_date_app_start",
    notifi_date_app_start);
   notifi_date_app_start_flag=false;
  }*/
  //**************************************
  break;
  case 2:
  menu_flag_group_st=true;
  //bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
  bar.removeAllTabs();
  if(bar.getTabCount()==0){
ActionBar.Tab tab = bar.newTab();

   tab.setText("МОЛИТВЫ");
   tab.setTabListener( this);
   bar.addTab(tab);

   tab = bar.newTab();
   tab.setText("ИЗБРАННОЕ");
   tab.setTabListener(this);
   bar.addTab(tab);

   tab = bar.newTab();
   tab.setText("АКАФИСТЫ КАНОНЫ");
   tab.setTabListener(this);
   bar.addTab(tab);

   tab = bar.newTab();
   tab.setText("ПСАЛТИРЬ");
   tab.setTabListener(this);
   bar.addTab(tab);

   tab = bar.newTab();
   tab.setText("ТРОПАРИ И КОНДАКИ ДНЯ");
   tab.setTabListener(this);
   bar.addTab(tab);

  }
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;

  String  prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
   this, "pref_prayers_language", "ru");
  if (prayers_language.equals("ru")) {
   menu_activ_prayers_language_ru_st=true;
   menu_activ_prayers_language_cs_st=false;
  }
  if (prayers_language.equals("cs")) {
   menu_activ_prayers_language_ru_st=false;
   menu_activ_prayers_language_cs_st=true;
  }
  menu_activ_prayers_fonts_st=true;

  //menu_activ_prayers=true;
menuActivPrayers ();
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment = new FragmentPrayers();
  args.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(position).getItemName());
  args.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(position).getImgResID());
  break;
  case 3:
  menu_flag_group_st=false;
  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();

  cal.setTodayDate();
  fragment = new FragmentBible();
  args.putString(FragmentBible.ITEM_NAME,
   dataList.get(position).getItemName());
  args.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(position).getImgResID());
  break;
  case 4:
  menu_flag_group_st=false;
  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  this.invalidateOptionsMenu();

  cal.setTodayDate();
  fragment = new FragmentEaster();
  args.putString(FragmentEaster.ITEM_NAME,
   dataList.get(position).getItemName());
  args.putInt(FragmentEaster.IMAGE_RESOURCE_ID, dataList
   .get(position).getImgResID());
  break;
  case 5:
  menu_flag_group_st=false;
  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();

  cal.setTodayDate();
  fragment = new FragmentHolidays();
  args.putString(FragmentHolidays.ITEM_NAME,
   dataList.get(position).getItemName());
  args.putInt(FragmentHolidays.IMAGE_RESOURCE_ID, dataList
   .get(position).getImgResID());
  break;
  case 6:
  menu_flag_group_st=false;
  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();

  cal.setTodayDate();
  fragment = new FragmentSouls();
  args.putString(FragmentSouls.ITEM_NAME,
   dataList.get(position).getItemName());
  args.putInt(FragmentSouls.IMAGE_RESOURCE_ID,
   dataList.get(position).getImgResID());
  break;
  case 7:
  menu_flag_group_st=false;
  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();

  cal.setTodayDate();
  fragment = new FragmentPosts();
  args.putString(FragmentPosts.ITEM_NAME,
   dataList.get(position).getItemName());
  args.putInt(FragmentPosts.IMAGE_RESOURCE_ID,
   dataList.get(position).getImgResID());
  break;

  case 8:
  menu_flag_group_st=false;
  //bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
  bar.removeAllTabs();
  if(bar.getTabCount()==0){
   androidx.appcompat.app.ActionBar.Tab tab2 = bar.newTab();

   tab2.setText("ИНФОРМАЦИЯ");
   tab2.setTabListener( this);
   bar.addTab(tab2);

   tab2 = bar.newTab();
   tab2.setText("СЛОВАРЬ ТЕРМИНОВ");
   tab2.setTabListener(this);
   bar.addTab(tab2);
  }
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();

  cal.setTodayDate();
  fragment = new FragmentDirectory();
  args.putString(FragmentDirectory.ITEM_NAME,
   dataList.get(position).getItemName());
  args.putInt(FragmentDirectory.IMAGE_RESOURCE_ID,
   dataList.get(position).getImgResID());
  break;
  case 11:
  menu_flag_group_st=false;
  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();

  cal.setTodayDate();
  fragment = new FragmentHelp();
  args.putString(FragmentHelp.ITEM_NAME,
   dataList.get(position).getItemName());
  args.putInt(FragmentHelp.IMAGE_RESOURCE_ID,
   dataList.get(position).getImgResID());
  break;
  case 13:
  menu_flag_group_st=false;
  bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();

  cal.setTodayDate();
  fragment = new FragmentAbout();
  args.putString(FragmentAbout.ITEM_NAME,
   dataList.get(position).getItemName());
  /*args.putString(GlobalData.getData_style(),
   dataList.get(position).getItemName());*/
  args.putInt(FragmentAbout.IMAGE_RESOURCE_ID,
   dataList.get(position).getImgResID());
  break;
  default:
  break;
  }

  fragment.setArguments(args);
  // FragmentManager frgManager = getFragmentManager();
  FragmentManager frgManager = getSupportFragmentManager();
  frgManager.beginTransaction()
   .replace(R.id.content_frame, fragment).commit();

  mDrawerList.setItemChecked(position, true);
  setTitle(dataList.get(position).getItemName());
  mDrawerLayout.closeDrawer(mDrawerList);
 }
 } else
 mDrawerLayout.closeDrawer(mDrawerList);

}
//выбран уже выбранный таб
@Override
public void onTabReselected(ActionBar.Tab arg0,
 FragmentTransaction arg1) {
 // TODO Auto-generated method stub

}
//таб выбран
@Override
public void onTabSelected(ActionBar.Tab arg0,
 FragmentTransaction arg1) {
 // TODO Auto-generated method stub
 Log.d(LOG_TAG, "onTabSelected: " + arg0.getText()+"-"+arg0.getPosition());

 menu_flag_tab_index_st=-1;

 Fragment fragment = null;
 Bundle args = new Bundle();
 if(arg0.getText().equals("ИНФОРМАЦИЯ") || arg0.getText().equals("СЛОВАРЬ ТЕРМИНОВ")){
 switch (arg0.getPosition()) {
 case 0:
  menu_flag_tab_st=false;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment = new FragmentDirectory();
  args.putString(FragmentDirectory.ITEM_NAME,
   dataList.get(8).getItemName());
  args.putInt(FragmentDirectory.IMAGE_RESOURCE_ID,
   dataList.get(8).getImgResID());
  break;
 case 1:
  menu_flag_tab_st=false;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment = new FragmentDirectory2();
  args.putString(FragmentDirectory2.ITEM_NAME,
   dataList.get(8).getItemName());
  args.putInt(FragmentDirectory2.IMAGE_RESOURCE_ID,
   dataList.get(8).getImgResID());
  break;
 default:
  break;
 }

 fragment.setArguments(args);
 // FragmentManager frgManager = getFragmentManager();
 FragmentManager frgManager = getSupportFragmentManager();
 frgManager.beginTransaction()
  .replace(R.id.content_frame, fragment).commit();

 mDrawerList.setItemChecked(8, true);
 setTitle(dataList.get(8).getItemName());
 mDrawerLayout.closeDrawer(mDrawerList);
 }else{
 String  prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_prayers_language", "ru");
 //ps-Псалтирь
 //ys-Псалтирь по усопшим
 String  psaltur_view = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_psaltur_view", "ps");
 menu_flag_tab_index_st=arg0.getPosition();
 switch (arg0.getPosition()) {
 case 0:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;

  if (prayers_language.equals("ru")) {
  menu_activ_prayers_st=true;
  menu_activ_prayers_language_ru_st=true;
  menu_activ_prayers_language_cs_st=false;
  }
  if (prayers_language.equals("cs")) {
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=true;
  }
  menu_activ_prayers_fonts_st=true;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;

  //menu_activ_prayers=true;
  menuActivPrayers();
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment = new FragmentPrayers();
  args.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
 case 1:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=true;
  menu_activ_prayers_st=false;

  if (prayers_language.equals("ru")) {
  menu_activ_prayers_language_ru_st=true;
  menu_activ_prayers_language_cs_st=false;
  }
  if (prayers_language.equals("cs")) {
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=true;
  }
  menu_activ_prayers_fonts_st=true;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;

  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment = new FragmentPrayersBookmarks2();
  args.putString(FragmentPrayersBookmarks2.ITEM_NAME,
   dataList.get(2).getItemName());
  args.putInt(FragmentPrayersBookmarks2.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
 case 2:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;

  if (prayers_language.equals("ru")) {
  menu_activ_prayers_st=true;
  menu_activ_prayers_language_ru_st=true;
  menu_activ_prayers_language_cs_st=false;
  }
  if (prayers_language.equals("cs")) {
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=true;
  }
  menu_activ_prayers_fonts_st=true;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;

  //menu_activ_prayers=true;
  menuActivPrayers();
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment = new FragmentPrayers();
  args.putString("prayers_type","ak");
  args.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
 case 3:
  menu_flag_tab_st=true;
  menu_activ_st = false;
  menu_activ_psaltur_st=true;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;

  if (prayers_language.equals("ru")) {
  menu_activ_prayers_language_ru_st=true;
  menu_activ_prayers_language_cs_st=false;
  }
  if (prayers_language.equals("cs")) {
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=true;
  }
  menu_activ_prayers_fonts_st=true;

  if (psaltur_view.equals("ps")) {
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=true;
  }
  if (psaltur_view.equals("ys")) {
  menu_activ_psaltur_view1=true;
  menu_activ_psaltur_view2=false;
  }

  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment = new FragmentPrayers();
  args.putString("prayers_type","kafisma");
  args.putString(FragmentPrayers.ITEM_NAME,
   dataList.get(2).getItemName());
  args.putInt(FragmentPrayers.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
 case 4:
  menu_flag_tab_st=false;
  menu_activ_st = false;
  menu_activ_psaltur_st=false;
  menu_activ_bookmark_st=false;
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=false;
  menu_activ_prayers_fonts_st=false;
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=false;
  invalidateOptionsMenu();
  cal.setTodayDate();
  fragment = new FragmentPrayersTroparKondakDay();
  args.putString(FragmentPrayersTroparKondakDay.ITEM_NAME,
   dataList.get(2).getItemName());
  args.putInt(FragmentPrayersTroparKondakDay.IMAGE_RESOURCE_ID,
   dataList.get(2).getImgResID());
  break;
 default:
  break;
 }

 fragment.setArguments(args);
 // FragmentManager frgManager = getFragmentManager();
 FragmentManager frgManager = getSupportFragmentManager();
 frgManager.beginTransaction()
  .replace(R.id.content_frame, fragment).commit();

 mDrawerList.setItemChecked(2, true);
 setTitle(dataList.get(2).getItemName());
 mDrawerLayout.closeDrawer(mDrawerList);
 }

 /*fragment.setArguments(args);
 // FragmentManager frgManager = getFragmentManager();
 FragmentManager frgManager = getSupportFragmentManager();
 frgManager.beginTransaction()
  .replace(R.id.content_frame, fragment).commit();

 mDrawerList.setItemChecked(6, true);
 setTitle(dataList.get(6).getItemName());
 mDrawerLayout.closeDrawer(mDrawerList);*/

}
//таб более не выбран
@Override
public void onTabUnselected(ActionBar.Tab arg0,
 FragmentTransaction arg1) {
 // TODO Auto-generated method stub

}

@Override
public void setTitle(CharSequence title) {
 mTitle = title;
 // getSupportActionBar().setTitle(mTitle);
 getSupportActionBar().setTitle(mTitle);
}


public void menuActivPrayers (){
 String prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(this, "pref_prayers_language", "ru");
 if (prayers_language.equals("ru")) menu_activ_prayers_st=true;
 if (prayers_language.equals("cs")) menu_activ_prayers_st=false;
}

@Override
protected void onPostCreate(Bundle savedInstanceState) {
 super.onPostCreate(savedInstanceState);
 // Sync the toggle state after onRestoreInstanceState has occurred.
 mDrawerToggle.syncState();
}

@Override
public void onConfigurationChanged(Configuration newConfig) {
 super.onConfigurationChanged(newConfig);
 // Pass any configuration change to the drawer toggles
 mDrawerToggle.onConfigurationChanged(newConfig);
}

private class DrawerItemClickListener implements
 ListView.OnItemClickListener {
 @Override
 public void onItemClick(AdapterView<?> parent, View view, int position,
  long id) {
 if (dataList.get(position).getTitle() == null) {
  SelectItem(position);
 }

 }
}

// вызывается перед тем, как Activity будет видно пользователю
@Override
protected void onStart() {
 super.onStart();
 Log.d(LOG_TAG, "MainActivity: onStart()");
}

// вызывается перед тем как будет доступно для активности пользователя
// (взаимодействие)
@Override
protected void onResume() {
 super.onResume();
 if(menu_flag_group_st && menu_flag_tab_st){
 menu_activ_st = false;

 if(menu_flag_tab_index_st==3)
  menu_activ_psaltur_st=true;
 else
  menu_activ_psaltur_st=false;

 menu_activ_bookmark_st=false;

 String  prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_prayers_language", "ru");
 String  psaltur_view = PreferencesActivity.MyPreferenceFragment.ReadString(
  this, "pref_psaltur_view", "ps");
 if (prayers_language.equals("ru")) {

  if(menu_flag_tab_index_st==0 || menu_flag_tab_index_st==2)
  menu_activ_prayers_st=true;
  else
  menu_activ_prayers_st=false;

  menu_activ_prayers_language_ru_st=true;
  menu_activ_prayers_language_cs_st=false;
 }
 if (prayers_language.equals("cs")) {
  menu_activ_prayers_st=false;
  menu_activ_prayers_language_ru_st=false;
  menu_activ_prayers_language_cs_st=true;
 }

 if(menu_flag_tab_index_st==4)
  menu_activ_prayers_fonts_st=false;
 else
  menu_activ_prayers_fonts_st=true;
 if(menu_activ_psaltur_st==true){
  if (psaltur_view.equals("ps")) {
  menu_activ_psaltur_view1=false;
  menu_activ_psaltur_view2=true;
  }
  if (psaltur_view.equals("ys")) {
  menu_activ_psaltur_view1=true;
  menu_activ_psaltur_view2=false;
  }
 }

 //menu_activ_prayers=true;
 //menuActivPrayers();
 invalidateOptionsMenu();
 }
 Log.d(LOG_TAG, "MainActivity: onResume()");
}

// вызывается перед тем, как будет показано другое Activity
@Override
protected void onPause() {
 super.onPause();
 Log.d(LOG_TAG, "MainActivity: onPause()");
}

// вызывается когда Activity становится не видно пользователю
@Override
protected void onStop() {
 super.onStop();

 Log.d(LOG_TAG, "MainActivity: onStop()");
}

@Override
protected void onRestart() {
 // TODO Auto-generated method stub
 super.onRestart();

 Log.d(LOG_TAG, "MainActivity: onRestart()");
}

// вызывается перед тем, как Activity будет уничтожено
@Override
protected void onDestroy() {
 super.onDestroy();

 if(dataList!=null) {
 dataList.clear();
 dataList=null;
 }
 cal.setTodayDate();
 /*list_position_st = -1;
 menu_activ_st = true;
 menu_activ_psaltur_st = false;
 menu_activ_bookmark_st = false;
 menu_activ_prayers_st = false;
 menu_activ_prayers_language_ru_st = false;
 menu_activ_prayers_language_cs_st = false;
 menu_activ_prayers_fonts_st = false;
 menu_flag_group_st=false;
 menu_flag_tab_st=false;
 menu_flag_tab_index_st=-1;*/
 list_navigation_item = -1;
 // закрываем подключение к базе
 if(db!=null)db.closeConnecion();

 Log.d(LOG_TAG, "R_MainActivity: onDestroy()");

}

// позволяет востановить данные
protected void onRestoreInstanceState(Bundle savedInstanceState) {
 Log.d(LOG_TAG, "onRestoreInstanceState");
 super.onRestoreInstanceState(savedInstanceState);
 //Log.d(LOG_TAG, "onRestoreInstanceState");
}

// позволяет сохранить данные
protected void onSaveInstanceState(Bundle outState) {
 Log.d(LOG_TAG, "onSaveInstanceState");
 super.onSaveInstanceState(outState);
 //Log.d(LOG_TAG, "onSaveInstanceState");
}

// нажатие на кнопку назад
@Override
public void onBackPressed() {
 if (back_pressed + 2000 > System.currentTimeMillis()) {
 //cal.setTodayDate();
 // закрываем подключение к базе
 //list_navigation_item = -1;
 //if(db!=null)db.closeConnecion();
 Log.d(LOG_TAG, "R_MainActivity: onBackPressed()");
 super.onBackPressed();
 } else
 Toast.makeText(getApplicationContext(), "Нажмите еще раз для выхода",
  Toast.LENGTH_SHORT).show();
 back_pressed = System.currentTimeMillis();
}

}
