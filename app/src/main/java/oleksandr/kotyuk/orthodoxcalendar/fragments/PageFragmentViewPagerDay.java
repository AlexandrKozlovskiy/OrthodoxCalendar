package oleksandr.kotyuk.orthodoxcalendar.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import oleksandr.kotyuk.orthodoxcalendar.DescriptionActivity;
import oleksandr.kotyuk.orthodoxcalendar.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendar.GlobalData;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.MyView;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

public class PageFragmentViewPagerDay extends Fragment implements
 OnClickListener {

static final String TAG = "myLogs";

static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

private int flag_visible_tropar_kondak = 0;
private int flag_visible_prayers_links = 0;
private int flag_visible_gospel = 0;
private int flag_visible_feofan = 0;
private int flag_visible_citations = 0;

int pageNumber;

int data;
int month;
int year;

Calendar calendar;
private DatabaseHelper db;
Cursor cursor;

MyCalendar cal = MyCalendar.getInstance();

private final String FONT_PATH1 = "fonts/Russo_One.ttf";

// название дня недели СРЕДА
MyView MyView_weekday_name;
// текущий год 2019
MyView MyView_year;
// основной стиль(новый/старый)
MyView MyView_new_style_name;
// основной стиль(новый/старый)
MyView MyView_old_style_name;
// дата(14 июня)
MyView MyView_new_style_date;
// дата(14 июня)
MyView MyView_old_style_date;

MyView MyView_sedmitsa;
MyView MyView_post;
// MyView MyView_gr_holiday;
// MyView MyView_big_holiday;
MyView MyView_holiday;
MyView MyView_tropar_kondak_title;
MyView MyView_tropar_kondak_text;
MyView MyView_prayers_links_title;
MyView MyView_prayers_links_text;
MyView MyView_gospel_reading_text;
MyView MyView_gospel_reading_title;
MyView MyView_think_feofan_text;
MyView MyView_think_feofan_title;
MyView MyView_citations_elders_text;
MyView MyView_citations_elders_title;

RelativeLayout rel1;
RelativeLayout rel2;
View vie1;

String text_sedmitsa = "";
String text_post = "";
String text_gr_holiday = "";
String text_big_holiday = "";
String text_holiday = "";
String text_icon_holiday = "";
String text_gospel_reading = "";
String text_link_prayer_gospel = "<a href=\"activity-run://DescriptionOtherActivityHost?id_prayer=173\">Молитвы пред и по чтении Евангелия</a><br><br>";
String text_think_feofan = "На сегодняшние Евангельские чтения отсутствуют мысли святителя Феофана Затворника.";
String text_prayers_links = "На сегодняшний день в молитвослове отсутствуют молитвы, акафисты и каноны.";
String text_citations = "";
String text_post_info = "#";
//String text_sedmitsa_info="#";

String sql;

String text_size = "0";
float standart_text_size = 0;

String[] mas_date_calendar;
String prayers_tmp[];
String prayers[];
String prayers_final[];


String prayers_day = "#";
int prayers_day_count = 1;

String date_calendar;
ArrayList<String> unmovable_holiday_text_tmp = new ArrayList<String>();
ArrayList<String> unmovable_holiday_index_tmp = new ArrayList<String>();

ArrayList<String> unmovable_holiday_text = new ArrayList<String>();
ArrayList<String> unmovable_holiday_index = new ArrayList<String>();

ArrayList<String> arr_prayers_tmp = new ArrayList<String>();

ArrayList<String> unmovable_holiday_new = new ArrayList<String>();

//String prayers_tk = "";
String prayers_tk_link = "";
String prayers_pr_ak_link = "";

static PageFragmentViewPagerDay newInstance(int page) {
 PageFragmentViewPagerDay pageFragment = new PageFragmentViewPagerDay();
 Bundle arguments = new Bundle();
 arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
 pageFragment.setArguments(arguments);
 return pageFragment;
}

@Override
public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

}

@SuppressLint("InflateParams")
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 View view;
 view = inflater.inflate(R.layout.fragment_view_pager_day, null);

 cal.AddDay(pageNumber);

 data = cal.getDayMonth();
 month = cal.getMonth() + 1;
 year = cal.getYear();

 if (year == 2020) {
 if ((month == 2 && data == 29) || (month == 3 && (data > 0 && data < 14))) {
  newVisYearPrayers();
  newVisYearPrayersLink();
 }else{
  createTroparKondakDayText();
  createPrayersLinkText();
 }
 }else{
 createTroparKondakDayText();
 createPrayersLinkText();
 }
 
 //---------------------------------
 //createTroparKondakDayText();
 //---------------------------------
 //prayers_tk = replace_tk(prayers_tk);
 

 rel1 = (RelativeLayout) view.findViewById(R.id.relativeLayout_day1);
 rel2 = (RelativeLayout) view.findViewById(R.id.relativeLayout_day2);
 vie1 = (View) view.findViewById(R.id.view_separator);
 MyView_tropar_kondak_title = (MyView) view
  .findViewById(R.id.MyView_tropar_kondak_title);
MyView_tropar_kondak_title.setExpanded(false);
 MyView_prayers_links_title = (MyView) view
  .findViewById(R.id.MyView_prayers_links_title);
MyView_prayers_links_title.setExpanded(false);
 MyView_gospel_reading_title = (MyView) view
  .findViewById(R.id.MyView_gospel_reading_title);
MyView_gospel_reading_title.setExpanded(false);
 MyView_think_feofan_title = (MyView) view
  .findViewById(R.id.MyView_think_feofan_title);
MyView_think_feofan_title.setExpanded(false);
 MyView_citations_elders_title = (MyView) view
  .findViewById(R.id.MyView_citations_elders_title);
MyView_citations_elders_title.setExpanded(false);
 if (!cal.getDayWeekNamesLongCaps().equals("ВОСКРЕСЕНЬЕ")) {
 rel1.setBackgroundResource(R.drawable.ro1);
 rel2.setBackgroundResource(R.drawable.rx1);
 vie1.setBackgroundResource(R.drawable.separator1);
 MyView_tropar_kondak_title
  .setBackgroundResource(R.drawable.rectangle_top_b);
 MyView_prayers_links_title
  .setBackgroundResource(R.drawable.rectangle_top_b);
 MyView_gospel_reading_title
  .setBackgroundResource(R.drawable.rectangle_top_b);
 MyView_think_feofan_title
  .setBackgroundResource(R.drawable.rectangle_top_b);
 MyView_citations_elders_title
  .setBackgroundResource(R.drawable.rectangle_top_b);
 } else {
 rel1.setBackgroundResource(R.drawable.ro2);
 rel2.setBackgroundResource(R.drawable.rx2);
 vie1.setBackgroundResource(R.drawable.separator2);
 MyView_tropar_kondak_title
  .setBackgroundResource(R.drawable.rectangle_top_r);
 MyView_prayers_links_title
  .setBackgroundResource(R.drawable.rectangle_top_r);
 MyView_gospel_reading_title
  .setBackgroundResource(R.drawable.rectangle_top_r);
 MyView_think_feofan_title
  .setBackgroundResource(R.drawable.rectangle_top_r);
 MyView_citations_elders_title
  .setBackgroundResource(R.drawable.rectangle_top_r);
 }

 MyView_weekday_name = (MyView) view
  .findViewById(R.id.MyView_weekday_name);
