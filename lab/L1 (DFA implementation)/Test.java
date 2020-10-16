//Testing class

public class Test {

    public static void main(String[] args) {
        
        //Generic automata
        Automata automata;
        //Test String
        String str;

        //Es1_1
        automata = new Es1_1();
        str = "1001";
        System.out.println("**Es1_1 automata: recognises strings without 3 consecutive zeros**");
        System.out.println("Recognising \"" + str +"\" with Es1_1 automata: " + automata.test(str));
        System.out.println("");

        //Es1_2
        automata = new Es1_2();
        str = "pp";
        System.out.println("**Es1_2 automata: recognises Java identifier**");
        System.out.println("Recognising \"" + str +"\" with Es1_2 automata: " + automata.test(str));
        System.out.println("");

    }
    
}
