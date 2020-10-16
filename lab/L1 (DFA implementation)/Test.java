//Testing class

public class Test {

    public static void main(String[] args) {
        
        //Generic automata
        Automata automata;
        //Test String
        String str;

        //Es1_1
        automata = new Es1_1();
        str = "1000214";
        System.out.println("**Es1_1 automata: recognise strings without 3 consecutive zeros**");
        System.out.println("Recognising \"" + str +"\" with Es1_1 automata: " + automata.test(str));
    }
    
}
