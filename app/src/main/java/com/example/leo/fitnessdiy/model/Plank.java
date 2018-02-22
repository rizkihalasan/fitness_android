package com.example.leo.fitnessdiy.model;

/**
 * Created by Leo on 22/02/2018.
 */

public class Plank {
    private String date;
    private String start;
    private String end;
    private int duration;

    public Plank(String date, String start, String end, int duration) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
