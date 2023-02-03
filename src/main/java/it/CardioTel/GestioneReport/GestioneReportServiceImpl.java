package it.CardioTel.GestioneReport;

import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class GestioneReportServiceImpl {

    @Inject
    GestioneReportData gestioneReportData;

    public ArrayList<String> getMeasurements (String periodOfTime){
        Date [] pot = new Date [2];
        String startDate = "" ;
        String endDate = "" ;
        try {
        String[] split = periodOfTime.split(" ");
        startDate = split[0];
        endDate = split[1];
        //pot = array di date
        pot[0] = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        pot[1] = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Document> list = new ArrayList<Document>();
        list = gestioneReportData.getMeasurement(pot);

        return getMeasurementsInString(list);
    }

    private ArrayList<String> getMeasurementsInString(ArrayList<Document> l){
        ArrayList<String> s = new ArrayList<String>();
        for (Document d : l){
            s.add(formatDocumentToString(d));
        }
      return s;
    }

    private String formatDocumentToString(Document d) {
        if(d.isEmpty() || d == null) {
            return "";
        }

        StringBuilder str = new StringBuilder();
        str = str.append("Nome device: ");
        str = str.append(d.get("deviceName") + "; ");
        str = str.append("Frequenza cardiaca: ");
        str = str.append(d.get("heartFrequency") + "; ");
        str = str.append("Temperatura: ");
        str = str.append(d.get("temp") + "; ");
        str = str.append("Ossigenazione: ");
        str = str.append(d.get("ossigenazione") + "; ");
        str = str.append("Colesterolo: ");
        str = str.append(d.get("colesterolo") + "; ");
        str = str.append("Pressione (massima, minima): ");
        str = str.append(d.get("pressione massima") + ", ");
        str = str.append(d.get("pressione minima") + "; ");
        str = str.append("Data: ");
        str = str.append(d.get("date") + ". ");

        return str.toString();

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
