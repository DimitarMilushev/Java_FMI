package com.company.weather;

import com.company.weather.dto.WeatherForecast;
import com.company.weather.exceptions.LocationNotFoundException;
import com.company.weather.exceptions.WeatherForecastClientException;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class WeatherForecastClient {

    private final String SCHEME = "http";
    private final String HOST = "api.openweathermap.org";
    private final String PATH = "/data/2.5/weather";

    private Map<String, String> query = new LinkedHashMap<>();


    private final HttpClient client;


    public WeatherForecastClient(HttpClient weatherHttpClient) {
        this.client = weatherHttpClient;
    }

    /**
     * Fetches the weather forecast for the specified city.
     *
     * @return the forecast
     * @throws LocationNotFoundException if the city is not found
     * @throws WeatherForecastClientException if information regarding the weather for this location could not be retrieved
     */
    public WeatherForecast getForecast(String city) throws Exception {
        String unit = "metric";
        String apiKey = "5a1d504a803e8076e18c2c43def99e21";

        this.query.put("q", city);
        this.query.put("units", unit);
        this.query.put("appid", apiKey);

        String encodedQuery = getQuery();

        URI uri = new URI(SCHEME, HOST, PATH, encodedQuery, null);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();

        if(statusCode == 200) {
            return new Gson().fromJson(response.body(), WeatherForecast.class);
        }
        else if(statusCode == 404) {
            throw new LocationNotFoundException(String.format("The location of %s was not found.", city));
        }
        else {
            throw new WeatherForecastClientException
                    (String.format("Information regarding the weather for %s could not be retrieved.", city));
        }
    }

    private String getQuery() {
       return this.query.keySet().stream()
               .map(key -> {
                   try {
                       return key + "=" + encodeValue(this.query.get(key));
                   } catch (UnsupportedEncodingException e) {
                       e.printStackTrace();
                   }
                   return key;
               })
               .collect(joining("&"));
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}