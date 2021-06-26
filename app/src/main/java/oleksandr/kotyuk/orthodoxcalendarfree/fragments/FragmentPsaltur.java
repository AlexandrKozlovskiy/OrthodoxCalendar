package oleksandr.kotyuk.orthodoxcalendarfree.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleCursorTreeAdapter;

import androidx.fragment.app.Fragment;

import oleksandr.kotyuk.orthodoxcalendarfree.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.PsalturActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.R;
import oleksandr.kotyuk.orthodoxcalendarfree.adapters.MySimpleCursorTreeAdapterPsaltur;
import oleksandr.kotyuk.orthodoxcalendarfree.db.DatabaseHelper;
import oleksandr.kotyuk.orthodoxcalendarfree.models.MyCalendar;

public class FragmentPsaltur extends Fragment{
	
	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	static final String TAG = "myLogs";

	// Calendar calendar;
	private DatabaseHelper db;
	Cursor cursor;

	MyCalendar cal = MyCalendar.getInstance();

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
	
    public FragmentPsaltur(){
		
	}
    
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//return inflater.inflate(R.layout.fragment_view_pager_psaltur, null);
		v = inflater.inflate(R.layout.fragment_view_pager_psaltur, null);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(
				getActivity(), "pref_prayers_language", "ru");

		// db =
		// DatabaseHelper.getInstance(getActivity().getApplicationContext());
		if (prayers_language.equals("ru")) {
			prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_ru", "1");
			db = DatabaseHelper.getInstance(getActivity()
					.getApplicationContext());
			cursor = db.executeQuery("SELECT _id, kafisma_ru FROM psaltur_group;");
			
			// сопоставление данных и View для групп
		    groupFrom =new String []{ "kafisma_ru" };
		    groupTo =new int [] { R.id.text_list_group };
		    // сопоставление данных и View для элементов
		    childFrom = new String []{ "psalom_name_ru" };
		    childTo = new int [] { R.id.text_list_child };
		} else {
			prayers_fonts_cs=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_cs", "1");
			db = DatabaseHelper.getInstance(getActivity()
					.getApplicationContext());
			cursor = db.executeQuery("SELECT _id, kafisma_sc FROM psaltur_group;");
			
			// сопоставление данных и View для групп
		    groupFrom = new String []{ "kafisma_sc" };
		    groupTo = new int [] { R.id.text_list_group };
		    // сопоставление данных и View для элементов
		    childFrom = new String []{ "psalom_name_sc" };
		    childTo = new int [] { R.id.text_list_child };
		}
		
	    // создаем адаптер и настраиваем список
		MySimpleCursorTreeAdapterPsaltur sctAdapter = new MyAdapter(getActivity().getApplicationContext(), cursor,
	        R.layout.my_simple_expandable_list_item_1, groupFrom,
	        groupTo, R.layout.my_simple_list_item_1, childFrom,
	        childTo);
	    elvMain = (ExpandableListView) v.findViewById(R.id.elv_psaltur);
	    elvMain.setAdapter(sctAdapter);
	    elvMain.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView paramExpandableListView,
					View paramView, int paramInt1, int paramInt2, long paramLong) {
				Log.d(TAG, "paramInt1="+paramInt1+" paramInt2="+paramInt2+" paramLong="+paramLong);
				
				// создаем новое окно просмотра статей, передаем номер раздела
				/*Intent intent = new Intent(paramExpandableListView.getContext(),
						BibleReadActivity.class);
				intent.putExtra("id", (int) paramLong);
				startActivity(intent);*/
				
				Intent intent = new Intent(getActivity(), PsalturActivity.class);
				intent.putExtra("psalm_id", (int) paramLong);
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
		      // получаем курсор по элементам для конкретной группы
		      int idColumn = groupCursor.getInt(groupCursor.getColumnIndex("_id"));
		      String sql2="select * from psaltur where id_kafist="+idColumn;
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
		
		if (!PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(),
				"pref_prayers_language", "ru").equals(prayers_language)) {
			Log.d(TAG, "onResume()22222");

			prayers_language = PreferencesActivity.MyPreferenceFragment
					.ReadString(getActivity(), "pref_prayers_language", "ru");
			
			if (prayers_language.equals("ru")) {
				db = DatabaseHelper.getInstance(getActivity()
						.getApplicationContext());
				cursor = db.executeQuery("SELECT _id, kafisma_ru FROM psaltur_group;");
				
				// сопоставление данных и View для групп
			    groupFrom =new String []{ "kafisma_ru" };
			    groupTo =new int [] { R.id.text_list_group };
			    // сопоставление данных и View для элементов
			    childFrom = new String []{ "psalom_name_ru" };
			    childTo = new int [] { R.id.text_list_child };
			} else {
				db = DatabaseHelper.getInstance(getActivity()
						.getApplicationContext());
				cursor = db.executeQuery("SELECT _id, kafisma_sc FROM psaltur_group;");
				
				// сопоставление данных и View для групп
			    groupFrom = new String []{ "kafisma_sc" };
			    groupTo = new int [] { R.id.text_list_group };
			    // сопоставление данных и View для элементов
			    childFrom = new String []{ "psalom_name_sc" };
			    childTo = new int [] { R.id.text_list_child };
			}
			
		    // создаем адаптер и настраиваем список
			MySimpleCursorTreeAdapterPsaltur sctAdapter = new MyAdapter(getActivity().getApplicationContext(), cursor,
		        R.layout.my_simple_expandable_list_item_1, groupFrom,
		        groupTo, R.layout.my_simple_list_item_1, childFrom,
		        childTo);
		    elvMain = (ExpandableListView) v.findViewById(R.id.elv_psaltur);
		    elvMain.setAdapter(sctAdapter);
		}
		
		String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(
				getActivity(), "pref_text_size", "0");
		if (!text_size.equals(tmp)) {
			text_size = tmp;
			MySimpleCursorTreeAdapterPsaltur sctAdapter = new MyAdapter(getActivity().getApplicationContext(), cursor,
			        R.layout.my_simple_expandable_list_item_1, groupFrom,
			        groupTo, R.layout.my_simple_list_item_1, childFrom,
			        childTo);
			    elvMain = (ExpandableListView) v.findViewById(R.id.elv_psaltur);
			    elvMain.setAdapter(sctAdapter);
		}
		
		String tmp_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_ru", "1");
		String tmp_fonts_cs=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_cs", "1");
		if(!tmp_fonts_ru.equals(prayers_fonts_ru) || !tmp_fonts_cs.equals(prayers_fonts_cs)){
			
			prayers_fonts_ru=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_ru", "1");
			prayers_fonts_cs=PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_prayers_fonts_cs", "1");
			MySimpleCursorTreeAdapterPsaltur sctAdapter = new MyAdapter(getActivity().getApplicationContext(), cursor,
			        R.layout.my_simple_expandable_list_item_1, groupFrom,
			        groupTo, R.layout.my_simple_list_item_1, childFrom,
			        childTo);
			    elvMain = (ExpandableListView) v.findViewById(R.id.elv_psaltur);
			    elvMain.setAdapter(sctAdapter);
		}
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