MyView_weekday_name.setTypeface(FontsHelper.getTypeFace(getActivity()
  .getApplicationContext(), FONT_PATH1));
 MyView_weekday_name.setText(cal.getDayWeekNamesLongCaps());

 MyView_year = (MyView) view.findViewById(R.id.MyView_year);
 MyView_year.setTypeface(FontsHelper.getTypeFace(getActivity()
  .getApplicationContext(), FONT_PATH1));
 MyView_year.setText(String.valueOf(cal.getYear()));

 MyView_new_style_name = (MyView) view
  .findViewById(R.id.MyView_new_style_name);
 MyView_new_style_name.setText(R.string.new_style_name);
 MyView_new_style_date = (MyView) view
  .findViewById(R.id.MyView_new_style_date);
 MyView_new_style_date.setTypeface(FontsHelper.getTypeFace(
  getActivity().getApplicationContext(), FONT_PATH1));
 MyView_new_style_date.setText(String.valueOf(cal.getDayMonth() + " "
  + cal.getMonthName()));

 MyView_old_style_name = (MyView) view
  .findViewById(R.id.MyView_old_style_name);
 MyView_old_style_name.setText(R.string.old_style_name);

 // виставляємо дату по старому стилю -13 днів
 cal.SetOldStyleDate();
 MyView_old_style_date = (MyView) view
  .findViewById(R.id.MyView_old_style_date);
 MyView_old_style_date.setTypeface(FontsHelper.getTypeFace(
  getActivity().getApplicationContext(), FONT_PATH1));
 MyView_old_style_date.setText(String.valueOf(cal.getDayMonth() + " "
  + cal.getMonthName()));
 // вертаємо дату до нового стилю стилю +13 днів
 cal.SetNewStyleDate();

 MyView_sedmitsa = (MyView) view
  .findViewById(R.id.MyView_sedmitsa);
 MyView_post = (MyView) view.findViewById(R.id.MyView_post);
 // MyView_gr_holiday = (MyView)
 // view.findViewById(R.id.MyView_gr_holiday);
 // MyView_big_holiday = (MyView)
 // view.findViewById(R.id.MyView_big_holiday);
 MyView_holiday = (MyView) view.findViewById(R.id.MyView_holiday);
 MyView_holiday.setLinksClickable(true);
 MyView_holiday.setMovementMethod(new LinkMovementMethod());
 // MyView_icon_holiday = (MyView)
 // view.findViewById(R.id.MyView_icon_holiday);
 //----------------------------------------------------
 MyView_tropar_kondak_text = (MyView) view
  .findViewById(R.id.MyView_tropar_kondak_text);
 MyView_tropar_kondak_text.setLinksClickable(true);
 MyView_tropar_kondak_text
  .setMovementMethod(new LinkMovementMethod());
 
 MyView_prayers_links_text = (MyView) view
  .findViewById(R.id.MyView_prayers_links_text);
 MyView_prayers_links_text.setLinksClickable(true);
 MyView_prayers_links_text
  .setMovementMethod(new LinkMovementMethod());
 //----------------------------------------------------
 MyView_gospel_reading_text = (MyView) view
  .findViewById(R.id.MyView_gospel_reading_text);
 MyView_gospel_reading_text.setLinksClickable(true);
 MyView_gospel_reading_text
  .setMovementMethod(new LinkMovementMethod());
 MyView_think_feofan_text = (MyView) view
  .findViewById(R.id.MyView_think_feofan_text);
 MyView_citations_elders_text = (MyView) view
  .findViewById(R.id.MyView_citations_elders_text);

 // чипляємо обробник кліків по текстовим полям
 // MyView_gr_holiday.setOnClickListener(this);
 // MyView_big_holiday.setOnClickListener(this);
 // /////////////////////////////////////////////////////////////////////////
 // MyView_holiday.setOnClickListener(this);
 // ////////////////////////////////////////////////////////////////////////
 // MyView_icon_holiday.setOnClickListener(this);
 MyView_tropar_kondak_title.setOnClickListener(this);
 MyView_prayers_links_title.setOnClickListener(this);
 MyView_gospel_reading_title.setOnClickListener(this);
 MyView_think_feofan_title.setOnClickListener(this);
 MyView_citations_elders_title.setOnClickListener(this);
 //MyView_sedmitsa.setOnClickListener(this);
 MyView_post.setOnClickListener(this);

 // db =
 // DatabaseHelper.getInstance(getActivity().getApplicationContext());
 sql = "SELECT _id, month, day, ru_great_holiday, ru_big_holiday, ru_holiday,ru_icon_holiday, r"+ cal.getYear()+ ", p"+ cal.getYear()+ ", p"+ cal.getYear()+ "_info, gr"+ cal.getYear()+ ", tf"+ cal.getYear()+ " FROM data_calendar WHERE month="+ (cal.getMonth() + 1)+ " AND day=" + cal.getDayMonth() + ";";
 // Log.d(TAG, sql);
 // Log.d(TAG, "pageNumber="+pageNumber);
 // Log.d(TAG, "year="+cal.getYear());
 // Log.d(TAG, "month="+cal.getMonth());
 // Log.d(TAG, "day="+cal.getDayMonth());
 int id_think_feofan1 = 0;
 int id_think_feofan2 = 0;
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null) {
 if (cursor.moveToFirst()) {
  try {
  // String bufer;

  // String
  // sss=cursor.getString(cursor.getColumnIndex("r"+cal.getYear()));
  // text_sedmitsa=sss.replace("Глас", "\nГлас");
  text_sedmitsa = cursor.getString(cursor.getColumnIndex("r"
   + cal.getYear()));
  //text_sedmitsa_info = cursor.getString(cursor.getColumnIndex("r"
   //+ cal.getYear() + "_info"));
  // if(text_sedmitsa.equals("#"))text_sedmitsa="";

  text_post = cursor.getString(cursor.getColumnIndex("p"
   + cal.getYear()));
  text_post_info = cursor.getString(cursor.getColumnIndex("p"
   + cal.getYear() + "_info"));
  // if(text_post.equals("#"))text_post="";

  text_gr_holiday = cursor.getString(cursor
   .getColumnIndex("ru_great_holiday"));
  if (text_gr_holiday.equals("#"))
   text_gr_holiday = "";
  text_gr_holiday = text_gr_holiday.replace("\r\n", "<br>");

  text_big_holiday = cursor.getString(cursor
   .getColumnIndex("ru_big_holiday"));
  if (text_big_holiday.equals("#"))
   text_big_holiday = "";
  text_big_holiday = text_big_holiday.replace("\r\n", "<br>");

  text_holiday = cursor.getString(cursor
   .getColumnIndex("ru_holiday"));
  if (text_holiday.equals("#"))
   text_holiday = "";
  text_holiday = text_holiday.replace("\r\n", "<br>");

  text_icon_holiday = cursor.getString(cursor
   .getColumnIndex("ru_icon_holiday"));
  if (text_icon_holiday.equals("#"))
   text_icon_holiday = "";
  text_icon_holiday = text_icon_holiday.replace("\r\n",
   "<br>");

  text_gospel_reading = cursor.getString(cursor
   .getColumnIndex("gr" + cal.getYear()));
  // Log.d(TAG, "!!!text_gospel_reading1=" +
  // text_gospel_reading);
  if (text_gospel_reading.equals("#"))
   text_gospel_reading = "";
  // text_gospel_reading = text_gospel_reading.replace("   ",
  // "&nbsp;&nbsp;&nbsp;");
  text_gospel_reading = text_gospel_reading.replace("\r\n", "<br>");
  /////////////////////////////////////////////////////////////////
  text_gospel_reading = text_gospel_reading.replace("GospelReadingActivityHost", GlobalData.getGOSPEL_READING_ACTIVITY_HOST());
  text_gospel_reading = text_gospel_reading.replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST());
  text_gospel_reading = text_gospel_reading.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
  text_gospel_reading = text_gospel_reading.replace("DescriptionLessonsActivityHost", GlobalData.getDESCRIPTION_LESSONS_ACTIVITY_HOST());
  /////////////////////////////////////////////////////////////////

  String tmp_id_think_feofan = cursor.getString(cursor
   .getColumnIndex("tf" + cal.getYear()));
  int tmp_id = tmp_id_think_feofan.indexOf("#");
  if (tmp_id != -1) {
   String[] mas_id_think_feofan = tmp_id_think_feofan
    .split("#");
   id_think_feofan1 = Integer
    .parseInt(mas_id_think_feofan[0]);
   id_think_feofan2 = Integer
    .parseInt(mas_id_think_feofan[1]);
  } else {
   id_think_feofan1 = Integer
    .parseInt(tmp_id_think_feofan);
  }

  // id_think_feofan=cursor.getInt(cursor.getColumnIndex("tf"+cal.getYear()));
  } catch (NumberFormatException e) {
  // Log.d(TAG, "ERROR=" + e.toString());
  }
 }
 }

 if (text_sedmitsa.equals("#"))
 text_sedmitsa = "";
 MyView_sedmitsa.setText(text_sedmitsa);
 if (text_post.equals("#"))
 text_post = "";
 MyView_post.setText(text_post);
 AddValuesGrDay();
 /*if (!text_gr_holiday.equals("")) {

 text_gr_holiday = "<FONT COLOR=RED><b>" + text_gr_holiday
  + "</b></FONT>" + "<br>";
 }*/
 if (!text_gr_holiday.equals("")) {

 text_gr_holiday = text_gr_holiday + "<br>";
 }
 // if(text_gr_holiday.length()==0)
 // MyView_gr_holiday.setTextSize(TypedValue.COMPLEX_UNIT_SP,0);
 // MyView_gr_holiday.setText(Html.fromHtml(text_gr_holiday));
 AddValuesBigDay();
 text_big_holiday = text_big_holiday.replace("\r\n", "<br>");
 if (!text_big_holiday.equals(""))
 text_big_holiday = text_big_holiday + "<br>";
 // if(text_big_holiday.length()==0)
 // MyView_big_holiday.setTextSize(TypedValue.COMPLEX_UNIT_SP,0);
 // MyView_big_holiday.setText(Html.fromHtml(text_big_holiday));
 AddValuesDay();
 text_holiday = text_holiday.replace("\r\n", "<br>");
 if (!text_holiday.equals(""))
 text_holiday = text_holiday + "<br>";
 // if(text_holiday.length()==0)
 // MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_SP,0);
 // MyView_holiday.setText(Html.fromHtml(text_holiday));

 text_icon_holiday = text_icon_holiday.replace("\r\n", "<br>");
 // if(text_icon_holiday.length()==0)
 // MyView_icon_holiday.setTextSize(TypedValue.COMPLEX_UNIT_SP,0);
 // MyView_icon_holiday.setText(Html.fromHtml(text_icon_holiday));
 // Log.d(TAG, "!!!MyView_holiday=" +
 // text_gr_holiday+text_big_holiday+text_holiday+text_icon_holiday);
 String text_all_holidays = text_gr_holiday + text_big_holiday
  + text_holiday + text_icon_holiday;

 if (year == 2020) {
 if (month == 2 && data == 29) {
  text_all_holidays = findTextVisYear().replace("\r\n", "<br>");
  //////////////////////////////////////////////////////////////
  //text_all_holidays = text_all_holidays.replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST());
  //////////////////////////////////////////////////////////////
 }
 if (month == 3 && (data > 0 && data < 14)) {
  text_all_holidays = findTextVisYear().replace("\r\n", "<br>");
  //////////////////////////////////////////////////////////////
  //text_all_holidays = text_all_holidays.replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST());
  //////////////////////////////////////////////////////////////
 }
 }
 text_all_holidays = text_all_holidays.replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST());
 MyView_holiday.setText(Html.fromHtml(text_all_holidays));

 // Log.d(TAG, "!!!text_gospel_reading2=" + text_gospel_reading);
 //---------------------------------------------------------
 String full_prayers_tk_link="• <a href=\"activity-run://DescriptionOtherActivityHost?id_tropar_kondak=100."+year+"."+month+"."+data+"."+pageNumber+"\">Все тропари и кондаки дня</a><br>";
 full_prayers_tk_link=full_prayers_tk_link.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
 MyView_tropar_kondak_text.setText(Html.fromHtml(full_prayers_tk_link+prayers_tk_link));
 
 MyView_prayers_links_text.setText(Html.fromHtml(prayers_pr_ak_link));
 //---------------------------------------------------------
 text_link_prayer_gospel=text_link_prayer_gospel.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
 MyView_gospel_reading_text.setText(Html.fromHtml(text_link_prayer_gospel+text_gospel_reading.replace("(от полу?)", "(от полу)")));

 if (id_think_feofan1 != 0)
 findThinkFeofan(id_think_feofan1, id_think_feofan2);
 MyView_think_feofan_text.setText(text_think_feofan);
 randomFindCitations();
 MyView_citations_elders_text.setText(text_citations);

 cursor.close();
 return view;
}

