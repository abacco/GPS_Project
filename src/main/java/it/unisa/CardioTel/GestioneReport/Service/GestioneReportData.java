package it.unisa.CardioTel.GestioneReport.Service;

import java.util.ArrayList;
import java.util.Date;

import com.mongodb.client.*;
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

        MongoDatabase database = mongoClient.getDatabase("misurazioni");

        MongoCollection<Document> measureCollection = database.getCollection("misurazioni");
        Bson condition = null;


        condition = new Document("$gte", startDate).append("$lte", endDate);


        Bson filter = new Document("date", condition);


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