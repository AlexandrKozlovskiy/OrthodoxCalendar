package oleksandr.kotyuk.orthodoxcalendar.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import java.util.List;

import oleksandr.kotyuk.orthodoxcalendar.R;
import oleksandr.kotyuk.orthodoxcalendar.MyView;
import oleksandr.kotyuk.orthodoxcalendar.models.DrawerItem;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

Context context;
List<DrawerItem> drawerItemList;
int layoutResID;

public CustomDrawerAdapter(Context context, int layoutResourceID,
 List<DrawerItem> listItems) {
 super(context, layoutResourceID, listItems);
 this.context = context;
 this.drawerItemList = listItems;
 this.layoutResID = layoutResourceID;

}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerItemHolder drawerHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();
            convertView = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (MyView) convertView
                    .findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) convertView.findViewById(R.id.drawer_icon);
            drawerHolder.title = (MyView) convertView.findViewById(R.id.drawerTitle);
            drawerHolder.headerLayout = (LinearLayout) convertView
                    .findViewById(R.id.headerLayout);
            drawerHolder.itemLayout = (LinearLayout) convertView
                    .findViewById(R.id.itemLayout);
            convertView.setTag(drawerHolder);
        }
        else drawerHolder = (DrawerItemHolder) convertView.getTag();
        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
        if (dItem.getTitle() != null) {
            drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.title.setText(dItem.getTitle());
            ViewCompat.setAccessibilityHeading(convertView,true);
}
        else {
            drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.icon.setImageDrawable(convertView.getResources().getDrawable(
                    dItem.getImgResID()));
            drawerHolder.ItemName.setText(dItem.getItemName());
        }
        return convertView;
    }

private static class DrawerItemHolder {
 MyView ItemName, title;
 ImageView icon;
 LinearLayout headerLayout, itemLayout;
}
}