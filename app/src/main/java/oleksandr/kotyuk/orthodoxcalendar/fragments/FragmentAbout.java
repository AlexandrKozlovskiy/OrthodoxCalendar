package oleksandr.kotyuk.orthodoxcalendar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import oleksandr.kotyuk.orthodoxcalendar.BuildConfig;
import oleksandr.kotyuk.orthodoxcalendar.DescriptionActivity;
import oleksandr.kotyuk.orthodoxcalendar.MyView;
import oleksandr.kotyuk.orthodoxcalendar.R;

public class FragmentAbout extends Fragment {

public static final String IMAGE_RESOURCE_ID = "iconResourceID";
public static final String ITEM_NAME = "itemName";



public FragmentAbout() {

}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState)  {
 View view = inflater.inflate(R.layout.fragment_layout_about, container,
  false);
 MyView getapp =(MyView) view.findViewById(R.id.source);
 getapp.setMovementMethod(LinkMovementMethod.getInstance());

Button button_help3=(Button) view.findViewById(R.id.button_about3);
 button_help3.setOnClickListener(new OnClickListener() {
 
 @Override
 public void onClick(View v) {
  // TODO Auto-generated method stub
  ///////////////////////////////////
  //Toast.makeText(getActivity(), "FragmentAbout...onClick", Toast.LENGTH_LONG).show();
  /////////////////////////////////////
  Intent intent=new Intent(getActivity(), DescriptionActivity.class);
  intent.putExtra("id", 8);
  startActivity(intent);
 }
 });
 ((MyView) view.findViewById(R.id.MyView4About)).setText(getString(R.string.program_version,BuildConfig.VERSION_NAME));
 return view;
}

}
