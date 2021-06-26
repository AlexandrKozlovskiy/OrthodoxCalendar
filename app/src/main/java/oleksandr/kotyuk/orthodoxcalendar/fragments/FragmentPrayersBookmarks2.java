package oleksandr.kotyuk.orthodoxcalendar.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import com.mobeta.android.dslv.DragSortListView;

import oleksandr.kotyuk.orthodoxcalendar.PrayersBookmarksActivity;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.adapters.MyPrayersArrayAdaptersBookmarks;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class FragmentPrayersBookmarks2 extends ListFragment implements OnClickListener, OnScrollListener{

public static final String IMAGE_RESOURCE_ID = "iconResourceID";
public static final String ITEM_NAME = "itemName";

static final String TAG = "myLogs";

//private DatabaseHelper db;
//Cursor cursor;
int prayers_group[];
int prayers_position[];
String prayers[];
String prayers_language;
ArrayList <String> arr_bookmark;

ArrayList <String> arr_prayers;

String text_message="";

MyPrayersArrayAdaptersBookmarks adapter;

DragSortListView lv;

int list_position=0;

//int id_remove_bookmarks=-1;

private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
 @Override
 public void drop(int from, int to) {
  if(arr_bookmark!=null){
  int size_arr=arr_bookmark.size();
  if(size_arr>1){
  String str_arr=arr_bookmark.get(from);
  arr_bookmark.remove(from);
  arr_bookmark.add(to, str_arr);
  if (prayers_language.equals("ru")) {
   PreferencesActivity.MyPreferenceFragment.putList(getActivity(), "new_prayers_bookmarks_ru", arr_bookmark);
  } 
  if (prayers_language.equals("cs")) {
   PreferencesActivity.MyPreferenceFragment.putList(getActivity(), "new_prayers_bookmarks_cs", arr_bookmark);
  }
  }
  
  prayers = new String[arr_bookmark.size()];
  prayers_group = new int[arr_bookmark.size()];
  prayers_position = new int[arr_bookmark.size()];
  for(int i=0; i<arr_bookmark.size();i++){
  String [] text_line_bookmark=arr_bookmark.get(i).split("###");
  prayers_group[i]=Integer.parseInt(text_line_bookmark[0]);
  prayers_position[i]=Integer.parseInt(text_line_bookmark[1]);
  
  if (prayers_language.equals("ru") && text_line_bookmark[2].equals("Молитва за всякого усопшего") && text_line_bookmark[1].equals("218")) {
   prayers[i]="Молитва за умирающего";
  }else
   prayers[i]=text_line_bookmark[2];
     }
 }
 String item = adapter.getItem(from);
 //Toast.makeText(getApplicationContext(), "drop", Toast.LENGTH_SHORT).show();
 adapter.notifyDataSetChanged();
 adapter.remove(item);
 adapter.insert(item, to);
 }
};

private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
 @Override
 public void remove(int which) {
 //id_remove_bookmarks=which;
 
 //DialogFragment dialog_bookmarks= new MyDialogPrayersBookmarks();
 //dialog_bookmarks.show(getFragmentManager(), "dialog_prayers_bookmarks");
 
 if(arr_bookmark!=null){
  int size_arr=arr_bookmark.size();
  if(size_arr>1 && size_arr>which){
  arr_bookmark.remove(which);
  if (prayers_language.equals("ru")) {
   PreferencesActivity.MyPreferenceFragment.putList(getActivity(), "new_prayers_bookmarks_ru", arr_bookmark);
  } 
  if (prayers_language.equals("cs")) {
   PreferencesActivity.MyPreferenceFragment.putList(getActivity(), "new_prayers_bookmarks_cs", arr_bookmark);
  }
  }
  
  if(size_arr==1){
  if (prayers_language.equals("ru")) {
   arr_bookmark.clear();
   PreferencesActivity.MyPreferenceFragment.putList(getActivity(), "new_prayers_bookmarks_ru", arr_bookmark);
  } 
  if (prayers_language.equals("cs")) {
   arr_bookmark.clear();
   PreferencesActivity.MyPreferenceFragment.putList(getActivity(), "new_prayers_bookmarks_cs", arr_bookmark);
  }
  }
  adapter.remove(adapter.getItem(which));
  
  prayers = new String[arr_bookmark.size()];
  prayers_group = new int[arr_bookmark.size()];
  prayers_position = new int[arr_bookmark.size()];
  for(int i=0; i<arr_bookmark.size();i++){
  String [] text_line_bookmark=arr_bookmark.get(i).split("###");
  prayers_group[i]=Integer.parseInt(text_line_bookmark[0]);
  prayers_position[i]=Integer.parseInt(text_line_bookmark[1]);
  
  if (prayers_language.equals("ru") && text_line_bookmark[2].equals("Молитва за всякого усопшего") && text_line_bookmark[1].equals("218")) {
   prayers[i]="Молитва за умирающего";
  }else
   prayers[i]=text_line_bookmark[2];
     }
 }
 }
};

