package com.example.mitosis.timepicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by mitosis on 7/4/17.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Get a Calendar instance
        final Calendar calendar = Calendar.getInstance();
        // Get the current hour and minute
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        /*
            Creates a new time picker dialog.
                TimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener listener,
                    int hourOfDay, int minute, boolean is24HourView)
         */
        // Create a TimePickerDialog with current time
        TimePickerDialog tpd = new TimePickerDialog(getActivity(),this,hour,minute,false);
        // Return the TimePickerDialog
        return tpd;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        // Do something with the returned time
        TextView tv = (TextView) getActivity().findViewById(R.id.tv);
        tv.setText(hourOfDay + ":" + minute);
    }
}