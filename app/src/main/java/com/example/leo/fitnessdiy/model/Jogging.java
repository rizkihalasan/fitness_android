package com.example.leo.fitnessdiy.model;

/**
 * Created by Leo on 19/02/2018.
 */

public class Jogging {
    private String date;
    private String start;
    private String end;
    private float distance;
    private String startingPoint;
    private String endPoint;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Jogging(String date, String start, String end, float distance, String startingPoint, String endPoint) {

        this.date = date;
        this.start = start;
        this.end = end;
        this.distance = distance;
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
