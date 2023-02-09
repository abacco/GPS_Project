package it.CardioTel.GestioneReport;

import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ApplicationScoped
public class GestioneReportServiceImpl {

    @Inject
    GestioneReportData gestioneReportData;

    public ArrayList<Document> getMeasurements (String periodOfTime){

        //formattazione stringa
        Date [] pot = new Date [2];
        String startDate = "" ;
        String endDate = "" ;
        String[] split = periodOfTime.split(",");
        startDate = split[0];
        endDate = split[1];

        try{
            pot[0]  = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            pot[1]  = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            Calendar calendar = dateToCalendar(pot[1]);
            calendar.add(Calendar.DATE, 1);
            pot[1] =calendarToDate(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<Document> list = new ArrayList<Document>();


        //array di documenti device
        list = gestioneReportData.getMeasurement(pot);

        return list;
    }

    //Converte Date a Calendar
    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    //Converte Calendar a Date
    private Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }


    public ArrayList<String> getMeasurementsToPrint (ArrayList<Document> l){
        return getMeasurementsInString(l);
    }


    //trasforma i document in stringhe formattate
    private ArrayList<String> getMeasurementsInString(ArrayList<Document> l){
        ArrayList<String> s = new ArrayList<String>();
        for (Document d : l){
            s.add(formatDocumentToString(d));
        }
      return s;
    }

    //Ottiene le medie dei valori nelle date selezionate
    public ArrayList<String> getAverages(ArrayList<Document> l ){
        return getMeasurentsInAverage(l);
    }


    //formatta in stringa i device presi da DB
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

    private ArrayList<String> getMeasurentsInAverage(ArrayList<Document> l){
        ArrayList<String> s = new ArrayList<String>();
        int preMax = 0;
        int preMin = 0;
        int colesterolo = 0;
        int freqCard = 0;
        int ossigenazione = 0;
        int temp = 0;
        int numInstancies = 0;



        Date date = (Date) l.get(0).get("date"); //prima data
        int i = 0;
        Date tDate = date;
        Date currDate =date;

        //aggiunge all'array le stringhe delle medie giornaliere
        while(l.size()>i){
            Document d = l.get(i);
            tDate = (Date)d.get("date");
            if(tDate.getYear() == date.getYear() && tDate.getMonth()==date.getMonth() && tDate.getDate()==date.getDate()){
                numInstancies++;
                freqCard += (int)d.get("heartFrequency");
                colesterolo += (int)d.get("colesterolo");
                ossigenazione += (int)d.get("ossigenazione");
                preMin += (int)d.get("pressione minima");
                preMax += (int)d.get("pressione massima");
                temp += (int)d.get("temp");
                currDate = tDate;

                i++;
            }else{

                s.add(GetAverageinString(freqCard,colesterolo,ossigenazione,preMin,preMax,temp,currDate,numInstancies));

                //passa al giorno successivo
                Calendar calendar = dateToCalendar(date);
                calendar.add(Calendar.DATE, 1);
                date =calendarToDate(calendar);
                numInstancies = 0;

            }
        }
        s.add(GetAverageinString(freqCard,colesterolo,ossigenazione,preMin,preMax,temp,currDate,numInstancies));

        return s;

}

    private String GetAverageinString(int freqCard, int colesterolo, int ossigenazione, int preMin, int preMax, int temp, Date tDate, int numInstancies) {
        freqCard /= numInstancies;
        colesterolo  /= numInstancies;
        ossigenazione  /= numInstancies;
        preMin  /= numInstancies;
        preMax  /= numInstancies;
        temp  /= numInstancies;

        return  "Frequenza cardiaca : " + String.valueOf(freqCard) +
                "\nTemperatura : "+String.valueOf(temp)+
                "\nOssigenazione : " + String.valueOf(ossigenazione)+
                "\nColesterolo : " + String.valueOf(colesterolo)+
                "\nPressione Minima : "+String.valueOf(preMin)+
                "\nPressione Massima : "+String.valueOf(preMax)+
                "\nData : "+String.valueOf(tDate);

    }
}
