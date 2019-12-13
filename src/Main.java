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
        System.out.println("Would you like (A)LL rules, a specific (I)D range, or (D)ate range  ");
        answer = console.nextLine();
        if(answer.toLowerCase().equals("a")){
            dao.findAll();
            RuleSet ruleList = dao.processDocuments();
            printAll(ruleList, dao);
        }
        if(answer.toLowerCase().equals("i")){
         printIDRange(dao, console);
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
        for(int i = start; i < end; i++){
            dao.insertOne(ruleList.get(i).jsonString());
        }
    }


    public static void printAll(RuleSet ruleList, MongoDAO dao){
        System.out.println("INPUTTED RULES LIST:");
        printArray(ruleList);
        System.out.println();
        ruleGeneration(ruleList);
        System.out.println("UPDATED RULES LIST:");
        printArray(ruleList);
        dao.disconnect();
    }

    public static void printArray(RuleSet ruleList) {
        for (int i = 0; i < ruleList.size(); i++) {
            System.out.println(ruleList.get(i).toString());
        }
    }

    public static RuleSet ruleGeneration(RuleSet ruleList) {
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