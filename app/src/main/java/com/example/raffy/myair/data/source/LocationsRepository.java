package com.example.raffy.myair.data.source;

import android.util.Log;

import com.example.raffy.myair.data.Location;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

import com.example.raffy.myair.data.LocationFeed;
import com.example.raffy.myair.data.LocationItem;
import com.google.gson.Gson;

/**
 * Created by raffy on 20/12/2017.
 */

import java.io.IOException;
import java.util.UUID;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.ResponseBody;

public class LocationsRepository implements LocationsDataSource {

    public static String API_TOKEN = "228ae8f34d6881c088dc80b6a08acb8d0ea56538";

    OkHttpClient client = new OkHttpClient();

    private Realm mRealm;

    private static LocationsRepository INSTANCE = null;

    public LocationsRepository(Realm realm) {
        mRealm = realm;
    }

    public static LocationsRepository getInstance(Realm realm) {
        if (INSTANCE == null) {
            INSTANCE = new LocationsRepository(realm);
        }

        return INSTANCE;
    }

    @Override
    public void getLocations(LoadLocationsCallback callback) {
        List<Location> locationList =  mRealm.where(Location.class).findAll();

        callback.onLocationsLoaded(locationList);
    }


    public void getLocationFeed(Location location, LoadFeedCallback callback) {
        String url = String.format("http://api.waqi.info/feed/@%d/?token=%s", location.getLocationId(), API_TOKEN);

        Log.i("Location Feed Url", url);

        doFetchFeed(url, callback);
    }

    @Override
    public void saveLocation(LocationItem locationItem) {
        mRealm.beginTransaction();
        Location location = mRealm.createObject(Location.class, UUID.randomUUID().toString()); // Create a new object
        location.setName(locationItem.getStation().getName());
        location.setLocationId(locationItem.getUid());
        location.setAqi(0);

        mRealm.commitTransaction();
    }

    @Override
    public void deleteLocation(Location location, DeleteLocationCallback callback) {
        mRealm.beginTransaction();

        location.deleteFromRealm();

        mRealm.commitTransaction();

        callback.onDeleteLocation();
    }

    @Override
    public void searchLocation(String keyword, SearchLocationsCallback callback) {
        String url = String.format("http://api.waqi.info/search/?token=%s&keyword=%s", API_TOKEN, keyword);

        doSearch(url, callback);
    }

    private void doFetchFeed(String url, final LoadFeedCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    String json = responseBody.string();

                    Gson gson = new Gson();

                    FeedRet ret = gson.fromJson(json, FeedRet.class);

                    LocationFeed feed = ret.getData();

                    callback.onLoadFeed(feed);
                }
            }
        });
    }

    private void doSearch(String url, final SearchLocationsCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    String json = responseBody.string();

                    Gson gson = new Gson();

                    SearchRet ret = gson.fromJson(json, SearchRet.class);

                    List<LocationItem> data = ret.getData();

                    callback.onLoadLocations(data);
                }
            }
        });
    }

    private class FeedRet {
        private String status;

        private LocationFeed data;

        public String getStatus() {
            return status;
        }

        public LocationFeed getData() {
            return data;
        }
    }

    private class SearchRet {
        private String status;

        private List<LocationItem> data;

        public SearchRet() {

        }

        public String getStatus() {
            return status;
        }

        public List<LocationItem> getData() {
            return data;
        }
    }
}
