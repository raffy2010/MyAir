package com.example.raffy.myair.locations;

import android.databinding.ObservableField;
import android.util.Log;

import com.example.raffy.myair.data.Location;
import com.example.raffy.myair.data.source.LocationsRepository;

/**
 * Created by raffy on 21/12/2017.
 */

public class LocationViewModel extends LocationsViewModel {

    public ObservableField<Location> location = new ObservableField<>();

    public LocationViewModel(LocationsRepository repository) {
        super(repository);
    }

    public void viewDetail() {

    }

    public void setLocation(Location newLocation) {
        location.set(newLocation);
    }
}
