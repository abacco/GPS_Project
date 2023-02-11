package it.unisa.CardioTel.GestioneAreaPredizioni.Controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.unisa.CardioTel.GestioneAreaPredizioni.Service.PredizioniAteroService;
import it.unisa.CardioTel.GestioneAreaPredizioni.Service.PredizioniInfartoService;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

@Timed(name = "time", unit = MetricUnits.MILLISECONDS)
@Path("/AreaPredizioni")
public class PredizioniResources {

    @Inject
    PredizioniInfartoService serviceInfarto;
    PredizioniAteroService serviceAtero;
    @Channel("my-data-stream-AP")
    public Publisher<String> data;

    @GET
    @Path("stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
        public Publisher<String> stream() {return data;

    }

}



