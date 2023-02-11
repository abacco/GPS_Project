package it.unisa.CardioTel.GestioneAreaPredizioni.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Timed(name = "time", unit = MetricUnits.MILLISECONDS)
@ApplicationScoped
public class Reciever {

    @Incoming("AreaPredizioni")
    @Outgoing("my-data-stream-AP")
    @Broadcast
    public String process(byte[] data) {

        //deserializza la lista da byte[] e la passa come stringa
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> list = new ArrayList<>();
            list = objectMapper.readValue(data, List.class);


            //converts in json la lista
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(list);
            return json;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
