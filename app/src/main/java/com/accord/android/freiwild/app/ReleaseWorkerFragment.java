package com.accord.android.freiwild.app;


import com.accord.android.freiwild.app.io.Release;
import com.accord.android.freiwild.app.model.Model;
import com.accord.android.freiwild.app.model.ReleaseModel;


public class ReleaseWorkerFragment extends BaseWorkerFragment<Release> {

    public static final String TAG_WORKER = ReleaseWorkerFragment.class.getSimpleName();

    private static ReleaseModel mReleaseModel;

    public static ReleaseWorkerFragment newInstance() {
        return new ReleaseWorkerFragment();
    }

    public Model<Release> getModel() {
        if (mReleaseModel == null) {
            mReleaseModel = new ReleaseModel(mRequestApi);
        }
        return mReleaseModel;
    }
}
