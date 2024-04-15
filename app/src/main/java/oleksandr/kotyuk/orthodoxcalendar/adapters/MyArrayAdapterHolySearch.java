package oleksandr.kotyuk.orthodoxcalendar.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import oleksandr.kotyuk.orthodoxcalendar.MyView;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;

public class MyArrayAdapterHolySearch extends ArrayAdapter<String> {

    static final String TAG = "myLogs";

    private final Activity context;
    private final String[] arr_memory_date_holy;
    private final String[] arr_name_holy;
    float size = 0;

    public MyArrayAdapterHolySearch(Activity context, int resource, String[] arr_memory_date_holy, String[] arr_name_holy) {
        super(context, resource, arr_memory_date_holy);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.arr_memory_date_holy = arr_memory_date_holy;
        this.arr_name_holy = arr_name_holy;
    }

    private class ViewHolder {
        protected MyView text_title;
        protected MyView text_data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // return super.getView(position, convertView, parent);
        ViewHolder viewHolder;

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.my_list_item_search_holy, null, true);

            viewHolder = new ViewHolder();

            viewHolder.text_title = (MyView) view
                    .findViewById(R.id.tv_search_holy_item1);
            viewHolder.text_data = (MyView) view
                    .findViewById(R.id.tv_search_holy_item2);

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
        String tmp_arr1=arr_name_holy[position];
        String tmp_arr2= arr_memory_date_holy[position];
        viewHolder.text_title.setText(arr_name_holy[position]);
        viewHolder.text_data.setText(arr_memory_date_holy [position]);

        return view;
    }

}
