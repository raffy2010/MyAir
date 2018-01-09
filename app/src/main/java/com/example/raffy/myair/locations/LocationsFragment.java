package com.example.raffy.myair.locations;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raffy.myair.R;
import com.example.raffy.myair.databinding.FragmentLocationsBinding;
import com.example.raffy.myair.databinding.FragmentLocationsListBinding;
import com.google.android.flexbox.FlexboxLayoutManager;

public class LocationsFragment extends Fragment {

    private LocationsViewModel mLocationsViewModel;

    private FragmentLocationsListBinding mBinding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationsFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LocationsFragment newInstance(int columnCount) {
        LocationsFragment fragment = new LocationsFragment();

        return fragment;
    }

    public void setViewModel (LocationsViewModel viewModel) {
        mLocationsViewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentLocationsListBinding.inflate(inflater, container, false);

        mBinding.setViewmodel(mLocationsViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLocationAdapter();
    }

    public void setupLocationAdapter() {
        Context context = getContext();
        LocationsActivity activity = (LocationsActivity) context;
        RecyclerView recyclerView = mBinding.locationList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        mLocationsViewModel.loadLocations();

        recyclerView.setAdapter(new MyLocationsRecyclerViewAdapter(
                mLocationsViewModel.locationList,
                activity.getRepository()
        ));
    }
}
