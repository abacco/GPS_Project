package it.unisa.CardioTel.GestioneChatBot.Service;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
@ApplicationScoped
@Timed(name = "time", unit = MetricUnits.MILLISECONDS)
public class GestioneChatBotServiceImpl implements GestioneChatBotService{

    @Inject
    GestioneChatBotData gestioneChatBotData;

    @Override
    public List<Solution> selezionaProblema(String Problema) throws SolutionsNotFound {
        List<Solution> list = gestioneChatBotData.getSolutions(Problema);
        System.out.println(list);

        if(list.size() <= 0) {
            throw new SolutionsNotFound("Soluzioni trovate per il problema " + Problema + " da parte del CB: " + list.size() + " ");
        }
        return list;
    }
}
