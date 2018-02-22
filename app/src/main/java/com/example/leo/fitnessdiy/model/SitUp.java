package com.example.leo.fitnessdiy.model;

import com.example.leo.fitnessdiy.Utilities.NetworkUtils;
import com.example.leo.fitnessdiy.routes.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Leo on 22/02/2018.
 */

public class SitUp {
    private String date;
    private String start;
    private String end;
    private String frequency;

    public SitUp(String date, String start, String end, String frequency) {
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
