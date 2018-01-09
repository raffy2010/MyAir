package com.example.raffy.myair.locations;

import com.example.raffy.myair.data.Location;
import com.example.raffy.myair.data.source.LocationsDataSource;
import com.example.raffy.myair.data.source.LocationsRepository;

import java.util.List;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.Observable;

/**
 * Created by raffy on 20/12/2017.
 */

public class LocationsViewModel extends BaseObservable {
    private LocationsRepository mLocationRepository;

    public ObservableList<Location> locationList = new ObservableArrayList<>();

    public LocationsViewModel(LocationsRepository locationsRepository) {
        mLocationRepository = locationsRepository;
    }

    public void loadLocations() {
        mLocationRepository.getLocations(new LocationsDataSource.LoadLocationsCallback() {
            @Override
            public void onLocationsLoaded(List<Location> locations) {
                locationList.clear();
                locationList.addAll(locations);
            }
        });
    }
}
