package com.accord.android.freiwild.app;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class ServiceGenerator {

    private static GsonBuilder builder = new GsonBuilder();

    // No need to instantiate this class.
    private ServiceGenerator() { }

    public static <S> S createService(Class<S> serviceClass) {
        builder.registerTypeAdapterFactory(new ResponseTypeAdapterFactory())
               .create();
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://www.frei-wild.net/api/")
                .setConverter(new GsonConverter(builder.create()))
                .build();

        return adapter.create(serviceClass);
    }

    public static void setDateFormat(@NonNull final String format) {
        builder.setDateFormat(format);
    }
}