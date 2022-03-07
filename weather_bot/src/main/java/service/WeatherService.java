package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Region;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WeatherService {
    private String KEY = "1a77f99451877aa08cc3c5c9e592decb";
//    api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
    public Region getRegionWeatherData(String city){
        Region region = new Region();
        if (city.endsWith("Viloyati")){
            city = city.substring(0, city.length()-9);
        }
        String urlH = "https://api.openweathermap.org/data/2.5/weather?q=";
        String urlL = "&appid=";
        try {
            URL url = new URL(urlH+city+urlL+KEY);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            region = objectMapper.readValue(inputStream, new TypeReference<>() {});
        }catch (Exception e){
            e.printStackTrace();
        }
        return region;
    }
}
