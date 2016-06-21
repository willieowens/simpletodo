package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wowens on 6/20/16.
 */
public class ItemAdapter extends ArrayAdapter<TodoItem> {

    public ItemAdapter(Context context, List<TodoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item, parent, false);
        }

        TextView dueDate = (TextView) convertView.findViewById(R.id.dueDate);
        TextView text = (TextView) convertView.findViewById(R.id.text);

        String dueDateStr = null;
        if (item.getDueDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yy");
            dueDateStr = sdf.format(item.getDueDate());
        }
        dueDate.setText(dueDateStr);
        text.setText(item.getText());

        return convertView;
    }
}
