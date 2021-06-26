package oleksandr.kotyuk.orthodoxcalendarfree.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

import oleksandr.kotyuk.orthodoxcalendarfree.BibleReadActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.R;
import oleksandr.kotyuk.orthodoxcalendarfree.accessibility.MyView;
import oleksandr.kotyuk.orthodoxcalendarfree.adapters.MyArrayAdapterBible;
import oleksandr.kotyuk.orthodoxcalendarfree.db.DatabaseHelper;

public class PageFragmentViewPagerBibleRead extends ListFragment implements
		OnClickListener{

	static final String ARGUMENT_PAGE_NUMBER = "arg_page_number_bible_read";
	public int pageNumber;

	public int pageLine;
private String title = "";
private String text = "";
	private DatabaseHelper db;
	Cursor cursor;
	String sql = "";

	float size_default_MyView = 0;
	float size = 0;

	String [] mas;

	ListView lv;
	MyArrayAdapterBible adapter;
	int pos=0;
	static final String TAG = "my_log";

	LinearLayout llDescriptionActivity;
	boolean description_style=false;

	String black_fon_color;

	public static PageFragmentViewPagerBibleRead newInstance(int page) {
		PageFragmentViewPagerBibleRead pageFragment = new PageFragmentViewPagerBibleRead();
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

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		        android.R.layout.simple_list_item_1, mas);*/
		adapter = new MyArrayAdapterBible(getActivity(),
				R.layout.my_list_item_bible_read, mas);
		lv=getListView();
		setListAdapter(adapter);

		if(BibleReadActivity.intent_line_text_bible_book!=-1){
			getListView().setSelection(BibleReadActivity.intent_line_text_bible_book-1);
			pos=BibleReadActivity.intent_line_text_bible_book-1;
			BibleReadActivity.intent_line_text_bible_book=-1;
		}else getListView().setSelection(0);


		//нажатие на кнопки громкости для прокрутки
		getListView().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				//lv=getListView();
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					switch (event.getKeyCode()) {
						case KeyEvent.KEYCODE_VOLUME_UP:

							scrollToPrevious();

							return true;
						case KeyEvent.KEYCODE_VOLUME_DOWN:
							scrollToNext();
							return true;
					}
				}
				if (event.getAction() == KeyEvent.ACTION_UP
						&& (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
						|| event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)) {
					return true;
				}
				return false;
			}
		});

		getListView().setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(scrollState==0) pos=view.getFirstVisiblePosition();
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});

		//длительное нажатие на пункт списка
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
										   int position, long id) {
				//Toast.makeText(getActivity(), "position="+(position+1)+" page="+(BibleReadActivity.start_position_bible+pageNumber), Toast.LENGTH_LONG).show();
				pageLine=position;
				DialogFragment dialog_bookmarks= new MyDialogBible();
dialog_bookmarks.show(getChildFragmentManager(), "dialog_bible_read");
				return false;
			}
		});

		/*getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

	        @Override
	        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	                int arg2, long arg3) {
	            Toast.makeText(getActivity(), "On long click listener", Toast.LENGTH_LONG).show();
	            return true;
	        }
	    });*/

	}

	private void scrollToNext() {
		//final int currentPosition = lv.getFirstVisiblePosition();
		final int currentPosition = pos;
		//if(pos==-1) pos=currentPosition;
		int i=lv.getCount();
		if (currentPosition == i - 1)
			return;
		lv.post(new Runnable() {
			@Override
			public void run() {
				lv.setSelection(currentPosition + 1);
				lv.clearFocus();
			}
		});
		if(pos<i) pos++;

		//adapter.notifyDataSetChanged();
	}

	private void scrollToPrevious() {
		//final int currentPosition = lv.getFirstVisiblePosition();
		final int currentPosition = pos;
		if (currentPosition == 0)
			return;
		lv.post(new Runnable() {
			@Override
			public void run() {
				lv.setSelection(currentPosition - 1);
				lv.clearFocus();
			}
		});
		if(pos>0)pos--;
		//adapter.notifyDataSetChanged();
	}
