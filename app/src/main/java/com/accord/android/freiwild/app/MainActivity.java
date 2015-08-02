package com.accord.android.freiwild.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.Subscription;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.releases_listView)
    ListView mList;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private ObserverAdapter mObserverAdapter;

    private RequestsApi requestsApi;

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

//        WorkerFragment workerFragment = (WorkerFragment) getSupportFragmentManager()
//                .findFragmentByTag(WorkerFragment.TAG_WORKER);
//        if (workerFragment == null) {
//            workerFragment = WorkerFragment.newInstance();
//        }
//
//        mCompositeSubscription.add(AndroidObservable.bindActivity(this, workerFragment.getReleases())
//                .subscribe(handleRelease()));

        mCompositeSubscription.add(AndroidObservable.bindActivity(this, getReleases())
                .subscribe(handleRelease()));

        mObserverAdapter = new ObserverAdapter();
        mList.setAdapter(mObserverAdapter);
    }

    public Observable<Release> getReleases() {
        return requestsApi.getReleases()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Release>>>() {
                    @Override
                    public Observable<? extends List<Release>> call(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage());
                        return Observable.empty();
                    }
                })
                .flatMap(new Func1<List<Release>, Observable<Release>>() {
                    @Override
                    public Observable<Release> call(List<Release> releases) {
                        return Observable.from(releases);
                    }
                })
                .subscribeOn(Schedulers.newThread());
    }


    public Action1<Release> handleRelease() {
        return new Action1<Release>() {
            @Override
            public void call(Release release) {
                mObserverAdapter.addItem(release);
            }
        };
    }
    public void performRequest() {

    }

    private Observable<Release> getReleases(RequestsApi requestsApi) {
        return requestsApi.getReleases()
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .onErrorResumeNext(handleError())
                .flatMap(convertListToItems());
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }

    public static class ObserverAdapter extends BaseAdapter {


        private List<Release> releases = new ArrayList<>();

        public ObserverAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return releases.size();
        }

        @Override
        public Object getItem(int position) {
            return releases.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
            textView.setText(releases.get(position).title);
            return convertView;
        }

        public void addItem(Release release) {
            releases.add(release);
            notifyDataSetChanged();
        }
    }
}
