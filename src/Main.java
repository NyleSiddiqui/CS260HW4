import javax.swing.text.Document;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;


/*
Nyle Siddiqui
CS260 Homework 4
December 14, 2019
This program reads a database of rules, and based on varied user input, generate all possible rules possible within that rule set, select varied ranges, and store the results in a separate repository.
*/


public class Main {


    //TODO Finish taking out automatic delete
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        MongoDAO dao = new MongoDAO(); //Create database connection
        dao.connect();
        dao.setDatabase("CS260");
        System.out.print("Collection? "); //Set which data set to interact with
        String answer = console.nextLine();
        dao.setCollection(answer);
        System.out.println("Would you like (A)LL rules, a specific (I)D range, or (D)ate range "); //Input first letter
        answer = console.nextLine();

        if (answer.toLowerCase().equals("a")) {
            printAll(dao);
        }

        if (answer.toLowerCase().equals("i")) {
            printIDRange(dao, console);
        }

        if (answer.toLowerCase().equals("d")) {
            printDateRange(dao, console);
        }
    }

    /*
    Prints (and sends information to a database) every possible rule generation given a set of rules.
     */

    public static void printAll(MongoDAO dao) {
        dao.findAll();
        RuleSet ruleList = dao.processDocuments();
        int initialCount = ruleList.size(); //size of original list of rules
        ruleGeneration(ruleList);
        System.out.println("UPDATED RULES LIST:");
        int finalCount = ruleList.size(); //size of new list of rules
        int count = finalCount - initialCount; //Calculates count of new rules
        printArraywCount(ruleList, count); //Calls print array method that also prints count
        resetDatabase(dao, ruleList); //Takes care of database updating
    }


    /*
    Loops through an array of rules and prints all the values.
     */
    public static void printArray(RuleSet ruleList) {
        for (int i = 0; i < ruleList.size(); i++) {
            System.out.println(ruleList.get(i).toString());
        }
    }


    /*
    Loops through an array and of rules and prints all the values, but also includes count of new rules generated where appropriate.
     */
    public static void printArraywCount(RuleSet ruleList, int count) {
        for (int i = 0; i < ruleList.size(); i++) {
            System.out.println(ruleList.get(i).toString());
        }
        System.out.println(count + " new rules generated.");
    }


    /*
    Asks for date range, finds the documents that fit that range, create an array list of Rules, prints them out, and sends the results to the database.
    */
    public static void printDateRange(MongoDAO dao, Scanner console) {
        System.out.println("Start date: ");
        String start = console.nextLine();
        System.out.println("End date: ");
        String end = console.nextLine();
        dao.dateTime("r_datetime", start, end);
        RuleSet set = dao.processDocuments();
        resetDatabase(dao, set);
        printArray(set);
    }

    /*
    Asks for ID range, finds the documents that fit that range, create an array list of Rules, prints them out, and sends the results to the database.
    */
    public static void printIDRange(MongoDAO dao, Scanner console) {
        RuleSet ruleList = new RuleSet();
        System.out.println("Start of ID range?");
        int start = console.nextInt();
        System.out.println("End of ID range?");
        int end = console.nextInt();
        for (int i = start; i <= end; i++) {
            dao.findSomeEqual("r_id", i);
            RuleSet set = dao.processDocuments();
            ruleList.add(set.get(0)); //Each search only returns one result at a time. This adds each individual result into the set
        }
        int initialCount = ruleList.size();
        ruleGeneration(ruleList);
        int finalCount = ruleList.size();
        int count = finalCount - initialCount;
        printArraywCount(ruleList, count);
        resetDatabase(dao, ruleList);
    }


    /*
    Stores repetitive commands of disconnecting original DAO connection, resetting the database and collection, and inserting the new values into the database.
    */

    public static void resetDatabase(MongoDAO dao, RuleSet set) {
        dao.disconnect(); //Breaks old dao connection
        dao.connect(); //Creates a new one in order to change database and collection
        dao.setDatabase("SIDDIQUN8701");
        dao.setCollection("RulesOutput");
        dao.deleteAll();
        for (int i = 0; i < set.size(); i++) {
            dao.insertOne(set.get(i).jsonString());
        }
    }


    /*
    This method runs through a list of rules, reads the left and right hands of the statement, and generates every possible new relational rule. It then stores all the rules, including the original rules, into an array of rules and returns it.
     */

    public static RuleSet ruleGeneration(RuleSet ruleList) {
        int x = ruleList.get(ruleList.size() - 1).id + 1; //Assigns id number by retrieving id number of last rule in the array and adding one
        Date date = new Date();
        for (int i = 0; i < ruleList.size(); i++) { //Loops through the list
            for (int j = 0; j < ruleList.size(); j++) { //Loops through the list again, effectively checking the first loop's rule with every other rule in the list
                if (i != j) { //Dont compare a rule to itself
                    if (Rule.ruleMatch(ruleList.get(i), ruleList.get(j))) {
                        Rule rule = new Rule(x, ruleList.get(i).lhs, ruleList.get(j).rhs, date);
                        x++; //Increment id number for next rule
                        ruleList.add(rule);
                    }
                }
            }
        }
        return ruleList;
    }
}