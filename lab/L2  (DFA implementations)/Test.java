//Testing class

public class Test {

    public static void main(String[] args) {
        
        //Generic automata
        Automata automata;
        //Test String
        String str;

        //Es1_1
        automata = new Es1_6();
        str = "1001";
        System.out.println("**Es1_6 automata: recognises strings without 3 consecutive zeros**");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

    }
    
}