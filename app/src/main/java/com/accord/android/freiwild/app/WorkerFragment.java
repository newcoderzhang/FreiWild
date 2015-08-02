package com.accord.android.freiwild.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class WorkerFragment extends Fragment {

    public static final String TAG_WORKER = WorkerFragment.class.getSimpleName();

    private RequestsApi requestsApi;

    public static WorkerFragment newInstance() {
        return new WorkerFragment();
    }

    public WorkerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new ResponseTypeAdapterFactory());

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.connection_point))
                .setConverter(new GsonConverter(gsonBuilder.create()))
                .build();

        requestsApi = restAdapter.create(RequestsApi.class);
    }

    public Observable<Release> getReleases() {
        return requestsApi.getReleases()
                .flatMap(new Func1<List<Release>, Observable<Release>>() {
                    @Override
                    public Observable<Release> call(List<Release> releases) {
                        return Observable.from(releases);
                    }
                })
                .subscribeOn(Schedulers.newThread());
    }
}
