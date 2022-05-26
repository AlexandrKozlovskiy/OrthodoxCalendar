package oleksandr.kotyuk.orthodoxcalendar.adapters;

import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import oleksandr.kotyuk.orthodoxcalendar.MyView;

public class MyArrayAdapterBibleBookmarks extends ArrayAdapter<String> {

    static final String TAG = "myLogs";

    private final Activity context;
    private final String[] title;
    private final String[] data;


    float size_default_MyView = 0;
    float size = 0;

    public MyArrayAdapterBibleBookmarks(Activity context, int resource, String[] title, String[] data) {
        super(context, resource, title);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.title = title;
        this.data = data;
    }

    private class ViewHolder {
        protected MyView text_title;
        protected MyView text_data;
    }

    @SuppressLint("InflateParams")
    @Override
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

 /*size_default_MyView = PreferencesActivity.MyPreferenceFragment
  .ReadFloat(context, "pref_bible_text_gr_size", 0);
 size = viewHolder.text_data.getTextSize();
 // Log.d(TAG, "size =" + size);
 size = size + size_default_MyView;
 viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);*/

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (size == 0) size = viewHolder.text_title.getTextSize();
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

        viewHolder.text_title.setText(title[position]);
        viewHolder.text_data.setText(data[position]);

        return view;
    }

}
