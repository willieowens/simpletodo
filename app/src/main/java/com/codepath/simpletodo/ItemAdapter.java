package com.codepath.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        TextView tvDueDate = (TextView) convertView.findViewById(R.id.dueDate);
        TextView tvText = (TextView) convertView.findViewById(R.id.text);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.priority);

        String dueDateStr = item.getDueDate();
        tvDueDate.setText(dueDateStr);
        boolean isDue = false;
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat(TodoItem.DUE_DATE_FORMAT);
            try {
                Date dueDate = sdf.parse(dueDateStr);
                if (dueDate.before(new Date())) {
                    isDue = true;
                }
            } catch (ParseException e) {
                Log.w(getClass().getSimpleName(), "Failed to parse dueDateStr, '" + dueDateStr
                        + "', for date using DateFormat " + TodoItem.DUE_DATE_FORMAT);
            }
        }
        if (isDue) {
            tvDueDate.setTextColor(Color.RED);
        } else {
            tvDueDate.setTextColor(Color.BLACK);
        }

        tvText.setText(item.getText());

        TodoItem.Priority priority = item.getPriority() == null ? TodoItem.Priority.MED : item.getPriority();
        tvPriority.setText(item.getPriority() == null ? null : item.getPriority().name());
        tvPriority.setTextColor(priority.getTextColor());

        if (priority == TodoItem.Priority.HIGH && isDue) {
            tvText.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            tvText.setTypeface(Typeface.DEFAULT);
        }

        if (position % 2 == 0) {
//            convertView.setBackgroundColor(0xF090CAF9);
            convertView.setBackgroundColor(0xF0E3F2FD);
        } else {
            convertView.setBackgroundColor(0xF0BBDEFB);
        }

        return convertView;
    }
}
