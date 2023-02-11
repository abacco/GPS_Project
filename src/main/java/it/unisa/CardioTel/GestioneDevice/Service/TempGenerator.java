package it.unisa.CardioTel.GestioneDevice.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import it.unisa.CardioTel.GestioneDevice.Service.Device;
import org.bson.Document;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.reactivex.Flowable;

@Timed(name = "time", unit = MetricUnits.MILLISECONDS)
@ApplicationScoped
public class TempGenerator {

    @Inject MongoClient mongoClient;
    private Timer myTimer;


    //private Date date = new Date();

    Device esp8266 = new Device("ESP8266-01");

    @Outgoing("device-temp")
    public Flowable<String> generate() {
        try {
            return Flowable.interval(10,  TimeUnit.SECONDS)
                    .onBackpressureDrop()
                    .map(t -> {

                        Document document = new Document()
                                .append("deviceName", esp8266.getDeviceName())
                                .append("heartFrequency", esp8266.getHeartFrequency())
                                .append("temp", esp8266.getTemp())
                                .append("ossigenazione", esp8266.getOssigenazione())
                                .append("colesterolo", esp8266.getColesterolo())
                                .append("pressione massima", esp8266.getPressione())
                                .append("pressione minima", esp8266.getPressione_due())
                                .append("date", new Date());
                        myTimer = new Timer();
                        String data = esp8266.toString();
                        myTimer.schedule(new TimerTask(){
                            @Override
                            public void run(){
                                getCollection().insertOne(document);
                            }
                        }, 0, 50);
                        System.out.println(data);
                        return data;
                    });
        } catch (Exception e) {
            e.getMessage();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Device getEsp8266() {
        return esp8266;
    }

    public List<String> list() {
        List<String> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Device device = new Device();
                device.setDeviceName(document.getString("deviceName"));
                device.setHeartFreq(document.getInteger("heartFrequency"));
                device.setTemp(document.getInteger("temp"));
                device.setOssigenazione(document.getInteger("ossigenazione"));
                device.setColesterolo(document.getInteger("colesterolo"));
                device.setPressione(document.getInteger("pressione"));
                list.add(String.valueOf(device));
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    private MongoCollection getCollection() {
        return mongoClient.getDatabase("misurazioni").getCollection("misurazioni");
    }

}
