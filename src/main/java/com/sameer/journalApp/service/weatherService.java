package com.sameer.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sameer.journalApp.apiresponse.georeponse;
import com.sameer.journalApp.apiresponse.weatherResponse;
import com.sameer.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class weatherService {

    // Spring doesnt mess with static variables.
    @Value("${weather.api.key}")
    private String apiKey;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache APP_CACHE;

    @Autowired RedisService redisService;


    public ResponseEntity<weatherResponse> getWeather(String city) {
        weatherResponse cachedResponse = redisService.get("weather_of_" + city, weatherResponse.class);
        if (cachedResponse != null) {
            System.out.println("no api call");
            return ResponseEntity.ok(cachedResponse);
        }

        String api_uri = APP_CACHE.cache.get("geo_weather_api")
                .replace("{city name}", city)
                .replace("{limit}", "2")
                .replace("{API key}", apiKey);

        ResponseEntity<georeponse[]> response = restTemplate.exchange(
                api_uri,
                HttpMethod.GET,
                null,
                georeponse[].class
        );

        georeponse[] geoArray = response.getBody();
        if (geoArray == null || geoArray.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String lon = String.valueOf(geoArray[0].getLon());
        String lat = String.valueOf(geoArray[0].getLat());

        String weatherAPI_uri = APP_CACHE.cache.get("weather_api")
                .replace("{lat}", lat)
                .replace("{lon}", lon)
                .replace("{API key}", apiKey);

        ResponseEntity<weatherResponse> weatherResponseEntity = restTemplate.exchange(
                weatherAPI_uri,
                HttpMethod.GET,
                null,
                weatherResponse.class
        );
        if (weatherResponseEntity.getBody() != null) {
            // Use a positive TTL or null to avoid Redis errors
            redisService.set("weather_of_" + city, weatherResponseEntity.getBody(), -1L); // 1 hour TTL example
        }

        return weatherResponseEntity;
    }



}
