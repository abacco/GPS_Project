package it.CardioTel.GestioneReport;

import org.bson.Document;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GestioneReportServiceImpl {

    @Inject
    GestioneReportData gestioneReportData;

    public ArrayList<Document> getMeasurements (String periodOfTime){
        Date [] pot = new Date [2];
        String startDate = "" ;
        String endDate = "" ;
        try {
        String[] split = periodOfTime.split(" ");
        startDate = split[0];
        endDate = split[2];
        //pot = array di date
        pot[0] = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        pot[1] = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Document> list = new ArrayList<Document>();
        list = gestioneReportData.getMeasurement(pot);
        return list;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    private Date startDate;
    private Date endDate;
}
