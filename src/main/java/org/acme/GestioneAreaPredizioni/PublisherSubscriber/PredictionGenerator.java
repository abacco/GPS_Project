package org.acme.GestioneAreaPredizioni.PublisherSubscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import io.reactivex.Flowable;
import org.acme.DBQueries;
import org.acme.Device;
import org.acme.GestioneAreaPredizioni.MachineLearning.PredizioniAteroService;
import org.acme.GestioneAreaPredizioni.MachineLearning.PredizioniInfartoService;
import org.bson.Document;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class PredictionGenerator {

    @Inject
    MongoClient mongoClient;

    @Outgoing("AreaPredizioni-temp")
    public Flowable<byte[]> generate() {
        return Flowable.interval(5,  TimeUnit.SECONDS)
                .onBackpressureDrop()
                .map(t -> {
                    List<Device> ril = getRilevazioni();
                    List<String> pr = new ArrayList<>();
                    pr.add(PredizioniInfartoService.getPredizioneInfarto(ril).toString());
                    pr.add(PredizioniAteroService.getPredizioneAtero(ril).toString());

                    //serializza l'array in byte[]
                    ObjectMapper objectMapper = new ObjectMapper();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    objectMapper.writeValue(out,pr);
                    byte[] data = out.toByteArray();
                    return data;


                });
    }

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

        public List<Predizione> getPredizioni(){
            List<Predizione> predizioni = new ArrayList<>();
            List<Device> ril = getRilevazioni();
            predizioni.add(PredizioniInfartoService.getPredizioneInfarto(ril));
            predizioni.add(PredizioniAteroService.getPredizioneAtero(ril));
            if(predizioni.get(0).getRischio()==null && predizioni.get(1).getRischio()==null ){
                Predizione pTest = predizioni.get(0);
                pTest.setPercentualeRischio(25);
                pTest.setRischio("sotto_controllo");
                predizioni.set(0,pTest);
                predizioni.set(1,pTest);
            }

            return predizioni;
        }



}
