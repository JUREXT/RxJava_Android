package com.example.rxjava3.experimental;

import com.example.rxjava3.rx.jurel.mapping.BaseMapper;

import io.reactivex.rxjava3.core.Observable;

public class TestMapper implements BaseMapper<Integer, String> {
    @Override
    public Observable<String> map(Integer integer) {
        if (integer == null) {
            return Observable.error(new IllegalArgumentException("Cannot transform a null value"));
        }
        if (integer > 5) {
            return Observable.error(new IllegalArgumentException("Can transform only max 5 input value"));
        }

        return Observable.just(integer)
                .map(value -> {
                    StringBuilder result = new StringBuilder();
                    result.append("-> ");
                    for (int i = 0; i < value; i++) {
                        result.append(value).append(" ");
                    }
                    return result.append("#").toString();
                });
    }
}