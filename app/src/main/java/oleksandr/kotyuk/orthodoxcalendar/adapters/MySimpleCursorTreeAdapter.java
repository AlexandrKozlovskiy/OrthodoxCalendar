package oleksandr.kotyuk.orthodoxcalendar.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorTreeAdapter;

import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.MyView;

public class MySimpleCursorTreeAdapter extends SimpleCursorTreeAdapter {

    float size = 0;

    public MySimpleCursorTreeAdapter(Context context, Cursor cursor,
                                     int groupLayout, String[] groupFrom, int[] groupTo,
                                     int childLayout, String[] childFrom, int[] childTo) {
        super(context, cursor, groupLayout, groupFrom, groupTo, childLayout,
                childFrom, childTo);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor,
                                 boolean isLastChild) {
        // TODO Auto-generated method stub
        super.bindChildView(view, context, cursor, isLastChild);

        MyView textChild = (MyView) view.findViewById(R.id.text_list_child);
        if (size == 0) size = textChild.getTextSize();
        String text_size = PreferencesActivity.MyPreferenceFragment.ReadString(
                context, "pref_text_size", "0");

        if (text_size.equals("-5")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 10);
        }
        if (text_size.equals("-4")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 8);
        }
        if (text_size.equals("-3")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 6);
        }
        if (text_size.equals("-2")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 4);
        }
        if (text_size.equals("-1")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 2);
        }
        if (text_size.equals("0")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        if (text_size.equals("+1")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 2);
        }
        if (text_size.equals("+2")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 4);
        }
        if (text_size.equals("+3")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 6);
        }
        if (text_size.equals("+4")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 8);
        }
        if (text_size.equals("+5")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 10);
        }
        if (text_size.equals("+6")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 12);
        }
        if (text_size.equals("+7")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 14);
        }
        if (text_size.equals("+8")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 16);
        }
    }

    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor,
                                 boolean isExpanded) {
        // TODO Auto-generated method stub
        super.bindGroupView(view, context, cursor, isExpanded);
        MyView textGroup = (MyView) view.findViewById(R.id.text_list_group);
        textGroup.setExpanded(isExpanded);
        if (size == 0) size = textGroup.getTextSize();
        String text_size = PreferencesActivity.MyPreferenceFragment.ReadString(
                context, "pref_text_size", "0");

        if (text_size.equals("-5")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 10);
        }
        if (text_size.equals("-4")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 8);
        }
        if (text_size.equals("-3")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 6);
        }
        if (text_size.equals("-2")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 4);
        }
        if (text_size.equals("-1")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 2);
        }
        if (text_size.equals("0")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
        if (text_size.equals("+1")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 2);
        }
        if (text_size.equals("+2")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 4);
        }
        if (text_size.equals("+3")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 6);
        }
        if (text_size.equals("+4")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 8);
        }
        if (text_size.equals("+5")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 10);
        }
        if (text_size.equals("+6")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 12);
        }
        if (text_size.equals("+7")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 14);
        }
        if (text_size.equals("+8")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 16);
        }
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        // TODO Auto-generated method stub
        return null;
    }

}
