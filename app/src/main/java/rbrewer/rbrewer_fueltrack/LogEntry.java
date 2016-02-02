package rbrewer.rbrewer_fueltrack;

import java.util.Date;

/**
 * Created by Ryan on 2016-01-30.
 */
public class LogEntry {
    protected Date date;
    protected String station;
    protected Double odometer;
    protected String fuelGrade;
    protected Double fuelAmount;
    protected Double fuelUnitCost;
    protected Double fuelCost;

    public LogEntry(Date date, String station, Double odometer, String fuelGrade, Double fuelAmount, Double fuelUnitCost) {
        this.date = date;
        this.station = station;
        this.odometer = odometer;
        this.fuelGrade = fuelGrade;
        this.fuelAmount = fuelAmount;
        this.fuelUnitCost = fuelUnitCost;
        this.fuelCost = fuelAmount * (fuelUnitCost / 100.00);
    }

    public Date getDate() {
        return date;
    }

    public String getStation() {
        return station;
    }

    public Double getOdometer() { return odometer; }

    public String getFuelGrade() {
        return fuelGrade;
    }

    public Double getFuelAmount() {
        return fuelAmount;
    }

    public Double getFuelUnitCost() {
        return fuelUnitCost;
    }

    public Double getFuelCost() {
        return fuelCost;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setOdometer(Double odometer) {
        this.odometer = odometer;
    }

    public void setFuelGrade(String fuelGrade) {
        this.fuelGrade = fuelGrade;
    }

    public void setFuelAmount(Double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public void setFuelUnitCost(Double fuelUnitCost) { this.fuelUnitCost = fuelUnitCost; }

}
