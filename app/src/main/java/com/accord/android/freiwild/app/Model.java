package com.accord.android.freiwild.app;


import rx.Observable;

public interface Model<T extends Data> {
    Observable<T> getData();
}
