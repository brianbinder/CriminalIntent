package com.brianfakeurltwitter.criminalintent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createSingleFragment(R.id.fragment_container);
    }
    protected void onCreate(Bundle savedInstanceState, int fragmentId) {
        super.onCreate(savedInstanceState);
        createSingleFragment(fragmentId);
    }

    private void createSingleFragment(int fragmentContainerId) {
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(fragmentContainerId);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(fragmentContainerId, fragment)
                    .commit();
        }
    }
}
