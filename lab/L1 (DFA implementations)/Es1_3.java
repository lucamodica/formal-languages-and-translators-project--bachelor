public class Es1_3 extends Automata{

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
                    if ((int) ch % 2 == 0){ setState(1); }
                    else if ((int) ch % 2 == 1){ setState(2); }
                    else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){ 
                        setState(4); 
                    }
                    else{ setState(-1); }
                    break;
            
                case 1:
                    if ((ch >= 'A' && ch <= 'K') || (ch >= 'a' && ch <= 'k')){
                        setState(3); 
                    }
                    else if ((ch >= 'L' && ch <= 'Z') || (ch >= 'l' && ch <= 'z')){
                        setState(4); 
                    }
                    else if ((int) ch % 2 == 0){ setState(1); }
                    else if ((int) ch % 2 == 1){ setState(2); }
                    else{ setState(-1); }
                    break;

                case 2:
                    if ((ch >= 'A' && ch <= 'K') || (ch >= 'a' && ch <= 'k')){
                        setState(4); 
                    }
                    else if ((ch >= 'L' && ch <= 'Z') || (ch >= 'l' && ch <= 'z')){
                        setState(3); 
                    }
                    else if ((int) ch % 2 == 0){ setState(1); }
                    else if ((int) ch % 2 == 1){ setState(2); }
                    else{ setState(-1); }
                    break;

                case 3:
                    if (ch >= '0' && ch <= '9'){
                        setState(4); 
                    }
                    else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                        setState(3);
                    }
                    else{ setState(-1); }
                    break;

                case 4:
                    if ((ch >= '0' && ch <= '9') ||
                        (ch >= 'a' && ch <= 'z') || 
                        (ch >= 'A' && ch <= 'Z')){
                        setState(4); 
                    }
                    else{ setState(-1); }
                    break;
            }
        }

        return getState() == 3;
    }

} 