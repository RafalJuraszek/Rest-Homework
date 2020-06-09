package lab2.services;

import lab2.config.Utils;
import lab2.model.HistoricalData;
import lab2.model.RequestData;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class HistoricalService {

    private RestTemplate restTemplate;



    public HistoricalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public HistoricalData getHistoricalData(String start, String end, String city) {
        HistoricalData result = new HistoricalData();
        DateTimeFormatter s = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localStart = LocalDate.parse(start, s);
        LocalDate localEnd = LocalDate.parse(end, s);
        System.out.println(localStart.format(s));
        List<Double> temperatures = new ArrayList<>();


        for (int i = 0; i <= DAYS.between(localStart, localEnd); i++) {
            LocalDate tmp = localStart.plusDays(i);
            try {
                URIBuilder b = new URIBuilder("https://api.weatherbit.io/v2.0/history/daily");

                b.addParameter("city", city);
                b.addParameter("key", Utils.KEY);
                b.addParameter("start_date", tmp.toString());
                b.addParameter("end_date", tmp.plusDays(1).toString());

                URL url = b.build().toURL();
                System.out.println(url);
                ResponseEntity<String> response
                        = restTemplate.getForEntity(url.toString(), String.class);
                JSONObject jo = new JSONObject(response.getBody());
                JSONArray ja = jo.getJSONArray("data");

                temperatures.add(ja.getJSONObject(0).getDouble("temp"));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        OptionalDouble max = temperatures.stream().mapToDouble(a -> a).max();
        result.setMax(Utils.round(max.getAsDouble(), 2));

        OptionalDouble min = temperatures.stream().mapToDouble(a -> a).min();

        result.setMin(Utils.round(min.getAsDouble(), 2));
        OptionalDouble avg = temperatures.stream().mapToDouble(a -> a).average();
        result.setAvg(Utils.round(avg.getAsDouble(), 2));
        return result;
    }
}