public Dialog createDialog() {
final int id_book = BibleReadActivity.start_position_bible + pageNumber;
final int id_book_page = pageLine + 1;
return new AlertDialog.Builder(getActivity())
					// Set Dialog Icon
					//.setIcon(R.drawable.androidhappy)
					// Set Dialog Title
					.setTitle(R.string.bible_dialog6)
					// Set Dialog Message
					.setMessage(R.string.bible_dialog7)

					// Negative Button (Отмена - Azbyka.ru)
					.setNegativeButton(R.string.bible_dialog11, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int which) {
							//Toast.makeText(getActivity(), "NO1", Toast.LENGTH_LONG).show();

							String azbyka_link="https://azbyka.ru/biblia/?";

							sql = "select (select azbyka_link from bible where _id=bible_book.id_bible) as name_bible, chapter_id from bible_book where _id=" + id_book;
							db = DatabaseHelper.getInstance(getActivity());
							cursor = db.executeQuery(sql);
							if (cursor != null) {
								if (cursor.moveToFirst()) {
									try {
										title = cursor.getString(cursor.getColumnIndex("name_bible"));
										azbyka_link=azbyka_link+title+"."+cursor.getInt(cursor.getColumnIndex("chapter_id"))+":"+id_book_page;
										//Toast.makeText(getActivity(), "NO1="+azbyka_link, Toast.LENGTH_LONG).show();
										Intent intent = new Intent(Intent.ACTION_VIEW);
										intent.setData(Uri
												.parse(azbyka_link));
										startActivity(intent);

									} catch (NumberFormatException e) {
										Toast.makeText(getActivity(), "Произошла ошибка!", Toast.LENGTH_LONG).show();
									}
								}
cursor.close();
							}
						}
					})

					//Neutral button (В закладки)
					.setNeutralButton(R.string.bible_dialog8, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int which) {
							//Toast.makeText(getActivity(), "NO2", Toast.LENGTH_LONG).show();
							//String t1=""+(BibleReadActivity.start_position_bible+pageNumber)+","+(pageLine+1);
final String bookmark=id_book+","+(pageLine+1);
ArrayList <String> arr_bookmark=PreferencesActivity.MyPreferenceFragment.getList(getActivity(), "bible_bookmarks");
if(arr_bookmark==null){
								arr_bookmark=new ArrayList<String>();
arr_bookmark.add(bookmark);
	PreferencesActivity.MyPreferenceFragment.putList(getActivity(), "bible_bookmarks", arr_bookmark);
}
else{
Boolean sameBookmark=false;
//Проверяем,есть ли уже такая же закладка.
for(String a:arr_bookmark)
if(bookmark.equals(a)) {
sameBookmark=true;
break;
}
if(!sameBookmark) {
arr_bookmark.add(0,bookmark);
	PreferencesActivity.MyPreferenceFragment.putList(getActivity(), "bible_bookmarks", arr_bookmark);
}
							}
						}
					})
					// Positive button (Копировать)
					.setPositiveButton(R.string.bible_dialog9, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							//Toast.makeText(getActivity(), "YES", Toast.LENGTH_LONG).show();

							//Toast.makeText(getActivity(), ("YES id_book="+id_book+" id_book_page="+id_book_page), Toast.LENGTH_LONG).show();
							sql = "select (select name_book from bible where _id=bible_book.id_bible) as name_bible, chapter_name, chapter_text from bible_book where _id=" + id_book;
							db = DatabaseHelper.getInstance(getActivity());
							cursor = db.executeQuery(sql);
							if (cursor != null) {
								if (cursor.moveToFirst()) {
									try {
										if (id_book < 1102) {
											title = "Ветхий Завет. " + cursor.getString(cursor
													.getColumnIndex("name_bible")) + ". " + cursor.getString(cursor
													.getColumnIndex("chapter_name"));
										} else {
											title = "Новый Завет. " + cursor.getString(cursor.getColumnIndex("name_bible")) + ". " + cursor.getString(cursor.getColumnIndex("chapter_name"));
										}
										text = getShortText(cursor.getString(cursor.getColumnIndex("chapter_text")), id_book_page);
										Toast.makeText(getActivity(), title + "\n" + text, Toast.LENGTH_LONG).show();
										android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
										android.content.ClipData clip = android.content.ClipData.newPlainText("Text Label", title + "\n" + text);
										clipboard.setPrimaryClip(clip);
									} catch (NumberFormatException e) {
										title = "";
										text = "";
}
}
cursor.close();
							}
						}
					}).create();
}
public static class MyDialogBible extends DialogFragment implements OnClickListener {

