package GestioneChatBot.Service;

import java.util.List;

public interface GestioneChatBotService {
    List<Solution> selezionaProblema(String Problema) throws SolutionsNotFound;
}
