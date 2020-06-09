package lab2.controllers;


import lab2.config.Utils;
import lab2.model.CityWeatherData;
import lab2.model.Form;
import lab2.model.HistoricalData;
import lab2.model.RequestData;
import lab2.services.HistoricalService;
import lab2.services.ForecastService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ResultController {


    private ForecastService forecastService;
    private HistoricalService historicalService;


    public ResultController(ForecastService forecastService, HistoricalService historicalService) {
        this.forecastService = forecastService;
        this.historicalService = historicalService;
    }

    @PostMapping("/getResults")
    public String getResults(@ModelAttribute Form data, Model model) {



        List<RequestData> filteredList = data.getCities().stream().filter(a -> !a.getCity().equals(""))
                .collect(Collectors.toList());
        filteredList.forEach(el -> System.out.println(Utils.normalize(el.getCity())));

        HistoricalData historicalData = null;
        if (Utils.validData(data.getStartDate(), data.getEndDate())) {
            historicalData = historicalService.getHistoricalData(data.getStartDate(), data.getEndDate(), Utils.normalize(data.getCurrCity()));
        }
        List<CityWeatherData> result = forecastService.getForecastResults(filteredList, data.getNumber());


        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        String minCity = "";
        String maxCity = "";
        for (CityWeatherData r : result) {
            if (r.getMax() > max) {
                max = r.getMax();
                maxCity = r.getCity();
            }
            if (r.getMin() < min) {
                min = r.getMin();
                minCity = r.getCity();
            }
        }


        model.addAttribute("data", result);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("minCity", minCity);
        model.addAttribute("maxCity", maxCity);
        model.addAttribute("amplitude", Utils.round(max - min, 2));

        if(historicalData!=null) {
            model.addAttribute("historicalMax", historicalData.getMax());
            model.addAttribute("historicalMin", historicalData.getMin());
            model.addAttribute("historicalAvg", historicalData.getAvg());
        }


        return "results";
    }


}
