package com.example.rxjava3.rx.jurel.mapping;

import io.reactivex.rxjava3.core.Observable;

public interface BaseMapper<INPUT, OUTPUT> {
    Observable<OUTPUT> map(INPUT input);
}