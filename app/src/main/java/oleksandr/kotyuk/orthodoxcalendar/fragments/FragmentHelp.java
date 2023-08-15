package oleksandr.kotyuk.orthodoxcalendar.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import oleksandr.kotyuk.orthodoxcalendar.BuildConfig;
import oleksandr.kotyuk.orthodoxcalendar.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendar.MyView;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;

public class FragmentHelp extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    MyView text_help;
    private final String FONT_PATH1 = "fonts/Russo_One.ttf";

    static final String TAG = "my_log";

    String text_size = "0";
    float standart_text_size1 = 0;
    float standart_text_size2 = 0;
    float standart_text_size3 = 0;
    MyView text_help_link;
    MyView text_help1;
    MyView text_help2;
    MyView text_help3;
    //MyView text_help4;
    MyView text_help5;
    //Button button_help_2;

    public FragmentHelp() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_help, container,
                false);

        text_help_link = (MyView) view.findViewById(R.id.MyViewHelp3);
        text_help1 = (MyView) view.findViewById(R.id.MyViewHelp2);
        text_help2 = (MyView) view.findViewById(R.id.MyViewHelp4);
        text_help3 = (MyView) view.findViewById(R.id.MyViewHelp5);
        //text_help4 = (MyView) view.findViewById(R.id.MyViewHelp6);
        text_help5 = (MyView) view.findViewById(R.id.MyViewHelp10);
        text_help = (MyView) view.findViewById(R.id.MyViewHelp1);
       // button_help_2 = (Button) view.findViewById(R.id.button_help2);
        ViewCompat.setAccessibilityHeading(text_help, true);
        text_help.setTypeface(FontsHelper.getTypeFace(getActivity()
                .getApplicationContext(), FONT_PATH1));
        return view;
    }
/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (BuildConfig.isPaid && text_help4 != null && button_help_2 != null) {
            ((ViewGroup) text_help4.getParent()).removeView(text_help4);
            ((ViewGroup) button_help_2.getParent()).removeView(button_help_2);
        }
    }*/

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (standart_text_size1 == 0) standart_text_size1 = text_help.getTextSize();//22
        if (standart_text_size2 == 0) standart_text_size2 = text_help_link.getTextSize();//20
        if (standart_text_size3 == 0) standart_text_size3 = text_help1.getTextSize();//19

        String tmp = PreferencesActivity.MyPreferenceFragment.ReadString(getActivity(), "pref_text_size", "0");
        if (!text_size.equals(tmp)) {
            text_size = tmp;
 /*int int_text_size=Integer.valueOf(text_size);
 Toast.makeText(getActivity(), text_size+"="+int_text_size,
  Toast.LENGTH_SHORT).show();
 float size=MyView_holiday.getTextSize();*/
            int fontSize = Integer.parseInt(text_size);
            text_help.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size1 + fontSize * 2);
            text_help_link.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size2 + fontSize * 2);
            text_help1.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + fontSize * 2);
            text_help2.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + fontSize * 2);
            text_help3.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + fontSize * 2);
            /*if (!BuildConfig.isPaid)
                text_help4.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + fontSize * 2);*/
            text_help5.setTextSize(TypedValue.COMPLEX_UNIT_PX, standart_text_size3 + fontSize * 2);
        }
    }
}