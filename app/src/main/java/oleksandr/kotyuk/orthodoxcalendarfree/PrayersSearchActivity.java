package oleksandr.kotyuk.orthodoxcalendarfree;

import java.util.ArrayList;
import java.util.Locale;

import oleksandr.kotyuk.orthodoxcalendarfree.adapters.MyPrayersArrayAdapterSearch;
import oleksandr.kotyuk.orthodoxcalendarfree.db.DatabaseHelper;
import androidx.appcompat.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PrayersSearchActivity  extends AppCompatActivity implements
OnQueryTextListener{
	
	static final String TAG = "myLogs";

	private DatabaseHelper db;
	Cursor cursor;
	String sql = "";

	float standart_text_size = 0;
	String text_size = "0";
	String text_search = "";

	ProgressDialog pd;
	
	MenuItem searchItem;
	
	String prayers_search_name[];
	//String prayers_search_id[];
	//String prayers_search_group[];
	
	ArrayList<String> prayers = new ArrayList<String>();
	ArrayList<String> prayers_id = new ArrayList<String>();
	ArrayList<String> prayers_group = new ArrayList<String>();
	
	ArrayList<String> prayers_final = new ArrayList<String>();
	ArrayList<String> prayers_id_final = new ArrayList<String>();
	ArrayList<String> prayers_group_final = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_layout_prayers_search);

		ActionBar actionBar = getSupportActionBar();
		// Enabling Up / Back navigation
		actionBar.setDisplayHomeAsUpEnabled(true);

	
	}


		@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the options menu from XML
		getMenuInflater().inflate(R.menu.main_bible_search, menu);

		searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		searchView.setQueryHint("Поиск... (мин. 3 символа)");
		searchView.setOnQueryTextListener(this);
		//searchItem.expandActionView();
		MenuItemCompat.expandActionView(searchItem);
		return true;
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "New text is " + arg0);
		
		if (arg0.length() > 2) {
			SearchForList(arg0);
			createSearchList();
		}else{
			if (prayers_final != null) prayers_final.clear();
			if (prayers_id_final != null) prayers_id_final.clear();
			if (prayers_group_final != null) prayers_group_final.clear();
			createSearchList();
		}
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Search text is " + arg0);

		if (arg0.length() > 2) {
			SearchForList(arg0);
			createSearchList();
		}else{
			if (prayers_final != null) prayers_final.clear();
			if (prayers_id_final != null) prayers_id_final.clear();
			if (prayers_group_final != null) prayers_group_final.clear();
			createSearchList();
		}
		return false;
	}

	public void SearchForList(String text) {
		
		if (prayers != null) prayers.clear();
		if (prayers_id != null) prayers_id.clear();
		if (prayers_group != null) prayers_group.clear();

		sql = "SELECT _id, name_prayers FROM prayers_ru_pr;";
		db = DatabaseHelper.getInstance(this);
		cursor = db.executeQuery(sql);
		
		if (cursor != null) {
			
			if (cursor.moveToFirst()) {
				do {
					try {
						
						prayers.add(cursor.getString(cursor.getColumnIndex("name_prayers")));
						prayers_id.add(Integer.toString(cursor.getInt(cursor.getColumnIndex("_id"))));
						prayers_group.add("pr");
						
					} catch (NumberFormatException e) {
						// Log.d(TAG, "ERROR=" + e.toString());
					}
				} while (cursor.moveToNext());
			}
		}
		
		if (cursor != null)
			cursor.close();
		
		sql = "SELECT _id, name_prayers FROM prayers_ru_ak;";
		db = DatabaseHelper.getInstance(this);
		cursor = db.executeQuery(sql);
		
		if (cursor != null) {
			
			if (cursor.moveToFirst()) {
				do {
					try {
						
						prayers.add(cursor.getString(cursor.getColumnIndex("name_prayers")));
						prayers_id.add(Integer.toString(cursor.getInt(cursor.getColumnIndex("_id"))));
						prayers_group.add("ak");
						
					} catch (NumberFormatException e) {
						// Log.d(TAG, "ERROR=" + e.toString());
					}
				} while (cursor.moveToNext());
			}
		}
		
		if (cursor != null)
			cursor.close();
		
		if (prayers_final != null) prayers_final.clear();
		if (prayers_id_final != null) prayers_id_final.clear();
		if (prayers_group_final != null) prayers_group_final.clear();
		String text_lower_case=text.toLowerCase(Locale.getDefault());
		String tmp="";
		for(int i=0; i<prayers.size();i++){
			tmp=prayers.get(i).toLowerCase(Locale.getDefault());
			//if((prayers.get(i).toLowerCase(Locale.getDefault())).contains(text_lower_case)){
			//if(tmp.contains(text_lower_case)){
			if(tmp.indexOf(text_lower_case) != -1){
				prayers_final.add(prayers.get(i));
				prayers_id_final.add(prayers_id.get(i));
				prayers_group_final.add(prayers_group.get(i));
			}
		}
	}

	public void createSearchList() {

		prayers_search_name=prayers_final.toArray(new String[prayers_final.size()]);
		//prayers_search_id=prayers_id_final.toArray(new String[prayers_id_final.size()]);
		
		
		ListView lvMain = (ListView) findViewById(R.id.listViewSearchPrayers);
		MyPrayersArrayAdapterSearch adapter = new MyPrayersArrayAdapterSearch(
				this, R.layout.my_list_item_prayers, prayers_search_name);

		lvMain.setAdapter(adapter);
		lvMain.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
				intent.putExtra("id", 5);
				intent.putExtra("prayers_id", Integer.parseInt(prayers_id_final.get(position)));
				intent.putExtra("prayers_name", prayers_final.get(position));
				intent.putExtra("prayers_type", prayers_group_final.get(position));
				//intent.putExtra("prayers_id", Integer.parseInt(prayers_search_id[position]));
				//intent.putExtra("prayers_name", prayers_search_name[position]);
				//intent.putExtra("prayers_type", prayers_search_group[position]);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		
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
		
		if (cursor != null)
			cursor.close();
		if (db != null)
			db.closeConnecion();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// cursor.close();
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "Cliced MenuItem is " + item.getTitle());
		switch (item.getItemId()) {
		case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			//MenuItemCompat.collapseActionView(searchItem);
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}

