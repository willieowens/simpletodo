package com.codepath.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_INDEX = "itemIndex";
    public static final String EXTRA_ITEM_TEXT = "itemText";

    private int itemIndex;
    EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemIndex = getIntent().getIntExtra(EXTRA_ITEM_INDEX, 0);
        setEditItemText();
    }

    private void setEditItemText() {
        String editText = getIntent().getStringExtra(EXTRA_ITEM_TEXT);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(editText);
        etEditItem.setSelection(editText.length());

        etEditItem.requestFocus();

        // TODO: Have keyboard open upon first activity load
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(etEditItem, InputMethodManager.SHOW_IMPLICIT);
    }

    public void onSaveItem(View v) {
        String itemText = etEditItem.getText().toString();
        Intent result = new Intent();
        result.putExtra(EXTRA_ITEM_INDEX, itemIndex);
        result.putExtra(EXTRA_ITEM_TEXT, itemText);
        setResult(MainActivity.RESULT_OK, result);
        finish();
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
