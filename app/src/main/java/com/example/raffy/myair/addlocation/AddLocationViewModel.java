package com.example.raffy.myair.addlocation;

import android.util.Log;
import java.util.List;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.View;

import com.example.raffy.myair.BR;
import com.example.raffy.myair.data.Location;
import com.example.raffy.myair.data.LocationItem;
import com.example.raffy.myair.data.source.LocationsDataSource;
import com.example.raffy.myair.data.source.LocationsRepository;


/**
 * Created by raffy on 21/12/2017.
 */

public class AddLocationViewModel extends BaseObservable {
    public ObservableField<String> keyword = new ObservableField<>();

    public ObservableList<LocationItem> locationItems = new ObservableArrayList<>();

    public ObservableField<LocationItem> locationItem = new ObservableField<>();

    private LocationsRepository mLocationRepository;

    public AddLocationViewModel(LocationsRepository repository) {
        mLocationRepository = repository;
    }

    public void searchLocation() {
        Log.i("add Location ViewModel", keyword.get());

        mLocationRepository.searchLocation(keyword.get(), new LocationsDataSource.SearchLocationsCallback() {
            @Override
            public void onLoadLocations(List<LocationItem> locationList) {
                locationItems.clear();

                locationItems.addAll(locationList);
            }
        });
    }

    public void setLocationItem(LocationItem item) {
        locationItem.set(item);
    }


    public void addLocation(View view) {
        mLocationRepository.saveLocation(locationItem.get());
    }

    public void setupKeywordObserver() {
        keyword.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
                searchLocation();
            }
        });
    }
}
