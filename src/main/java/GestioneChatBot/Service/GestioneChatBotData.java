package GestioneChatBot.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GestioneChatBotData {
    @Inject
    MongoClient mongoClient;
    private static final String DBname = "soluzioni";

    private MongoCollection getCollection() {
        MongoCollection mc = mongoClient.getDatabase(DBname).getCollection(DBname);
        if(mc.countDocuments()<=0){
            Document document = new Document()
                    .append("Febbre", "Febbre")
                    .append("Pressione Bassa", "Pressione Bassa")
                    .append("Pressione Alta", "Pressione Alta")
                    .append("Tachicardia", "Tachicardia")
                    .append("Infarto", "Infarto");
            mc.insertOne(document);
        }
        return mc;

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
