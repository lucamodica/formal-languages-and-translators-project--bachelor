public class Es1_1 extends Automata{

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
                    if (ch == '0'){ setState(1); }
                    else if (ch == '1'){ setState(0); }
                    else{ setState(-1); }
                    break;
            
                case 1:
                    if (ch == '0'){ setState(2); }
                    else if (ch == '1'){ setState(0); }
                    else{ setState(-1); }
                    break;

                case 2:
                    if (ch == '0'){ setState(3); }
                    else if (ch == '1'){ setState(0); }
                    else{ setState(-1); }
                    break;

                case 3:
                    if (ch == '0' || ch == '1'){ setState(3); }
                    else{ setState(-1); }
                    break;     
            }
        }

        return getState() == 0;
    }

} 