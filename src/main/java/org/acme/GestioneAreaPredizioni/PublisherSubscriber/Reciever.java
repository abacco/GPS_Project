package org.acme.GestioneAreaPredizioni.PublisherSubscriber;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.acme.Device;
import org.acme.GestioneAreaPredizioni.MachineLearning.PredizioniAteroService;
import org.acme.GestioneAreaPredizioni.MachineLearning.PredizioniInfartoService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class Reciever {

    @Incoming("AreaPredizioni")
    @Outgoing("my-data-stream-AP")
    @Broadcast
    public List<Predizione> process(List<Device> data) {
        List<Device> ril = data;

        List<Predizione> pr = new ArrayList<>();

        pr.add(PredizioniInfartoService.getPredizioneInfarto(ril));
        pr.add(PredizioniAteroService.getPredizioneAtero(ril));

        return pr;
    }


    public String process(byte[] data) {
        String d = new String(data);
        System.out.println("Receiving readings: " + d);
        return d;
    }
}
