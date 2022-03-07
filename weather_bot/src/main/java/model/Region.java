package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Region {

    @JsonProperty("name")
    String name;
    @JsonProperty("weather")
    ArrayList<MainWeather> weathers;
    @JsonProperty("main")
    Main main;
    @JsonProperty("wind")
    Wind wind;



}
