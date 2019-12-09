import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

public class Main {
    public static void main(String[] args){
        MongoDAO dao = new MongoDAO();
        dao.connect();
        dao.setDatabase("CS260");
        dao.setCollection("Rules");
        dao.findAll();
        List<String> collection = dao.processDocuments();
        System.out.println(collection);
        ArrayList<String> leftResults = new ArrayList<>();
        ArrayList<String> rightResults = new ArrayList<>();
        ArrayList<Rule> ruleList = new ArrayList<>();
        for (String str : collection) {
            int rIndex = str.indexOf("r_rhs=");
            int lastrIndex = str.indexOf(']', rIndex);
            int lIndex = str.indexOf("r_lhs=");
            int lastlIndex = str.indexOf(']', lIndex);
            String lResult = str.substring(lIndex + 6, lastlIndex + 1);
            String rResult = str.substring(rIndex + 6, lastrIndex + 1);
            ArrayList<String> leftstringparser = new ArrayList<>();
            ArrayList<String> rightstringparser = new ArrayList<>();
            for(int i = 0; i < lResult.length(); i++){
                if(lResult.charAt(i) < 91 && lResult.charAt(i) > 64){
                    leftstringparser.add(lResult.charAt(i) + "");
                }
            }
            for(int i = 0; i < rResult.length(); i++){
                if(rResult.charAt(i) < 91 && rResult.charAt(i) > 64){
                    rightstringparser.add(rResult.charAt(i) + "");
                }
            }
            leftResults.add(lResult);
            rightResults.add(rResult);


            Rule rule = new Rule(leftstringparser, rightstringparser);
            ruleList.add(rule);


        }
        System.out.println(ruleList.toString());



        dao.disconnect();

    }
}
