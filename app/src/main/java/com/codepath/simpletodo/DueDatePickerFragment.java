package com.codepath.simpletodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wowens on 6/28/16.
 */
public class DueDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText dueDateText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), 0, this, year, month, day);
    }

    // TODO; better way?
    public void setDueDateText(EditText dueDateText) {
        this.dueDateText = dueDateText;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String dateText = (month+1) + "/" + day + "/" + year;
        Log.d(getClass().getSimpleName(), "Setting dueDateText to " + dateText);
        dueDateText.setText(dateText);
    }
}
