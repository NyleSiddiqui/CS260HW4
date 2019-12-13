import javax.swing.text.Document;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        MongoDAO dao = new MongoDAO();
        dao.connect();
        dao.setDatabase("CS260");
        System.out.print("Collection? ");
        String answer = console.nextLine();
        dao.setCollection(answer);
        System.out.println("Would you like (A)LL rules, a specific (I)D range, or (D)ate range ");
        answer = console.nextLine();

        if(answer.toLowerCase().equals("a")){
            printAll(dao);
        }

        if(answer.toLowerCase().equals("i")){
            printIDRange(dao, console);
        }

        if(answer.toLowerCase().equals("d")){
            printDateRange(dao);
        }
    }

    public static void printIDRange(MongoDAO dao, Scanner console){
        RuleSet ruleList = new RuleSet();
        System.out.println("Start of ID range?");
        int start = console.nextInt();
        System.out.println("End of ID range?");
        int end = console.nextInt();
        for(int i = start; i <= end; i++){
            dao.findSomeEqual("r_id", i);
            RuleSet set = dao.processDocuments();
            ruleList.add(set.get(0));
        }
        dao.disconnect();
        ruleGeneration(ruleList);
        printArray(ruleList);
        resetDatabase(dao, ruleList);
    }


    public static void printDateRange(MongoDAO dao){
        //System.out.println("Start date: ");
        //String start = console.nextLine();
        String start = "2019-04-05T02:00:00";
        //System.out.println("End date: ");
        //String end = console.nextLine();
        String end = "2019-04-05T02:08:00";
        dao.dateTime("r_datetime", start, end);
        RuleSet set = dao.processDocuments();
        resetDatabase(dao, set);
        System.out.println(set.toString());
    }

    public static void resetDatabase(MongoDAO dao, RuleSet set){
        dao.disconnect();
        dao.connect();
        dao.setDatabase("SIDDIQUN8701");
        dao.setCollection("RulesOutput");
        dao.deleteAll();
        for(int i = 0; i < set.size(); i++) {
            dao.insertOne(set.get(i).jsonString());
        }
    }


    public static void printAll(MongoDAO dao){
        dao.findAll();
        RuleSet ruleList = dao.processDocuments();
        System.out.println("INPUTTED RULES LIST:");
        printArray(ruleList);
        System.out.println();
        ruleGeneration(ruleList);
        System.out.println("UPDATED RULES LIST:");
        printArray(ruleList);
        resetDatabase(dao, ruleList);
    }

    public static void printArray(RuleSet ruleList) {
        for (int i = 0; i < ruleList.size(); i++) {
            System.out.println(ruleList.get(i).toString());
        }
    }

    public static int storeCount(int count){
        return count;
    }

    public static RuleSet ruleGeneration(RuleSet ruleList) {
        int x = ruleList.get(ruleList.size() - 1).id + 1;
        Date date = new Date();
        int count = 0;
        for (int i = 0; i < ruleList.size(); i++) {
            for (int j = 0; j < ruleList.size(); j++) {
                if(i != j){ //Dont compare a rule to itself
                    if (Rule.ruleMatch(ruleList.get(i), ruleList.get(j))) {
                        Rule rule = new Rule(x, ruleList.get(i).lhs, ruleList.get(j).rhs, date);
                        x++;
                        ruleList.add(rule);
                    }
                }
            }
        }
        storeCount(count);
        return ruleList;
    }
}