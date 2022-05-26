package oleksandr.kotyuk.orthodoxcalendar.adapters;

import oleksandr.kotyuk.orthodoxcalendar.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;
import oleksandr.kotyuk.orthodoxcalendar.GlobalData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import oleksandr.kotyuk.orthodoxcalendar.MyView;

public class MyArrayAdapter extends ArrayAdapter<String> {

    static final String TAG = "myLogs";

    private final Activity context;
    private final String[] data;
    private final String[] day;
    private final String[] values_gr;
    private final String[] values_big;
    private final String[] values_post;

    //float text_size_gr=-1;
//float text_size_big=-1;
//float text_size_post=-1;
    float standart_text_size1 = 0;
    float standart_text_size2 = 0;
    float standart_text_size3 = 0;
    float standart_text_size4 = 0;

    private final String FONT_PATH1 = "fonts/Russo_One.ttf";

    MyCalendar cal = MyCalendar.getInstance();

    public MyArrayAdapter(Activity context, int resource, String[] values_gr,
                          String[] values_big, String[] data, String[] day, String[] post) {
        super(context, resource, post);
        // TODO Auto-generated constructor stub
        this.values_gr = values_gr;
        this.values_big = values_big;
        this.context = context;
        this.data = data;
        this.day = day;
        this.values_post = post;
    }

    private class ViewHolder {
        protected MyView text_data;
        protected MyView text_day;
        protected MyView text_values_post;
        //protected MyView text_values_gr;
        protected MyView text_values_big;

        protected LinearLayout lin1;
        //protected LinearLayout lin2;
        protected LinearLayout lin3;

    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // return super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        String buffer;

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.my_list_item_month, null, true);

            viewHolder = new ViewHolder();

            viewHolder.lin1 = (LinearLayout) view.findViewById(R.id.LinearLayout_month2);
            //viewHolder.lin2=(LinearLayout)view.findViewById(R.id.LinearLayout_month3);
            viewHolder.lin3 = (LinearLayout) view.findViewById(R.id.LinearLayout_month4);

            viewHolder.text_data = (MyView) view
                    .findViewById(R.id.MyView_data_list);
            viewHolder.text_day = (MyView) view
                    .findViewById(R.id.MyView_day_list);
            viewHolder.text_values_post = (MyView) view
                    .findViewById(R.id.MyView_values_post_list);
 /*viewHolder.text_values_gr = (MyView) view
  .findViewById(R.id.MyView_values_gr_list);*/
            viewHolder.text_values_big = (MyView) view
                    .findViewById(R.id.MyView_values_big_list);
            viewHolder.text_values_big.setLinksClickable(true);
            viewHolder.text_values_big.setMovementMethod(new LinkMovementMethod());
            //viewHolder.text_values_post.setOnClickListener(null);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (day[position].equals("Вс")) {
            viewHolder.lin1.setBackgroundResource(R.drawable.ro2);
            viewHolder.lin3.setBackgroundResource(R.drawable.rx4);
        } else {
            viewHolder.lin1.setBackgroundResource(R.drawable.ro1);
            viewHolder.lin3.setBackgroundResource(R.drawable.rx3);
        }

        viewHolder.text_data.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH1));
        viewHolder.text_data.setText(data[position]);

        viewHolder.text_day.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH1));
        viewHolder.text_day.setText(day[position]);

 
 /*if(text_size_post==-1) text_size_post=viewHolder.text_values_post.getTextSize();
 if (values_post[position].equals("")){
 viewHolder.text_values_post
  .setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);
 }
 else{
 viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX,
  text_size_post);
 }*/
        viewHolder.text_values_post.setText(values_post[position]);

        // Log.d(TAG,"position= " + position);
        // Log.d(TAG,"unmovable_gr.length= " + unmovable_gr.length);
        // Log.d(TAG,"values_big.length= " + values_big.length);

 /*if(text_size_gr==-1) text_size_gr=viewHolder.text_values_gr.getTextSize();
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
 
 /*if(values_gr[position].equals("")) buffer="";
 else buffer="<FONT COLOR=RED><b>"+values_gr[position]+"</b></FONT>";*/

        if (values_gr[position].equals("")) buffer = "";
        else buffer = values_gr[position];

        if (!values_big[position].equals("")) {
            if (buffer.equals("")) buffer = values_big[position];
            else buffer = buffer + "<br>" + values_big[position];
        }

        buffer = buffer.replace("\r\n", "<br>");
        buffer = buffer.replace("DescriptionHolyActivityHost", GlobalData.getDESCRIPTION_HOLY_ACTIVITY_HOST());
        viewHolder.text_values_big.setText(Html.fromHtml(buffer));

        if (standart_text_size1 == 0) standart_text_size1 = viewHolder.text_data.getTextSize();//22
        if (standart_text_size2 == 0) standart_text_size2 = viewHolder.text_day.getTextSize();//18
        if (standart_text_size3 == 0)
            standart_text_size3 = viewHolder.text_values_big.getTextSize();//19
        if (standart_text_size4 == 0)
            standart_text_size4 = viewHolder.text_values_post.getTextSize();//16


        String text_size = PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_text_size", "0");

        if (text_size.equals("-5")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 10);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 10);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 10);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 - 10);
        }
        if (text_size.equals("-4")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 8);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 8);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 8);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 - 8);
        }
        if (text_size.equals("-3")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 6);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 6);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 6);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 - 6);
        }
        if (text_size.equals("-2")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 4);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 4);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 4);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 - 4);

        }
        if (text_size.equals("-1")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 - 2);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 - 2);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 - 2);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 - 2);

        }
        if (text_size.equals("0")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4);

        }
        if (text_size.equals("+1")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 2);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 2);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 2);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 + 2);

        }
        if (text_size.equals("+2")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 4);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 4);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 4);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 + 4);

        }
        if (text_size.equals("+3")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 6);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 6);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 6);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 + 6);

        }
        if (text_size.equals("+4")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 8);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 8);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 8);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 + 8);

        }
        if (text_size.equals("+5")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 10);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 10);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 10);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 + 10);

        }
        if (text_size.equals("+6")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 12);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 12);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 12);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 + 12);

        }
        if (text_size.equals("+7")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 14);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 14);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 14);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 + 14);

        }
        if (text_size.equals("+8")) {
            viewHolder.text_data.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + 16);
            viewHolder.text_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + 16);
            viewHolder.text_values_big.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + 16);
            viewHolder.text_values_post.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size4 + 16);

        }

        return view;
    }

}
