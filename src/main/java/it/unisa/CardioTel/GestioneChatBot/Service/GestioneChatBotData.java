package it.unisa.CardioTel.GestioneChatBot.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class GestioneChatBotData {
    @Inject
    MongoClient mongoClient;
    private static final String DBname = "soluzioni";

    private MongoCollection getCollection() {
       MongoCollection mc = null;      //aggiunto controllo per la connessione al DB per la pipeline
        try{
            mc= mongoClient.getDatabase(DBname).getCollection(DBname);
            if(mc.countDocuments()<=0){
                Document documentI = addProblema("Infarto","soluzioneInfarto");
                Document documentF = addProblema("Febbre","soluzioneFebbre");
                Document documentP = addProblema("Pressione Alta","soluzionePressioneAlta");
                Document documentp = addProblema("Pressione Bassa","soluzionePressioneBassa");
                Document documentT = addProblema("Tachicardia","soluzioneTachicardia");

                List<Document> list = Collections.EMPTY_LIST;
                Collections.addAll(list = new ArrayList<Document>(),documentI,documentF,documentP,documentp,documentT);

                mc.insertMany(list);

            }
        }catch (Exception e){}
        return mc;

    }

    public Document addProblema(String problema, String soluzione) {
        return new Document()
                .append("problem",problema )
                .append("solution",soluzione);
    }

    public List<Solution> getSolutions(String problem) {
        List<Solution> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while(cursor.hasNext()) {
                Document document = cursor.next();

                if(document.getString("problem").equals(problem)) {
                    Solution solution = new Solution();

                    solution.setProblem(document.getString("problem"));
                    solution.setSolution(document.getString("solution"));

                    list.add(solution);
                }
            }

        } finally {
            cursor.close();
        }

        return list;
    }
}
