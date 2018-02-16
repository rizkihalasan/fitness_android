package com.example.leo.fitnessdiy.model;

/**
 * Created by Leo on 16/02/2018.
 */

public class History {
    private String sport_name;
    private String sport_date;
    private String sport_start;
    private String sport_end;

    public History(String name, String date, String start, String end) {
        this.sport_name = name;
        this.sport_date = date;
        this.sport_start = start;
        this.sport_end = end;
    }

    public String getSport_name() {
        return sport_name;
    }

    public void setSport_name(String sport_name) {
        this.sport_name = sport_name;
    }

    public String getSport_date() {
        return sport_date;
    }

    public void setSport_date(String sport_date) {
        this.sport_date = sport_date;
    }

    public String getSport_start() {
        return sport_start;
    }

    public void setSport_start(String sport_start) {
        this.sport_start = sport_start;
    }

    public String getSport_end() {
        return sport_end;
    }

    public void setSport_end(String sport_end) {
        this.sport_end = sport_end;
    }
}
