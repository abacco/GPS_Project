package org.acme.GestioneAreaPredizioni.PublisherSubscriber;

import io.reactivex.Flowable;
import org.acme.DBQueries;
import org.acme.Device;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class PredictionGenerator {


    @Outgoing("AreaPredizioni")
    public Flowable<List<Device>> generate() {
        return Flowable.interval(1,  TimeUnit.MINUTES)
                .onBackpressureDrop()
                .map(t -> {
                    DBQueries query = new DBQueries();
                    return query.getRilevazioni();
                });
    }

}
