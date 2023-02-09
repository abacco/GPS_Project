package it.CardioTel.GestioneReport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import com.mongodb.client.*;
import io.quarkus.vertx.http.runtime.filters.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GestioneReportData{

    @Inject
    MongoClient mongoClient ;//=  new MongoClient("localhost", 27017);;

    private static final String DBname = "misurazioni";

    private MongoCollection getCollection() {
        return mongoClient.getDatabase(DBname).getCollection(DBname);
    }


    //Prende da DB i documenti che rappresentano i device
    public ArrayList<Document> getMeasurement(Date[] pot) {

        Date startDate = pot[0];
        Date endDate = pot[1];
        ArrayList<Document> list = new ArrayList<Document>();
        //controllare mongoClient
       // MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("misurazioni");

        MongoCollection<Document> measureCollection = database.getCollection("misurazioni");
        Bson condition = null;

        //così se sono uguali prende le  predizione della giornata fino a fine giornata
        if(startDate.equals(endDate)) {
            endDate.setHours(23);
            endDate.setMinutes(59);
            endDate.setSeconds(59);
        }


        condition = new Document("$gte", startDate).append("$lte", endDate);


        Bson filter = new Document("date", condition);
       // FindIterable<Document> documents = measureCollection.find(filter);

        MongoCursor<Document> cursor = measureCollection.find(filter).iterator();
        try {
            while(cursor.hasNext()) {
                Document document = cursor.next();
                list.add(document);
            }
         } finally {
        cursor.close();
         }
        return list;
    }

}