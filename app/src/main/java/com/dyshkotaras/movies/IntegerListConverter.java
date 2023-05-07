package com.dyshkotaras.movies;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntegerListConverter {
    @TypeConverter
    public static List<Integer> fromString(String value) {
        if (value == null) {
            return Collections.emptyList();
        }
        String[] items = value.split(",");
        List<Integer> list = new ArrayList<>(items.length);
        for (String item : items) {
            list.add(Integer.valueOf(item));
        }
        return list;
    }

    @TypeConverter
    public static String fromList(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int item : list) {
            sb.append(item).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
