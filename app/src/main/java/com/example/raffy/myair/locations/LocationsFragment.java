package com.example.raffy.myair.locations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raffy.myair.R;
import com.example.raffy.myair.databinding.FragmentLocationsBinding;
import com.example.raffy.myair.databinding.FragmentLocationsListBinding;
import com.google.android.flexbox.FlexboxLayoutManager;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

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
        setupItemTouchHelper();
        setupItemAnimator();
    }

    private void setupItemAnimator() {
        RecyclerView recyclerView = mBinding.locationList;

        recyclerView.getItemAnimator().setMoveDuration(1000);
    }

    private void setupLocationAdapter() {
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

    private void setupItemTouchHelper() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private Drawable mBackground;

            private boolean mInitiated;

            private Paint mPaint;

            private Rect mRect;

            private String mText = "Remove";

            private void init() {
                mBackground = new ColorDrawable(Color.RED);
                mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
                mRect = new Rect();
                mPaint.setTextAlign(Paint.Align.LEFT);
                mPaint.setColor(Color.WHITE);
                int scaledSize = getResources().getDimensionPixelSize(R.dimen.text_size);
                mPaint.setTextSize(scaledSize);
                mInitiated = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                RecyclerView recyclerView = mBinding.locationList;

                MyLocationsRecyclerViewAdapter adapter = (MyLocationsRecyclerViewAdapter) recyclerView.getAdapter();

                adapter.remove(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (!mInitiated) {
                    init();
                }

                View itemView = viewHolder.itemView;

                // draw red background
                mBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                mBackground.draw(c);

                mPaint.getTextBounds(mText, 0, mText.length(), mRect);

                int scaledSize = getResources().getDimensionPixelSize(R.dimen.text_margin);
                int x = itemView.getRight() - mRect.width() - scaledSize;
                float y = itemView.getTop() + (itemView.getBottom() - itemView.getTop()) / 2f + mRect.height() / 2f - mRect.bottom;

                c.drawText(mText, x, y, mPaint);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mBinding.locationList);
    }
}
