package com.accord.android.freiwild.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private RequestsApi requestsApi;

    @OnClick(R.id.request_button)
    public void doRequest() {
        performRequest();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new ResponseTypeAdapterFactory());

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.connection_point))
                .setConverter(new GsonConverter(gsonBuilder.create()))
                .build();

        requestsApi = restAdapter.create(RequestsApi.class);
    }

    private void performRequest() {
        getReleases(requestsApi)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Release>() {
                    @Override
                    public void call(Release release) {
                        Log.d(MainActivity.class.getSimpleName(), release.title);
                    }
                });
    }

    private Observable<Release> getReleases(RequestsApi requestsApi) {
        return requestsApi.getReleases()
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .onErrorResumeNext(handleError())
                .flatMap(convertListToItems());
    }

//    private final Subscription subscription =
//            requestsApi.getReleases()
//                    .timeout(5, TimeUnit.SECONDS)
//                    .retry(2)
//                    .onErrorResumeNext(handleError())
//                    .flatMap(convertListToItems() )
//                    .subscribe();

    public Func1<Throwable, Observable<? extends List<Release>>> handleError() {
        return new Func1<Throwable, Observable<? extends List<Release>>>() {
            @Override
            public Observable<? extends List<Release>> call(Throwable throwable) {
                Log.e(MainActivity.class.getSimpleName(), throwable.getMessage());
                return Observable.<List<Release>>empty();
            }
        };
    }

    public Func1<List<Release>, Observable<Release>> convertListToItems() {
        return new Func1<List<Release>, Observable<Release>>() {
            @Override
            public Observable<Release> call(List<Release> releases) {
                return Observable.from(releases);
            }
        };
    }
}
