package org.acme.GestioneAreaPredizioni.PublisherSubscriber;

import io.reactivex.Flowable;
import org.acme.DBQueries;
import org.acme.Device;
import org.acme.GestioneAreaPredizioni.MachineLearning.PredizioniAteroService;
import org.acme.GestioneAreaPredizioni.MachineLearning.PredizioniInfartoService;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class PredictionGenerator {

    @Outgoing("AreaPredizioni-temp")
    public Flowable<String> generate() {
        return Flowable.interval(5,  TimeUnit.SECONDS)
                .onBackpressureDrop()
                .map(t -> {

                    DBQueries query = new DBQueries();

                    List<Device> ril =  query.getRilevazioni();

                    List<String> pr = new ArrayList<>();
                    /*
                    pr.add(PredizioniInfartoService.getPredizioneInfarto(ril).toString());
                    pr.add(PredizioniAteroService.getPredizioneAtero(ril).toString());

                    return pr.toString();
                    */
                    System.out.println("\n\n\n\n");
                    System.out.println(ril.toString());
                    return ril.toString();
                });
    }

}
