package GestioneChatBot.Controller;

import GestioneChatBot.Service.GestioneChatBotService;
import GestioneChatBot.Service.GestioneChatBotServiceImpl;
import GestioneChatBot.Service.Solution;
import GestioneChatBot.Service.SolutionsNotFound;
import io.vertx.core.json.JsonArray;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
            return null;
        }
    }

    @GET
    @Path("/interface")
    @Produces(MediaType.TEXT_HTML)
    public String printCBPage() throws IOException {
        String path = "C:\\Users\\aless\\Documents\\GPS_Project\\src\\main\\java\\GestioneChatBot\\Controller\\ChatBot.html";

        return Files.readString(Paths.get(path));
    }

    @GET
    @Path("/ChatBotScript")
    @Produces(MediaType.TEXT_PLAIN)
    public String getScript() throws IOException {
        String path = "C:\\Users\\aless\\Documents\\GPS_Project\\src\\main\\java\\GestioneChatBot\\Controller\\ChatBotScript.js";

        return Files.readString(Paths.get(path));
    }

}
