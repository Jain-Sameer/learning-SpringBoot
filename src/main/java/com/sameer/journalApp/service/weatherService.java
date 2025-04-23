package com.sameer.journalApp.service;

import com.sameer.journalApp.apiresponse.georeponse;
import com.sameer.journalApp.apiresponse.weatherResponse;
//import com.sameer.journalApp.cache.APP_CACHE;
import com.sameer.journalApp.cache.AppCache;
import com.sameer.journalApp.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class weatherService {

    // Spring doesnt mess with static variables.
    @Value("${weather.api.key}")
    private String apiKey;
//    private static final String API = "http://api.openweathermap.org/geo/1.0/direct?q={city name}&limit={limit}&appid={API key}";
//    private static final String WeatherAPI = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}";
    private String lon ;
    private String lat ;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache APP_CACHE;

    public ResponseEntity<weatherResponse> getWeather(String city) {
        String api_uri = APP_CACHE.cache.get("geo_weather_api")
                .replace("{city name}", city)
                .replace("{limit}", "2")
                .replace("{API key}", apiKey);

        // 👇 Corrected type
        ResponseEntity<georeponse[]> response = restTemplate.exchange(
                api_uri,
                HttpMethod.GET,
                null,
                georeponse[].class // Expect an array
        );

        georeponse[] geoArray = response.getBody();
        if(geoArray == null) return null;
        lon = geoArray[0].getLon() + "" ;
        lat = geoArray[0].getLat()+ "";

        String weatherAPI_uri = APP_CACHE.cache.get("weather_api").replace("{lat}", lat).replace("{lon}", lon).replace("{API key}", apiKey);

        ResponseEntity<weatherResponse> weatherResponseResponseEntity = restTemplate.exchange(weatherAPI_uri, HttpMethod.GET, null, weatherResponse.class);
        // Return the first result or null
        return weatherResponseResponseEntity;
    }

    public void openWeather() {

    }

}
