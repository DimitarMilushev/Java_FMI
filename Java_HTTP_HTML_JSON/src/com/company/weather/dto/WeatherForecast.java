package com.company.weather.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public class WeatherForecast {

    @SerializedName("weather")
    private final WeatherCondition[] conditions;
    @SerializedName("main")
    private final WeatherData data;

    public WeatherForecast(WeatherCondition[] conditions, WeatherData data) {
        this.conditions = conditions;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherForecast that = (WeatherForecast) o;
        return Arrays.equals(conditions, that.conditions) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(data);
        result = 31 * result + Arrays.hashCode(conditions);
        return result;
    }

    @Override
    public String toString() {
        return "Conditions: " + conditions[0].toString() +
                System.lineSeparator() +
                "Data:" +
                System.lineSeparator() +
                data;
    }
}
