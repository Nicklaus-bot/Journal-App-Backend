package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String apiKey = "f8eac4b198c0cd58152c74bbff0fef87";

    private static final String API = "http://api.weatherstack.com/current?access_key=apiKey&query=mycity";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String finalAPI = API.replace("mycity" , city).replace("apiKey" , apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET,
                null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }






}
