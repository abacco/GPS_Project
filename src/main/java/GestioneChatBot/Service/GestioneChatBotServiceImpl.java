package GestioneChatBot.Service;

import javax.inject.Inject;
import java.util.List;

public class GestioneChatBotServiceImpl implements GestioneChatBotService{

    @Inject
    GestioneChatBotData gestioneChatBotData;

    @Override
    public List<Solution> selezionaProblema(String Problema) throws SolutionsNotFound {
        List<Solution> list = gestioneChatBotData.getSolutions(Problema);

        if(list.size() <= 0) {
            throw new SolutionsNotFound("Soluzioni trovate per il problema " + Problema + " da parte del CB: " + list.size() + " ");
        }

        return list;
    }
}
