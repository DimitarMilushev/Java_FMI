package com.company.weather.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class WeatherData {

    @SerializedName("temp")
    private final double temperature;
    @SerializedName("feels_like")
    private final double feelsLike;

    @Override
    public String toString() {
        return  "Temperature: " + temperature +
                 System.lineSeparator() +
                "Feels like: " + feelsLike;
    }

    public WeatherData(double temp, double feelsLike) {
        this.temperature = temp;
        this.feelsLike = feelsLike;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherData that = (WeatherData) o;
        return Double.compare(that.temperature, temperature) == 0 && Double.compare(that.feelsLike, feelsLike) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, feelsLike);
    }
}
