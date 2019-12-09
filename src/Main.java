import java.util.Arrays;

public class Main {
    public static void main(String[] args){



        String[] arr1 = {"A"};
        String[] arr2 = {"B"};
        String[] arr3 = {"B"};
        String [] arr4 = {"C"};
        Rule rule = new Rule(arr1, arr2);
        Rule rule2 = new Rule(arr3, arr4);
        if(Arrays.equals(rule.rhs, rule2.lhs)){
            System.out.println("yes");
            Rule rule3 = new Rule(rule.lhs, rule2.rhs);
            System.out.println(rule3.toString());
        }

    }
}
