package com.accord.android.freiwild.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.OnClick;
import retrofit.RestAdapter;


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

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.connection_point))
                .build();

        requestsApi = restAdapter.create(RequestsApi.class);
    }

    private void performRequest() {
        requestsApi.getReleases();
    }
}
