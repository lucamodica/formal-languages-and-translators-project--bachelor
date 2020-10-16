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

        //Es1_3
        automata = new Es1_3();
        str = "123456Bianchi";
        System.out.println("**Es1_3 automata: recognises students of T2 and T3 lab turn**");
        System.out.println("-- The string must structured with the id number followed by the student surname.");
        System.out.println("-- T2: even id number, surname initial between A and K");
        System.out.println("-- T3: odd id number, surname initial between L and Z");
        System.out.println("Recognising \"" + str +"\" with Es1_3 automata: " + automata.test(str));
        System.out.println("");

    }
    
}
