package com.example.itthipon.test3;

/**
 * Created by Itthipon on 7/17/2017.
 */

public class UpdateLocationParams {

    private int id;
    private float lat;
    private float lon;
    private  long time;
    private float hdop;
    private float alt;

    private float speed;

    public UpdateLocationParams(int id, float lat, float lon, long time, float hdop, float alt, float speed) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.hdop = hdop;
        this.alt = alt;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getHdop() {
        return hdop;
    }

    public void setHdop(float hdop) {
        this.hdop = hdop;
    }

    public float getAlt() {
        return alt;
    }

    public void setAlt(float alt) {
        this.alt = alt;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }



}
