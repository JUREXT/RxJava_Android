package com.example.rxjava3.callbackvsRx;

import io.reactivex.rxjava3.core.Observable;

public interface UserManagerReactive {
    Observable<UserTest> getUser();
    Observable<UserTest> setName(String name);
    Observable<UserTest> setAge(Integer age);
}