package it.CardioTel.GestioneReport;

import com.google.gson.Gson;

import io.vertx.core.json.JsonArray;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.inject.Inject;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE;

@Path("/Report")
public class GestioneReportController  {

   @Inject
    GestioneReportServiceImpl gestioneReportService;

        private void generatePdf(ArrayList<String> data, ByteArrayOutputStream baos) {
            try {
                PdfWriter writer = new PdfWriter(baos);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);


                for(String s: data) {
                    byte[] b = s.getBytes();

                    String str = new String(b, StandardCharsets.UTF_8);

                    //ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    document.add(new Paragraph(str));
                }

                document.close();

            } catch (Exception e) {
            }
        }

    @GET
    @Produces("application/pdf")
    public StreamingOutput getReport(@QueryParam("daterange") String periodOfTime) throws IOException {
            ArrayList<String> measurementList = gestioneReportService.getMeasurements(periodOfTime);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            generatePdf(measurementList, baos);
            byte[] pdf = baos.toByteArray();
            return output -> {output.write(pdf);};
            //return output -> { generatePdf(measurementList.toString(), new ByteArrayOutputStream(output)); };
    }
}
/*
    private byte[] generatePdfBytes(List<String> strings) throws IOException {
        // create a new PDF document
        PDDocument document = new PDDocument();
        // create a new page
        PDPage page = new PDPage();
        // add the page to the document
        document.addPage(page);
        // create a new font
        PDFont font = PDType1Font.HELVETICA;
        // create a new font size
        float fontSize = 12;
        // create a new content stream
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        // set the font and font size
        contentStream.setFont(font, fontSize);
        // set the position for the text
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 700);
        // add the text to the content stream
        for(String text:strings){
            contentStream.showText(text+"\n");
            contentStream.newLine();
        }
        contentStream.endText();
       // contentStream.newLineAtOffset(0, -20);
        // close the content stream
        contentStream.close();
        // save the document to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
        document.close();
        // return the byte array
        return byteArrayOutputStream.toByteArray();
    }

    private String getReportPage(ArrayList<Document> measurementList) {
        StringBuilder sb = new StringBuilder();
        for (Document measurement : measurementList) {
            sb.append("Measurement ID: ").append(measurement.get("_id")).append("\n");
            sb.append("Timestamp: ").append(measurement.get("timestamp")).append("\n");
            sb.append("Value: ").append(measurement.get("value")).append("\n");
            sb.append("Unit: ").append(measurement.get("unit")).append("\n\n");
        }
        return sb.toString();
    }*/



