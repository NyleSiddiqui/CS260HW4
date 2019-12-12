import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public ArrayList<Rule> processDocuments(){
        ArrayList<Rule> rulelist = new ArrayList<>();
        for(Document document : documents) {
            if(document.toString() != null){
                int id = (int) document.get("r_id");
                ArrayList<String> left = (ArrayList<String>) document.get("r_lhs");
                ArrayList<String> right = (ArrayList<String>) document.get("r_rhs");
                Rule rule = new Rule(id, left, right);
                rulelist.add(rule);
            }
        }
        return rulelist;
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

}
