package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<TodoItem> items;
    ItemAdapter itemsAdapter;
    ListView lvItems;
    public static final int RESULT_EDITED_ITEM = 1;

    private int sortColumn = -1;
    private boolean mainSortDirection = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ItemAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();

        // TODO: Hide keyboard until user wants to add an item
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        TodoItem deletedItem = items.remove(position);
                        deleteItem(deletedItem);
                        Toast toast = Toast.makeText(parent.getContext(), "Deleted '" + deletedItem.getText() + "'", Toast.LENGTH_SHORT);
                        toast.show();
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TodoItem item = (TodoItem) parent.getItemAtPosition(position);
                        Intent editItemIntent = new Intent(getApplicationContext(), EditItemActivity.class);
                        editItemIntent.putExtra(EditItemActivity.EXTRA_ITEM, item);
                        editItemIntent.putExtra(EditItemActivity.EXTRA_ITEM_INDEX, position);
                        startActivityForResult(editItemIntent, RESULT_EDITED_ITEM);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(getClass().getSimpleName(), "Request[" + requestCode + "] result[" + resultCode + "]");
        if (resultCode == RESULT_OK && requestCode == RESULT_EDITED_ITEM) {
            int index = data.getIntExtra(EditItemActivity.EXTRA_ITEM_INDEX, 0);
            TodoItem item = (TodoItem) data.getSerializableExtra(EditItemActivity.EXTRA_ITEM);
            items.set(index, item);
            itemsAdapter.notifyDataSetChanged();
            updateItem(item);
        }
    }


    private boolean isEmpty(String s) {
        return (s == null || s.length() == 0);
    }


    public void toggleDueDateSort(View v) {
//        Toast.makeText(getApplication(), "Toggling due date sort", Toast.LENGTH_SHORT).show();

        if (sortColumn != 1) {
            sortColumn = 1;
            mainSortDirection = true;
        }

        final SimpleDateFormat sdf = new SimpleDateFormat(TodoItem.DUE_DATE_FORMAT);
        final int sortDir = mainSortDirection ? 1 : -1;

        Collections.sort(items, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem lhs, TodoItem rhs) {
                String lDueDate = lhs.getDueDate();
                String rDueDate = rhs.getDueDate();
                if (isEmpty(lDueDate)) {
                    if (isEmpty(rDueDate)) {
                        return 0;
                    }
                    return 1 * sortDir;
                } else if (isEmpty(rDueDate)) {
                    return -1 * sortDir;
                }

                try {
                    return sdf.parse(lDueDate).compareTo(sdf.parse(rDueDate)) * sortDir;
                } catch (ParseException e) {
                    return 0;
                }
            }
        });

        itemsAdapter.notifyDataSetChanged();
        mainSortDirection = !mainSortDirection;
    }

    public void toggleItemTextSort(View v) {
//        Toast.makeText(getApplication(), "Toggling item text sort", Toast.LENGTH_SHORT).show();

        if (sortColumn != 2) {
            sortColumn = 2;
            mainSortDirection = true;
        }

        final int sortDir = mainSortDirection ? 1 : -1;

        Collections.sort(items, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem lhs, TodoItem rhs) {
                String lText = lhs.getText();
                String rText = rhs.getText();
                if (isEmpty(lText)) {
                    if (isEmpty(rText)) {
                        return 0;
                    }
                    return 1*sortDir;
                } else if (isEmpty(rText)) {
                    return -1*sortDir;
                }

                return lText.compareTo(rText)*sortDir;
            }
        });

        itemsAdapter.notifyDataSetChanged();
        mainSortDirection = !mainSortDirection;
    }

    public void togglePrioritySort(View v) {
//        Toast.makeText(getApplication(), "Toggling priority sort", Toast.LENGTH_SHORT).show();

        if (sortColumn != 3) {
            sortColumn = 3;
            mainSortDirection = true;
        }

        final int sortDir = mainSortDirection ? 1 : -1;

        Collections.sort(items, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem lhs, TodoItem rhs) {
                TodoItem.Priority lPriority = lhs.getPriority();
                TodoItem.Priority rPriority = rhs.getPriority();

                return (rPriority.ordinal()-lPriority.ordinal())*sortDir;
            }
        });

        itemsAdapter.notifyDataSetChanged();
        mainSortDirection = !mainSortDirection;
    }


    private void readItems() {
        items = TodoItemDbHelper.getInstance(getApplicationContext()).getItems();
    }

    private void saveItem(TodoItem item) {
        TodoItemDbHelper.getInstance(getApplicationContext()).saveItem(item);
    }

    private void updateItem(TodoItem item) {
        TodoItemDbHelper.getInstance(getApplicationContext()).updateItem(item);
    }

    private void deleteItem(TodoItem item) {
        TodoItemDbHelper.getInstance(getApplicationContext()).deleteItem(item);
    }

    private void writeItems() {
        TodoItemDbHelper.getInstance(getApplicationContext()).writeItems(items);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if (itemText != null && !itemText.isEmpty()) {
            TodoItem item = new TodoItem(itemText);
            itemsAdapter.add(item);
            etNewItem.setText("");
            saveItem(item);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Cannot add an empty item", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
