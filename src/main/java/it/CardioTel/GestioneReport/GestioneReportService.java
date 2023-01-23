package it.CardioTel.GestioneReport;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;

public interface GestioneReportService{
    ArrayList<Document> getMeasurements (String periodOfTime);

}
