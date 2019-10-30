package com.brianfakeurltwitter.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {
    private static final String ARG_TIME = "time";
    public static final String EXTRA_TIME = "com.brianfakeurltwitter.criminalintent.time";

    private TimePicker mTimePicker;
    private Calendar mInitialCalendar;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        Date date = (Date) getArguments().getSerializable(ARG_TIME);
        mInitialCalendar = Calendar.getInstance();
        mInitialCalendar.setTime(date);
        int hour = mInitialCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mInitialCalendar.get(Calendar.MINUTE);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        mTimePicker = v.findViewById(R.id.dialog_time_picker);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Time of crime")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int hour = mTimePicker.getCurrentHour();
                                int minute = mTimePicker.getCurrentMinute();
                                Date date = new GregorianCalendar(
                                        mInitialCalendar.get(Calendar.YEAR),
                                        mInitialCalendar.get(Calendar.MONTH),
                                        mInitialCalendar.get(Calendar.DAY_OF_MONTH),
                                        hour,
                                        minute
                                ).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
