package com.accord.android.freiwild.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.accord.android.freiwild.app.ui.ReleasesAllFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment fragment = getSupportFragmentManager()
                    .findFragmentByTag(ReleasesAllFragment.TAG);
            if (fragment == null) {
                fragment = ReleasesAllFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_view, fragment, ReleasesAllFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
            }
        }
    }
}
