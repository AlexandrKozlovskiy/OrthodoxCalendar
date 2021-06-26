package oleksandr.kotyuk.orthodoxcalendar.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import oleksandr.kotyuk.orthodoxcalendar.R;

public class UpdateNewsDialogFragmentList extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                //.setIcon(R.drawable.androidhappy)
                // Set Dialog Title
                .setTitle(R.string.title_update_news)
                // Set Dialog Message
                .setMessage(R.string.text_update_news_list)

                // Positive button
                .setPositiveButton(R.string.button_dialog_update2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "YES", Toast.LENGTH_LONG).show();

                    }
                }).create();


    }
}
