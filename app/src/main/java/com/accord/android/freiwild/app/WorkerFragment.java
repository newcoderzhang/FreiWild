package com.accord.android.freiwild.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;


public class WorkerFragment extends Fragment {

    public static final String TAG_WORKER = WorkerFragment.class.getSimpleName();

    public static WorkerFragment newInstance() {
        return new WorkerFragment();
    }

    public WorkerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
