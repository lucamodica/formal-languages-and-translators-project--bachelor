public class Es1_5 extends Automata{

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
                    if ((ch >= 'A' && ch <= 'K') || (ch >= 'a' && ch <= 'k')){
                        setState(1); 
                    }
                    else if ((ch >= 'L' && ch <= 'Z') || (ch >= 'l' && ch <= 'z')){
                        setState(2); 
                    }
                    else if (ch >= '0' && ch <= '9'){
                        setState(0); 
                    }
                    else{ setState(-1); }
                    break;
            
                case 1:
                    if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                        setState(1);
                    }
                    else if ((int) ch % 2 == 0){ setState(3); }
                    else if ((int) ch % 2 == 1){ setState(5); }
                    else{ setState(-1); }
                    break;

                case 2:
                    if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                        setState(2);
                    }
                    else if ((int) ch % 2 == 0){ setState(6); }
                    else if ((int) ch % 2 == 1){ setState(4); }
                    else{ setState(-1); }
                    break;

                case 3:
                    if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                        setState(7);
                    }
                    else if ((int) ch % 2 == 0){ setState(3); }
                    else if ((int) ch % 2 == 1){ setState(5); }
                    else{ setState(-1); }
                    break;

                case 4:
                    if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                        setState(7);
                    }
                    else if ((int) ch % 2 == 0){ setState(5); }
                    else if ((int) ch % 2 == 1){ setState(4); }
                    else{ setState(-1); }
                    break;

                case 5:
                    if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                        setState(7);
                    }
                    else if ((int) ch % 2 == 0){ setState(3); }
                    else if ((int) ch % 2 == 1){ setState(5); }
                    else{ setState(-1); }
                    break;

                case 6:
                    if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                        setState(7);
                    }
                    else if ((int) ch % 2 == 0){ setState(6); }
                    else if ((int) ch % 2 == 1){ setState(4); }
                    else{ setState(-1); }
                    break;

                case 7:
                    if ((ch >= 'a' && ch <= 'z') || 
                        (ch >= 'A' && ch <= 'Z') ||
                        (ch >= '0' && ch <= '9')){
                        setState(7);
                    }
                    else{ setState(-1); }
                    break;
                    
            }
        }

        return getState() == 3 || getState() == 4;
    }

} 