package com.dyshkotaras.movies;

import android.text.TextUtils;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class StringListConverter {
    @TypeConverter
    public static List<String> fromString(String value) {
        return value == null ? null : Arrays.asList(value.split(","));
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        return list == null ? null : TextUtils.join(",", list);
    }
}
