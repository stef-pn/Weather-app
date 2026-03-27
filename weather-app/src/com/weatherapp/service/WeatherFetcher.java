package com.weatherapp.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherFetcher {

    public static String getWeather(String city) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name="
                    + city + "&count=5";

            HttpRequest geoRequest = HttpRequest.newBuilder()
                    .uri(URI.create(geoUrl))
                    .build();

            HttpResponse<String> geoResponse =
                    client.send(geoRequest, HttpResponse.BodyHandlers.ofString());

            String geoData = geoResponse.body();

            if (!geoData.contains("latitude")) {
                return "{\"error\": \"Città non trovata\"}";
            }

            String cityName = extractValue(geoData, "\"name\":\"");
            String country = extractValue(geoData, "\"country\":\"");

            double lat = Double.parseDouble(extractNumber(geoData, "\"latitude\":"));
            double lon = Double.parseDouble(extractNumber(geoData, "\"longitude\":"));

            String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude="
                    + lat + "&longitude=" + lon + "&current_weather=true";

            HttpRequest weatherRequest = HttpRequest.newBuilder()
                    .uri(URI.create(weatherUrl))
                    .build();

            HttpResponse<String> weatherResponse =
                    client.send(weatherRequest, HttpResponse.BodyHandlers.ofString());

            String weatherData = weatherResponse.body();

            int weatherIndex = weatherData.indexOf("\"current_weather\"");
            String sub = weatherData.substring(weatherIndex);

            double temperature = Double.parseDouble(
                    extractNumber(sub, "\"temperature\":")
            );

            double windspeed = Double.parseDouble(
                    extractNumber(sub, "\"windspeed\":")
            );

            int code = Integer.parseInt(
                    extractNumber(sub, "\"weathercode\":")
            );

            String description = getWeatherDescription(code);

            boolean corrected = !city.trim().equalsIgnoreCase(cityName.trim()) || city.length() <= 3;

            return "{"
                    + "\"city\":\"" + cityName + "\","
                    + "\"country\":\"" + country + "\","
                    + "\"temperature\":" + temperature + ","
                    + "\"windspeed\":" + windspeed + ","
                    + "\"description\":\"" + description + "\","
                    + "\"corrected\":" + corrected
                    + "}";

        } catch (Exception e) {
            return "{\"error\": \"Errore di rete o API\"}";
        }
    }

    private static String extractValue(String data, String key) {
        int index = data.indexOf(key);
        return data.substring(index + key.length(),
                data.indexOf("\"", index + key.length()));
    }

    private static String extractNumber(String data, String key) {
        int index = data.indexOf(key);
        int start = index + key.length();
        int end = data.indexOf(",", start);
        if (end == -1) end = data.indexOf("}", start);
        return data.substring(start, end);
    }

    private static String getWeatherDescription(int code) {
        switch (code) {
            case 0: return "Sereno";
            case 1: case 2: case 3: return "Parzialmente nuvoloso";
            case 45: case 48: return "Nebbia";
            case 61: case 63: case 65: return "Pioggia";
            case 71: case 73: case 75: return "Neve";
            case 95: return "Temporale";
            default: return "Sconosciuto";
        }
    }
}