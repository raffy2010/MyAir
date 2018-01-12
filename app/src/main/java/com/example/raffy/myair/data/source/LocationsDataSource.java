package com.example.raffy.myair.data.source;

/**
 * Created by raffy on 20/12/2017.
 */

import java.util.List;

import com.example.raffy.myair.data.Location;
import com.example.raffy.myair.data.LocationFeed;
import com.example.raffy.myair.data.LocationItem;

public interface LocationsDataSource {
    interface LoadLocationsCallback {
        void onLocationsLoaded(List<Location> locationList);
    }

    interface LoadFeedCallback {
        void onLoadFeed(LocationFeed feed);
    }

    interface SearchLocationsCallback {
        void onLoadLocations(List<LocationItem> locationList);
    }

    interface DeleteLocationCallback {
        void onDeleteLocation();
    }

    void getLocations(LoadLocationsCallback callback);

    void getLocationFeed(Location location, LoadFeedCallback callback);

    void saveLocation(LocationItem locationItem);

    void deleteLocation(Location location, DeleteLocationCallback callback);

    void searchLocation(String keyword, SearchLocationsCallback callback);
}
