package com.accord.android.freiwild.app.model;


import com.accord.android.freiwild.app.io.Data;

import rx.Observable;

public interface Model<T extends Data> {
    Observable<T> getData();
}
