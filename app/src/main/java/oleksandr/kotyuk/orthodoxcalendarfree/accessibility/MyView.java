package oleksandr.kotyuk.orthodoxcalendarfree.accessibility;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class MyView extends AppCompatTextView {
    private final AccessibilityNodeProvider MyProvider =new AccessibilityNodeProvider() {
        @Override
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
            AccessibilityNodeInfo info = AccessibilityNodeInfo.obtain(MyView.this);
            onInitializeAccessibilityNodeInfo(info);
            if (virtualViewId == NO_ID) {
                                if (!getText().toString().equals(builder.toString())) {
                    builder.clear();
                    builder.append(getText());
                    spans = builder.getSpans(0, builder.length(), URLSpan.class);
                }
                if (spans != null) for (int a = 0; a < spans.length; a++) {
                    info.addChild(MyView.this, a);
                }
                /*info.removeAction(AccessibilityNodeInfo.ACTION_EXPAND);
                info.removeAction(AccessibilityNodeInfo.ACTION_COLLAPSE);*/
                if(isExpandable()) info.addAction(isExpanded()? AccessibilityNodeInfo.ACTION_COLLAPSE:AccessibilityNodeInfo.ACTION_EXPAND);
            }
            else {
                    info.setSource(MyView.this, virtualViewId);
                    CharSequence content=builder.subSequence(builder.getSpanStart(spans[virtualViewId]), builder.getSpanEnd(spans[virtualViewId]));
info.setText(content);
                info.setContentDescription(content);
                    /*info.addAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS);
                                    info.addAction(AccessibilityNodeInfo.ACTION_CLEAR_ACCESSIBILITY_FOCUS);*/
                info.addAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                return info;
            }

        @Override
            public boolean performAction ( int virtualViewId, int action, Bundle arguments){
                if (virtualViewId == NO_ID) return performAccessibilityAction(action, arguments);
else {
                    if (action == AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS) sendAccessibilityEventForVirtualView(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED, virtualViewId);
                    else                     if (action == AccessibilityNodeInfo.ACTION_FOCUS) sendAccessibilityEventForVirtualView(AccessibilityEvent.TYPE_VIEW_FOCUSED, virtualViewId);
else if (action == AccessibilityNodeInfo.ACTION_CLICK) {
                        sendAccessibilityEventForVirtualView(AccessibilityEvent.TYPE_VIEW_CLICKED, virtualViewId);
                        spans[virtualViewId].onClick(MyView.this);
                    }
                }
                return true;
            }
        };

        final SpannableStringBuilder builder = new SpannableStringBuilder(getText());
        private URLSpan[] spans = builder.getSpans(0, builder.length(), URLSpan.class);
        private int accessibilityFocusedId = -2;
    private boolean isExpanded=false;
    private boolean isExpandable=false;
        public MyView(Context context) {
            super(context);
        }

        public MyView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        private void sendAccessibilityEventForVirtualView(int eventType, int virtualViewId) {
            if (getParent() != null && ((AccessibilityManager) getContext().getSystemService(Context.ACCESSIBILITY_SERVICE)).isTouchExplorationEnabled()) {
                AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
                event.setPackageName(getContext().getPackageName());
                event.setSource(MyView.this, virtualViewId);
                if(event.getEventType() ==AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED ||event.getEventType() ==AccessibilityEvent.TYPE_VIEW_FOCUSED) accessibilityFocusedId=virtualViewId;
getParent().requestSendAccessibilityEvent(MyView.this, event);
                                            }
        }

        private boolean touch(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_HOVER_EXIT) accessibilityFocusedId = -2;
            else if (event.getAction() == MotionEvent.ACTION_HOVER_ENTER || event.getAction() == MotionEvent.ACTION_HOVER_MOVE) {
                Layout layout = getLayout();
                if (layout != null) {
                    int x = (int) (event.getX()) - getTotalPaddingLeft() + getScrollX();
                    int y = (int) (event.getY()) - getTotalPaddingTop() + getScrollY();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);
                    URLSpan[] links = builder.getSpans(off, off, URLSpan.class);
                    //Поскольку мы работаем с одним символом,длинна ссылки у нас равна 1
                    if (links != null && links.length > 0) for (int a = 0; a < spans.length; a++)
                        if (spans[a].equals(links[0])) {
                                                        if (accessibilityFocusedId != a) {
                                sendAccessibilityEventForVirtualView(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED, a);
                            }
                            return true;
                        }
                                    }
                //if(accessibilityFocusedId!=-1)sendAccessibilityEventForVirtualView(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED,-1);
                accessibilityFocusedId=-1;
            }
return false;
    }
    public void setExpanded(boolean isExpanded) {
this.isExpanded=isExpanded;
this.isExpandable=true;
    }
    public void setExpandable(boolean isExpandable) {
this.isExpandable=isExpandable;
    }
    public boolean isExpanded() {
return isExpanded;
    }
    public boolean isExpandable() {
return isExpandable;
    }
    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        return MyProvider;
    }
    @Override
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (action == AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS ||action ==AccessibilityNodeInfo.ACTION_FOCUS)accessibilityFocusedId = -1;
        //else if(action==AccessibilityNodeInfo.ACTION_CLEAR_ACCESSIBILITY_FOCUS ||action==AccessibilityNodeInfo.ACTION_CLEAR_FOCUS) accessibilityFocusedId=-2;
        return super.performAccessibilityAction(action, arguments);
    }

    @Override
    public boolean performClick() {
            if(isExpandable()) setExpanded(!isExpanded());
            else if(accessibilityFocusedId>-1) {
                //Почему-то на android 4.4 при клике на дочернее accessibilityNodeInfo,клик происходит по родительскому accessibilitytyNodeInfo.
getAccessibilityNodeProvider().performAction(accessibilityFocusedId,AccessibilityNodeInfo.ACTION_CLICK,null);
return true;
            }
        return super.performClick();
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
            boolean touch=touch(event);
if(!touch) return super.dispatchHoverEvent(event); else return touch;
}
}
