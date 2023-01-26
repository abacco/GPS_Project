/*package Report;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import org.acme.Device;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Report")
public class ReportResource {

    @Inject
    MongoClient mongoClient;

    private static final String DBname = "Misurazioni";

    private MongoCollection getCollection() {
        return mongoClient.getDatabase(DBname).getCollection(DBname);
    }
    @Inject
    @Channel("my-data-stream")
    Publisher<String> data;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        BasicDBObject fields = new BasicDBObject().append("name", 1); // SELECT name
        BasicDBObject query = new BasicDBObject().append("name", "ESP8266-01"); // WHERE name = "ESP8266-01"
        DBCursor results = (DBCursor) getCollection().find((ClientSession) query, fields); // FROM yourCollection
        return results.toString();
    }

    @GET
    @Path("stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> stream() {
        return data;
    }
}*/