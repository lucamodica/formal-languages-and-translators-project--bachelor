//Testing class

public class Test {

    public static void main(String[] args) {
        
        //Generic automata
        Automata automata;
        //Test String
        String str;

        //Es1_6
        automata = new Es1_6();
        str = "bbaba";
        System.out.println("**Es1_6 automata: strings with at list an 'a' between the first 3 characters**");
        System.out.println("Recognising \"" + str +"\" : " + automata.test(str));
        System.out.println("");

        //Es1_7
        automata = new Es1_7();
        str = "abbab";
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