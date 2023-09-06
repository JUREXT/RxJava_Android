package com.example.rxjava3.util;

import androidx.appcompat.widget.SearchView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchViewHelperRx {

    public static @NonNull Observable<Boolean> getSearchViewOnCloseObservable(@NonNull SearchView searchView) {
        return Observable.create(emitter -> searchView.setOnCloseListener(() -> {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(true);
                    }
                    return false;
                }))
                .switchMap(state -> Observable.just((Boolean) state))
                .subscribeOn(Schedulers.io());
    }

    public static @NonNull Observable<SearchViewText> getSearchedTextObservable(@NonNull SearchView searchView) {
        return Observable.create(emitter -> searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(new SearchViewText(s, TextType.TextSubmit));
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(new SearchViewText(s, TextType.TextChange));
                        }
                        return false;
                    }
                }))
                .switchMap(text -> Observable.just((SearchViewText) text))
                .distinctUntilChanged()
                .debounce(800, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io());
    }

    public enum TextType {
        TextSubmit, TextChange
    }

    public static class SearchViewText {
        public String text;
        public TextType textType;

        public SearchViewText(String text, TextType textType) {
            this.text = text;
            this.textType = textType;
        }
    }
}