package com.sameer.journalApp.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class georeponse {
    private String name;
    private LocalNames local_names;
    private double lat;
    private double lon;
    private String country;
    private String state;

    // Getters and setters
    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public String getCountry() { return country; }
    public String getState() { return state; }
    public LocalNames getLocal_names() { return local_names; }

    public static class LocalNames {
        private String en;
        private String ascii;

        @JsonProperty("to")
        private String myto;

        // Getters
        public String getEn() { return en; }
        public String getAscii() { return ascii; }
        public String getMyto() { return myto; }
    }
}