@Override
public void onStart() {
 // TODO Auto-generated method stub
 super.onStart();
 if (standart_text_size == 0)
 standart_text_size = MyView_holiday.getTextSize();
 String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(
  getActivity(), "pref_text_size", "0");
 if (!text_size.equals(tmp)) {
 text_size = tmp;
 // int int_text_size=Integer.valueOf(text_size);
 /*
  * Toast.makeText(getActivity(), text_size+"="+int_text_size,
  * Toast.LENGTH_SHORT).show();
  */
 // float size=MyView_holiday.getTextSize();
 if (text_size.equals("-5")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size - 10);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 10);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 10);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 10);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 10);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 10);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 10);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 10);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 10);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 10);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 10);
 }
 if (text_size.equals("-4")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size - 8);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 8);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 8);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 8);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 8);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 8);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 8);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 8);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 8);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 8);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 8);
 }
 if (text_size.equals("-3")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size - 6);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 6);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 6);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 6);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 6);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 6);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 6);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 6);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 6);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 6);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 6);
 }
 if (text_size.equals("-2")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size - 4);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 4);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 4);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 4);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 4);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 4);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 4);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 4);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 4);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 4);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 4);
 }
 if (text_size.equals("-1")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size - 2);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 2);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 2);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 2);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 2);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 2);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 2);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 2);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 2);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 2);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size - 2);
 }
 if (text_size.equals("0")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size);
 }
 if (text_size.equals("+1")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size + 2);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 2);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 2);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 2);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 2);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 2);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 2);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 2);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 2);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 2);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 2);
 }
 if (text_size.equals("+2")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size + 4);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 4);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 4);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 4);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 4);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 4);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 4);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 4);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 4);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 4);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 4);
 }
 if (text_size.equals("+3")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size + 6);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 6);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 6);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 6);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 6);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 6);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 6);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 6);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 6);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 6);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 6);
 }
 if (text_size.equals("+4")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size + 8);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 8);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 8);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 8);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 8);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 8);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 8);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 8);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 8);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 8);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 8);
 }
 if (text_size.equals("+5")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size + 10);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 10);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 10);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 10);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 10);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 10);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 10);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 10);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 10);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 10);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 10);
 }
 if (text_size.equals("+6")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size + 12);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 12);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 12);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 12);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 12);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 12);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 12);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 12);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 12);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 12);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 12);
 }
 if (text_size.equals("+7")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size + 14);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 14);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 14);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 14);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 14);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 14);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 14);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 14);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 14);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 14);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 14);
 }
 if (text_size.equals("+8")) {
  MyView_holiday.setTextSize(TypedValue.COMPLEX_UNIT_PX,
   standart_text_size + 16);
  MyView_prayers_links_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 16);
  MyView_prayers_links_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 16);
  MyView_tropar_kondak_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 16);
  MyView_gospel_reading_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 16);
  MyView_tropar_kondak_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 16);
  MyView_gospel_reading_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 16);
  MyView_think_feofan_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 16);
  MyView_think_feofan_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 16);
  MyView_citations_elders_title.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 16);
  MyView_citations_elders_text.setTextSize(
   TypedValue.COMPLEX_UNIT_PX, standart_text_size + 16);
 }

 }
}

