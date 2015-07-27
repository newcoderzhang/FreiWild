package com.accord.android.freiwild.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
        requestsApi.getReleases()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Release>>() {
                    @Override
                    public void call(List<Release> releases) {
                        releases.size();
                    }
                });
    }
}
