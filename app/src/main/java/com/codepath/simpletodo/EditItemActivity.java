package com.codepath.simpletodo;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditItemActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_INDEX = "item_index";
    public static final String EXTRA_ITEM = "item";

    private TodoItem item;
    private int itemIndex;

    private EditText etEditItem;
    private EditText etDueDate;
    private Spinner spPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item = (TodoItem) getIntent().getSerializableExtra(EXTRA_ITEM);
        itemIndex = getIntent().getIntExtra(EXTRA_ITEM_INDEX, 0);
        configureEditableItem();
    }

    private void configureEditableItem() {
        String editText = item.getText();

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(editText);
        etEditItem.setSelection(editText.length());

        // TODO: allow user to not choose a date (and make the datepicker look a lot better)
        etDueDate = (EditText) findViewById(R.id.etDueDate);
        String dueDate = item.getDueDate();
        etDueDate.setText(dueDate);
        /*if (dueDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(TodoItem.DUE_DATE_FORMAT);
            Date date = new Date();
            try {
                date = sdf.parse(dueDate);
            } catch (ParseException e) {
                Log.w(getClass().getSimpleName(), "Failed to parse due date, '" + dueDate + "' using format " + TodoItem.DUE_DATE_FORMAT);
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            datePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        }*/

        spPriority = (Spinner) findViewById(R.id.spinnerPriority);
        ArrayAdapter<TodoItem.Priority> priorityAdapter = new ArrayAdapter<TodoItem.Priority>(
                this, android.R.layout.simple_list_item_1, TodoItem.Priority.values());
        spPriority.setAdapter(priorityAdapter);
        int priorityIndex = item.getPriority().ordinal();
        spPriority.setSelection(priorityIndex);
    }

    public void onSaveItem(View v) {
        String itemText = etEditItem.getText().toString();
        String dueDate = etDueDate.getText().toString();
        TodoItem.Priority priority = (TodoItem.Priority) spPriority.getSelectedItem();

        item.setText(itemText);
        item.setDueDate(dueDate);
        item.setPriority(priority);

        Intent result = new Intent();
        result.putExtra(EXTRA_ITEM, item);
        result.putExtra(EXTRA_ITEM_INDEX, itemIndex);
        setResult(MainActivity.RESULT_OK, result);

        finish();
    }

    public void clearDueDateText(View v) {
        etDueDate.setText(null);
    }

    public void showDatePickerDialog(View v) {
        DueDatePickerFragment datePickerFragment = new DueDatePickerFragment();
        datePickerFragment.setDueDateText(etDueDate);
        datePickerFragment.show(getFragmentManager(), "dueDatePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
