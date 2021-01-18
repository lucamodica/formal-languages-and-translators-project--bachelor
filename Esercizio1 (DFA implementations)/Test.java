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
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

        //Es1_2
        automata = new Es1_2();
        str = "pp";
        System.out.println("**Es1_2 automata: recognises Java identifiers**");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

        //Es1_3
        automata = new Es1_3();
        str = "123456Bianchi";
        System.out.println("**Es1_3 automata: recognises students of T2 and T3 lab turn**");
        System.out.println("-- The string must structured with the id number followed by the student surname.");
        System.out.println("-- T2: even id number, surname initial between A and K");
        System.out.println("-- T3: odd id number, surname initial between L and Z");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

        //Es1_4
        automata = new Es1_4();
        str = "200 Be    Anchi";
        System.out.println("**Es1_4 automata: same as Es1_3, but with space between id number and surname allowed**");
        System.out.println("-- Composite surnames are also recognised.");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

        //Es1_5
        automata = new Es1_5();
        str = "Bianchi123456";
        System.out.println("**Es1_5 automata: same as Es1_3, but with id/surname order flipped**");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

        //Es1_6
        automata = new Es1_6();
        str = "bbaba";
        System.out.println("**Es1_6 automata: strings with at least an 'a' between the first 3 characters**");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

        //Es1_7
        automata = new Es1_7();
        str = "abbba";
        System.out.println("**Es1_7 automata: strings with at list an 'a' between the last 3 characters**");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

        //Es1_8
        automata = new Es1_8();
        str = "luca";
        System.out.println("**Es1_8 automata: strings with my beautiful name ('Luca' :DD) with a least 1 character replaced**");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

        //Es1_9
        automata = new Es1_9();
        str = "/* ciaooooo */";
        System.out.println("**Es1_9 automata: a single Java multi-line comment**");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

        //Es1_10
        automata = new Es1_10();
        str = "/* a */ a /* a */";
        System.out.println("**Es1_10 automata: string that can contain several Java multi-line comment**");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

    }
    
}