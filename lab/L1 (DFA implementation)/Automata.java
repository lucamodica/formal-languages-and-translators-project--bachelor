//Abstract class for all the automatas in this lesson

public abstract class Automata {
    
    abstract boolean scan(String s);

    public String test(String s) {
        return scan(s) ? "OK" : "NOPE";
    }

}
