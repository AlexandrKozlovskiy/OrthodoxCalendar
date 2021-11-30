package oleksandr.kotyuk.orthodoxcalendar.fragments;

import oleksandr.kotyuk.orthodoxcalendar.DescriptionActivity;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.PsalturActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.adapters.MySimpleCursorTreeAdapterPsaltur;
import oleksandr.kotyuk.orthodoxcalendar.db.DatabaseHelper;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import oleksandr.kotyuk.orthodoxcalendar.MyView;

public class FragmentPrayers extends Fragment {

public static final String IMAGE_RESOURCE_ID = "iconResourceID";
public static final String ITEM_NAME = "itemName";

static final String TAG = "myLogs";
private String prayersType="pr";
// Calendar calendar;
private DatabaseHelper db;
Cursor cursor;

String text_size = "0";
String prayers_language;
String prayers_fonts_ru="1";
String prayers_fonts_cs="1";
String sql="";

ExpandableListView elvMain;
SimpleCursorTreeAdapter sctAdapter;

String[] groupFrom;
    int[] groupTo;
    String[] childFrom;
    int[] childTo;
    
    View v;

public FragmentPrayers(){
 
}
public void setPrayersType(String prayersType) {
    this.prayersType=prayersType;
}
@SuppressLint("InflateParams")
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 //return inflater.inflate(R.layout.fragment_view_pager_psaltur, null);
 v = inflater.inflate(R.layout.fragment_view_pager_prayers_pr, null);
 return v;
}
private void inflateList() {
    if(db!=null) {
db.closeConnecion();
db =null;
    }
    db = DatabaseHelper.getInstance(getActivity()
            .getApplicationContext());
    String language=!prayers_language.equals("ru") &&prayersType.equals("kafisma")?"sc":prayers_language;
    if(prayersType.equals("kafisma")) cursor = db.executeQuery("SELECT _id, kafisma_"+language+" FROM psaltur_group;");
    else cursor = db.executeQuery("SELECT _id, name_group FROM prayers_"+language+"_"+prayersType+"_group;");
    // сопоставление данных и View для групп
    if(prayersType.equals("kafisma")) groupFrom = new String[] {"kafisma_"+language};
    else groupFrom =new String []{ "name_group" };
    groupTo =new int [] { R.id.text_list_group };
    // сопоставление данных и View для элементов
    childFrom = prayersType.equals("kafisma")? new String[]{"psalom_name_ru"}:new String []{"name_prayers"};
    childTo = new int [] { R.id.text_list_child };

    // создаем адаптер и настраиваем список
    MySimpleCursorTreeAdapterPsaltur sctAdapter = new MyAdapter(getActivity().getApplicationContext(), cursor,
            R.layout.my_simple_expandable_list_item_1, groupFrom,
            groupTo, R.layout.my_simple_list_item_1, childFrom,
            childTo);
    if(elvMain==null) elvMain = (ExpandableListView) v.findViewById(R.id.elv_prayers_pr);
    elvMain.setAdapter(sctAdapter);
}

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if(args!=null &&args.containsKey("prayers_type")) setPrayersType(args.getString("prayers_type"));
    }

    @Override
public void onActivityCreated(Bundle savedInstanceState) {
 // TODO Auto-generated method stub
 super.onActivityCreated(savedInstanceState);

prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
  getActivity(), "pref_prayers_language", "ru");

    prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_"+prayers_language, "1");
    inflateList();
    elvMain.setOnChildClickListener(new OnChildClickListener() {
 @Override
 public boolean onChildClick(ExpandableListView paramExpandableListView,
  View paramView, int paramInt1, int paramInt2, long paramLong) {
  Log.d(TAG, "paramInt1="+paramInt1+" paramInt2="+paramInt2+" paramLong="+paramLong);
  
  // создаем новое окно просмотра статей, передаем номер раздела

  String message;
         MyView tx = (MyView) paramView;
         message=tx.getText().toString();
  
  Intent intent = new Intent(getActivity(), prayersType.equals("kafisma")? PsalturActivity.class:DescriptionActivity.class);
  if(prayersType.equals("kafisma")) intent.putExtra("psalm_id", (int) paramLong);
  else {
      intent.putExtra("prayers_id", (int) paramLong);
      intent.putExtra("id", 5);
      intent.putExtra("prayers_name", message);
      intent.putExtra("prayers_type", prayersType);
  }
  startActivity(intent);
  return true;
 }
 });
}

class MyAdapter extends MySimpleCursorTreeAdapterPsaltur {

  Context context;
     public MyAdapter(Context context, Cursor cursor, int groupLayout,
         String[] groupFrom, int[] groupTo, int childLayout,
         String[] childFrom, int[] childTo) {
       super(context, cursor, groupLayout, groupFrom, groupTo,
           childLayout, childFrom, childTo);
       this.context=context;
     }

     protected Cursor getChildrenCursor(Cursor groupCursor) {
       // получаем курсор по элементам для конкретной групы
       int idColumn = groupCursor.getInt(groupCursor.getColumnIndex("_id"));

       prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
   getActivity(), "pref_prayers_language", "ru");

       String sql2=prayersType.equals("kafisma")?"select * from psaltur where id_kafist="+idColumn:"select * from prayers_"+prayers_language+"_"+prayersType+" where id_group="+idColumn;
  db = DatabaseHelper.getInstance(context);

  // готовим данные по группам для адаптера
  return db.executeQuery(sql2);
       //return db.getPhoneData(groupCursor.getInt(idColumn));
     }
   }

@Override
public void onResume() {
 // TODO Auto-generated method stub
 super.onResume();
 Log.d(TAG, "onResume()");
boolean needUpdate=false;
 if (!PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(),
  "pref_prayers_language", "ru").equals(prayers_language)) {
 Log.d(TAG, "onResume()22222");
 prayers_language = PreferencesActivity.MyPreferenceFragment
  .ReadString(getActivity(), "pref_prayers_language", "ru");
     prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_"+prayers_language, "1");
needUpdate=true;
 }
 
 String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(
  getActivity(), "pref_text_size", "0");
 if (!text_size.equals(tmp)) {
 text_size = tmp;
needUpdate=true;
 }
 
 String tmp_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_ru", "1");
 String tmp_fonts_cs=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_cs", "1");
 if(!tmp_fonts_ru.equals(prayers_fonts_ru) || !tmp_fonts_cs.equals(prayers_fonts_cs)){
  prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_ru", "1");
 prayers_fonts_cs=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_cs", "1");
needUpdate=true;
 }
 if(needUpdate) inflateList();
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

}
