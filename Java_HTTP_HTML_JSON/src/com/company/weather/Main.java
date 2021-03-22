package com.company.weather;

import com.company.weather.dto.WeatherForecast;
import com.company.weather.exceptions.WeatherForecastClientException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        HttpClient httpClient = HttpClient.newBuilder().build();
        WeatherForecastClient forecastClient = new WeatherForecastClient(httpClient);

        Scanner scanner = new Scanner(System.in);
        String city = scanner.nextLine();

        try {
            WeatherForecast forecast = forecastClient.getForecast(city);

            System.out.printf("Forecast for city %s:\n%s", city, forecast.toString());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
