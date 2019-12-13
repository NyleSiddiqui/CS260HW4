import java.util.ArrayList;
import java.util.*;
import java.util.Arrays;

public class Rule {
    ArrayList<String> lhs;
    ArrayList<String> rhs;
    int id;
    Date date;

    public Rule(int id, ArrayList<String> lhs, ArrayList<String> rhs, Date date){
        this.lhs = lhs;
        this.rhs = rhs;
        this.id = id;
        this.date = date;


    }

    public Rule(){
        
    }

    public boolean equals(Rule rule) {
        return rule.lhs == this.lhs && rule.rhs == this.rhs;
    }

    public String toString() {
        String output = id + " " + "IF ";
        if(lhs.size() > 1){
            for(int i = 0; i < lhs.size(); i++){
                output += lhs.get(i);
                if(i + 1 < lhs.size()){
                    output += " AND ";
                }
            }
        } else {
            output += lhs.get(0);
        }


        output += " THEN ";

        if(rhs.size() > 1){
            for(int i = 0; i < rhs.size(); i++){
                output += rhs.get(i);
                if(i + 1 < rhs.size()){
                    output += " AND ";
                }
            }
        } else {
            output += rhs.get(0);
        }
        return output;
    }
    public static boolean ruleMatch(Rule r1, Rule r2){
        int counter = 0;
        for(int i = 0; i < r1.rhs.size(); i++){ //Loops through every individual letter on the right side
            for(int j = 0; j < r2.lhs.size(); j++){ //Loops through every individual letter on the left side
                if (r1.rhs.get(i).equals(r2.lhs.get(j))){ //If the letter on the right hand side also equals the letter on the left hand, increase the counter
                    counter++;
                }
            }
        }
        if(counter == r2.lhs.size()){ //If the count of the size of the resulting possible pair is the same size as the right hand pair that was being tested, and all the letters were already tested, it is a new rule. Duplicates prevented when ran through multiple times in the ruleGeneration method
            return true;
        }
        return false; //Prevent rule form being created and added, incorrect
    }

    public String jsonString(){
        return "{'r_id' : " + id + " , 'r_lhs' : [ '" + lhs + "' ] , 'r_rhs' : [ '" + rhs + "' ], 'r_datetime' : '" + date + "' }";
    }

}


