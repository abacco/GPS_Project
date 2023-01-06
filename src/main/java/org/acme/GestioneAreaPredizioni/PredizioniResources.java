package org.acme.GestioneAreaPredizioni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/AreaPredizioni")
public class PredizioniResources {
    @Inject
    PredizioniInfartoService serviceInfarto;
    PredizioniAteroService serviceAtero;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public double PredizioneInfarto(Rilevazione rilevazione) {
        return serviceInfarto.getPredizioneInfarto(rilevazione);
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public double PredizioneAtero(Rilevazione rilevazione) {
        return serviceAtero.getPredizioneAtero(rilevazione);
    }




}