package com.example.raffy.myair.locations;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.raffy.myair.R;
import com.example.raffy.myair.addlocation.AddLocationActivity;
import com.example.raffy.myair.data.source.LocationsRepository;
import com.example.raffy.myair.util.ActivityUtils;

import io.realm.Realm;

public class LocationsActivity extends AppCompatActivity implements LocationNavigator {
    private LocationsViewModel mLocationsViewModel;

    public Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Locations");

        mRealm = Realm.getDefaultInstance();
        findOrCreateFragment();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(LocationsActivity.this, AddLocationActivity.class);
                startActivity(newIntent);
            }
        });
    }

    private LocationsFragment findOrCreateFragment() {
        LocationsFragment locationsFragment = (LocationsFragment) getSupportFragmentManager().findFragmentById(R.id.locations_container);

        if (locationsFragment == null) {
            locationsFragment = LocationsFragment.newInstance(0);

            mLocationsViewModel = new LocationsViewModel(getRepository());
            locationsFragment.setViewModel(mLocationsViewModel);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    locationsFragment, R.id.locations_container);
        }

        return locationsFragment;
    }

    public LocationsRepository getRepository() {
        Realm realm = Realm.getDefaultInstance();

        LocationsRepository locationsRepository = LocationsRepository.getInstance(realm);

        return locationsRepository;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }

    @Override
    public void openLocationDetail() {
        // TODO
    }
}
