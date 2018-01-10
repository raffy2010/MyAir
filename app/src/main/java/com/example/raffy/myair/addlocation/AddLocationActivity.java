package com.example.raffy.myair.addlocation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.raffy.myair.R;
import com.example.raffy.myair.data.source.LocationsRepository;
import com.example.raffy.myair.util.ActivityUtils;

import io.realm.Realm;

public class AddLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Add Location");

        findOrCreateFragment();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void findOrCreateFragment() {
        AddLocationFragment addLocationFragment = AddLocationFragment.newInstance();

        AddLocationViewModel viewModel = new AddLocationViewModel(getRepository());

        addLocationFragment.setViewModel(viewModel);

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                addLocationFragment, R.id.add_location_container);
    }

    public LocationsRepository getRepository() {
        Realm realm = Realm.getDefaultInstance();

        LocationsRepository locationsRepository = LocationsRepository.getInstance(realm);

        return locationsRepository;
    }
}