public String findTextVisYear() {
 String text_holiday_vis_year = "";
 cursor = null;
 sql = "select holiday from data_calendar_leap_year where month="
  + month + " AND day=" + data + ";";
 // Log.d(TAG,"запросDayGR= " + sql);
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null && cursor.getCount() > 0) {
 if (cursor.moveToFirst()) {
  try {
  text_holiday_vis_year = cursor.getString(cursor
   .getColumnIndex("holiday"));
  } catch (NumberFormatException e) {
  // Log.d(TAG, "ERROR=" + e.toString());
  }
 }
 }

 return text_holiday_vis_year;
}

public void randomFindCitations() {
 Random r = new Random(System.currentTimeMillis());
 int num = r.nextInt(903) + 1;

 cursor = null;
 sql = "select text from citations where _id=" + num + ";";
 // Log.d(TAG,"запросDayGR= " + sql);
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null && cursor.getCount() > 0) {
 if (cursor.moveToFirst()) {
  try {
  text_citations = cursor.getString(cursor
   .getColumnIndex("text"));
  } catch (NumberFormatException e) {
  // Log.d(TAG, "ERROR=" + e.toString());
  }
 }
 }

}

public void findThinkFeofan(int id1, int id2) {
 cursor = null;
 sql = "select thinks_text from think_feofan where _id=" + id1 + ";";
 // Log.d(TAG,"запросDayGR= " + sql);
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null && cursor.getCount() > 0) {
 if (cursor.moveToFirst()) {
  try {
  text_think_feofan = cursor.getString(cursor
   .getColumnIndex("thinks_text"));
  } catch (NumberFormatException e) {
  // Log.d(TAG, "ERROR=" + e.toString());
  }

 }
 }

 if (id2 != 0) {
 cursor = null;
 sql = "select thinks_text from think_feofan where _id=" + id2 + ";";
 // Log.d(TAG,"запросDayGR= " + sql);
 db = DatabaseHelper.getInstance(getActivity()
  .getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null && cursor.getCount() > 0) {
  if (cursor.moveToFirst()) {
  try {
   text_think_feofan = text_think_feofan
    + "\n\n"
    + cursor.getString(cursor
     .getColumnIndex("thinks_text"));
  } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
  }

  }
 }
 }
}

