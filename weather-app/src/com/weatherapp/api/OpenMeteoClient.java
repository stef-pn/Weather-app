package com.weatherapp.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenMeteoClient {

    public String getWeatherData(double lat, double lon) {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude="
                    + lat + "&longitude=" + lon + "&current_weather=true";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public double[] getCoordinates(String city) {
        try {
            String url = "https://geocoding-api.open-meteo.com/v1/search?name="
                    + city + "&count=1";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            String data = response.body();

            int latIndex = data.indexOf("\"latitude\":");
            int lonIndex = data.indexOf("\"longitude\":");

            double lat = Double.parseDouble(
                    data.substring(latIndex + 11, data.indexOf(",", latIndex))
            );

            double lon = Double.parseDouble(
                    data.substring(lonIndex + 12, data.indexOf(",", lonIndex))
            );

            return new double[]{lat, lon};

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
