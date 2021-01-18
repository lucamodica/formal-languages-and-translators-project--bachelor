public class Es1_6 extends Automata{

    public boolean scan(String s){
        int i = 0;

        //Analyzing the input string
        while(getState() >= 0 && i < s.length()){
            //Current char to be analyzed
            final char ch = s.charAt(i++);

            //State printing (only for debugging)
            //System.out.println("State rn: " + getState());

            switch (getState()) {
                case 0:
                    if(ch == 'a'){ setState(4); }
                    else if(ch == 'b'){ setState(1); }
                    else{ setState(-1); }
                    break;
            
                case 1:
                    if(ch == 'a'){ setState(4); }
                    else if(ch == 'b'){ setState(2); }
                    else{ setState(-1); }
                    break;
                    
                case 2:
                    if(ch == 'a'){ setState(4); }
                    else if(ch == 'b'){ setState(3); }
                    else{ setState(-1); }
                    break;
                    
                case 3:
                    if(ch == 'a' || ch == 'b'){ setState(3); }
                    else{ setState(-1); }
                    break;

                case 4:
                    if(ch == 'a' || ch == 'b'){ setState(4); }
                    else{ setState(-1); }
                    break;
                    
            }
        }

        return getState() == 4;
    }
    
}
