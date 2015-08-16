package com.accord.android.freiwild.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    private Model mModel;

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

        mModel = workerFragment.getModel(new ReleaseModelCreatorImpl());

        mCompositeSubscription.add(AndroidObservable.bindActivity(this, getReleases())
                .subscribe(handleRelease(), handleError()));

        mObserverAdapter = new ObserverAdapter();
        mList.setAdapter(mObserverAdapter);

        Intent intent = new Intent(this, MainActivity.class);
    }

    public Observable<Release> getReleases() {
        return mModel.getData();
    }

    public Action1<Release> handleRelease() {
        return new Action1<Release>() {
            @Override
            public void call(Release release) {
                mObserverAdapter.addItem(release);
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
