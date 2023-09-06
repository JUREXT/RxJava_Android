package com.example.rxjava3.callbackvsRx;

public interface UserManagerCallback {
    UserTest getUser();

    void setName(String name, UpdateListener listener);

    void setAge(Integer age, UpdateListener listener);

    interface UpdateListener {
        void success();

        void error(String error);
    }
}