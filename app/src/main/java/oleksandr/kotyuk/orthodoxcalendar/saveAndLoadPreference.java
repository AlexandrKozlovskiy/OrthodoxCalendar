package oleksandr.kotyuk.orthodoxcalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
public class saveAndLoadPreference extends Preference {
    public View.OnClickListener l;

    public saveAndLoadPreference(Context context) {
        this(context,null);
    }

    public saveAndLoadPreference(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
    }

    public saveAndLoadPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }
    public saveAndLoadPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setLayoutResource(R.layout.custom_layout);
    }
    public void setOnClickListener(View.OnClickListener l) {
        this.l = l;
    }

    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        if(l!=null) {
            holder.findViewById(R.id.save_settings).setOnClickListener(l);
            holder.findViewById(R.id.load_settings).setOnClickListener(l);
        }
    }
/*
    @Override
    protected View onCreateView(ViewGroup parent) {
LinearLayout lL =(LinearLayout) ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_layout,parent,false);
if(l!=null) {
    lL.findViewById(R.id.save_settings).setOnClickListener(l);
    lL.findViewById(R.id.load_settings).setOnClickListener(l);
}
return lL;
    }
    */
}