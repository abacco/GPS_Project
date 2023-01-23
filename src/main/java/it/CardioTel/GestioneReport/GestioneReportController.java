package it.CardioTel.GestioneReport;

import com.google.gson.Gson;
import io.vertx.core.json.JsonArray;
import org.bson.Document;

import javax.inject.Inject;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Path("/report")
public class GestioneReportController  {

   @Inject
    GestioneReportServiceImpl gestioneReportService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String[] getReport(@QueryParam("daterange") String periodOfTime) {
        try {
            ArrayList<Document> measurementList = gestioneReportService.getMeasurements(periodOfTime);

            JsonArray array = new JsonArray();

            for(Document measurement : measurementList) {
                array.add(measurement);
            }
            return getReportPage(array);
        } catch (Exception e) {
            return new String[]{e.getMessage()};
        }
    }

        public String[] getReportPage (JsonArray data) {
            List<String> strings = new ArrayList<String>();
            for(int i = 0 ; i < strings.size() ; i++){
                strings.add(data.getString(i));
            }
            int size = strings.size();
            String[] stringArray = strings.toArray(new String[size]);
            return stringArray;
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


