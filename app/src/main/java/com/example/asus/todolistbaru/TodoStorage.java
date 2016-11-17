package com.example.asus.todolistbaru;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ASUS on 11/12/2016.
 */

public class TodoStorage {

    public static final String PREFERENCE_NAME = "TODO_PREF";
    public static final String LIST_COUNT_TAG = "LIST_COUNT";
    public static final String ITEM_TAG = "ITEM";

    public void saveTodo(String[] todoList, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(LIST_COUNT_TAG, todoList.length);
        for (int i =0; i<todoList.length; i++) {
            editor.putString(ITEM_TAG + "_" + i, todoList[i]);
        }
        editor.apply();
    }

    public String[] loadTodo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        int listCount = preferences.getInt(LIST_COUNT_TAG, 0);
        String[] todoList = new String[listCount];
        for (int i=0; i<listCount; i++) {
            todoList[i] = preferences.getString(ITEM_TAG + "_" + i, "");
        }
        return todoList;
    }
}
