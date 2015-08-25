package com.accord.android.freiwild.app.ui;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accord.android.freiwild.app.R;
import com.accord.android.freiwild.app.ReleaseWorkerFragment;
import com.accord.android.freiwild.app.io.Release;
import com.accord.android.freiwild.app.model.Model;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.observables.AndroidObservable;
import rx.functions.Action1;

public class ReleasesAllFragment extends Fragment {

    public static final String TAG = ReleasesAllFragment.class.getSimpleName();

    @Bind(R.id.releases_list)
    RecyclerView mRecyclerView;

    private ReleasesAdapter mReleasesAdapter;
    private Model<Release> mReleaseModel;

    public static ReleasesAllFragment newInstance() {
        return new ReleasesAllFragment();
    }

    public ReleasesAllFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReleaseWorkerFragment releaseWorkerFragment = (ReleaseWorkerFragment) getFragmentManager()
                .findFragmentByTag(ReleaseWorkerFragment.TAG_WORKER);
        if (releaseWorkerFragment == null) {
            releaseWorkerFragment = ReleaseWorkerFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .add(releaseWorkerFragment, ReleaseWorkerFragment.TAG_WORKER)
                    .commit();
        }
        mReleaseModel = releaseWorkerFragment.getModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_releases_all, container, false);
        ButterKnife.bind(this, view);

        mReleasesAdapter = new ReleasesAdapter(getActivity(), new ArrayList<>());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources()
                .getDimensionPixelSize(R.dimen.recycler_grid_item_space)));
        mRecyclerView.setAdapter(mReleasesAdapter);

        AndroidObservable.bindFragment(this, getReleases())
                .subscribe(handleRelease(), handleError());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public Observable<Release> getReleases() {
        return mReleaseModel.getData();
    }

    public Action1<Release> handleRelease() {
        return mReleasesAdapter::addItem;
    }

    public Action1<Throwable> handleError() {
        return throwable -> Log.e(TAG, throwable.getMessage());
    }

    public static class ReleaseHolder extends  RecyclerView.ViewHolder {

        @Bind(R.id.icon)
        ImageView iconView;
        @Bind(R.id.title)
        TextView titleView;
        @Bind(R.id.date) TextView dateView;

        public ReleaseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ReleasesAdapter extends RecyclerView.Adapter<ReleaseHolder> {

        private List<Release> mReleases;
        private final Context mContext;
        private final SimpleDateFormat mDateFormat;
        private final LayoutInflater mInflater;

        public ReleasesAdapter(Context context, @NonNull List<Release> releases) {
            mContext = context;
            mReleases = releases;
            mDateFormat = new SimpleDateFormat(getString(R.string.date_format),
                    context.getResources().getConfiguration().locale);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ReleaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.item_release_card, parent, false);
            return new ReleaseHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ReleaseHolder holder, int position) {
            final Release item = mReleases.get(position);
            holder.titleView.setText(item.title);
            holder.dateView.setText(mDateFormat.format(item.releaseDate));
            Picasso.with(mContext)
                    .load("http://www.frei-wild.net/" + item.imageUrl)
                    .into(holder.iconView);
        }

        @Override
        public int getItemCount() {
            return mReleases.size();
        }

        public void addItem(final Release release) {
            mReleases.add(release);
            notifyDataSetChanged();
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private final int mSpacePx;

        public SpacesItemDecoration(int spaceDp) {
            mSpacePx = spaceDp;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = mSpacePx;
            outRect.right = mSpacePx;
            outRect.top = mSpacePx;
            outRect.bottom = mSpacePx;
        }
    }

}
