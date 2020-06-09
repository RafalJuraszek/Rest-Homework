package lab2.config;

import lab2.model.HistoricalData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String KEY = "c63e6c1f6a604f03b46c778f493b29b6";

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean validData(String start, String end) {

        if(start==null || end==null)
            return false;

        DateTimeFormatter s = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localStart = LocalDate.parse(start, s);
        LocalDate localEnd = LocalDate.parse(end, s);
        LocalDate currentDate = LocalDate.now();
        if(localStart.isBefore(localEnd) && localEnd.isBefore(currentDate)) {
            return true;
        }
        return false;
    }

    public static String normalize(String s)
    {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }
}
