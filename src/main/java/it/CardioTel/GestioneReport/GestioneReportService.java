package it.CardioTel.GestioneReport;

import org.bson.Document;

import java.util.ArrayList;

public interface GestioneReportService{
    ArrayList<Document> getMeasurements (String periodOfTime);

    ArrayList<String> getMeasurementsToPrint (ArrayList<Document> l);
    ArrayList<String> getAverages(ArrayList<Document> l);
}
