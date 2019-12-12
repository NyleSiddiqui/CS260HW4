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
        int x = 1;
        for (String str : collection) {
            int rIndex = str.indexOf("r_rhs=");
            int lastrIndex = str.indexOf(']', rIndex);
            int lIndex = str.indexOf("r_lhs=");
            int lastlIndex = str.indexOf(']', lIndex);
            String lResult = str.substring(lIndex + 6, lastlIndex+ 1);
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


            Rule rule = new Rule(x, leftstringparser, rightstringparser);
            x++;
            ruleList.add(rule);


        }
        for(int q = 0; q < ruleList.size(); q++) {
            System.out.println(ruleList.get(q).toString());
        }
        ArrayList<Rule> newrulelist = new ArrayList<>();
        for (int i = 0; i < ruleList.size(); i++){
            Rule rule = ruleList.get(i); //Initial rule
            for(int j = i + 1; j < ruleList.size(); j++){
                Rule newrule = ruleList.get(j); // Rule ahead of initial rule
                if (rule.rhs.equals(newrule.lhs)) {
                    Rule newerrule = new Rule(x , rule.lhs, newrule.rhs); //Create new implied rule
                    x++;
                    newrulelist.add(newerrule);
                }
                else {
                    for(int k = 0; k < rule.rhs.size(); k++){
                        if(rule.rhs.get(k).equals(newrule.lhs.get(0))){
                            Rule r = new Rule(x, rule.lhs, newrule.rhs);
                            x++;
                            newrulelist.add(r);
                        }
                    }
                }
            }
        }

        for(int i = 0; i < newrulelist.size(); i++){
            Rule rule = newrulelist.get(i);
            for(int j = i + 1; j < ruleList.size(); j++){
                Rule newrule = ruleList.get(j); // Rule ahead of initial rule
                if (rule.rhs.equals(newrule.lhs)) {
                    Rule newerrule = new Rule(x , rule.lhs, newrule.rhs); //Create new implied rule
                    x++;
                    newrulelist.add(newerrule);
                }
                else {
                    for(int k = 0; k < rule.rhs.size(); k++){
                        if(rule.rhs.get(k).equals(newrule.lhs.get(0))){
                            Rule r = new Rule(x, rule.lhs, newrule.rhs);
                            x++;
                            newrulelist.add(r);
                        }
                    }
                }
            }
        }
        for(int q = 0; q < newrulelist.size(); q++) {
            System.out.println(newrulelist.get(q).toString());
        }


        dao.disconnect();

    }
}
