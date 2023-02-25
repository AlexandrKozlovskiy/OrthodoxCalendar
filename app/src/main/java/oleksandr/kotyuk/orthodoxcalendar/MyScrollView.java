package oleksandr.kotyuk.orthodoxcalendar;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    private View getnearInvisibleView(boolean toFirst) {
        //Этот метод предполагает,что в нашем scrollView есть только одно viewGroup и больше ничего,а в этом layout есть ещё View. Посути это очень ленивый разбор,который в последствии нужно улучшить.
        //Поскольку мы хотим делать быструю прокрутку и назад,а у нас всего два view,первое view видимое,поэтому если мы не находим невидимых view,мы возвращаем первое или последнее view в нашем layout.
        ViewGroup layout = getChildAt(0) instanceof ViewGroup?(ViewGroup) getChildAt(0):this;
        for(int i=0;i<layout.getChildCount();i++) {
            View view =layout.getChildAt(!toFirst?i:layout.getChildCount()-i-1);
            if(view !=null) {
                Rect rect =new Rect();
                getDrawingRect(rect);
                if(!view.getGlobalVisibleRect(rect)) return view;
            }
        }
        return layout.getChildAt(!toFirst?0:layout.getChildCount());
    }
    @Override
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if(action== AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD ||action==AccessibilityNodeInfo.ACTION_SCROLL_FORWARD) {
            View view=getnearInvisibleView(action==AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
            if(view!=null) {
                int inc=action==AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD?-1:1;
                scrollTo(inc*view.getLeft(),inc*view.getTop());
                return true;
            }
        }
        return super.performAccessibilityAction(action, arguments);
    }
}