public FragmentPrayersBookmarks2(){
 
}

@Override
public void onActivityCreated(Bundle savedInstanceState) {
 // TODO Auto-generated method stub
 super.onActivityCreated(savedInstanceState);
 
 lv = (DragSortListView) getListView();

 lv.setDropListener(onDrop);
 lv.setRemoveListener(onRemove);
 
 prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
  getActivity(), "pref_prayers_language", "ru");
 
 if (prayers_language.equals("ru")) {

 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(getActivity(), "new_prayers_bookmarks_ru");
 text_message="Вы еще не добавляли в Избранное!!!";
 } 
 if (prayers_language.equals("cs")) {
 arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(getActivity(), "new_prayers_bookmarks_cs");
 text_message="Вы еще не добавлzли в Избранное!!!";
 }
 if(arr_bookmark==null){
 prayers = new String[1];
 prayers_group = new int[1];
 prayers_position = new int[1];
 prayers[0] = text_message;
 prayers_group[0]=-1;
 prayers_position[0]=-1;
 }else{
 prayers = new String[arr_bookmark.size()];
 prayers_group = new int[arr_bookmark.size()];
 prayers_position = new int[arr_bookmark.size()];
 
 for(int i=0; i<arr_bookmark.size();i++){
  String [] text_line_bookmark=arr_bookmark.get(i).split("###");
  prayers_group[i]=Integer.parseInt(text_line_bookmark[0]);
  prayers_position[i]=Integer.parseInt(text_line_bookmark[1]);
  
  if (prayers_language.equals("ru") && text_line_bookmark[2].equals("Молитва за всякого усопшего") && text_line_bookmark[1].equals("218")) {
  prayers[i]="Молитва за умирающего";
  }else
  prayers[i]=text_line_bookmark[2];
     }
 }
 arr_prayers=new ArrayList<String>(Arrays.asList(prayers));
 
 adapter = new MyPrayersArrayAdaptersBookmarks(getActivity(), R.layout.my_list_item_click_drag_remove, R.id.text, arr_prayers);
 setListAdapter(adapter);
 
 getListView().setOnScrollListener(this);
 
 if(arr_bookmark==null && prayers.length==1)adapter.remove(adapter.getItem(0));
 
 //adapter = new ArrayAdapter<String>(this,R.layout.list_item_click_remove, R.id.text, list);
 //setListAdapter(adapter);
}


