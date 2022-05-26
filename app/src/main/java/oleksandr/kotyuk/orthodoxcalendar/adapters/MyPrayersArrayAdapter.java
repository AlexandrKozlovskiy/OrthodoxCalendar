package oleksandr.kotyuk.orthodoxcalendar.adapters;

import oleksandr.kotyuk.orthodoxcalendar.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import oleksandr.kotyuk.orthodoxcalendar.MyView;

public class MyPrayersArrayAdapter extends ArrayAdapter<String> {

    static final String TAG = "myLogs";

    private final Activity context;
    private final String[] prayers_name;
    String prayers_language;

    float text_size_gr = -1;
    float text_size_big = -1;
    float text_size_post = -1;

//private final String FONT_PATH1 = "fonts/TRIOD35.TTF";
//private final String FONT_PATH2 = "fonts/IrmUcs.ttf";

    private final String FONT_PATH_RU1 = "fonts/Arial.ttf";
    private final String FONT_PATH_RU2 = "fonts/Calibri.ttf";
    private final String FONT_PATH_RU3 = "fonts/Cambria.ttf";
    private final String FONT_PATH_RU4 = "fonts/DroidSans.ttf";
    private final String FONT_PATH_RU5 = "fonts/DroidSerif.ttf";
    private final String FONT_PATH_RU6 = "fonts/Times.ttf";
    private final String FONT_PATH_RU7 = "fonts/Verdana.ttf";

    private final String FONT_PATH_CS1 = "fonts/Canonic.ttf";
    private final String FONT_PATH_CS2 = "fonts/Orthodox.ttf";
    private final String FONT_PATH_CS3 = "fonts/Triodion.ttf";

    MyCalendar cal = MyCalendar.getInstance();

    float size = 0;

    public MyPrayersArrayAdapter(Activity context, int resource, String[] prayers) {
        super(context, resource, prayers);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.prayers_name = prayers;
    }

    private class ViewHolder {
        protected MyView tv_prayers_name;

    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // return super.getView(position, convertView, parent);
        ViewHolder viewHolder;

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.my_list_item_prayers, null, true);

            viewHolder = new ViewHolder();


            viewHolder.tv_prayers_name = (MyView) view
                    .findViewById(R.id.MyView_prayers_item);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(getContext(), "pref_prayers_language", "ru");

        if (prayers_language.equals("ru")) {
            String prayers_fonts_ru = PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_prayers_fonts_ru", "1");
            if (prayers_fonts_ru.equals("1"))
                viewHolder.tv_prayers_name.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU1));
            if (prayers_fonts_ru.equals("2"))
                viewHolder.tv_prayers_name.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU2));
            if (prayers_fonts_ru.equals("3"))
                viewHolder.tv_prayers_name.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU3));
            if (prayers_fonts_ru.equals("4"))
                viewHolder.tv_prayers_name.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU4));
            if (prayers_fonts_ru.equals("5"))
                viewHolder.tv_prayers_name.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU5));
            if (prayers_fonts_ru.equals("6"))
                viewHolder.tv_prayers_name.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU6));
            if (prayers_fonts_ru.equals("7"))
                viewHolder.tv_prayers_name.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU7));
            viewHolder.tv_prayers_name.setText(prayers_name[position]);
        } else {
            String prayers_fonts_cs = PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_prayers_fonts_cs", "1");
            if (prayers_fonts_cs.equals("1"))
                viewHolder.tv_prayers_name.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_CS1));
            if (prayers_fonts_cs.equals("2"))
                viewHolder.tv_prayers_name.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_CS2));
            if (prayers_fonts_cs.equals("3"))
                viewHolder.tv_prayers_name.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_CS3));
            viewHolder.tv_prayers_name.setText(prayers_name[position]);
        }

        if (size == 0) size = viewHolder.tv_prayers_name.getTextSize();
        String text_size = PreferencesActivity.MyPreferenceFragment.ReadString(
                context, "pref_text_size", "0");

        if (text_size.equals("-5")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 10);
        }
        if (text_size.equals("-4")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 8);
        }
        if (text_size.equals("-3")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 6);
        }
        if (text_size.equals("-2")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 4);
        }
        if (text_size.equals("-1")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 2);
        }
        if (text_size.equals("0")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        if (text_size.equals("+1")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 2);
        }
        if (text_size.equals("+2")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 4);
        }
        if (text_size.equals("+3")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 6);
        }
        if (text_size.equals("+4")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 8);
        }
        if (text_size.equals("+5")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 10);
        }
        if (text_size.equals("+6")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 12);
        }
        if (text_size.equals("+7")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 14);
        }
        if (text_size.equals("+8")) {
            viewHolder.tv_prayers_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 16);
        }

 /*viewHolder.text_data.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH1));
 viewHolder.text_data.setText(data[position]);

 viewHolder.text_day.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH1));
 viewHolder.text_day.setText(day[position]);

 
 if(text_size_post==-1) text_size_post=viewHolder.text_values_post.getTextSize();
 if (values_post[position].equals("")){
 viewHolder.text_values_post
  .setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);
 }
 else{
 viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX,
  text_size_post);
 }
 viewHolder.text_values_post.setText(values_post[position]);

 // Log.d(TAG,"position= " + position);
 // Log.d(TAG,"unmovable_gr.length= " + unmovable_gr.length);
 // Log.d(TAG,"values_big.length= " + values_big.length);

 if(text_size_gr==-1) text_size_gr=viewHolder.text_values_gr.getTextSize();
 buffer = values_gr[position];
 buffer = buffer.replace("\r\n", "<br>");
 if (buffer.equals("")){
 viewHolder.text_values_gr
  .setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);
 }
 else{
 viewHolder.text_values_gr.setTextSize(TypedValue.COMPLEX_UNIT_PX,
  text_size_gr);
 }
 viewHolder.text_values_gr.setText(Html.fromHtml(buffer));
 
 
 if(text_size_big==-1) text_size_big=viewHolder.text_values_big.getTextSize();
 buffer = values_big[position];
 buffer = buffer.replace("\r\n", "<br>");
 if (buffer.equals("")){
 viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX,
  0);
 }
 else{
 viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX,
  text_size_big);
 }
 viewHolder.text_values_big.setText(Html.fromHtml(buffer));*/
        return view;
    }

}
