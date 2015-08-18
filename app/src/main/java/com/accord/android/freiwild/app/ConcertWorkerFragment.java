package com.accord.android.freiwild.app;

import com.accord.android.freiwild.app.io.Concert;
import com.accord.android.freiwild.app.model.ConcertModel;
import com.accord.android.freiwild.app.model.ConcertModelCreator;
import com.accord.android.freiwild.app.model.Model;


public class ConcertWorkerFragment extends BaseWorkerFragment<Concert> {

    public static final String TAG_WORKER = ConcertWorkerFragment.class.getSimpleName();

    private static ConcertModel mConcertModel;

    public static ConcertWorkerFragment newInstance() {
        return new ConcertWorkerFragment();
    }

    @Override
    public Model<Concert> getModel() {
        if (mConcertModel == null) {
            mConcertModel = (new ConcertModelCreator()).createModel();
        }
        return mConcertModel;
    }
}
