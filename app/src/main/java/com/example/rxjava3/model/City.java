package com.example.rxjava3.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class City {
    public int id;
    public String name;

    public static final City FAKE_OBJ = new City(-1,"");
    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<City> cityList() {
        List<City> list = new ArrayList<>();
        list.add(new City(1, "Celje"));
        list.add(new City(2, "Maribor"));
        list.add(null);
        list.add(new City(3, "Ljubljana"));
        list.add(new City(4, "Ljubljana"));
        list.add(null);
        return list;
    }

    public boolean isValid() {
        return id > -1 && !name.isEmpty();
    }

    @NonNull
    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}