		final String LOG_TAG = "myLogs";

public Dialog onCreateDialog(Bundle savedInstanceState) {
return ((PageFragmentViewPagerBibleRead) getParentFragment()).createDialog();
}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}
	public String getShortText(String text, int line){
		int start=text.indexOf("<p>"+line);
		int end=text.indexOf("<p>"+(line+1));
		if(end==-1) end=text.length();
		else end=end-2;
		return text.substring(start, end).replace("<p>", "");
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_bible_read, null);
		String text_title="";
		String text_title2="";
		String text="";
		sql = "select (select name_book from bible where _id=bible_book.id_bible) as name_bible, chapter_name, chapter_text from bible_book where _id=" + (BibleReadActivity.start_position_bible+pageNumber);
		db = DatabaseHelper.getInstance(getActivity());
		cursor = db.executeQuery(sql);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				try {
					//ID_BIBLE = cursor.getInt(cursor.getColumnIndex("id_bible"));
					text_title = cursor.getString(cursor
							.getColumnIndex("name_bible"));
					text_title2 = cursor.getString(cursor
							.getColumnIndex("chapter_name"));
					text = cursor.getString(cursor
							.getColumnIndex("chapter_text"));
				} catch (NumberFormatException e) {
					text_title="Произошла ошибка!!!";
					text_title2="";
				}
			}
		}

		description_style=PreferencesActivity.MyPreferenceFragment.ReadBoolean(getActivity(), "pref_description_style4", false);
		llDescriptionActivity=(LinearLayout)view.findViewById(R.id.llPageBibleRead);
		MyView tvPage = (MyView) view.findViewById(R.id.tvPageBibleRead);
		ViewCompat.setAccessibilityHeading(tvPage,true);
		black_fon_color = PreferencesActivity.MyPreferenceFragment.ReadString(
				getActivity(), "pref_black_fon_color", "black");

		if(description_style){
			if(black_fon_color.equals("black")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.BLACK));
			if(black_fon_color.equals("dark_green")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_green));
			if(black_fon_color.equals("blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.blue));
			if(black_fon_color.equals("dark_blue")) llDescriptionActivity.setBackgroundColor(getResources().getColor(R.color.dark_blue));
			tvPage.setTextColor(getResources().getColor(R.color.WHITE2));
		}else{
			llDescriptionActivity.setBackgroundResource(R.drawable.rx1);
			tvPage.setTextColor(getResources().getColor(R.color.BLACK));
		}

		size_default_MyView = PreferencesActivity.MyPreferenceFragment
				.ReadFloat(getActivity(), "pref_bible_text_gr_size", 0);
		size = tvPage.getTextSize();
		// Log.d(TAG, "size =" + size);
		size = size + size_default_MyView;
		tvPage.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

		text=text.replace("<p>", "");
		text=text.replace("\r\n", "<br>");
		tvPage.setText(Html.fromHtml(text_title+". "+text_title2));

		mas=text.split("<br>");
		// находим список
	    /*ListView lvMain = (ListView) view.findViewById(R.id.lvBibleRead);

	    // создаем адаптер
	    MyArrayAdapterBible adapter = new MyArrayAdapterBible(getActivity(),
		        R.layout.my_list_item_bible_read, mas);

	    // присваиваем адаптер списку
	    lvMain.setAdapter(adapter);*/

		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		//Log.d(TAG, "FragmentPrayers===onDestroyView()");
		if(cursor!=null)cursor.close();
		if(db!=null)db.closeConnecion();
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		//Toast.makeText(getActivity(), "position = " + (position+1), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
