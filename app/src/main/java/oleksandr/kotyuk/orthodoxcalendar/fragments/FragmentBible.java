package oleksandr.kotyuk.orthodoxcalendar.fragments;

import oleksandr.kotyuk.orthodoxcalendar.R;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentBible extends Fragment {

public static final String IMAGE_RESOURCE_ID = "iconResourceID";
public static final String ITEM_NAME = "itemName";

static final String TAG = "my_log";

public FragmentBible() {

}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {

 View view = inflater.inflate(R.layout.fragment_layout_bible, container,
  false);
ViewCompat.setAccessibilityHeading(view.findViewById(R.id.MyViewBible1),true);
 return view;
}

@Override
public void onDestroy() {
 // TODO Auto-generated method stub
 super.onDestroy();

}

@Override
public void onResume() {
 // TODO Auto-generated method stub
 super.onResume();
}

@Override
public void onPause() {
 super.onPause();
}
}