// добавляем перемещаемые даты GREAT праздников
public void AddValuesGrDay() {
 cursor = null;
 sql = "SELECT ru_great_holiday FROM gr_unmovable_holiday WHERE m"
  + cal.getYear() + "=" + (cal.getMonth() + 1) + " and d"
  + cal.getYear() + "=" + cal.getDayMonth();
 // Log.d(TAG,"запросDayGR= " + sql);
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 // Log.d(TAG,"cursor.getCount()= " + cursor.getCount());
 // Log.d(TAG,"text_gr_holiday= " + text_gr_holiday);
 if (cursor != null && cursor.getCount() > 0) {
 // Log.d(TAG,"text_gr_holiday= " + text_gr_holiday);
 if (cursor.moveToFirst()) {
  do {
  if (text_gr_holiday.length() == 0) {
   text_gr_holiday = cursor.getString(cursor
    .getColumnIndex("ru_great_holiday"));
  } else {
   text_gr_holiday = cursor.getString(cursor
    .getColumnIndex("ru_great_holiday"))
    + "<br>"
    + text_gr_holiday;
  }
  } while (cursor.moveToNext());

 }
 }
}

// добавляем перемещаемые даты BIG праздников
public void AddValuesBigDay() {
 cursor = null;
 sql = "SELECT ru_big_holiday, level FROM big_unmovable_holiday WHERE m"
  + cal.getYear() + "=" + (cal.getMonth() + 1) + " and d"
  + cal.getYear() + "=" + cal.getDayMonth();
 // Log.d(TAG,"запросDayBIG= " + sql);
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 // Log.d(TAG,"cursor.getCount()= " + cursor.getCount());
 // Log.d(TAG,"text_big_holiday= " + text_big_holiday);

 if (cursor != null && cursor.getCount() > 0) {
 // Log.d(TAG,"text_gr_holiday= " + text_gr_holiday);
 if (cursor.moveToFirst()) {
  do {
  int length = text_big_holiday.length();
  if (length == 0) {
   text_big_holiday = cursor.getString(cursor
    .getColumnIndex("ru_big_holiday"));
  } else {
   switch (cursor.getInt(cursor.getColumnIndex("level"))) {
   case 0:
   if (length == 0)
    text_big_holiday = cursor.getString(cursor
     .getColumnIndex("ru_big_holiday"));
   else
    text_big_holiday = cursor.getString(cursor
     .getColumnIndex("ru_big_holiday"))
     + "<br>" + text_big_holiday;
   break;
   case 1:
   if (length == 0)
    text_big_holiday = cursor.getString(cursor
     .getColumnIndex("ru_big_holiday"));
   else {
    int i = text_big_holiday.indexOf("<br>");
    if (i == -1)
    text_big_holiday = text_big_holiday
     + "<br>"
     + cursor.getString(cursor
      .getColumnIndex("ru_big_holiday"));
    else {
    // values_big[index].replace("\r\n",
    // "<br>"+values+"<br>");

    StringBuilder sb = new StringBuilder(
     text_big_holiday);
    sb.replace(
     i,
     i + 4,
     ("<br>"
      + cursor.getString(cursor
       .getColumnIndex("ru_big_holiday")) + "<br>"));
    text_big_holiday = sb.toString();
    }
   }
   break;
   case 2:
   if (length == 0)
    text_big_holiday = cursor.getString(cursor
     .getColumnIndex("ru_big_holiday"));
   else
    text_big_holiday = text_big_holiday
     + "<br>"
     + cursor.getString(cursor
      .getColumnIndex("ru_big_holiday"));
   break;

   default:
   break;
   }
  }
  } while (cursor.moveToNext());

 }
 }
}

// добавляем перемещаемые даты небольших праздников
public void AddValuesDay() {
 cursor = null;
 /*sql = "SELECT ru_holiday, level FROM unmovable_holiday WHERE m"
 + cal.getYear() + "=" + (cal.getMonth() + 1) + " and d"
 + cal.getYear() + "=" + cal.getDayMonth();*/
 sql = "SELECT ru_holiday, level, level"+cal.getYear()+" FROM unmovable_holiday WHERE m"
 + cal.getYear() + "=" + (cal.getMonth() + 1) + " and d"
 + cal.getYear() + "=" + cal.getDayMonth();
 Log.d(TAG, "запросDayHOLIDAY= " + sql);
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null && cursor.getCount() > 0) {
 /*
  * Log.d(TAG,"cursor.getCount()= " + cursor.getCount());
  * Log.d(TAG,"text_holiday= " + text_holiday); Log.d(TAG,"test= " +
  * "test"); Log.d(TAG,"text_icon_holiday= " + text_icon_holiday);
  * Log.d(TAG,"ru_holiday= " +
  * cursor.getString(cursor.getColumnIndex("ru_holiday")));
  * Log.d(TAG,"holiday_level= " +
  * cursor.getInt(cursor.getColumnIndex("level")));
  */
 if (cursor.moveToFirst()) {
  do {
  // int length=text_holiday.length();
  // if(length==0){
  // text_holiday=cursor.getString(cursor.getColumnIndex("ru_holiday"));
  // }
  // else{
  int level_tmp=cursor.getInt(cursor.getColumnIndex(("level"+cal.getYear())));
  switch (cursor.getInt(cursor.getColumnIndex("level"))) {

  case 1:
   if (text_holiday.length() == 0)
   text_holiday = cursor.getString(cursor.getColumnIndex("ru_holiday"));
   else {
   int i = text_holiday.indexOf("<br>");
   if (i == -1){
    if(level_tmp==0)
    text_holiday = cursor.getString(cursor.getColumnIndex("ru_holiday"))+"<br>"+text_holiday;
    else
    text_holiday = text_holiday + "<br>"+ cursor.getString(cursor.getColumnIndex("ru_holiday"));
   }
   else {
    if(level_tmp==33){
    text_holiday = text_holiday + "<br>"+ cursor.getString(cursor.getColumnIndex("ru_holiday"));
    }
    else{
    if(level_tmp==0){
     text_holiday = cursor.getString(cursor.getColumnIndex("ru_holiday"))+"<br>"+text_holiday;
    }
    else{
     int jj=0;
     for(int ii=0; ii<level_tmp; ii++){
     jj = text_holiday.indexOf("<br>", jj+4);
     }
     StringBuilder sb = new StringBuilder(text_holiday);
     sb.replace(jj, jj + 4, ("<br>"+ cursor.getString(cursor.getColumnIndex("ru_holiday")) + "<br>"));
     text_holiday = sb.toString();
    }
    }
   }
   }
   break;
  case 2:
   if (text_holiday.length() == 0)
   text_holiday = cursor.getString(cursor.getColumnIndex("ru_holiday"));
   else {
   int i = text_holiday.indexOf("<br>");
   if (i == -1){
    if(level_tmp==0)
    text_holiday = cursor.getString(cursor.getColumnIndex("ru_holiday"))+"<br>"+text_holiday;
    else
    text_holiday = text_holiday + "<br>"+ cursor.getString(cursor.getColumnIndex("ru_holiday"));
   }
   else {
    if(level_tmp==33){
    text_holiday = text_holiday + "<br>"+ cursor.getString(cursor.getColumnIndex("ru_holiday"));
    }
    else{
    if(level_tmp==0){
     text_holiday = cursor.getString(cursor.getColumnIndex("ru_holiday"))+"<br>"+text_holiday;
    }
    else{
     int jj=0;
     for(int ii=0; ii<level_tmp; ii++){
     jj = text_holiday.indexOf("<br>", jj+4);
     }
     StringBuilder sb = new StringBuilder(text_holiday);
     sb.replace(jj, jj + 4, ("<br>"+ cursor.getString(cursor.getColumnIndex("ru_holiday")) + "<br>"));
     text_holiday = sb.toString();
    }
    }
   }
   }
   break;
  case 4:
   if (text_icon_holiday.length() == 0)
   text_icon_holiday = cursor.getString(cursor
    .getColumnIndex("ru_holiday"));
   else
   text_icon_holiday = text_icon_holiday
    + "<br>"
    + cursor.getString(cursor
     .getColumnIndex("ru_holiday"));
   break;

  default:
   break;
  }
  // }
  } while (cursor.moveToNext());

 }
 }
}

