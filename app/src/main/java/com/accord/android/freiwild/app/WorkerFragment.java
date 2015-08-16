package com.accord.android.freiwild.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.accord.android.freiwild.app.io.Data;
import com.accord.android.freiwild.app.model.Model;
import com.accord.android.freiwild.app.model.ModelCreator;


public class WorkerFragment extends Fragment {

    private static RequestsApi mRequestApi;

    public static final String TAG_WORKER = WorkerFragment.class.getSimpleName();

    public static WorkerFragment newInstance() {
        return new WorkerFragment();
    }

    public WorkerFragment() {
        if (mRequestApi == null) {
            mRequestApi = ServiceGenerator.createService(RequestsApi.class);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public <T extends ModelCreator<V>, V extends Data> Model<V> getModel(T creator) {
        return creator.createModel(mRequestApi);
    }
}
