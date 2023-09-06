package com.example.rxjava3;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.rxjava3.rx.jurel.DisposableManager;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class AutoDisposableObserver implements DisposableManager, LifecycleEventObserver {

    private static final String TAG = AutoDisposableObserver.class.getSimpleName();
    private final CompositeDisposable compositeDisposable;

    public AutoDisposableObserver() {
        Log.d(TAG, "AutoDisposableObserver Created");
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void add(Disposable... disposables) {
        Log.d(TAG, "Adding Disposable to CompositeDisposable");
        for (Disposable dispose : disposables) {
            compositeDisposable.add(dispose);
        }
    }

    @Override
    public void forceDispose() {
        disposeCompositeDisposable();
    }

    private void clearCompositeDisposable() {
        if (!compositeDisposable.isDisposed()) {
            Log.d(TAG, "Clear CompositeDisposable");
            compositeDisposable.clear(); // Just clears and still enables to add new subscribers.
        }
    }

    private void disposeCompositeDisposable() {
        if (!compositeDisposable.isDisposed()) {
            Log.d(TAG, "Dispose CompositeDisposable");
            compositeDisposable.dispose(); // No longer allows anything to subscribe to observable previously being observed.
        }
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_STOP) {
            clearCompositeDisposable();
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            disposeCompositeDisposable();
        }
    }
}