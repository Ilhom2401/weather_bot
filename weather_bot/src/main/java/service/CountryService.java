package service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Countries;
import model.Country;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class CountryService {

    public ArrayList<Country> countries;

    {
        try {
            URL url = new URL("https://countriesnow.space/api/v0.1/countries");
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            Countries data = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            countries = data.getCountries();
        } catch (Exception e) {
            System.out.println("error !!!>");
        }
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

}
