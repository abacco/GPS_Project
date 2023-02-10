package it.unisa.CardioTel.GestioneReport.Service;

import org.bson.Document;

import java.util.ArrayList;

public interface GestioneReportService{
    ArrayList<String> getMeasurements (String periodOfTime);

    ArrayList<String> getAverages(ArrayList<Document> l);
}
