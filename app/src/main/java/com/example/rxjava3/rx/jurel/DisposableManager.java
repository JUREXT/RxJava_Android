package com.example.rxjava3.rx.jurel;

import io.reactivex.rxjava3.disposables.Disposable;

public interface DisposableManager {
    void add(Disposable... disposables);
    void forceDispose();
}
