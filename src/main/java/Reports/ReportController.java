/*package Reports;

import io.vertx.core.json.JsonArray;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

@Path("/Report")
public class ReportController {

    @Inject
    ReportServiceImpl reportService;

    @POST
    @Path("/getJsonMeasurement")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTheMeasurement(@FormParam("date") String time) {
        try {
            List<Measurement> measurementsList = reportService.getMeasurements(time);

            JsonArray array = new JsonArray();

            for(Measurement measurement : measurementsList) {
                array.add(measurement.getData());
            }

            return array.toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/interface")
    @Produces(MediaType.TEXT_HTML)
    public String printReportPage() throws IOException {
        String path = "src/main/resources/META-INF/resources/Report.html";

        return Files.readString(Paths.get(path));
    }

    @GET
    @Path("/ReportScript")
    @Produces(MediaType.TEXT_PLAIN)
    public String getScript() throws IOException {
        String path = "src/main/resources/META-INF/resources/Report.js";

        return Files.readString(Paths.get(path));
    }
}*/