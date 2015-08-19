package com.accord.android.freiwild.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.accord.android.freiwild.app.io.Concert;
import com.accord.android.freiwild.app.io.Release;
import com.accord.android.freiwild.app.model.ConcertModelCreator;
import com.accord.android.freiwild.app.model.Model;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.observables.AndroidObservable;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.releases_listView)
    ListView mList;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private ObserverAdapter mObserverAdapter;

    private Model<Release> mReleaseModel;
    private Model<Concert> mConcertModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ReleaseWorkerFragment releaseWorkerFragment = (ReleaseWorkerFragment) getSupportFragmentManager()
                .findFragmentByTag(ReleaseWorkerFragment.TAG_WORKER);
        if (releaseWorkerFragment == null) {
            releaseWorkerFragment = ReleaseWorkerFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(releaseWorkerFragment, ReleaseWorkerFragment.TAG_WORKER)
                    .commit();
        }

        ConcertWorkerFragment concertWorkerFragment = (ConcertWorkerFragment) getSupportFragmentManager()
                .findFragmentByTag(ConcertWorkerFragment.TAG_WORKER);
        if (concertWorkerFragment == null) {
            concertWorkerFragment = ConcertWorkerFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(concertWorkerFragment, ConcertWorkerFragment.TAG_WORKER)
                    .commit();
        }

        mObserverAdapter = new ObserverAdapter();
        mList.setAdapter(mObserverAdapter);

        mReleaseModel = releaseWorkerFragment.getModel();
        mConcertModel = concertWorkerFragment.getModel();


//        mCompositeSubscription.add(AndroidObservable.bindActivity(this, getReleases())
//                .subscribe(handleRelease(), handleError()));
        mCompositeSubscription.add(AndroidObservable.bindActivity(this, getConcerts())
                .subscribe(handleConcert(), handleError()));
    }

    public Observable<Release> getReleases() {
        return mReleaseModel.getData();
    }

//    public Action1<Release> handleRelease() {
//        return mObserverAdapter::addItem;
//    }

    public Action1<Throwable> handleError() {
        return throwable -> Log.e("MainActivity", throwable.getMessage());
    }

    public Observable<Concert> getConcerts() {
        return mConcertModel.getData();
    }

    public Action1<Concert> handleConcert() {
        return mObserverAdapter::addItem;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }

    public static class ObserverAdapter extends BaseAdapter {


        private List<Concert> dataList = new ArrayList<>();

        public ObserverAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
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
            textView.setText(dataList.get(position).title);
            return convertView;
        }

        public void addItem(Concert data) {
            dataList.add(data);
            notifyDataSetChanged();
        }
    }
}