@Override
public void onClick(View v) {
// TODO Auto-generated method stub
 switch (v.getId()) {
 case R.id.MyView_tropar_kondak_title:
 //------------------------------------------
 /*Intent intent = new Intent(getActivity(),
  PrayersTroparKondarActivity.class);
 intent.putExtra("prayers_tk", prayers_tk);
 startActivity(intent);*/
 if (flag_visible_tropar_kondak == 0) {
  MyView_tropar_kondak_text.setVisibility(View.VISIBLE);
  flag_visible_tropar_kondak = 1;
}
 else {
  MyView_tropar_kondak_text.setVisibility(View.GONE);
  flag_visible_tropar_kondak = 0;
 }
 //----------------------------------------
 break;
 case R.id.MyView_prayers_links_title:
 //------------------------------------------
 /*Intent intent = new Intent(getActivity(),
  PrayersTroparKondarActivity.class);
 intent.putExtra("prayers_tk", prayers_tk);
 startActivity(intent);*/
 if (flag_visible_prayers_links == 0) {
  MyView_prayers_links_text.setVisibility(View.VISIBLE);
  flag_visible_prayers_links = 1;
 } else {
  MyView_prayers_links_text.setVisibility(View.GONE);
  flag_visible_prayers_links = 0;
 }
 //----------------------------------------
 break;
 case R.id.MyView_gospel_reading_title:
 if (flag_visible_gospel == 0) {
  MyView_gospel_reading_text.setVisibility(View.VISIBLE);
  flag_visible_gospel = 1;
 } else {
  MyView_gospel_reading_text.setVisibility(View.GONE);
  flag_visible_gospel = 0;
 }
 break;
 case R.id.MyView_think_feofan_title:
 if (flag_visible_feofan == 0) {
  MyView_think_feofan_text.setVisibility(View.VISIBLE);
  flag_visible_feofan = 1;
 } else {
  MyView_think_feofan_text.setVisibility(View.GONE);
  flag_visible_feofan = 0;
 }
 break;
 case R.id.MyView_citations_elders_title:
 if (flag_visible_citations == 0) {
  MyView_citations_elders_text.setVisibility(View.VISIBLE);
  flag_visible_citations = 1;
 } else {
  MyView_citations_elders_text.setVisibility(View.GONE);
  flag_visible_citations = 0;
 }
 break;
 case R.id.MyView_post:
 Intent intent_post = new Intent(getActivity(),
  DescriptionActivity.class);
 intent_post.putExtra("post_info",
  text_post_info.replace("\n", "<br>"));
 intent_post.putExtra("id", 10);
 startActivity(intent_post);
 break;
 /*case R.id.MyView_sedmitsa:
 Intent intent_sedmitsa = new Intent(getActivity(),
  DescriptionActivity.class);
 intent_sedmitsa.putExtra("sedmitsa_info",
  text_sedmitsa_info.replace("\n", "<br>"));
 intent_sedmitsa.putExtra("id", 11);
 startActivity(intent_sedmitsa);
 break;*/

 default:
 break;
 }
}

public void createTroparKondakDayText() {

 sql = "SELECT tropari_kondaki_day FROM data_calendar WHERE month="
  + month + " AND day=" + data;

 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
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

 sql = "SELECT tropari_kondaki_day, tk_level" + year
  + " FROM big_unmovable_holiday WHERE m" + year + "=" + month
  + " AND d" + year + "=" + data + " AND tk_level" + year + "<>0";

 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null) {
 if (cursor.moveToFirst()) {
  do {
  try {

   unmovable_holiday_text_tmp.add(cursor.getString(cursor
    .getColumnIndex("tropari_kondaki_day")));

   unmovable_holiday_index_tmp.add(Integer.toString(cursor
    .getInt(cursor
     .getColumnIndex("tk_level" + year))));

  } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
  }
  } while (cursor.moveToNext());
 }
 }

 sql = "SELECT tropari_kondaki_day, tk_level" + year
  + " FROM unmovable_holiday WHERE m" + year + "=" + month
  + " AND d" + year + "=" + data + " AND tk_level" + year + "<>0";

 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null) {
 if (cursor.moveToFirst()) {
  do {
  try {

   unmovable_holiday_text_tmp.add(cursor.getString(cursor
    .getColumnIndex("tropari_kondaki_day")));

   unmovable_holiday_index_tmp.add(Integer.toString(cursor
    .getInt(cursor
     .getColumnIndex("tk_level" + year))));

  } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
  }
  } while (cursor.moveToNext());
 }
 }
 
 addPrayersDay();
 //----------------------------------------
 /*if(!date_calendar.equals("#")){
 if(!prayers_day.equals("#"))
  date_calendar=prayers_day+"***\r\n\r\n"+date_calendar;
 }else
 date_calendar=prayers_day;*/
 //-------------------------------------------------
 
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
 
 createTroparKondakLink();
 //--------------------------
 //createTitlePrayers();
 //replace_tk();
 //saveTextTkPreferences();


 //------------------------------------------------------------
 /*if (year == 2020) {
 if ((month == 2 && data == 29)
  || (month == 3 && (data > 0 && data < 14))) {
  newVisYearPrayers();
 }
 }*/
 //-------------------------------------------------------

}

