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
        System.out.println("Would you like (A)LL rules, a specific (I)D range, or (D)ate range  ");
        answer = console.nextLine();
        if(answer.toLowerCase().equals("a")){
            System.out.println("INPUTTED RULES:");
            printArray(ruleList);
            System.out.println();
            ruleGeneration(ruleList);
            System.out.println("UPDATED RULES:");
            printArray(ruleList);
            dao.disconnect();
        }


    }


    public static void printArray(ArrayList<Rule> ruleList) {
        for (int i = 0; i < ruleList.size(); i++) {
            System.out.println(ruleList.get(i).toString());
        }
    }

    public static ArrayList<Rule> ruleGeneration(ArrayList<Rule> ruleList) {
        int x = ruleList.get(ruleList.size() - 1).id + 1;
        for (int i = 0; i < ruleList.size(); i++) {
            for (int j = 0; j < ruleList.size(); j++) {
                if(i != j){ //Dont compare a rule to itself
                    if (Rule.ruleMatch(ruleList.get(i), ruleList.get(j))) {
                        Rule rule = new Rule(x, ruleList.get(i).lhs, ruleList.get(j).rhs);
                        x++;
                        ruleList.add(rule);
                    }
                }
            }
        }
        return ruleList;
    }
}