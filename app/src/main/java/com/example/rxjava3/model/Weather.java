package com.example.rxjava3.model;

import java.util.ArrayList;
import java.util.List;

public class Weather {
    public int id;
    public int temp;

    public static final Weather FAKE_OBJ = new Weather(-1,-1);
    public Weather(int id, int temp) {
        this.id = id;
        this.temp = temp;
    }

    public static List<Weather> weatherList() {
        List<Weather> list = new ArrayList<>();
        list.add(null);
        list.add(new Weather(1, 28));
        list.add(new Weather(2, 25));
        list.add(null);
        list.add(new Weather(3, 22));
        list.add(new Weather(4, 23));
        list.add(new Weather(5, 23));
        list.add(new Weather(6, 23));
        list.add(new Weather(7, 25));
        list.add(new Weather(8, 28));
        return list;
    }

    public boolean isValid() {
        return id > -1 && temp > -1;
    }

    // TODO: https://gist.github.com/aldoKelvianto/f06ac408c26e0c6e9ccf0bd7188d7d01
    public static Weather getWeatherByCityId(int cityId) {
        for (Weather weather : Weather.weatherList()) {
            if (weather != null && weather.id == cityId) {
                return weather;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", temp=" + temp +
                '}';
    }
}