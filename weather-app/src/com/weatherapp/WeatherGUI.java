package com.weatherapp;

import javax.swing.*;
import java.awt.*;
import com.weatherapp.service.WeatherFetcher;
import com.weatherapp.util.HistoryManager;

public class WeatherGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("🌦️ Weather App");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(135, 206, 250));
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel title = new JLabel("Weather App", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JTextField cityInput = new JTextField();
        JButton button = new JButton("Cerca");

        JLabel result = new JLabel("", SwingConstants.CENTER);
        JLabel error = new JLabel("", SwingConstants.CENTER);
        error.setForeground(Color.RED);

        panel.add(title);
        panel.add(new JLabel("Inserisci città:", SwingConstants.CENTER));
        panel.add(cityInput);
        panel.add(button);
        panel.add(result);
        panel.add(error);

        frame.add(panel);
        frame.setVisible(true);

        button.addActionListener(e -> {
            String city = cityInput.getText();

            if (city.isEmpty()) {
                error.setText("Inserisci una città");
                return;
            }

            HistoryManager.save(city);

            String data = WeatherFetcher.getWeather(city);

            if (data.contains("error")) {
                error.setText("Città non trovata");
                result.setText("");
            } else {
                String temp = get(data, "\"temperature\":", ",\"windspeed\"");
                String wind = get(data, "\"windspeed\":", ",\"description\"");
                String desc = get(data, "\"description\":\"", "\",\"corrected\"");
                String corrected = get(data, "\"corrected\":", "}");

                if (corrected.equals("true")) {
                    error.setText("⚠ Risultato approssimato");
                } else {
                    error.setText("");
                }

                result.setText("🌡 " + temp + "°C | 💨 " + wind + " km/h | " + desc);

                // colore dinamico
                if (desc.contains("Sereno")) {
                    panel.setBackground(new Color(255, 223, 0));
                } else if (desc.contains("Pioggia")) {
                    panel.setBackground(Color.LIGHT_GRAY);
                } else {
                    panel.setBackground(new Color(135, 206, 250));
                }
            }
        });
    }

    private static String get(String data, String startKey, String endKey) {
        int start = data.indexOf(startKey) + startKey.length();
        int end = data.indexOf(endKey, start);
        return data.substring(start, end);
    }
}