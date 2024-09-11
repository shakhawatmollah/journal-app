package com.shakhawat.journalapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeatherResponse {

    private Current current;

    @Getter
    @Setter
    public static class Current {

        private int temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private int humidity;

        private int feelslike;
    }

}
