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

public class MyArrayAdapterBible extends ArrayAdapter<String> {

    static final String TAG = "myLogs";

    private final Activity context;
    private final String[] data;


    float size_default_MyView = 0;
    float size = 0;

    boolean description_style = false;

    public MyArrayAdapterBible(Activity context, int resource, String[] data) {
        super(context, resource, data);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
    }

    private class ViewHolder {
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
            view = inflater.inflate(R.layout.my_list_item_bible_read, null, true);

            viewHolder = new ViewHolder();

            description_style = PreferencesActivity.MyPreferenceFragment.ReadBoolean(context, "pref_description_style4", false);
            viewHolder.text_data = (MyView) view
                    .findViewById(R.id.MyView_bible_read_item);

            if (description_style) {
                viewHolder.text_data.setTextColor(context.getResources().getColor(R.color.WHITE2));
            } else {
                viewHolder.text_data.setTextColor(context.getResources().getColor(R.color.BLACK));
            }

            size_default_MyView = PreferencesActivity.MyPreferenceFragment
                    .ReadFloat(context, "pref_bible_text_gr_size", 0);
            size = viewHolder.text_data.getTextSize();
            // Log.d(TAG, "size =" + size);
            size = size + size_default_MyView;
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.text_data.setText(data[position]);

        return view;
    }

}
