package model;

import java.util.List;

/**
 * Created by Finn on 24.01.2020.
 */
public class Operation {
    private String einsatznr;
    private int id;
    private String stichwort;
    private String description;
    private String adresse;
    private String objekt;
    private String alarmzeit;
    private String alarmzeit_seconds;
    private List<Einheit> einheiten;
    private List<Zusatzinfo> zusatzinfos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStichwort() {
        return stichwort;
    }

    public void setStichwort(String stichwort) {
        this.stichwort = stichwort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getObjekt() {
        return objekt;
    }

    public void setObjekt(String objekt) {
        this.objekt = objekt;
    }

    public String getAlarmzeit() {
        return alarmzeit;
    }

    public void setAlarmzeit(String alarmzeit) {
        this.alarmzeit = alarmzeit;
    }

    public String getAlarmzeit_seconds() {
        return alarmzeit_seconds;
    }

    public void setAlarmzeit_seconds(String alarmzeit_seconds) {
        this.alarmzeit_seconds = alarmzeit_seconds;
    }

    public List<Einheit> getEinheiten() {
        return einheiten;
    }

    public void setEinheiten(List<Einheit> einheiten) {
        this.einheiten = einheiten;
    }

    public List<Zusatzinfo> getZusatzinfos() {
        return zusatzinfos;
    }

    public void setZusatzinfos(List<Zusatzinfo> zusatzinfos) {
        this.zusatzinfos = zusatzinfos;
    }

    public String getEinsatznr() {
        return einsatznr;
    }

    public void setEinsatznr(String einsatznr) {
        this.einsatznr = einsatznr;
    }
}
