import java.util.ArrayList;
import java.util.Arrays;

public class Rule {
    ArrayList<String> lhs;
    ArrayList<String> rhs;

    public Rule(ArrayList<String> lhs, ArrayList<String> rhs){
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Rule(){
        
    }

    public ArrayList<String> getLhs(){
        return this.lhs;
    }

    public ArrayList<String> getRhs(){
        return this.rhs;
    }

    /*public boolean equals(Rule rule) {
        return rule.rhs == this.lhs;
    }*/

    public String toString() {
        return lhs.toString() + " " + rhs.toString();
    }

    public void setLhs(ArrayList<String> lhs) {
        this.lhs = lhs;
    }

    public void setRhs(ArrayList<String> rhs) {
        this.rhs = rhs;
    }
}


