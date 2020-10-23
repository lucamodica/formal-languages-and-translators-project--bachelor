//Abstract class for all the automatas in this lesson

public abstract class Automata {

    //Automata state
    private int state = 0;
    
    //Abstract method for scanning mode
    public abstract boolean scan(String s);

    //State getter/setter
    public int getState(){ return state; }
    public void setState(int state){ this.state = state; }

    //Method for testing an input
    public String test(String s) {
        return scan(s) ? "OK" : "NOPE";
    }

}
