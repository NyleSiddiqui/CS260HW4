import java.util.ArrayList;
import java.util.Arrays;

public class Rule {
    ArrayList<String> lhs;
    ArrayList<String> rhs;
    int id;

    public Rule(int id, ArrayList<String> lhs, ArrayList<String> rhs){
        this.lhs = lhs;
        this.rhs = rhs;
        this.id = id;
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
        for(int i = 0; i < r1.rhs.size(); i++){
            for(int j = 0; j < r2.lhs.size(); j++){
                if (r1.rhs.get(i).equals(r2.lhs.get(j))){
                    counter++;
                }
            }
        }
        if(counter == r2.lhs.size()){
            return true;
        }
        return false;
    }

    public String jsonString(){
        return "{'r_id' : " + id + " , 'r_lhs' : [ '" + lhs + "' ] , 'r_rhs' : [ '" + rhs + "' ] }";
    }

}


