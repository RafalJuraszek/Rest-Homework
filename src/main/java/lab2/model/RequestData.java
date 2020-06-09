package lab2.model;

public class RequestData {

    private String city;



    public RequestData(String city) {
        this.city = city;
    }

    public RequestData() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    @Override
    public String toString() {
        return "RequestData{" +
                "city='" + city + '\'' +
                '}';
    }
}
