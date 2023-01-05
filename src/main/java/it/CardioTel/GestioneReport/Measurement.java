package it.CardioTel.GestioneReport;

import java.util.Date;

public class Measurement {

    public Measurement (String measurement, Date date){

        this.measurement = measurement;
        this.date = date;
    }

    public String getMeasurement() {
        return measurement;
    }

    public Date getDate() {
        return date;
    }

    public boolean isInTheRange(Measurement m1, Measurement m2){

        boolean inRange = false;

        if (this.date.after(m1.getDate()) && this.date.before(m2.getDate()))
            inRange = true;

        return inRange;
    }

    private String measurement = null;

    private Date date = null;
}
