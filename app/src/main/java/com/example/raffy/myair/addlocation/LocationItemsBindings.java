package com.example.raffy.myair.addlocation;

import android.databinding.BindingAdapter;
import android.widget.ListView;

import com.example.raffy.myair.data.LocationItem;

import java.util.List;

/**
 * Created by raffy on 09/01/2018.
 */

public class LocationItemsBindings {
    @BindingAdapter("app:items")
    public static void setItems(ListView listview, List<LocationItem> items) {
        AddLocationFragment.LocationItemAdapter adapter = (AddLocationFragment.LocationItemAdapter) listview.getAdapter();

        if (adapter != null) {
            adapter.replaceData(items);
        }
    }
}
