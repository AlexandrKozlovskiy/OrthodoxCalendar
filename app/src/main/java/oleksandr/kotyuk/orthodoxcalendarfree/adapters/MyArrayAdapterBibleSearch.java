package oleksandr.kotyuk.orthodoxcalendarfree.adapters;

import oleksandr.kotyuk.orthodoxcalendarfree.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import oleksandr.kotyuk.orthodoxcalendarfree.accessibility.MyView;

public class MyArrayAdapterBibleSearch extends ArrayAdapter<String>{

	static final String TAG = "myLogs";

	private final Activity context;
	private final String[] arr_id_bible;
	private final String[] arr_chapter_id;
	private final String[] arr_chapter_line;
	private final String[] arr_chapter_text;

	private final String[] name_bible_book;
	
	float size_default_MyView = 0;
	float size = 0;

	public MyArrayAdapterBibleSearch(Activity context, int resource, String [] arr_id_bible, String[] arr_chapter_id, String[] arr_chapter_line, String [] arr_chapter_text) {
		super(context, resource, arr_id_bible);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.arr_id_bible=arr_id_bible;
		this.arr_chapter_id = arr_chapter_id;
		this.arr_chapter_line=arr_chapter_line;
		this.arr_chapter_text=arr_chapter_text;
		this.name_bible_book=context.getResources()
				.getStringArray(R.array.entries_bibli_search_item_group1);
	}

	private class ViewHolder {
		protected MyView text_title;
		protected MyView text_data;
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// return super.getView(position, convertView, parent);
		ViewHolder viewHolder;
		//String buffer="";

		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			view = inflater.inflate(R.layout.my_list_item_bible_bookmarks, null, true);

			viewHolder = new ViewHolder();
			
			viewHolder.text_title = (MyView) view
					.findViewById(R.id.tv_bible_bookmarks_item1);
			viewHolder.text_data = (MyView) view
					.findViewById(R.id.tv_bible_bookmarks_item2);
			
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		if(size==0) size = viewHolder.text_title.getTextSize();
		String text_size = PreferencesActivity.MyPreferenceFragment.ReadString(
				context, "pref_text_size", "0");

		if (text_size.equals("-5")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 10);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 10);
		}
		if (text_size.equals("-4")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 8);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 8);
		}
		if (text_size.equals("-3")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 6);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 6);
		}
		if (text_size.equals("-2")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 4);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 4);
		}
		if (text_size.equals("-1")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 2);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 2);
		}
		if (text_size.equals("0")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
		}
		if (text_size.equals("+1")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 2);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 2);
		}
		if (text_size.equals("+2")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 4);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 4);
		}
		if (text_size.equals("+3")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 6);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 6);
		}
		if (text_size.equals("+4")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 8);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 8);
		}
		if (text_size.equals("+5")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 10);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 10);
		}
		if (text_size.equals("+6")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 12);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 12);
		}
		if (text_size.equals("+7")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 14);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 14);
		}
		if (text_size.equals("+8")) {
			viewHolder.text_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 16);
			viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 16);
		}
		viewHolder.text_title.setText(name_bible_book[Integer.parseInt(arr_id_bible[position])]+" "+arr_chapter_id[position]+":"+arr_chapter_line[position]);
		viewHolder.text_data.setText(Html.fromHtml(arr_chapter_text[position].substring(0, arr_chapter_text[position].length()-4)));

		return view;
	}

}

