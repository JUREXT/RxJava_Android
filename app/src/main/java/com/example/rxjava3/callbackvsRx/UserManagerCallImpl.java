package com.example.rxjava3.callbackvsRx;

public class UserManagerCallImpl implements UserManagerCallback {

    private final UserTest user;

    public UserManagerCallImpl() {
        this.user = new UserTest();
    }

    @Override
    public UserTest getUser() {
        return this.user;
    }

    @Override
    public void setName(String name, UpdateListener listener) {
        if(name.isBlank()) {
            listener.error("Blank name");
        } else {
            user.name = name;
            listener.success();
        }
    }

    @Override
    public void setAge(Integer age, UpdateListener listener) {
        if(age <= 0) {
            listener.error("Age is less then 0");
        } else {
            user.age = age;
            listener.success();
        }
    }
}
