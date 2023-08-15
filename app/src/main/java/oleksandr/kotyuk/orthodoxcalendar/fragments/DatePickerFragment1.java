package oleksandr.kotyuk.orthodoxcalendar.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import oleksandr.kotyuk.orthodoxcalendar.MainActivity;
import oleksandr.kotyuk.orthodoxcalendar.models.MyCalendar;

public class DatePickerFragment1 extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    DatePickerDialog dpd;
    final String LOG_TAG = "myLogs";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Используем текущую дату в качестве даты по умолчанию
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Создаем новый экземпляр DatePickerDialog и возвращаем его
        dpd = new DatePickerDialog(getActivity(), this, year, month, day);
        dpd.setTitle("Выберите дату c 2021г. по 2024г.");
        dpd.setButton(DialogInterface.BUTTON_NEGATIVE,
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.cancel();
                        }
                    }
                });
        dpd.setButton(DialogInterface.BUTTON_POSITIVE,
                "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {

                            DatePicker datePicker = dpd.getDatePicker();

                            MyCalendar cal = MyCalendar.getInstance();
                            cal.setDate(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

                            ((MainActivity) getActivity()).UpdateActivityDate();
                        }
                    }
                });
        DatePicker dp = dpd.getDatePicker();
        // устанавливаем период дат(2021-2024гг)
        Calendar cal1 = new GregorianCalendar(2021, 0, 01);
        Calendar cal2 = new GregorianCalendar(2024, 11, 31);
        dp.setMinDate(cal1.getTimeInMillis());
        dp.setMaxDate(cal2.getTimeInMillis());
        return dpd;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Сделать что-нибудь с датой , выбранной пользователем

 /*MyCalendar cal = MyCalendar.getInstance();
 cal.setDate(year, month, day);

 UpdateActivityDate();*/
        Log.d(LOG_TAG, "DatePickerFragment");
    }
}