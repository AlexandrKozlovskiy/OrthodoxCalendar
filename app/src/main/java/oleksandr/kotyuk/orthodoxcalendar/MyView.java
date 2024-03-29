package oleksandr.kotyuk.orthodoxcalendar;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class MyView extends AppCompatTextView {

    final SpannableStringBuilder builder = new SpannableStringBuilder(getText());
    private URLSpan[] spans = builder.getSpans(0, builder.length(), URLSpan.class);
    private int accessibilityFocusedId = -1;
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
        if (event.getAction() == MotionEvent.ACTION_HOVER_EXIT) accessibilityFocusedId = -1;
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
            //accessibilityFocusedId=-1;
        }
        return false;
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat infoCompat=AccessibilityNodeInfoCompat.wrap(info);
        if(infoCompat.isClickable() ||infoCompat.isLongClickable() ||infoCompat.isScrollable() ||infoCompat.isCheckable() ||infoCompat.isScrollable() ||isExpandable()) {
            infoCompat.setFocusable(true);
infoCompat.setScreenReaderFocusable(true);
infoCompat.setImportantForAccessibility(true);
        }
        if(getTypeface()!=null &&getTypeface().getStyle()==Typeface.BOLD &&spans.length ==0 &&!isExpandable()) infoCompat.setHeading(true);
infoCompat.setContentDescription(getText()); //Чтобы talkback самостоятельно реализовывал навигацию по символам,словам и т.д,вместо фреймворка.
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
        return new AccessibilityNodeProvider() {
            @Override
            public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
                AccessibilityNodeInfoCompat info = AccessibilityNodeInfoCompat.obtain(MyView.this,virtualViewId);
                onInitializeAccessibilityNodeInfo(info.unwrap());
                if (virtualViewId == NO_ID) {
                    if (!getText().toString().equals(builder.toString())) {
                        builder.clear();
                        builder.append(getText());
                        spans = builder.getSpans(0, builder.length(), URLSpan.class);
                    }
                    if (spans != null &&spans.length>0) {
                        for (int a = 0; a < spans.length; a++) info.addChild(MyView.this, a);
                        info.addAction(AccessibilityNodeInfo.ACTION_CLICK);
                        info.setClickable(true);
                    }
                /*info.removeAction(AccessibilityNodeInfo.ACTION_EXPAND);
                info.removeAction(AccessibilityNodeInfo.ACTION_COLLAPSE);*/
                    if(isExpandable()) info.addAction(isExpanded()? AccessibilityNodeInfo.ACTION_COLLAPSE:AccessibilityNodeInfo.ACTION_EXPAND);
                    if(!isExpandable() &&virtualViewId==NO_ID &&spans.length==1 &&builder.getSpanStart(spans[0])+builder.getSpanEnd(spans[0])==builder.length()) info.setVisibleToUser(false);
                }
                else {
                    Layout layout=getLayout();
                    if(layout==null) return null;
                    int start =builder.getSpanStart(spans[virtualViewId]);
                    int end=builder.getSpanEnd(spans[virtualViewId]);
                    CharSequence content=builder.subSequence(start,end);
                    int startLine=layout.getLineForOffset(start);
                    int endLine=layout.getLineForOffset(end);
                    double startClick=layout.getPrimaryHorizontal(start);
                    double endClick=layout.getPrimaryHorizontal(end);
                    Rect r=new Rect();
                    int[] location=new int[2];
                    getLocationOnScreen(location);
                    layout.getLineBounds(startLine, r);
/*int paddingTop=getCompoundPaddingTop();
int paddingLeft=getCompoundPaddingLeft();*/
                    int paddingTop=getTotalPaddingTop();
                    int paddingLeft=getTotalPaddingLeft();
                    int parentTopAndBottomOffset = location[1]-getScrollY() + paddingTop;
                    r.top += parentTopAndBottomOffset;
                    r.bottom += parentTopAndBottomOffset;
                    if(startLine!=endLine) {
                        if(r.top>((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight()-r.bottom) endClick=layout.getLineRight(startLine);
                        else {
                            r=new Rect();
                            layout.getLineBounds(endLine,r);
                            r.top+=parentTopAndBottomOffset;
                            r.bottom+=parentTopAndBottomOffset;
                            startClick=layout.getLineLeft(endLine);
                        }
                    }
                    r.left+=location[0]+startClick+paddingLeft-getScrollX();
                    r.right=(int)(r.left+endClick-startClick);
                    info.setBoundsInScreen(r);
                    info.setClickable(true);
                    info.setScreenReaderFocusable(true);
                    info.setText(content);
                    info.setContentDescription(content);
                    info.setParent(MyView.this);
                /*info.addAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS);
                                    info.addAction(AccessibilityNodeInfo.ACTION_CLEAR_ACCESSIBILITY_FOCUS);*/
                    info.addAction(AccessibilityNodeInfo.ACTION_CLICK);
                    info.setHeading(false);
                }
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT &&spans.length>0) {
                    //if(virtualViewId==NO_ID) info.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(spans.length,1,false));
                    if(virtualViewId>NO_ID)info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(virtualViewId,1,0,1,false));
                }
                return info.unwrap();
            }

            @Override
            public boolean performAction ( int virtualViewId, int action, Bundle arguments){
                if (virtualViewId == NO_ID) return performAccessibilityAction(action, arguments);
                else {
                    if (action == AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS) sendAccessibilityEventForVirtualView(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED, virtualViewId);
                    else if(action==AccessibilityNodeInfo.ACTION_CLEAR_ACCESSIBILITY_FOCUS) sendAccessibilityEventForVirtualView(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED,virtualViewId);
/*else if(action == AccessibilityNodeInfo.ACTION_FOCUS) sendAccessibilityEventForVirtualView(AccessibilityEvent.TYPE_VIEW_FOCUSED, virtualViewId);
                else if(action==AccessibilityNodeInfo.ACTION_CLEAR_FOCUS) sendAccessibilityEventForVirtualView(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED);*/
                    else if (action == AccessibilityNodeInfo.ACTION_CLICK) {
                        spans[virtualViewId].onClick(MyView.this);
                        sendAccessibilityEventForVirtualView(AccessibilityEvent.TYPE_VIEW_CLICKED,virtualViewId);
                    }
//else if(action== AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SHOW_ON_SCREEN.getId()) return false;
                    return true;
                }
            }
        };
    }
    @Override
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (action == AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS ||action ==AccessibilityNodeInfo.ACTION_FOCUS) accessibilityFocusedId = -1;
        else if(action ==AccessibilityNodeInfo.ACTION_EXPAND ||action==AccessibilityNodeInfo.ACTION_COLLAPSE) return performClick();
      return super.performAccessibilityAction(action, arguments);
    }

    @Override
    public boolean performClick() {
if(isExpandable()) setExpanded(!isExpanded());
        else if(accessibilityFocusedId>-1) {
            //Почему-то на android 4.4 при клике на дочернее accessibilityNodeInfo,иногда клик происходит по родительскому accessibilitytyNodeInfo.
            getAccessibilityNodeProvider() .performAction(accessibilityFocusedId,AccessibilityNodeInfo.ACTION_CLICK,null);
            return true;
        }
        return super.performClick();
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        boolean touch=touch(event);
        if(!touch) return super.dispatchHoverEvent(event); else return touch;
    }
    @Override
    public CharSequence getAccessibilityClassName() {
return MyView.class.getName();
    }
}
