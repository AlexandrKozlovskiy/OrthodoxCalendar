package oleksandr.kotyuk.orthodoxcalendarfree.fragments;

import java.util.ArrayList;

import oleksandr.kotyuk.orthodoxcalendarfree.PrayersBookmarksActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.R;
import oleksandr.kotyuk.orthodoxcalendarfree.adapters.MyPrayersArrayAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class FragmentPrayersBookmarks extends ListFragment implements OnClickListener{

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
	
	String text_message="";
	
	public FragmentPrayersBookmarks(){
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
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
		

		MyPrayersArrayAdapter adapter = new MyPrayersArrayAdapter(
				getActivity(), R.layout.my_list_item_prayers, prayers);
		setListAdapter(adapter);
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume()");
		/*if (!PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(),
				"pref_prayers_language", "ru").equals(prayers_language)) {
			Log.d(TAG, "onResume()22222");*/

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
			

			MyPrayersArrayAdapter adapter = new MyPrayersArrayAdapter(
					getActivity(), R.layout.my_list_item_prayers, prayers);
			setListAdapter(adapter);
		//}
			
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if(prayers_group[0]!=-1){
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
		return inflater.inflate(R.layout.fragment_view_pager_prayers, null);
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

}
