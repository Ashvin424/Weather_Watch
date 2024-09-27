package com.example.weatherwatch.Model;

import java.util.List;

public class OpenWeatherMap {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private Wind wind;
    private Rain rain;
    private Cloud cloud;
    private int dt;
    private Sys sys;
    private int id;
    private String name;
    private int cod;

    public OpenWeatherMap(){

    }

    public OpenWeatherMap(String base, Cloud cloud, int cod, Coord coord, int dt, int id, Main main, String name, Rain rain, Sys sys, List<Weather> weatherList, Wind wind) {
        this.base = base;
        this.cloud = cloud;
        this.cod = cod;
        this.coord = coord;
        this.dt = dt;
        this.id = id;
        this.main = main;
        this.name = name;
        this.rain = rain;
        this.sys = sys;
        this.weather = weatherList;
        this.wind = wind;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
