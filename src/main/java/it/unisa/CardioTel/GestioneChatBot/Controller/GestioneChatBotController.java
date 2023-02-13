package it.unisa.CardioTel.GestioneChatBot.Controller;

import it.unisa.CardioTel.GestioneChatBot.Service.GestioneChatBotServiceImpl;
import it.unisa.CardioTel.GestioneChatBot.Service.Solution;
import it.unisa.CardioTel.GestioneChatBot.Service.SolutionsNotFound;
import io.vertx.core.json.JsonArray;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Timed(name = "time", unit = MetricUnits.MILLISECONDS)
@Path("/ChatBot")
public class GestioneChatBotController {

    @Inject
    GestioneChatBotServiceImpl gestioneChatBotService;

    @POST
    @Path("/getJsonSolution")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSolutions(@FormParam("problem") String problem) {
        try {
            List<Solution> solutionList = gestioneChatBotService.selezionaProblema(problem);

            JsonArray array = new JsonArray();

            for(Solution sol : solutionList) {
                array.add(sol.getSolution());
            }

            return array.toString();
        } catch (SolutionsNotFound e) {
            return "Solution not found";   //in caso non riesce a connettersi al db restituisce una stringa e non null per evitare nullPointer
            //return null;
        }
    }

    /*
    @GET
    @Path("/interface")
    @Produces(MediaType.TEXT_HTML)
    public String printCBPage() throws IOException {

        File currentFolder = new File(System.getProperty("user.dir"));
        File srcFolder = new File(currentFolder, "src");
        String path = null;

        if (srcFolder.exists() && srcFolder.isDirectory()) {
            path = "./src/main/java/GestioneChatBot/Controller/ChatBot.html";
        } else {
            path = "../src/main/java/GestioneChatBot/Controller/ChatBot.html";
        }

        return Files.readString(Paths.get(path));
    }


    @GET
    @Path("/ChatBotScript")
    @Produces(MediaType.TEXT_PLAIN)
    public String getScript() throws IOException {

        File currentFolder = new File(System.getProperty("user.dir"));
        File srcFolder = new File(currentFolder, "src");
        String path = null;

        if (srcFolder.exists() && srcFolder.isDirectory()) {
            path = "./src/main/java/GestioneChatBot/Controller/ChatBotScript.js";
        } else {
            path = "../src/main/java/GestioneChatBot/Controller/ChatBotScript.js";
        }

        return Files.readString(Paths.get(path));
    }
     */
}