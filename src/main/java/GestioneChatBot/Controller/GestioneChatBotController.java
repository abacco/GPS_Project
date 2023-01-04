package GestioneChatBot.Controller;

import GestioneChatBot.Service.GestioneChatBotService;
import GestioneChatBot.Service.GestioneChatBotServiceImpl;
import GestioneChatBot.Service.Solution;
import GestioneChatBot.Service.SolutionsNotFound;
import io.vertx.core.json.JsonArray;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

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
    @Produces(MediaType.TEXT_HTML)
    public String printCBPage() {

        //stub

        return null;
    }
}
