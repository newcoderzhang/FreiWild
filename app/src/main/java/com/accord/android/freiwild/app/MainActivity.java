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
import com.accord.android.freiwild.app.io.Data;
import com.accord.android.freiwild.app.io.Release;
import com.accord.android.freiwild.app.model.ConcertModelCreator;
import com.accord.android.freiwild.app.model.Model;
import com.accord.android.freiwild.app.model.ReleaseModelCreator;

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

        WorkerFragment workerFragment = (WorkerFragment) getSupportFragmentManager()
                .findFragmentByTag(WorkerFragment.TAG_WORKER);
        if (workerFragment == null) {
            workerFragment = WorkerFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(workerFragment, WorkerFragment.TAG_WORKER)
                    .commit();
        }

        mReleaseModel = workerFragment.<ReleaseModelCreator, Release>getModel(new ReleaseModelCreator());
        mConcertModel = workerFragment.<ConcertModelCreator, Concert>getModel(new ConcertModelCreator());

        mCompositeSubscription.add(AndroidObservable.bindActivity(this, getReleases())
                .subscribe(handleRelease(), handleError()));
        mCompositeSubscription.add(AndroidObservable.bindActivity(this, getConcerts())
                .subscribe(handleConcert(), handleError()));

        mObserverAdapter = new ObserverAdapter();
        mList.setAdapter(mObserverAdapter);

    }

    public Observable<Release> getReleases() {
        return mReleaseModel.getData();
    }

    public Action1<Release> handleRelease() {
        return new Action1<Release>() {
            @Override
            public void call(Release release) {
//                mObserverAdapter.addItem(release);
            }
        };
    }

    public Action1<Throwable> handleError() {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("MainActivity", throwable.getMessage());
            }
        };
    }

    public Observable<Concert> getConcerts() {
        return mConcertModel.getData();
    }

    public Action1<Concert> handleConcert() {
        return new Action1<Concert>() {
            @Override
            public void call(Concert concert) {
                mObserverAdapter.addItem(concert);
            }
        };
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
