package com.rafal.movielibrary.viewmodel.util;

import android.widget.TextView;

import androidx.databinding.InverseMethod;

public class IntConverter {
    @InverseMethod("toInt")
    public static String toString(TextView view, Integer oldValue,
                                  Integer value) {
        if(value == null)return "";
        return Integer.toString(value);
    }

    public static Integer toInt(TextView view, Integer oldValue,
                                  String value) {
        if(value.equals(""))return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return oldValue;
        }
    }
}