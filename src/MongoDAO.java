import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.client.ListDatabasesIterable;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
public class MongoDAO {
    MongoClient client;
    MongoCredential credential;
    MongoCollection<Document> collection;
    MongoDatabase database;
    List<Document> documents;


    public void connect() {
        // set up credentials - user, authenticationDB, pw (all case-sensitive)
        credential = MongoCredential.createCredential("SIDDIQUN8701","CS260", getPW().toCharArray());
        // make a client connection to the remote MongoDB database instance (host, port)  - with credentials
        client = new MongoClient(new ServerAddress("10.35.195.203", 27017), Arrays.asList(credential));
        // turn off console logging (if desired)
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
    }

    public void setDatabase(String str){
        // set the database
        database = client.getDatabase(str);
    }

    public void setCollection(String str){
        // set the collection
        collection = database.getCollection(str);
    }

    public void findAll(){
        // find all documents in the collection and return them
        documents = collection.find().into(
                new ArrayList<>());
    }

    public RuleSet processDocuments(){
        RuleSet set = new RuleSet();
        for(Document document : documents) {
            if(document.toString() != null){
                int id = (int) document.get("r_id");
                Date date = (Date) document.get("r_datetime");
                ArrayList<String> left = (ArrayList<String>) document.get("r_lhs");
                ArrayList<String> right = (ArrayList<String>) document.get("r_rhs");
                Rule rule = new Rule(id, left, right, date);
                set.add(rule);
            }
        }
        return set;
    }

    public void disconnect(){
        // clean up and close connection
        client.close();
    }
    private static String getPW() {
        return new String("CNH2SMO7");
    }

    public void insertOne (String jsonString){
        Document doc = Document.parse(jsonString);
        collection.insertOne(doc);
    }

    public void deleteEqualTest(String field, Object value){
        collection.deleteMany(eq(field, value));
    }

    public void findSomeEqual(String field, Object value){
        documents = collection.find(eq(field, value)).into(new ArrayList<>());
    }

    public void deleteAll(){
        collection.deleteMany(new Document());
    }

    public void dateTime(String field, String start, String end){
        start += ".000Z";
        Instant instant = Instant.parse(start);
        instant = instant.plus(5, ChronoUnit.HOURS);
        Date startDate = Date.from(instant);

        end += ".000Z";
        instant = Instant.parse(end);
        instant = instant.plus(5, ChronoUnit.HOURS);
        Date endDate = Date.from(instant);

        BasicDBObject getQuery = new BasicDBObject();
        getQuery.put(field, new BasicDBObject("$gte", startDate).append("$lte", endDate));
        documents = (List<Document>) collection.find(getQuery).into(new ArrayList<Document>());
    }

}
