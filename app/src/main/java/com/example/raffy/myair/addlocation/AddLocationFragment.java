package com.example.raffy.myair.addlocation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.raffy.myair.R;
import com.example.raffy.myair.data.LocationItem;
import com.example.raffy.myair.data.source.LocationsRepository;
import com.example.raffy.myair.databinding.FragmentAddLocationBinding;
import com.example.raffy.myair.databinding.LocationRetItemBinding;

import java.util.ArrayList;
import java.util.List;

public class AddLocationFragment extends Fragment {

    private AddLocationViewModel mViewModel;

    private FragmentAddLocationBinding mBinding;

    public AddLocationFragment() {
        // Required empty public constructor
    }

    public static AddLocationFragment newInstance() {
        AddLocationFragment fragment = new AddLocationFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAddLocationBinding.inflate(inflater, container, false);

        mBinding.setViewmodel(mViewModel);

        mViewModel.setupKeywordObserver();

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupResultAdapter();
    }

    public void setViewModel(AddLocationViewModel viewModel) {
        mViewModel = viewModel;
    }

    private void setupResultAdapter() {
        AddLocationActivity activity = (AddLocationActivity) getActivity();

        LocationItemAdapter adapter = new LocationItemAdapter(
                new ArrayList<LocationItem>(0),
                activity.getRepository(),
                mViewModel
        );

        ListView searchResult = mBinding.locationSearchRet;

        searchResult.setAdapter(adapter);
    }

    public void cleanSearchRet() {

    }

    public static class LocationItemAdapter extends BaseAdapter {

        private List<LocationItem> mLocationItems;

        private LocationsRepository mRepository;

        private AddLocationViewModel mViewModel;


        public LocationItemAdapter(List<LocationItem> locationItemList, LocationsRepository repository, AddLocationViewModel viewModel) {
            replaceData(locationItemList);
            mRepository = repository;
            mViewModel = viewModel;
        }

        @Override
        public int getCount() {
            if (mLocationItems == null) {
                return 0;
            }

            return mLocationItems.size();
        }

        @Override
        public Object getItem(int i) {
            return mLocationItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LocationItem locationItem = mLocationItems.get(i);

            LocationRetItemViewModel viewModel = new LocationRetItemViewModel(mRepository);

            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

            // Create the binding
            LocationRetItemBinding binding = LocationRetItemBinding.inflate(inflater, viewGroup, false);

            binding.setViewmodel(viewModel);

            viewModel.setLocationItem(locationItem);

            return binding.getRoot();
        }

        public void replaceData(List<LocationItem> locationItemList) {
            mLocationItems = locationItemList;

            notifyDataSetChanged();
        }

        public void cleanSearchRet() {
            mViewModel.resetSearch();
        }
    }
}