@Override
public void onResume() {
 // TODO Auto-generated method stub
 super.onResume();
 Log.d(TAG, "onResume()");
 /*if (!PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(),
  "pref_prayers_language", "ru").equals(prayers_language)) {*/
 lv = (DragSortListView) getListView();

 lv.setDropListener(onDrop);
 lv.setRemoveListener(onRemove);

  prayers_language = PreferencesActivity.MyPreferenceFragment
   .ReadString(getActivity(), "pref_prayers_language", "ru");
  
  if (prayers_language.equals("ru")) {

  arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(getActivity(), "new_prayers_bookmarks_ru");
  text_message="Вы еще не добавляли в Избранное!!!";
  } 
  if (prayers_language.equals("cs")) {
  arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(getActivity(), "new_prayers_bookmarks_cs");
  text_message="Вы еще не добавлzли в Избранное!!!";
  }
  if(arr_bookmark==null){
  prayers=null;
  prayers = new String[1];
  prayers_group=null;
  prayers_group = new int[1];
  prayers_position=null;
  prayers_position = new int[1];
  prayers[0] = text_message;
  prayers_group[0]=-1;
  prayers_position[0]=-1;
  }else{
  prayers = new String[arr_bookmark.size()];
  prayers_group = new int[arr_bookmark.size()];
  prayers_position = new int[arr_bookmark.size()];
  
  for(int i=0; i<arr_bookmark.size();i++){
   String [] text_line_bookmark=arr_bookmark.get(i).split("###");
   prayers_group[i]=Integer.parseInt(text_line_bookmark[0]);
   prayers_position[i]=Integer.parseInt(text_line_bookmark[1]);
   
   if (prayers_language.equals("ru") && text_line_bookmark[2].equals("Молитва за всякого усопшего") && text_line_bookmark[1].equals("218")) {
   prayers[i]="Молитва за умирающего";
   }else
   prayers[i]=text_line_bookmark[2];
      }
  }
  arr_prayers=new ArrayList<String>(Arrays.asList(prayers));

  adapter = new MyPrayersArrayAdaptersBookmarks(
   getActivity(), R.layout.my_list_item_click_drag_remove, R.id.text, arr_prayers);
  setListAdapter(adapter);
 //}
  if(arr_bookmark==null && prayers.length==1)adapter.remove(adapter.getItem(0));
  
  getListView().setOnScrollListener(this);
  getListView().setSelection(list_position);
 //}
}

public class MyDialogPrayersBookmarks extends DialogFragment implements OnClickListener {

   final String LOG_TAG = "myLogs";

   public Dialog onCreateDialog(Bundle savedInstanceState) {
   return new AlertDialog.Builder(getActivity())
  // Set Dialog Icon
  //.setIcon(R.drawable.androidhappy)
  // Set Dialog Title
  .setTitle(R.string.prayers_dialog1)
  // Set Dialog Message
  .setMessage(R.string.prayers_dialog2)

  // Positive button
  .setPositiveButton(R.string.bible_dialog1, new DialogInterface.OnClickListener() {
  public void onClick(DialogInterface dialog, int which) {
   //Toast.makeText(getActivity(), "YES", Toast.LENGTH_LONG).show();
 
  }
  })

  // Negative Button
  .setNegativeButton(R.string.bible_dialog2, new DialogInterface.OnClickListener() {
  public void onClick(DialogInterface dialog,int which) {
   //Toast.makeText(getActivity(), "NO", Toast.LENGTH_LONG).show();

  }
  }).create();
 }

 @Override
 public void onClick(View v) {
 // TODO Auto-generated method stub
 
 }
}

public void onListItemClick(ListView l, View v, int position, long id) {
 super.onListItemClick(l, v, position, id);
 if(prayers_group[0]!=-1){
 list_position=l.getFirstVisiblePosition();
 Intent intent = new Intent(getActivity(), PrayersBookmarksActivity.class);
 intent.putExtra("arr_position", position);
 intent.putExtra("prayers_group", prayers_group[position]);
 intent.putExtra("prayers_position", prayers_position[position]);
 intent.putExtra("prayers_name", prayers[position]);
 startActivity(intent);
 }
 /*
  * Toast.makeText(getActivity(), "position = " + position,
  * Toast.LENGTH_SHORT).show();
  */
}



@SuppressLint("InflateParams")
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 return inflater.inflate(R.layout.fragment_view_pager_prayers_bookmarks, null);
}

@Override
public void onDestroyView() {
 // TODO Auto-generated method stub
 super.onDestroyView();
 Log.d(TAG, "FragmentPrayers===onDestroyView()");
 /*if (cursor != null)
 cursor.close();
 if (db != null)
 db.closeConnecion();*/
}

@Override
public void onClick(View v) {
 // TODO Auto-generated method stub

}

@Override
public void onScrollStateChanged(AbsListView view, int scrollState) {
 // TODO Auto-generated method stub
}

@Override
public void onScroll(AbsListView view, int firstVisibleItem,
 int visibleItemCount, int totalItemCount) {
 // TODO Auto-generated method stub
 //if(firstVisibleItem!=0)list_position=firstVisibleItem;
}

}