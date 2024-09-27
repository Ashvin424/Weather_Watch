package com.example.weatherwatch.Model;

public class Wind {
    private double speed;
    private double deg;

    public Wind(double deg, double speed) {
        this.deg = deg;
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
