package com.example.raffy.myair.data;

/**
 * Created by raffy on 21/12/2017.
 */

public class LocationItem {
    private int uid;

    private String aqi;

    private RetTime time;

    private Station station;

    public LocationItem() {

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public RetTime getTime() {
        return time;
    }

    public void setTime(RetTime time) {
        this.time = time;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public class Station {
        private String name;

        private String url;

        private Float[] geo;

        public Station() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Float[] getGeo() {
            return geo;
        }

        public void setGeo(Float[] geo) {
            this.geo = geo;
        }
    }

    public class RetTime {
        private String tz;

        private String stime;

        private int vtime;

        public RetTime() {

        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public int getVtime() {
            return vtime;
        }

        public void setVtime(int vtime) {
            this.vtime = vtime;
        }
    }
}


