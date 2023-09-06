package com.example.rxjava3.callbackvsRx;

import io.reactivex.rxjava3.core.Observable;

public class UserManagerRx implements UserManagerReactive {

    private final UserTest user;

    public UserManagerRx() {
        this.user = new UserTest();
    }

    @Override
    public Observable<UserTest> getUser() {
        return Observable.just(user);
    }

    @Override
    public Observable<UserTest> setName(String name) {
        user.name = name;
        return Observable.just(user);
    }

    @Override
    public Observable<UserTest> setAge(Integer age) {
        user.age = age;
        return Observable.just(user);
    }
}
