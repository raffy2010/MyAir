package com.example.raffy.myair.addlocation;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.View;
import android.widget.ListView;

import com.example.raffy.myair.BR;
import com.example.raffy.myair.R;
import com.example.raffy.myair.data.Location;
import com.example.raffy.myair.data.LocationItem;
import com.example.raffy.myair.data.source.LocationsDataSource;
import com.example.raffy.myair.data.source.LocationsRepository;
import com.example.raffy.myair.locations.LocationsActivity;


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
                replaceLocationItems(locationList);
            }
        });
    }

    public void setLocationItem(LocationItem item) {
        locationItem.set(item);
    }

    public void replaceLocationItems(List<LocationItem> items) {
        locationItems.clear();

        locationItems.addAll(items);
    }

    public void resetSearch() {
        replaceLocationItems(new ArrayList<LocationItem>(0));
        keyword.set("");
    }


    public void addLocation(View view) {
        AddLocationActivity activity = (AddLocationActivity) view.getContext();

        ListView listview = activity.findViewById(R.id.location_search_ret);

        AddLocationFragment.LocationItemAdapter adapter = (AddLocationFragment.LocationItemAdapter) listview.getAdapter();

        adapter.cleanSearchRet();

        mLocationRepository.saveLocation(locationItem.get());
    }


    public void setupKeywordObserver() {
        keyword.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
                if (Objects.equals(keyword.get(), "")) {
                    return;
                }

                searchLocation();
            }
        });
    }
}
