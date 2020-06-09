package lab2.model;

import java.util.ArrayList;
import java.util.List;

public class Form {
    List<RequestData> cities = new ArrayList<RequestData>();
    private int number;
    private String currCity;
    private String startDate;
    private String endDate;

    public Form(List<RequestData> cities) {
        this.cities = cities;
    }

    public List<RequestData> getCities() {
        return cities;
    }

    public void setCities(List<RequestData> cities) {
        this.cities = cities;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCurrCity() {
        return currCity;
    }

    public void setCurrCity(String currCity) {
        this.currCity = currCity;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
