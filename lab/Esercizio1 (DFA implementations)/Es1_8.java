public class Es1_8 extends Automata{
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
                    if(ch == 'l' || ch == 'L'){ setState(1); }
                    else{ setState(5); }
                    break;

                case 1:
                    if(ch == 'u' || ch == 'U'){ setState(2); }
                    else{ setState(6); }
                    break;

                case 2:
                    if(ch == 'c' || ch == 'C'){ setState(3); }
                    else{ setState(7); }
                    break;

                case 3:
                    setState(4);
                    break;

                case 4:
                    setState(8);
                    break;

                case 5:
                    if(ch == 'u' || ch == 'U'){ setState(6); }
                    else{ setState(8); }
                    break;

                case 6:
                    if(ch == 'c' || ch == 'C'){ setState(7); }
                    else{ setState(8); }
                    break;

                case 7:
                    if(ch == 'a' || ch == 'A'){ setState(4); }
                    else{ setState(8); }
                    break;
                    
                case 8:
                    setState(8);
                    break;
                    
            }
        }

        return getState() == 4;
    }
    
}