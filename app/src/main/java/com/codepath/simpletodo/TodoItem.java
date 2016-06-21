package com.codepath.simpletodo;

import java.util.Date;

/**
 * Created by wowens on 6/20/16.
 */
public class TodoItem {
    private Integer id;
    private String text;
    private String dueDate; // TODO: date type

    public TodoItem(Integer id, String text, String dueDate) {
        this.id = id;
        this.text = text;
        this.dueDate = dueDate;
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

    // ID is set when storing in the db
    public Integer getId() {
        return id;
    }
}
