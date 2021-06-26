package oleksandr.kotyuk.orthodoxcalendarfree.fragments;

import oleksandr.kotyuk.orthodoxcalendarfree.DescriptionActivity;
import oleksandr.kotyuk.orthodoxcalendarfree.R;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentAbout extends Fragment {

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	
	

	public FragmentAbout() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_about, container,
				false);
ViewCompat.setAccessibilityHeading(view.findViewById(R.id.MyViewAbout1),true);
		ViewCompat.setAccessibilityHeading(view.findViewById(R.id.MyViewAbout2),true);

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

		return view;
	}

}
