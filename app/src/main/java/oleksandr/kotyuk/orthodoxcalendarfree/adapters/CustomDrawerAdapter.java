package oleksandr.kotyuk.orthodoxcalendarfree.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

import java.util.List;

import oleksandr.kotyuk.orthodoxcalendarfree.R;
import oleksandr.kotyuk.orthodoxcalendarfree.accessibility.MyView;
import oleksandr.kotyuk.orthodoxcalendarfree.models.DrawerItem;

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
		// TODO Auto-generated method stub

		DrawerItemHolder drawerHolder;
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			drawerHolder = new DrawerItemHolder();

			convertView = inflater.inflate(layoutResID, parent, false);
drawerHolder.ItemName = (MyView) convertView.findViewById(R.id.drawer_itemName);
			drawerHolder.icon = (ImageView) convertView.findViewById(R.id.drawer_icon);
			drawerHolder.title = (MyView) convertView.findViewById(R.id.drawerTitle);
			drawerHolder.headerLayout = (LinearLayout) convertView.findViewById(R.id.headerLayout);
			drawerHolder.itemLayout = (LinearLayout) convertView.findViewById(R.id.itemLayout);
			DrawerItem dItem = drawerItemList.get(position);

if (dItem.getTitle() != null) {
				drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
				drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
				drawerHolder.title.setText(dItem.getTitle());
}
else {

				drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
				drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
				drawerHolder.icon.setImageDrawable(convertView.getResources().getDrawable(dItem.getImgResID()));
				drawerHolder.ItemName.setText(dItem.getItemName());
			}

			convertView.setTag(drawerHolder);
		}
else {
			drawerHolder = (DrawerItemHolder) convertView.getTag();
		}
		if (drawerItemList.get(position).getTitle() != null) ViewCompat.setAccessibilityHeading(convertView,true);
		return convertView;
	}

	private static class DrawerItemHolder {
		MyView ItemName, title;
		ImageView icon;
		LinearLayout headerLayout, itemLayout;
	}
}