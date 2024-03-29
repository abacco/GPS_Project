package it.unisa.CardioTel.GestioneDevice.Controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

@Path("/devices")
@Timed(name = "time", unit = MetricUnits.MILLISECONDS)
public class DeviceResource {

    @Inject
    @Channel("my-data-stream")
    Publisher<String> data;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @GET
    @Path("stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> stream() {
        return data;
    }
}