//----------------------------------------------
//добавляем новые перемещаемые тропари и кондаки
public void addNewPrayersUnmovable(){
 
 //SELECT tropari_kondaki_text FROM tropari_kondaki_holiday_multi WHERE m2016s>=1 AND d2016s>=7 AND m2016f<=1 AND d2016f<=7
 //SELECT tropari_kondaki_text, m2016s, d2016s, m2016f, d2016f FROM tropari_kondaki_holiday_multi
 
 //выборка из таблицы tropari_kondaki_holiday_multi
 sql="SELECT tropari_kondaki_text, m"+year+"s, d"+year+"s, m"+year+"f, d"+year+"f FROM tropari_kondaki_holiday_multi";

 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 int m_year_s=0;
 int d_year_s=0;
 int m_year_f=0;
 int d_year_f=0;
 if (cursor != null) {
 if (cursor.moveToFirst()) {
  do {
  try {
   m_year_s=cursor.getInt(cursor.getColumnIndex("m"+year+"s"));
   d_year_s=cursor.getInt(cursor.getColumnIndex("d"+year+"s"));
   m_year_f=cursor.getInt(cursor.getColumnIndex("m"+year+"f"));
   d_year_f=cursor.getInt(cursor.getColumnIndex("d"+year+"f"));
   
   if(m_year_s!=0){
   if(m_year_s==m_year_f){
    if(m_year_s==month){
    if(d_year_s<=data && d_year_f>=data)
     unmovable_holiday_new.add(cursor.getString(cursor.getColumnIndex("tropari_kondaki_text")));
    }
   }else{
    if(m_year_s==month || m_year_f==month){
    if((d_year_s<=data && m_year_s==month) || (d_year_f>=data && m_year_f==month)){
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
 sql = "SELECT tropari_kondaki_text FROM tropari_kondaki_holiday_one WHERE m" + year + "=" + month
  + " AND d" + year + "=" + data;

 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
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
 
 /*int k1=unmovable_holiday_new.size();
 int k2=prayers.length;
 int k=k1+k2;
 prayers_final=new String[k];
 
 for(int i=0;i<k1;i++){
 prayers_final[i]=unmovable_holiday_new.get(i);
 }
 
 for(int j=k1;j<k2+k1;j++){
 prayers_final[j]=prayers[j-k1];
 }*/
}
//-----------------------------------------------------

// создаем тропари для високосного года
public void newVisYearPrayers() {
 String tmp_text_vis_years = "";
 cursor = null;
 sql = "select tropari_kondaki_day from data_calendar_leap_year where month="
  + month + " AND day=" + data + ";";
 // Log.d(TAG,"запросDayGR= " + sql);
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
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
 if (i != -1)
  prayers_final = tmp_text_vis_years.split("###");
 else {
  prayers_final = new String[1];
  prayers_final[0] = tmp_text_vis_years;
 }
 
 //prayers_title=null;
 createTroparKondakLink();
 //createTitlePrayers();
 //replace_tk();
 //saveTextTkPreferences();
 }
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
 int day_week = cal.getDayWeek();
 switch (day_week) {
 case 1:
 // воскресенье

 String sedmitsa = "";
 sql = "SELECT _id, r" + year + " FROM data_calendar WHERE month="
  + month + " AND day=" + data + ";";

 db = DatabaseHelper.getInstance(getActivity()
  .getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  try {

   sedmitsa = cursor.getString(cursor.getColumnIndex("r"
    + year));

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
 db = DatabaseHelper.getInstance(getActivity()
  .getApplicationContext());
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
 prayers_tmp[index - 1] = unmovable_holiday_text.get(i);//----
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
 /*if (prayers_day_count != 0) {
 prayers_tmp[count - 1] = prayers_day;
 }*/

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

public void createTroparKondakLink(){
 int end = 0;
 for (int i = 0; i < (prayers_final.length); i++) {
 end = prayers_final[i].indexOf("\r\n");
 prayers_tk_link = prayers_tk_link + "• <a href=\"activity-run://DescriptionOtherActivityHost?id_tropar_kondak="+i+"."+year+"."+month+"."+data+"."+pageNumber+"\">"+prayers_final[i].substring(0, end)+"</a><br>";
 end=0;
 }
 prayers_tk_link=prayers_tk_link.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
}

// копируем ссылки на молитвы, акафисты для високосного года
public void newVisYearPrayersLink() {
 String day_sedmits_pr_link = "";

 day_sedmits_pr_link = createDaySedmitsLinkText();
 
 String tmp_text_vis_years = "";
 cursor = null;
 sql = "select prayers_link from data_calendar_leap_year where month="
  + month + " AND day=" + data + ";";
 // Log.d(TAG,"запросDayGR= " + sql);
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null && cursor.getCount() > 0) {
 if (cursor.moveToFirst()) {
  try {
  tmp_text_vis_years = cursor.getString(cursor
   .getColumnIndex("prayers_link"));
  } catch (NumberFormatException e) {
  // Log.d(TAG, "ERROR=" + e.toString());
  }
 }
 }

 if (tmp_text_vis_years.equals("#")) {
 prayers_pr_ak_link = day_sedmits_pr_link + "<br>";
 prayers_pr_ak_link = prayers_pr_ak_link.replace("\r\n", "<br>");
 prayers_pr_ak_link=prayers_pr_ak_link.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
 } else {
 prayers_pr_ak_link = day_sedmits_pr_link + "<br><br>" + tmp_text_vis_years;
 prayers_pr_ak_link = prayers_pr_ak_link.replace("\r\n", "<br>");
 prayers_pr_ak_link=prayers_pr_ak_link.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
 }
}

public String createDaySedmitsLinkText(){
 
 String text_prayers_day_sedmits="";
 // день недели (воскресенье - 1; субота - 7)
 int day_week = cal.getDayWeek();
 switch (day_week) {
 case 1:
  text_prayers_day_sedmits="В воскресенье";
  break;
 case 2:
  // пн
  text_prayers_day_sedmits="В понедельник";
  break;
 case 3:
  // вт
  text_prayers_day_sedmits="Во вторник";
  break;
 case 4:
  // ср
  text_prayers_day_sedmits="В среду";
  break;
 case 5:
  // чт
  text_prayers_day_sedmits="В четверг";
  break;
 case 6:
  // пт
  text_prayers_day_sedmits="В пятницу";
  break;
 case 7:
  // сб
  text_prayers_day_sedmits="В субботу";
  break;

 default:
  break;
 }
 text_prayers_day_sedmits="• <a href=\"activity-run://DescriptionOtherActivityHost?id_prayers_day_sedmits=100."+year+"."+month+"."+data+"."+pageNumber+"\">"+text_prayers_day_sedmits+"</a>";
 return text_prayers_day_sedmits;
}

public void createPrayersLinkText() {
 String date_calendar_pr_link="";
 String gr_unmovable_pr_link="";
 String big_unmovable_pr_link="";
 String unmovable_pr_link="";
 String day_sedmits_pr_link="";
 
 day_sedmits_pr_link=createDaySedmitsLinkText();
 
 sql = "SELECT prayers_link FROM data_calendar WHERE month="
  + month + " AND day=" + data;

 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null) {
 if (cursor.moveToFirst()) {
  try {

  date_calendar_pr_link = cursor.getString(cursor
   .getColumnIndex("prayers_link"));

  } catch (NumberFormatException e) {
  // Log.d(TAG, "ERROR=" + e.toString());
  }
 }
 }

 sql = "SELECT prayers_link FROM gr_unmovable_holiday WHERE m" + year + "=" + month
  + " AND d" + year + "=" + data;

 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null) {
 if (cursor.moveToFirst()) {
  do {
  try {

   gr_unmovable_pr_link = cursor.getString(cursor
    .getColumnIndex("prayers_link"));

  } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
  }
  } while (cursor.moveToNext());
 }
 }
 
 sql = "SELECT prayers_link FROM big_unmovable_holiday WHERE m" + year + "=" + month
  + " AND d" + year + "=" + data;

 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null) {
 if (cursor.moveToFirst()) {
  do {
  try {

   big_unmovable_pr_link = cursor.getString(cursor
    .getColumnIndex("prayers_link"));

  } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
  }
  } while (cursor.moveToNext());
 }
 }
 
 sql = "SELECT prayers_link FROM unmovable_holiday WHERE m" + year + "=" + month
  + " AND d" + year + "=" + data;

 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null) {
 if (cursor.moveToFirst()) {
  do {
  try {

   unmovable_pr_link = cursor.getString(cursor
    .getColumnIndex("prayers_link"));

  } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
  }
  } while (cursor.moveToNext());
 }
 }

 
 if(gr_unmovable_pr_link.length()>2 || big_unmovable_pr_link.length()>2 || date_calendar_pr_link.length()>2 || unmovable_pr_link.length()>2)
 day_sedmits_pr_link=day_sedmits_pr_link+"<br><br>";
 else
 day_sedmits_pr_link=day_sedmits_pr_link+"<br>";
 
 if(date_calendar_pr_link.length()<2) date_calendar_pr_link="";
 else {
 if(unmovable_pr_link.length()<2) date_calendar_pr_link=date_calendar_pr_link+"<br>";
 else date_calendar_pr_link=date_calendar_pr_link+"<br><br>";
 }
 
 if(gr_unmovable_pr_link.length()<2) gr_unmovable_pr_link="";
 else {
 if(big_unmovable_pr_link.length()<2 && date_calendar_pr_link.length()<2 && unmovable_pr_link.length()<2)
  gr_unmovable_pr_link=gr_unmovable_pr_link+"<br>";
 else
  gr_unmovable_pr_link=gr_unmovable_pr_link+"<br><br>";
 }
 
 if(big_unmovable_pr_link.length()<2) big_unmovable_pr_link="";
 else {
 if(date_calendar_pr_link.length()<2 && unmovable_pr_link.length()<2)
  big_unmovable_pr_link=big_unmovable_pr_link+"<br>";
 else
  big_unmovable_pr_link=big_unmovable_pr_link+"<br><br>";
 }
 
 if(unmovable_pr_link.length()<2) unmovable_pr_link="";
 else unmovable_pr_link=unmovable_pr_link+"<br>";
 
 prayers_pr_ak_link=day_sedmits_pr_link+gr_unmovable_pr_link+big_unmovable_pr_link+date_calendar_pr_link+unmovable_pr_link;
 prayers_pr_ak_link = prayers_pr_ak_link.replace("\r\n", "<br>");
 if(prayers_pr_ak_link.length()==0) {
 //MyView_prayers_links_title.setVisibility(View.GONE);
 prayers_pr_ak_link=text_prayers_links;
 }
 prayers_pr_ak_link=prayers_pr_ak_link.replace("DescriptionOtherActivityHost", GlobalData.getDESCRIPTION_OTHER_ACTIVITY_HOST());
}

public void replace_tk() {
 
 String tmp="";
 for (int i = 0; i < (prayers_final.length); i++) {
 if (!prayers_final[i].equals("#")) {
  
  tmp = prayers_final[i].replace("Тропарь", "<FONT COLOR=#aa2c2c>Тропарь</FONT>");
  tmp = tmp.replace("Ин тропарь", "<FONT COLOR=#aa2c2c>Ин тропарь</FONT>");
  tmp = tmp.replace("Иной тропарь", "<FONT COLOR=#aa2c2c>Ин тропарь</FONT>");

  tmp = tmp.replace("Кондак", "<FONT COLOR=#aa2c2c>Кондак</FONT>");
  tmp = tmp.replace("Ин кондак", "<FONT COLOR=#aa2c2c>Ин кондак</FONT>");
  tmp = tmp.replace("Иной кондак", "<FONT COLOR=#aa2c2c>Ин кондак</FONT>");

  tmp = tmp.replace("Величание", "<FONT COLOR=#aa2c2c>Величание</FONT>");
  tmp = tmp.replace("Величания", "<FONT COLOR=#aa2c2c>Величания</FONT>");
  tmp = tmp
   .replace("Ин величание", "<FONT COLOR=#aa2c2c>Ин величание</FONT>");

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

  prayers_final[i] = tmp;
 }else{
  prayers_final[i]="";
 }
 }

}


public void createTitlePrayers() {

 String prayers_title = "";
 int end = 0;
 for (int i = 0; i < (prayers_final.length); i++) {
 if (!prayers_final[i].equals("#")) {
  end = prayers_final[i].indexOf("\r\n");
  prayers_title = prayers_final[i].substring(0, end);
  prayers_final[i] = prayers_final[i]
   .replace(prayers_title, ("<FONT COLOR=#aa2c2c><b>"
    + prayers_title + "</b></FONT>"));
  end = 0;
 }
 }
}

@Override
public void onDestroy() {
 // TODO Auto-generated method stub
 super.onDestroy();
 Log.d(TAG, "PageFragmentViewPagerDay===onDestroy()");
 if (cursor != null)
 cursor.close();
 if (db != null)
 db.closeConnecion();
}


}
