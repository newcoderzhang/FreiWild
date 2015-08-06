package com.accord.android.freiwild.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class ServiceGenerator {
    // No need to instantiate this class.
    private ServiceGenerator() { }

    public static <S> S createService(Class<S> serviceClass) {
        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .registerTypeAdapterFactory(new ResponseTypeAdapterFactory())
                .create();
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://www.frei-wild.net/api/")
                .setConverter(new GsonConverter(gson))
                .build();

        return adapter.create(serviceClass);
    }
}