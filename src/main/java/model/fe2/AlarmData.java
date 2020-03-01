package model.fe2;

/**
 * Created by Finn on 31.01.2020.
 */
public class AlarmData {
    private String keyword;
    private String keyword_description;
    private String keyword_category;
    private String keyword_color;

    private String street;
    private String house;
    private String city;
    private String city_abbr;
    private String building;
    private String vehicles;

    private String location_additional;
    private String keyword_misc;
    private String escalation_additional;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword_description() {
        return keyword_description;
    }

    public void setKeyword_description(String keyword_description) {
        this.keyword_description = keyword_description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_abbr() {
        return city_abbr;
    }

    public void setCity_abbr(String city_abbr) {
        this.city_abbr = city_abbr;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getLocation_additional() {
        return location_additional;
    }

    public void setLocation_additional(String location_additional) {
        this.location_additional = location_additional;
    }

    public String getKeyword_misc() {
        return keyword_misc;
    }

    public void setKeyword_misc(String keyword_misc) {
        this.keyword_misc = keyword_misc;
    }

    public String getEscalation_additional() {
        return escalation_additional;
    }

    public void setEscalation_additional(String escalation_additional) {
        this.escalation_additional = escalation_additional;
    }

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }

    public String getKeyword_category() {
        return keyword_category;
    }

    public void setKeyword_category(String keyword_category) {
        this.keyword_category = keyword_category;
    }

    public String getKeyword_color() {
        return keyword_color;
    }

    public void setKeyword_color(String keyword_color) {
        this.keyword_color = keyword_color;
    }
}
