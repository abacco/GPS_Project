package it.CardioTel.GestioneReport;

import io.vertx.core.json.JsonArray;
import org.bson.Document;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Path("/Report")
public class GestioneReportController  {

   @Inject
    GestioneReportServiceImpl gestioneReportService;

    @GET
    public String getReport(@QueryParam("daterange") String periodOfTime) {
        try {
            ArrayList<Document> measurementList = gestioneReportService.getMeasurements(periodOfTime);

            JsonArray array = new JsonArray();

            for(Document measurement : measurementList) {
                array.add(measurement);
            }

            return array.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GET
    @Path("/interface")
    @Produces(MediaType.TEXT_HTML)
    public String printReportPage() throws IOException {
        String path = "../src/main/resources/META-INF/resources/Report.html";

        return Files.readString(Paths.get(path));
    }

    @GET
    @Path("/ReportScript")
    @Produces(MediaType.TEXT_PLAIN)
    public String getScript() throws IOException {
        String path = "../src/main/resources/META-INF/resources/Report.html";

        return Files.readString(Paths.get(path));
    }

}


