package com.example.weatherwatch.Model;

public class Sys {
    private int type;
    private int id;
    private double message;
    private String country;
    private long sunrise;
    private long sunset;

    public Sys(String country, int id, double message, long sunrise, long sunset, int type) {
        this.country = country;
        this.id = id;
        this.message = message;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
