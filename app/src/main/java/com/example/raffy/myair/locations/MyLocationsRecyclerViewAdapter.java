package com.example.raffy.myair.locations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raffy.myair.data.Location;
import com.example.raffy.myair.R;
import com.example.raffy.myair.data.LocationFeed;
import com.example.raffy.myair.data.source.LocationsDataSource;
import com.example.raffy.myair.data.source.LocationsRepository;
import com.example.raffy.myair.databinding.FragmentLocationsBinding;

import java.util.List;

public class MyLocationsRecyclerViewAdapter extends RecyclerView.Adapter<MyLocationsRecyclerViewAdapter.ViewHolder> {

    private List<Location> mValues;

    private LocationsRepository mRepository;

    public MyLocationsRecyclerViewAdapter(List<Location> locations, LocationsRepository repository) {
        mValues = locations;
        mRepository = repository;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        FragmentLocationsBinding binding = FragmentLocationsBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Location location = mValues.get(position);
        LocationViewModel viewModel = new LocationViewModel(mRepository);

        LocationsActivity activity = (LocationsActivity) holder.mBinding.getRoot().getContext();

        fetchLocationFeed(activity, location, viewModel);

        viewModel.setLocation(location);
        holder.bind(viewModel);
    }

    public void fetchLocationFeed(final LocationsActivity activity, final Location location, final LocationViewModel viewModel) {
        mRepository.getLocationFeed(location, new LocationsDataSource.LoadFeedCallback() {
            @Override
            public void onLoadFeed(final LocationFeed feed) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.mRealm.beginTransaction();
                        location.setAqi(feed.getAqi());
                        activity.mRealm.commitTransaction();

                        viewModel.location.notifyChange();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void replaceData(List<Location> locations) {
        mValues = locations;

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentLocationsBinding mBinding;

        public ViewHolder(View view, FragmentLocationsBinding binding) {
            super(view);

            mBinding = binding;
        }

        public void bind(LocationViewModel viewModel) {
            mBinding.setViewmodel(viewModel);
            mBinding.executePendingBindings();
        }
    }
}
