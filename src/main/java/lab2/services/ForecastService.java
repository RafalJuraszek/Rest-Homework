package lab2.services;


import lab2.config.Utils;
import lab2.model.RequestData;
import lab2.model.CityWeatherData;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



@Service
public class ForecastService {

    private RestTemplate restTemplate;

    private List<RequestData> requestData;
    private int number;


    public ForecastService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public List<CityWeatherData> getForecastResults(List<RequestData> requests, int number) {
        requestData = requests;
        this.number = number;

        return getForecastResults();
    }

    private List<CityWeatherData> getForecastResults() {
        List<CityWeatherData> result = new ArrayList<>();
        for (RequestData r : requestData) {


            try {
                URIBuilder b = new URIBuilder("https://api.weatherbit.io/v2.0/forecast/daily");

                b.addParameter("city", Utils.normalize(r.getCity()));
                b.addParameter("key", Utils.KEY);
                b.addParameter("days", Integer.toString(number));

                URL url = b.build().toURL();

                ResponseEntity<String> response
                        = restTemplate.getForEntity(url.toString(), String.class);
                JSONObject jo = new JSONObject(response.getBody());

                CityWeatherData c = processForecastJson(jo);

                c.setCity(r.getCity());
                result.add(c);


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    private CityWeatherData processForecastJson(JSONObject jo) throws ParseException {
        JSONArray ja = jo.getJSONArray("data");

        List<Double> temperatures = new ArrayList<>();
        List<LocalDate> dates = new ArrayList<>();

        DateTimeFormatter s = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject j = ja.getJSONObject(i);
            temperatures.add(j.getDouble("temp"));
            dates.add(LocalDate.parse(j.getString("datetime"), s));
        }

        CityWeatherData result = new CityWeatherData();
        result.setTemperatures(temperatures);
        result.setDates(dates);
        OptionalDouble max = temperatures.stream().mapToDouble(a -> a).max();
        result.setMax(Utils.round(max.getAsDouble(), 2));

        OptionalDouble min = temperatures.stream().mapToDouble(a -> a).min();

        result.setMin(Utils.round(min.getAsDouble(), 2));
        OptionalDouble avg = temperatures.stream().mapToDouble(a -> a).average();
        result.setTemperature(Utils.round(avg.getAsDouble(), 2));

        return result;


    }


}
