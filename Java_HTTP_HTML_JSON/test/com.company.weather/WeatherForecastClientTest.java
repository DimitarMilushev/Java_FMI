package com.company.weather;

import com.company.weather.dto.WeatherCondition;
import com.company.weather.dto.WeatherData;
import com.company.weather.dto.WeatherForecast;
import com.company.weather.exceptions.LocationNotFoundException;
import com.company.weather.exceptions.WeatherForecastClientException;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastClientTest {

    private final String SOFIA_OUTPUT =
                    "Conditions: rainy" +
                    System.lineSeparator() +
                    "Data:" +
                    System.lineSeparator() +
                    "Temperature: -5.33" +
                     System.lineSeparator() +
                     "Feels like: -3.0";

    private final String PLEVEN_OUTPUT =
                    "Conditions: cloudy\n" +
                    System.lineSeparator() +
                    "Data:" +
                    System.lineSeparator() +
                    "Temperature: 5.33" +
                    System.lineSeparator() +
                    "Feels like: 8.1";

    private static WeatherForecast weatherForecastSofia;
    private static String weatherForecastSofiaJson;

    @Mock
    private HttpClient mockedWeatherHttpClient;

    @Mock
    private HttpResponse<String> mockedWeatherHttpResponse;

    private WeatherForecastClient client;

    @BeforeClass
    public static void setUpClass() {
        WeatherCondition condition = new WeatherCondition("rainy");
        WeatherData data = new WeatherData(-5.33, -3.0);
        weatherForecastSofia = new WeatherForecast(new WeatherCondition[] {condition}, data);
        weatherForecastSofiaJson = new Gson().toJson(weatherForecastSofia);
    }

    @Before
    public void setUp() throws IOException, InterruptedException {
        when(mockedWeatherHttpClient
                .send(Mockito.any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockedWeatherHttpResponse);

        client = new WeatherForecastClient(mockedWeatherHttpClient);
    }

    @Test
    public void testShouldReturnValidCity() throws Exception {
        when(mockedWeatherHttpResponse.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(mockedWeatherHttpResponse.body()).thenReturn(weatherForecastSofiaJson);

        var result = client.getForecast("Sofia");

        assertEquals("Incorrect weather forecast for valid city.", weatherForecastSofia, result);
    }

    @Test(expected = LocationNotFoundException.class)
    public void testGetForecastForNonexistentCity() throws Exception {
        when(mockedWeatherHttpResponse.statusCode()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);

        var result = client.getForecast("Fake City");
    }

    @Test
    public void testGetForecastServerError() {
        when(mockedWeatherHttpResponse.statusCode()).thenReturn(HttpURLConnection.HTTP_UNAVAILABLE);

        try {
            client.getForecast("Sofia");
        } catch (Exception exception) {
            assertEquals("Server error should return WeatherForecastClientException"
                    , WeatherForecastClientException.class, exception.getClass());

            assertNotEquals("Shouldn't return LocationNotFoundException since that's for Not found exceptions"
            , LocationNotFoundException.class, exception.getClass());
        }
    }

    @Test
    public void testResultShouldReturnFormattedOutput() throws Exception {
        when(mockedWeatherHttpResponse.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(mockedWeatherHttpResponse.body()).thenReturn(weatherForecastSofiaJson);

        var result = client.getForecast("Sofia").toString();

        assertEquals(SOFIA_OUTPUT, result);
        assertNotEquals(PLEVEN_OUTPUT, result);
    }
}
