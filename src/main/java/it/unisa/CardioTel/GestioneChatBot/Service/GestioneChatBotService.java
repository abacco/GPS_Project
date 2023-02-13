package it.unisa.CardioTel.GestioneChatBot.Service;

import java.util.List;

public interface GestioneChatBotService {
    List<Solution> selezionaProblema(String Problema) throws SolutionsNotFound;
}
