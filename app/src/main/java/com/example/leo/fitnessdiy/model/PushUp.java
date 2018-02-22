package com.example.leo.fitnessdiy.model;

/**
 * Created by Leo on 22/02/2018.
 */

public class PushUp {
    private String date;
    private String start;
    private String end;
    private int frequency;

    public PushUp(String date, String start, String end, int frequency) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.frequency = frequency;
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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
