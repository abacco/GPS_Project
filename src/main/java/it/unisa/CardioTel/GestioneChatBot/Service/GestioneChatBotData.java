package it.unisa.CardioTel.GestioneChatBot.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Timed(name = "time", unit = MetricUnits.MILLISECONDS)
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
                Document documentI1 = addProblema("Infarto","Respirare profondamente");
                Document documentI2 = addProblema("Infarto","Chiamare soccorso");
                Document documentF1 = addProblema("Febbre","Bere tanti liquidi, in particolare acqua e tisane");
                Document documentF2 = addProblema("Febbre","Applicare impacchi rinfrescanti per favorire la perdita di calore dal corpo");
                Document documentF3 = addProblema("Febbre","In caso di brividi, immergersi in acqua calda");
                Document documentF4 = addProblema("Febbre","Assumerere farmaci antipiretici se necessario");
                Document documentP1 = addProblema("Pressione Alta","Rilassarsi");
                Document documentP2 = addProblema("Pressione Alta","Informare il medico curante se la situazione persiste");
                Document documentP3 = addProblema("Pressione Alta","Fumo, stress e alcolici sono fattori influenzanti");
                Document documentp1 = addProblema("Pressione Bassa","Aumentare l'apporto idrico e salino");
                Document documentp2 = addProblema("Pressione Bassa","Evitare caffeina");
                Document documentT1 = addProblema("Tachicardia","Rilassarsi");
                Document documentT2 = addProblema("Tachicardia","Una camminata a passo veloce può aiutare al rilassamento");
                Document documentT3 = addProblema("Tachicardia","Respirare profondamente");

                List<Document> list = Collections.EMPTY_LIST;
                Collections.addAll(list = new ArrayList<Document>(),documentI1,documentI2,documentF1,documentF2,documentF3,documentF4,documentP1,documentP2,documentP3,documentp1,documentp2,documentT1,documentT2,documentT3);

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
