package com.example.raffy.myair.locations;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.example.raffy.myair.data.Location;

import java.util.List;

/**
 * Created by raffy on 09/01/2018.
 */

public class LocationsBindings {
    @BindingAdapter("app:locations")
    public static void setLocations(RecyclerView view, List<Location> locations) {
        MyLocationsRecyclerViewAdapter adapter = (MyLocationsRecyclerViewAdapter) view.getAdapter();

        if (adapter != null) {
            adapter.replaceData(locations);
        }
    }
}
