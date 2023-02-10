package it.unisa.CardioTel.GestioneDevice;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class DBQueries {
    /*

   @Inject
   MongoClient mongoClient;

    private MongoCollection getCollection(){
        return mongoClient.getDatabase("misurazioni").getCollection("misurazioni");
    }

    public List<Device> getRilevazioni(){
        List<Device> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Device device = new Device();

                device.setDeviceName(document.getString("deviceName"));
                device.setColesterolo(document.getInteger("colesterolo"));
                device.setOssigenazione(document.getInteger("ossigenazione"));
                device.setTemp(document.getInteger("temp"));
                device.setHeartFreq(document.getInteger("heartFrequency"));
                device.setHeartFreq(document.getInteger("pressione massima"));
                device.setHeartFreq(document.getInteger("pressione minima"));

                list.add(device);
            }
        } finally {
            cursor.close();
        }
        return list;

    }
*/


}
