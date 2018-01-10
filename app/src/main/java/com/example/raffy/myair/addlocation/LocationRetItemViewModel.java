package com.example.raffy.myair.addlocation;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.raffy.myair.R;
import com.example.raffy.myair.data.LocationItem;
import com.example.raffy.myair.data.source.LocationsRepository;

/**
 * Created by raffy on 08/01/2018.
 */

public class LocationRetItemViewModel extends AddLocationViewModel {
    public LocationRetItemViewModel(LocationsRepository repository) {
        super(repository);
    }
}
