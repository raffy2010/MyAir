package com.example.raffy.myair.addlocation;

import android.widget.ListView;

import com.example.raffy.myair.data.LocationItem;

import java.util.List;

/**
 * Created by raffy on 22/12/2017.
 */

public class LocationItemListBinding {
    public static void setItems(ListView listView, List<LocationItem> locationItemList) {
        AddLocationFragment.LocationItemAdapter adapter = (AddLocationFragment.LocationItemAdapter) listView.getAdapter();

        if (adapter != null) {
            adapter.replaceData(locationItemList);
        }
    }
}
