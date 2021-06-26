package oleksandr.kotyuk.orthodoxcalendarfree;

import java.util.ArrayList;
import java.util.Locale;

import oleksandr.kotyuk.orthodoxcalendarfree.adapters.MyArrayAdapterBibleSearch;
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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import oleksandr.kotyuk.orthodoxcalendarfree.accessibility.MyView;

public class BibleSearchActivity extends AppCompatActivity implements OnQueryTextListener {

	static final String TAG = "myLogs";
	String[] data = { "one", "two", "three", "four", "five" };

	ArrayAdapter<String> adapter2;
	Spinner spinner2;

	int id_book_group = 0;
	int start_book_search = 1;
	int end_book_search = 77;

	MyView tvCountSearch;
	//MyView tvspinner1;

	private DatabaseHelper db;
	Cursor cursor;
	String sql = "";

	ArrayList<String> arr_id_bible = new ArrayList<String>();
	ArrayList<String> arr_id_bible_book = new ArrayList<String>();
	ArrayList<String> arr_chapter_id = new ArrayList<String>();
	ArrayList<String> arr_chapter_text = new ArrayList<String>();
	ArrayList<String> arr_chapter_line = new ArrayList<String>();
	int count_item_search = 0;

	CheckBox checkboxRegistr;

	float standart_text_size = 0;
	String text_size = "0";
	String text_search = "";

	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_layout_bible_search);

		ActionBar actionBar = getSupportActionBar();
		// Enabling Up / Back navigation
		actionBar.setDisplayHomeAsUpEnabled(true);

		tvCountSearch = (MyView) findViewById(R.id.MyView_bible_search);
		//tvspinner1=(MyView)findViewById(R.id.MyView1);
		checkboxRegistr = (CheckBox) findViewById(R.id.checkBoxSearchBible);

		// адаптер
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				R.layout.my_simple_spinner_item_bible, getResources()
						.getStringArray(R.array.entries_bibli_search_group));
		adapter1.setDropDownViewResource(R.layout.my_simple_spinner_dropdown_item_bible);

		Spinner spinner1 = (Spinner) findViewById(R.id.spinner_bible_search1);
		spinner1.setAdapter(adapter1);
		// заглавие
		spinner1.setPrompt("Разделы");
		// выделяем элемент
		spinner1.setSelection(0);
		// устанавливаем обработчик нажатия
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SetSpiner1ItemSelected(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void SetSpiner1ItemSelected(int id) {

		switch (id) {
		case 0:
			id_book_group = 0;
			start_book_search = 1;
			end_book_search = 77;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group1));
			break;
		case 1:
			id_book_group = 1;
			start_book_search = 1;
			end_book_search = 50;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group2));
			break;
		case 2:
			id_book_group = 2;
			start_book_search = 1;
			end_book_search = 5;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group3));
			break;
		case 3:
			id_book_group = 3;
			start_book_search = 6;
			end_book_search = 17;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group4));
			break;
		case 4:
			id_book_group = 4;
			start_book_search = 18;
			end_book_search = 22;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group5));
			break;
		case 5:
			id_book_group = 5;
			start_book_search = 23;
			end_book_search = 39;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group6));
			break;
		case 6:
			id_book_group = 6;
			start_book_search = 40;
			end_book_search = 50;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group7));
			break;
		case 7:
			id_book_group = 7;
			start_book_search = 51;
			end_book_search = 77;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group8));
			break;
		case 8:
			id_book_group = 8;
			start_book_search = 51;
			end_book_search = 54;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group9));
			break;
		case 9:
			id_book_group = 9;
			start_book_search = 55;
			end_book_search = 55;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group10));
			break;
		case 10:
			id_book_group = 10;
			start_book_search = 56;
			end_book_search = 62;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group11));
			break;
		case 11:
			id_book_group = 11;
			start_book_search = 63;
			end_book_search = 76;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group12));
			break;
		case 12:
			id_book_group = 12;
			start_book_search = 77;
			end_book_search = 77;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group13));
			break;
		default:
			id_book_group = 0;
			start_book_search = 1;
			end_book_search = 77;
			adapter2 = new ArrayAdapter<String>(this,
					R.layout.my_simple_spinner_item_bible, getResources()
							.getStringArray(
									R.array.entries_bibli_search_item_group1));
			break;
		}

		adapter2.setDropDownViewResource(R.layout.my_simple_spinner_dropdown_item_bible);

		spinner2 = (Spinner) findViewById(R.id.spinner_bible_search2);
		spinner2.setAdapter(adapter2);
		// заглавие
		spinner2.setPrompt("Книги");
		// выделяем элемент
		spinner2.setSelection(0);
		// устанавливаем обработчик нажатия
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				SetSpiner2ItemSelected(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void SetSpiner2ItemSelected(int id) {
		switch (id_book_group) {
		case 0:
			if (id == 0) {
				start_book_search = 1;
				end_book_search = 77;
			} else {
				start_book_search = 1 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 1:
			if (id == 0) {
				start_book_search = 1;
				end_book_search = 50;
			} else {
				start_book_search = 1 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 2:
			if (id == 0) {
				start_book_search = 1;
				end_book_search = 5;
			} else {
				start_book_search = 1 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 3:
			if (id == 0) {
				start_book_search = 6;
				end_book_search = 17;
			} else {
				start_book_search = 6 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 4:
			if (id == 0) {
				start_book_search = 18;
				end_book_search = 22;
			} else {
				start_book_search = 18 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 5:
			if (id == 0) {
				start_book_search = 23;
				end_book_search = 39;
			} else {
				start_book_search = 23 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 6:
			if (id == 0) {
				start_book_search = 40;
				end_book_search = 50;
			} else {
				start_book_search = 40 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 7:
			if (id == 0) {
				start_book_search = 51;
				end_book_search = 77;
			} else {
				start_book_search = 51 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 8:
			if (id == 0) {
				start_book_search = 51;
				end_book_search = 54;
			} else {
				start_book_search = 51 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 9:
			start_book_search = 55;
			end_book_search = 55;
			break;
		case 10:
			if (id == 0) {
				start_book_search = 56;
				end_book_search = 62;
			} else {
				start_book_search = 56 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 11:
			if (id == 0) {
				start_book_search = 63;
				end_book_search = 76;
			} else {
				start_book_search = 63 + id - 1;
				end_book_search = start_book_search;
			}
			break;
		case 12:
			start_book_search = 77;
			end_book_search = 77;
			break;
		default:
			start_book_search = 1;
			end_book_search = 77;
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the options menu from XML
		getMenuInflater().inflate(R.menu.main_bible_search, menu);

		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		searchView.setQueryHint("Поиск... (мин. 3 символа)");
		searchView.setOnQueryTextListener(this);
		return true;
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "New text is " + arg0);
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Search text is " + arg0);

		if (arg0.length() > 2) {

			// text_search=arg0;

			SearchForList(arg0);
			createSearchList();
		}
		return false;
	}

	public void SearchForList(String text) {

		StringBuilder tmp_text = new StringBuilder();
		StringBuilder tmp_text2 = new StringBuilder();

		String text_UpCase = text.toUpperCase(Locale.getDefault());
		StringBuilder tmp_text_UpCase = new StringBuilder();
		StringBuilder tmp_text2_UpCase = new StringBuilder();

		if (arr_chapter_id != null)
			arr_chapter_id.clear();
		if (arr_chapter_text != null)
			arr_chapter_text.clear();
		if (arr_chapter_line != null)
			arr_chapter_line.clear();
		if (arr_id_bible != null)
			arr_id_bible.clear();
		if (arr_id_bible_book != null)
			arr_id_bible_book.clear();

		count_item_search = 0;

		/*
		 * sql =
		 * "SELECT id_bible, chapter_id, chapter_text FROM bible_book WHERE id_bible>="
		 * + start_book_search + " AND id_bible<=" + end_book_search;
		 */
		for (int ii = start_book_search; ii <= end_book_search; ii++) {
			sql = "SELECT _id, id_bible, chapter_id, chapter_text FROM bible_book WHERE id_bible="
					+ ii;
			db = DatabaseHelper.getInstance(this);
			cursor = db.executeQuery(sql);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						try {
							if (tmp_text.length() > 0)
								tmp_text.delete(0, tmp_text.length());
							if (tmp_text_UpCase.length() > 0)
								tmp_text_UpCase.delete(0,
										tmp_text_UpCase.length());
							tmp_text.append(cursor.getString(cursor
									.getColumnIndex("chapter_text")));
							tmp_text.append("<br>");
							tmp_text.delete(0, 3);

							if (checkboxRegistr.isChecked()) {
								if (tmp_text.indexOf(text) != -1) {
									String[] arr_temp = (tmp_text.toString())
											.split("<p>");
									for (int i = 0; i < arr_temp.length; i++) {
										if (tmp_text2.length() > 0)
											tmp_text2.delete(0,
													tmp_text2.length());
										tmp_text2.append(arr_temp[i]);
										if (tmp_text2.indexOf(text) != -1) {
											arr_id_bible_book
											.add(""
													+ (cursor
															.getInt(cursor
																	.getColumnIndex("_id"))));
											arr_id_bible
													.add(""
															+ (cursor
																	.getInt(cursor
																			.getColumnIndex("id_bible"))));
											arr_chapter_id
													.add(""
															+ (cursor
																	.getInt(cursor
																			.getColumnIndex("chapter_id"))));
											arr_chapter_text.add(tmp_text2
													.toString().replace("\r\n",
															"<br>"));
											arr_chapter_line.add(Integer
													.toString(i + 1));

											count_item_search++;
										}
									}
								}
							} else {

								tmp_text_UpCase.append(tmp_text.toString()
										.toUpperCase(Locale.getDefault()));

								if (tmp_text_UpCase.indexOf(text_UpCase) != -1) {
									String[] arr_temp = (tmp_text.toString())
											.split("<p>");
									String[] arr_temp_UpCase = (tmp_text_UpCase
											.toString()).split("<P>");
									for (int i = 0; i < arr_temp_UpCase.length; i++) {
										if (tmp_text2_UpCase.length() > 0)
											tmp_text2_UpCase.delete(0,
													tmp_text2_UpCase.length());
										if (tmp_text2.length() > 0)
											tmp_text2.delete(0,
													tmp_text2.length());

										tmp_text2.append(arr_temp[i]);
										tmp_text2_UpCase
												.append(arr_temp_UpCase[i]);

										if (tmp_text2_UpCase
												.indexOf(text_UpCase) != -1) {
											arr_id_bible_book
											.add(""
													+ (cursor
															.getInt(cursor
																	.getColumnIndex("_id"))));
											arr_id_bible
													.add(""
															+ (cursor
																	.getInt(cursor
																			.getColumnIndex("id_bible"))));
											arr_chapter_id
													.add(""
															+ (cursor
																	.getInt(cursor
																			.getColumnIndex("chapter_id"))));
											arr_chapter_text.add(tmp_text2
													.toString().replace("\r\n",
															"<br>"));
											arr_chapter_line.add(Integer
													.toString(i + 1));

											count_item_search++;
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
		}
		tvCountSearch.setText("Найдено результатов - " + count_item_search);
	}

	public void createSearchList() {
		// конвертируем ArrayList в массив
		String[] my_arr_id_bible = {};
		my_arr_id_bible = arr_id_bible.toArray(new String[arr_id_bible.size()]);
		String[] my_arr_chapter_id = {};
		my_arr_chapter_id = arr_chapter_id.toArray(new String[arr_chapter_id
				.size()]);
		String[] my_arr_chapter_line = {};
		my_arr_chapter_line = arr_chapter_line
				.toArray(new String[arr_chapter_line.size()]);
		String[] my_arr_chapter_text = {};
		my_arr_chapter_text = arr_chapter_text
				.toArray(new String[arr_chapter_text.size()]);

		ListView lvMain = (ListView) findViewById(R.id.listViewSearchBible);
		MyArrayAdapterBibleSearch adapter = new MyArrayAdapterBibleSearch(this,
				R.layout.my_list_item_bible_bookmarks, my_arr_id_bible,
				my_arr_chapter_id, my_arr_chapter_line, my_arr_chapter_text);
		lvMain.setAdapter(adapter);
		lvMain.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				
				int intent_id_number=1;
				
				int i=Integer.parseInt(arr_id_bible.get(position));
				
				if(i<51) intent_id_number=1;
				else intent_id_number=2;
				
				Intent intent = new Intent(getApplicationContext(), BibleListActivity.class);
				intent.putExtra("id", intent_id_number);
				intent.putExtra("id_bible_book", Integer.parseInt(arr_id_bible_book.get(position)));
				intent.putExtra("line_text_bible_book", Integer.parseInt(arr_chapter_line.get(position)));
				intent.putExtra("save_page", false);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		if (standart_text_size == 0)
			standart_text_size = tvCountSearch.getTextSize();// 19

		String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(this,
				"pref_text_size", "0");
		if (!text_size.equals(tmp)) {
			text_size = tmp;
			if (text_size.equals("-5")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size - 10);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size - 10);
			}
			if (text_size.equals("-4")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size - 8);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size - 8);
			}
			if (text_size.equals("-3")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size - 6);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size - 6);
			}
			if (text_size.equals("-2")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size - 4);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size - 4);
			}
			if (text_size.equals("-1")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size - 2);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size - 2);
			}
			if (text_size.equals("0")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size);
			}
			if (text_size.equals("+1")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 2);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 2);
			}
			if (text_size.equals("+2")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 4);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 4);
			}
			if (text_size.equals("+3")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 6);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 6);
			}
			if (text_size.equals("+4")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 8);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 8);
			}
			if (text_size.equals("+5")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 10);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 10);
			}
			if (text_size.equals("+6")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 12);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 12);
			}
			if (text_size.equals("+7")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 14);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 14);
			}
			if (text_size.equals("+8")) {
				tvCountSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 16);
				checkboxRegistr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						standart_text_size + 16);
			}
		}
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
		if (arr_chapter_id != null)
			arr_chapter_id.clear();
		if (arr_chapter_text != null)
			arr_chapter_text.clear();
		if (arr_chapter_line != null)
			arr_chapter_line.clear();
		if (arr_id_bible != null)
			arr_id_bible.clear();
		if (arr_id_bible_book != null)
			arr_id_bible_book.clear();
		
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
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
