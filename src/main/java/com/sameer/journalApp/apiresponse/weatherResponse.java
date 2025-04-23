package com.sameer.journalApp.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class weatherResponse {
    public Coord coord;
    public ArrayList<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Rain rain;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;

    public static class Clouds {
        public int all;
    }

    public static class Coord {
        public double lon;
        public double lat;
    }

    public static class Main {
        public double temp;

        @JsonProperty("feels_like")
        public double feelsLike;

        @JsonProperty("temp_min")
        public double tempMin;

        @JsonProperty("temp_max")
        public double tempMax;

        public int pressure;
        public int humidity;

        @JsonProperty("sea_level")
        public int seaLevel;

        @JsonProperty("grnd_level")
        public int grndLevel;
    }

    public static class Rain {
        @JsonProperty("1h")
        public double oneHour;
    }

    public static class Sys {
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
    }

    public static class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public static class Wind {
        public double speed;
        public int deg;
        public double gust;
    }
}
