package com.example.rxjava3.rx.jurel;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    public static List<Task> createTaskList() {
        var list = new ArrayList<Task>();
        list.add(new Task("Take out trash", true, 3));
        list.add(new Task("Walk the dog", false, 2));
        list.add(new Task("Make my bed", true, 1));
        list.add(new Task("Unload the dishwasher", false, 0));
        list.add(new Task("Make dinner", true, 2));
        return list;
    }
}