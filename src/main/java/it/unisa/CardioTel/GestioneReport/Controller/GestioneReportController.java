package it.unisa.CardioTel.GestioneReport.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import it.unisa.CardioTel.GestioneReport.Service.GestioneReportServiceImpl;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.StreamingOutput;
import java.nio.charset.StandardCharsets;

@Path("/Report")
@Timed(name = "time", unit = MetricUnits.MILLISECONDS)
public class GestioneReportController {

   @Inject
   GestioneReportServiceImpl gestioneReportService;

        private void generatePdf(ArrayList<String> data,
                                 ByteArrayOutputStream baos) {
            try {
                PdfWriter writer = new PdfWriter(baos);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                for (String s: data) {
                    byte[] b = s.getBytes();

                    String str = new String(b, StandardCharsets.UTF_8);

                    //ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    document.add(new Paragraph(str));
                    document.add(new Paragraph("\n"));
                }
                document.close();

            } catch (Exception e) {
            }
        }

    @GET
    @Produces("application/pdf")
    public StreamingOutput getReport(@QueryParam("daterange")
                                         String periodOfTime) throws IOException {
        ArrayList<String> measurementList = new ArrayList<>();
        try {
            measurementList = gestioneReportService.getMeasurements(periodOfTime);
        } catch (Exception e){  //se fallisce a fare lo split o a connettersi
            String d = "Misurazioni non disponibili";
            measurementList.add(d);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        generatePdf(measurementList, baos);
        byte[] pdf = baos.toByteArray();
        return output -> output.write(pdf);
        //return output -> { generatePdf(measurementList.toString(), new ByteArrayOutputStream(output)); };
    }
    @POST
    @Path("/getReports")
    @Produces(MediaType.APPLICATION_JSON)
    public String getReports(@FormParam("daterange") String periodOfTime) {
        ArrayList<String> measurementList = new ArrayList<>();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
            measurementList = gestioneReportService.getMeasurements(periodOfTime);
            //transforma in json l'array
            return ow.writeValueAsString(measurementList);
        } catch (Exception e){  //se fallisce a fare lo split o a connettersi
            e.printStackTrace();
        }

        return measurementList.toString();

    }
}



