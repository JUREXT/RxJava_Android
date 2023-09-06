package com.example.rxjava3.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtil {
    public static  <T> List<T> removeNullValues(List<T> list) {
        final List<T> temp = new ArrayList<>(list);
        temp.removeAll(Collections.singleton(null));
        return temp;
    }
}
