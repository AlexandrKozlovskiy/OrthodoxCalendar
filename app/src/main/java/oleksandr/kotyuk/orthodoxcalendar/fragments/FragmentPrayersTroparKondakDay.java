package oleksandr.kotyuk.orthodoxcalendar.fragments;

import java.util.ArrayList;

import oleksandr.kotyuk.orthodoxcalendar.PrayersTroparKondarActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.adapters.MyPrayersTKArrayAdapter;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentPrayersTroparKondakDay extends ListFragment implements
 OnClickListener {

public static final String IMAGE_RESOURCE_ID = "iconResourceID";
public static final String ITEM_NAME = "itemName";

static final String TAG = "myLogs";

private DatabaseHelper db;
Cursor cursor;
String sql;

// int prayers_position[];
String[] mas_date_calendar;
String prayers_tmp[];
String prayers[];
String prayers_final[];
String prayers_title[];

String prayers_day="#";
int prayers_day_count=1;

int data;
int month;
int year;
MyCalendar cal = MyCalendar.getInstance();

String date_calendar;
ArrayList<String> unmovable_holiday_text_tmp = new ArrayList<String>();
ArrayList<String> unmovable_holiday_index_tmp = new ArrayList<String>();

ArrayList<String> unmovable_holiday_text = new ArrayList<String>();
ArrayList<String> unmovable_holiday_index = new ArrayList<String>();

ArrayList<String> arr_prayers_tmp = new ArrayList<String>();

ArrayList<String> unmovable_holiday_new = new ArrayList<String>();

public FragmentPrayersTroparKondakDay() {

}

@Override
public void onActivityCreated(Bundle savedInstanceState) {
 // TODO Auto-generated method stub
 super.onActivityCreated(savedInstanceState);

 cal.setTodayDate();
 data = cal.getDayMonth();
 month = cal.getMonth() + 1;
 year = cal.getYear();

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
 if(!date_calendar.equals("#")){
 if(!prayers_day.equals("#"))
  date_calendar=prayers_day+"***\r\n\r\n"+date_calendar;
 }else
 date_calendar=prayers_day;
 
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
 if (!mas_date_calendar[0].equals("#")) k=mas_date_calendar.length;
 
 int l = k + unmovable_holiday_text_tmp.size();
 prayers_tmp = new String[l];

 if (unmovable_holiday_text_tmp.size() != 0) {
 sortLists();
 addPrayers(l); 
 } else {
 prayers = (String[]) mas_date_calendar.clone();
 }
 //newPositionPrayers();//*****
 //--------------------------
 addNewPrayersUnmovable();
 //--------------------------
 createTitlePrayers();
 
 newVisYearPrayers();
 
 MyPrayersTKArrayAdapter adapter = new MyPrayersTKArrayAdapter(
  getActivity(), R.layout.my_list_item_prayers_tk, prayers_title);
 setListAdapter(adapter);
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
 
 int k1=unmovable_holiday_new.size();
 int k2=prayers.length;
 int k=k1+k2;
 prayers_final=new String[k];
 
 for(int i=0;i<k1;i++){
 prayers_final[i]=unmovable_holiday_new.get(i);
 }
 
 for(int j=k1;j<k2+k1;j++){
 prayers_final[j]=prayers[j-k1];
 }
}
//-----------------------------------------------------

//создаем тропари для високосного года
public void newVisYearPrayers(){
 String tmp_text_vis_years="";
 if(year==2020){
 if((month==2 && data==29) || (month==3 && (data>0 && data<14))){
  cursor = null;
  sql = "select tropari_kondaki_day from data_calendar_leap_year where month=" + month + " AND day="+ data+";";
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
  
  prayers_title=null;
  createTitlePrayers();
  }
 }
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

public void addPrayersDay(){
 
 // день недели (воскресенье - 1; субота - 7)
 int day_week=cal.getDayWeek();
 switch (day_week) {
 case 1:
 //воскресенье
 
 String sedmitsa="";
 sql = "SELECT _id, r"+cal.getYear()+" FROM data_calendar WHERE month="+ (cal.getMonth() + 1) +" AND day="+cal.getDayMonth()+ ";";
 
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  try {
   
   sedmitsa=cursor.getString(cursor.getColumnIndex("r"+cal.getYear()));

  } catch (NumberFormatException e) {
   // Log.d(TAG, "ERROR=" + e.toString());
  }
  }
 }
 
 if(!sedmitsa.equals("#") && sedmitsa.indexOf("Глас")!=-1){
  if(sedmitsa.indexOf("Глас 1")!=-1) sql="select text from tropari_kondaki_day_sedmits where _id=7";
  if(sedmitsa.indexOf("Глас 2")!=-1) sql="select text from tropari_kondaki_day_sedmits where _id=8";
  if(sedmitsa.indexOf("Глас 3")!=-1) sql="select text from tropari_kondaki_day_sedmits where _id=9";
  if(sedmitsa.indexOf("Глас 4")!=-1) sql="select text from tropari_kondaki_day_sedmits where _id=10";
  if(sedmitsa.indexOf("Глас 5")!=-1) sql="select text from tropari_kondaki_day_sedmits where _id=11";
  if(sedmitsa.indexOf("Глас 6")!=-1) sql="select text from tropari_kondaki_day_sedmits where _id=12";
  if(sedmitsa.indexOf("Глас 7")!=-1) sql="select text from tropari_kondaki_day_sedmits where _id=13";
  if(sedmitsa.indexOf("Глас 8")!=-1) sql="select text from tropari_kondaki_day_sedmits where _id=14";
 }else{
  prayers_day_count=0;
 }
 break;
 case 2:
 //пн
 sql="select text from tropari_kondaki_day_sedmits where _id=1";
 break;
 case 3:
 //вт
 sql="select text from tropari_kondaki_day_sedmits where _id=2";
 break;
 case 4:
 //ср
 sql="select text from tropari_kondaki_day_sedmits where _id=3";
 break;
 case 5:
 //чт
 sql="select text from tropari_kondaki_day_sedmits where _id=4";
 break;
 case 6:
 //пт
 sql="select text from tropari_kondaki_day_sedmits where _id=5";
 break;
 case 7:
 //сб
 sql="select text from tropari_kondaki_day_sedmits where _id=6";
 break;
  
 default:
 break;
 }
 
 if (prayers_day_count != 0) {
 db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
 cursor = db.executeQuery(sql);

 if (cursor != null) {
  if (cursor.moveToFirst()) {
  try {
   
   prayers_day=cursor.getString(cursor.getColumnIndex("text"));

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
  if(index==mas_date_calendar.length) break;
 }
 }
 /*if(prayers_day_count!=0){
 prayers_tmp[count-1]=prayers_day;
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

public void createTitlePrayers() {
 prayers_title = new String[prayers_final.length + 1];
 prayers_title[0] = "Все тропари и кондаки дня";
 int end = 0;
 for (int i = 0; i < (prayers_final.length); i++) {
 end = prayers_final[i].indexOf("\r\n");
 prayers_title[i + 1] = prayers_final[i].substring(0, end);
 prayers_final[i]=prayers_final[i].replace(prayers_title[i + 1], ("<FONT COLOR=#aa2c2c><b>"+prayers_title[i + 1]+"</b></FONT>"));
 end=0;
 }
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

@Override
public void onResume() {
 // TODO Auto-generated method stub
 super.onResume();
 Log.d(TAG, "onResume()");
 
 MyPrayersTKArrayAdapter adapter = new MyPrayersTKArrayAdapter(
  getActivity(), R.layout.my_list_item_prayers_tk, prayers_title);
 setListAdapter(adapter);
}

public void onListItemClick(ListView l, View v, int position, long id) {
 super.onListItemClick(l, v, position, id);
 String prayers_tk = "";
 if(position==0) prayers_tk =newPrayer();
 else prayers_tk=prayers_final[position-1];
 
 prayers_tk=replace_tk(prayers_tk);
 
 Intent intent = new Intent(getActivity(),
  PrayersTroparKondarActivity.class);
 intent.putExtra("prayers_tk", prayers_tk);
 startActivity(intent);

}

public String newPrayer(){
 String text="";
 for(int i=0;i<prayers_final.length;i++){
 text=text+prayers_final[i]+"<br>";
 }
 return text;
}
public String replace_tk(String text){
 String tmp=text.replace("Тропарь", "<FONT COLOR=#aa2c2c>Тропарь</FONT>");
 tmp=tmp.replace("Ин тропарь", "<FONT COLOR=#aa2c2c>Ин тропарь</FONT>");
 tmp=tmp.replace("Иной тропарь", "<FONT COLOR=#aa2c2c>Ин тропарь</FONT>");
 
 tmp=tmp.replace("Кондак", "<FONT COLOR=#aa2c2c>Кондак</FONT>");
 tmp=tmp.replace("Ин кондак", "<FONT COLOR=#aa2c2c>Ин кондак</FONT>");
 tmp=tmp.replace("Иной кондак", "<FONT COLOR=#aa2c2c>Ин кондак</FONT>");
 
 tmp=tmp.replace("Величание", "<FONT COLOR=#aa2c2c>Величание</FONT>");
 tmp=tmp.replace("Величания", "<FONT COLOR=#aa2c2c>Величания</FONT>");
 tmp=tmp.replace("Ин величание", "<FONT COLOR=#aa2c2c>Ин величание</FONT>");
 
 tmp=tmp.replace("Вместо же Трисвятаго", "<FONT COLOR=#aa2c2c>Вместо же Трисвятаго</FONT>");
 
 tmp=tmp.replace("Задостойник", "<FONT COLOR=#aa2c2c>Задостойник</FONT>");
 
 tmp=tmp.replace("Стихира", "<FONT COLOR=#aa2c2c>Стихира</FONT>");
 
 tmp=tmp.replace("Ирмос", "<FONT COLOR=#aa2c2c>Ирмос</FONT>");
 
 tmp=tmp.replace("Ипакои", "<FONT COLOR=#aa2c2c>Ипакои</FONT>");
 
 tmp=tmp.replace("Богородичен", "<FONT COLOR=#aa2c2c>Богородичен</FONT>");
 
 tmp=tmp.replace("Богородичен отпустительный", "<FONT COLOR=#aa2c2c>Богородичен отпустительный</FONT>");
 
 tmp=tmp.replace("Богородичен Догматик", "<FONT COLOR=#aa2c2c>Богородичен Догматик</FONT>");

 return tmp;
}

@SuppressLint("InflateParams")
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 return inflater.inflate(R.layout.fragment_view_pager_prayers, null);
}

@Override
public void onDestroyView() {
 // TODO Auto-generated method stub
 super.onDestroyView();
 Log.d(TAG, "FragmentPrayers===onDestroyView()");
 if (cursor != null)
 cursor.close();
 if (db != null)
 db.closeConnecion();
}

@Override
public void onClick(View v) {
 // TODO Auto-generated method stub

}

}
