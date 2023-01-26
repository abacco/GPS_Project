/*package Reports;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ReportData {
    @Inject
    MongoClient mongoClient;
    private static final String DBname = "soluzioni";

    private MongoCollection getCollection() {
        return mongoClient.getDatabase(DBname).getCollection(DBname);
    }

    public List<Measurement> getMeasurement(String time) throws ParseException {
        List<Measurement> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();
        try{
        String[] split = time.split(" ");
        String startDate = split[0];
        String endDate = split[2];

        Date [] periodOfTime = new Date[2];
        periodOfTime[0] = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        periodOfTime[1] = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);

        try {
            while(cursor.hasNext()) {
                Document document = cursor.next();
                String tmp = document.getString("time");
                Date temp = new Date();
                temp = new SimpleDateFormat("dd/MM/yyyy").parse(tmp);
                if(temp.after(periodOfTime[0]) && temp.before(periodOfTime[1])) {
                    Measurement measurement= new Measurement();
                    measurement.setData(document.getString("data"));
                    measurement.setTime(document.getString("time"));

                    list.add(measurement);
                }
            }

        } finally {
            cursor.close();
        }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
*/