package oleksandr.kotyuk.orthodoxcalendar.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorTreeAdapter;

import oleksandr.kotyuk.orthodoxcalendar.FontsHelper;
import oleksandr.kotyuk.orthodoxcalendar.PreferencesActivity;
import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.MyView;

public class MySimpleCursorTreeAdapterPsaltur extends SimpleCursorTreeAdapter {

    float size = 0;
    String prayers_language;
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
    float font_size_add = 0;
    private final float font_ru1 = 0;
    private final float font_ru2 = 5;
    private final float font_ru3 = 1;
    private final float font_ru4 = 0;
    private final float font_ru5 = -2;
    private final float font_ru6 = 3;
    private final float font_ru7 = -2;
    private final float font_cs1 = 3;
    private final float font_cs2 = 6;
    private final float font_cs3 = 4;

    public MySimpleCursorTreeAdapterPsaltur(Context context, Cursor cursor,
                                            int groupLayout, String[] groupFrom, int[] groupTo,
                                            int childLayout, String[] childFrom, int[] childTo) {
        super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
                childTo);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor,
                                 boolean isLastChild) {
        // TODO Auto-generated method stub
        super.bindChildView(view, context, cursor, isLastChild);
        MyView textChild = (MyView) view.findViewById(R.id.text_list_child);
        if (size == 0) size = textChild.getTextSize();
        prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_prayers_language", "ru");
        if (prayers_language.equals("ru")) {
            String prayers_fonts_ru = PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_prayers_fonts_ru", "1");
            if (prayers_fonts_ru.equals("1")) {
                textChild.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU1));
                font_size_add = font_ru1;
            }
            if (prayers_fonts_ru.equals("2")) {
                textChild.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU2));
                font_size_add = font_ru2;
            }
            if (prayers_fonts_ru.equals("3")) {
                textChild.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU3));
                font_size_add = font_ru3;
            }
            if (prayers_fonts_ru.equals("4")) {
                textChild.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU4));
                font_size_add = font_ru4;
            }
            if (prayers_fonts_ru.equals("5")) {
                textChild.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU5));
                font_size_add = font_ru5;
            }
            if (prayers_fonts_ru.equals("6")) {
                textChild.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU6));
                font_size_add = font_ru6;
            }
            if (prayers_fonts_ru.equals("7")) {
                textChild.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU7));
                font_size_add = font_ru7;
            }
        } else {
            String prayers_fonts_cs = PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_prayers_fonts_cs", "1");
            if (prayers_fonts_cs.equals("1")) {
                textChild.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_CS1));
                font_size_add = font_cs1;
            }
            if (prayers_fonts_cs.equals("2")) {
                textChild.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_CS2));
                font_size_add = font_cs2;
            }
            if (prayers_fonts_cs.equals("3")) {
                textChild.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_CS3));
                font_size_add = font_cs3;
            }
        }
        String text_size = PreferencesActivity.MyPreferenceFragment.ReadString(
                context, "pref_text_size", "0");
        if (text_size.equals("-5")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 12 + font_size_add);
        }
        if (text_size.equals("-4")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 10 + font_size_add);
        }
        if (text_size.equals("-3")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 8 + font_size_add);
        }
        if (text_size.equals("-2")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 6 + font_size_add);
        }
        if (text_size.equals("-1")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 4 + font_size_add);
        }
        if (text_size.equals("0")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 2 + font_size_add);
        }
        if (text_size.equals("+1")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 0 + font_size_add);
        }
        if (text_size.equals("+2")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 2 + font_size_add);
        }
        if (text_size.equals("+3")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 4 + font_size_add);
        }
        if (text_size.equals("+4")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 6 + font_size_add);
        }
        if (text_size.equals("+5")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 8 + font_size_add);
        }
        if (text_size.equals("+6")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 10 + font_size_add);
        }
        if (text_size.equals("+7")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 12 + font_size_add);
        }
        if (text_size.equals("+8")) {
            textChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 14 + font_size_add);
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

        prayers_language = PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_prayers_language", "ru");

        if (prayers_language.equals("ru")) {
            String prayers_fonts_ru = PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_prayers_fonts_ru", "1");
            if (prayers_fonts_ru.equals("1")) {
                textGroup.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU1));
                font_size_add = font_ru1;
            }
            if (prayers_fonts_ru.equals("2")) {
                textGroup.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU2));
                font_size_add = font_ru2;
            }
            if (prayers_fonts_ru.equals("3")) {
                textGroup.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU3));
                font_size_add = font_ru3;
            }
            if (prayers_fonts_ru.equals("4")) {
                textGroup.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU4));
                font_size_add = font_ru4;
            }
            if (prayers_fonts_ru.equals("5")) {
                textGroup.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU5));
                font_size_add = font_ru5;
            }
            if (prayers_fonts_ru.equals("6")) {
                textGroup.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU6));
                font_size_add = font_ru6;
            }
            if (prayers_fonts_ru.equals("7")) {
                textGroup.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_RU7));
                font_size_add = font_ru7;
            }
        } else {
            String prayers_fonts_cs = PreferencesActivity.MyPreferenceFragment.ReadString(context, "pref_prayers_fonts_cs", "1");
            if (prayers_fonts_cs.equals("1")) {
                textGroup.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_CS1));
                font_size_add = font_cs1;
            }
            if (prayers_fonts_cs.equals("2")) {
                textGroup.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_CS2));
                font_size_add = font_cs2;
            }
            if (prayers_fonts_cs.equals("3")) {
                textGroup.setTypeface(FontsHelper.getTypeFace(context.getApplicationContext(), FONT_PATH_CS3));
                font_size_add = font_cs3;
            }
        }

        String text_size = PreferencesActivity.MyPreferenceFragment.ReadString(
                context, "pref_text_size", "0");
        //textGroup.setTextAppearance(context, R.style.boldText);
        if (text_size.equals("-5")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 10 + font_size_add);
        }
        if (text_size.equals("-4")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 8 + font_size_add);
        }
        if (text_size.equals("-3")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 6 + font_size_add);
        }
        if (text_size.equals("-2")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 4 + font_size_add);
        }
        if (text_size.equals("-1")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 2 + font_size_add);
        }
        if (text_size.equals("0")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + font_size_add);
        }
        if (text_size.equals("+1")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 2 + font_size_add);
        }
        if (text_size.equals("+2")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 4 + font_size_add);
        }
        if (text_size.equals("+3")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 6 + font_size_add);
        }
        if (text_size.equals("+4")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 8 + font_size_add);
        }
        if (text_size.equals("+5")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 10 + font_size_add);
        }
        if (text_size.equals("+6")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 12 + font_size_add);
        }
        if (text_size.equals("+7")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 14 + font_size_add);
        }
        if (text_size.equals("+8")) {
            textGroup.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 16 + font_size_add);
        }
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        // TODO Auto-generated method stub
        return null;
    }
}
