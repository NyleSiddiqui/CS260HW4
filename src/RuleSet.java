import java.util.ArrayList;

public class RuleSet {
    ArrayList<Rule> arr;

    public RuleSet(){
        arr = new ArrayList<>();
    }


    public Rule get(int i){
        return arr.get(i);
    }

    public int size(){
        return arr.size();
    }

    public void add(Rule rule) {
        if(!arr.contains(rule)){
            arr.add(rule);
        }
    }
}
