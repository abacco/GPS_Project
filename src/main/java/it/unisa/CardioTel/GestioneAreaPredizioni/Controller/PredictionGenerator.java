package it.unisa.CardioTel.GestioneAreaPredizioni.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import io.reactivex.Flowable;
import it.unisa.CardioTel.GestioneDevice.Service.Device;
import it.unisa.CardioTel.GestioneAreaPredizioni.Service.PredizioniAteroService;
import it.unisa.CardioTel.GestioneAreaPredizioni.Service.PredizioniInfartoService;
import org.bson.Document;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Timed(name = "time", unit = MetricUnits.MILLISECONDS)
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
                    device.setPressione(document.getInteger("pressione massima"));
                    device.setPressione_due(document.getInteger("pressione minima"));

                    list.add(device);
                }
            } finally {
                cursor.close();
            }


            //se il DB non ha abbastanza instance ne genera 10 per il testing
            // il modello non può fare una predizione se non ci sono almeno 10 instance

            for(int i=0;list.size()<10;i++){
                Device device = new Device();

                device.setDeviceName(device.getDeviceName());
                device.setColesterolo(device.getColesterolo());
                device.setOssigenazione(device.getOssigenazione());
                device.setTemp(device.getTemp());
                device.setHeartFreq(device.getHeartFrequency());
                device.setPressione(device.getPressione());
                device.setPressione_due(device.getPressione_due());

                list.add(device);

            }


            return list;

        }

        public List<Predizione> getPredizioni(){
            List<Predizione> predizioni = new ArrayList<>();


            //prova a fare le predizioni ottenendo una lista di device dal DB, se non riesce genera dati fittizi
            try{
                List<Device> ril = getRilevazioni();
                predizioni.add(PredizioniInfartoService.getPredizioneInfarto(ril));
                predizioni.add(PredizioniAteroService.getPredizioneAtero(ril));
                if(predizioni.get(0).getRischio() == null && predizioni.get(1).getRischio() == null)
                    throw new Exception();
            }catch (Exception e){
                predizioni = new ArrayList<>();
                Predizione pTest = new Predizione();
                pTest.setPercentualeRischio(25);
                pTest.setRischio("sotto_controllo");
                predizioni.add(pTest);
                predizioni.add(pTest);
            }

            return predizioni;
        }



}
