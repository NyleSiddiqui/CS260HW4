import java.util.Arrays;

public class Rule {
    String[] lhs;
    String[] rhs;

    public Rule(String[] lhs, String[] rhs){
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String[] getLhs(){
        return this.lhs;
    }

    public String[] getRhs(){
        return this.rhs;
    }

    /*public boolean equals(Rule rule) {
        return rule.rhs == this.lhs;
    }*/

    public String toString() {
        return Arrays.toString(this.lhs) + " " + Arrays.toString(this.rhs);
    }
}


