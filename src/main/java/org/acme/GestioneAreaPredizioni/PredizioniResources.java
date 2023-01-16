package org.acme.GestioneAreaPredizioni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.Device;
import org.acme.GestioneAreaPredizioni.MachineLearning.PredizioniAteroService;
import org.acme.GestioneAreaPredizioni.MachineLearning.PredizioniInfartoService;
import org.acme.GestioneAreaPredizioni.PublisherSubscriber.Predizione;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

import java.util.List;


@Path("/AreaPredizioni")
public class PredizioniResources {

    @Inject
    PredizioniInfartoService serviceInfarto;
    PredizioniAteroService serviceAtero;
    @Channel("my-data-stream-AP")
    Publisher<List<String>> data;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public String PredizioneInfarto(List<Device> rilevazione) {
        return null;//serviceInfarto.getPredizioneInfarto(rilevazione).toString();
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String PredizioneAtero(List<Device> rilevazione) {
        return null;//serviceAtero.getPredizioneAtero(rilevazione).toString();
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
        public Publisher<List<String>> stream() {return data;
    }

    }



