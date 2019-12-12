import java.util.*;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        MongoDAO dao = new MongoDAO();
        dao.connect();
        dao.setDatabase("CS260");
        System.out.print("Collection? ");
        String answer = console.nextLine();
        dao.setCollection(answer);
        dao.findAll();
        ArrayList<Rule> ruleList = dao.processDocuments();
        System.out.println("INPUTTED RULES:");
        printArray(ruleList);
        System.out.println();
        ArrayList<Rule> testlist = new ArrayList<>();
        ruleGeneration(ruleList, testlist);
        newRuleGeneration(ruleList, testlist);
        System.out.println("UPDATED RULES:");
        printArray(ruleList);
        printArray(testlist);
        dao.disconnect();

    }


    public static void printArray(ArrayList<Rule> ruleList) {
        for (int i = 0; i < ruleList.size(); i++) {
            System.out.println(ruleList.get(i).toString());
        }
    }

    public static ArrayList<Rule> ruleGeneration(ArrayList<Rule> ruleList, ArrayList<Rule> newRuleList) {
        int x = ruleList.get(ruleList.size() - 1).id + 1;
        for (int i = 0; i < ruleList.size(); i++) {
            Rule rule = ruleList.get(i); //Initial rule
            for (int j = i + 1; j < ruleList.size(); j++) {
                Rule rule2 = ruleList.get(j); // Rule ahead of initial rule
                if (rule.rhs.equals(rule2.lhs)) {
                    Rule newRule = new Rule(x, rule.lhs, rule2.rhs); //Create new implied rule
                    x++;
                    newRuleList.add(newRule);
                } else {
                    for (int k = 0; k < rule.rhs.size(); k++) {
                        if (rule.rhs.get(k).equals(rule2.lhs.get(0))) {
                            Rule r = new Rule(x, rule.lhs, rule2.rhs);
                            x++;
                            newRuleList.add(r);
                        }
                    }
                }
            }
        }
        return newRuleList;
    }

    public static ArrayList<Rule> newRuleGeneration(ArrayList<Rule> ruleList, ArrayList<Rule> newRuleList) {
        int x = newRuleList.get(newRuleList.size() - 1).id + 1;
        for (int i = 0; i < newRuleList.size(); i++) {
            Rule rule = newRuleList.get(i);
            for (int j = i + 1; j < ruleList.size(); j++) {
                Rule newrule = ruleList.get(j); // Rule ahead of initial rule
                if (rule.rhs.equals(newrule.lhs)) {
                    Rule newerrule = new Rule(x, rule.lhs, newrule.rhs); //Create new implied rule
                    x++;
                    newRuleList.add(newerrule);

                } else {
                    for (int k = 0; k < rule.rhs.size(); k++) {
                        if (rule.rhs.get(k).equals(newrule.lhs.get(0))) {
                            Rule r = new Rule(x, rule.lhs, newrule.rhs);
                            x++;
                            newRuleList.add(r);
                        }
                    }
                }
            }
        }
        return newRuleList;
    }

}




                  /*  for (int g = 0; g < newrulelist.size(); g++){
        Rule test2 = newrulelist.get(g + 1);
        if(newerrule.equals(test2)){
        newrulelist.remove(test2);
        }
        }
//ArrayList<String> leftResults = new ArrayList<>();
// ArrayList<String> rightResults = new ArrayList<>();
//ArrayList<Rule> ruleList = new ArrayList<>();
/*for (String str : collection) {
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


            Rule rule = new Rule(x, leftstringparser, rightstringparser);
            x++;
            ruleList.add(rule);


        }*/
 /*ArrayList<Rule> newrulelist = new ArrayList<>();
        for (int i = 0; i < ruleList.size(); i++){
            Rule rule = ruleList.get(i); //Initial rule
            for(int j = i + 1; j < ruleList.size(); j++){
                Rule newrule = ruleList.get(j); // Rule ahead of initial rule
                if (rule.rhs.equals(newrule.lhs)) {
                    Rule newerrule = new Rule(x, rule.lhs, newrule.rhs); //Create new implied rule
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
*/
 /*for(int i = 0; i < newrulelist.size(); i++){
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
 */