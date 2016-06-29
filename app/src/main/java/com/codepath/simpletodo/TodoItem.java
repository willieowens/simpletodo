package com.codepath.simpletodo;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wowens on 6/20/16.
 */
public class TodoItem implements Serializable {

    private Integer id;
    private String text;
    private String dueDate; // TODO: date type
    private Priority priority;

    public static final String DUE_DATE_FORMAT = "MM/dd/yyyy";

    public enum Priority {
        LOW(Color.DKGRAY),
        MED(Color.BLACK),
        HIGH(Color.RED);

        private int textColor;

        private Priority(int textColor) {
            this.textColor = textColor;
        }

        public int getTextColor() {
            return textColor;
        }
    }

    public TodoItem(Integer id, String text, String dueDate, Priority priority) {
        this.id = id;
        this.text = text;
        this.dueDate = dueDate;
        if (priority != null)
            this.priority = priority;
        else
            this.priority = Priority.MED;
    }
    public TodoItem(Integer id, String text, String dueDate) {
        this(id, text, dueDate, null);
    }
    public TodoItem(String text, String dueDate) {
        this(null, text, dueDate);
    }
    public TodoItem(String text) {
        this(null, text, null);
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    // ID is set when storing in the db
    public Integer getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id == null) {
            sb.append("NewTodoItem{");
        } else {
            sb.append("TodoItem").append(id).append('{');
        }
        sb.append(text);
        sb.append(':').append(' ');
        sb.append("Due[").append(dueDate).append(']').append(',');
        sb.append("Priority[").append(priority.name()).append(']');
        sb.append('}');
        return sb.toString();
    }
}
