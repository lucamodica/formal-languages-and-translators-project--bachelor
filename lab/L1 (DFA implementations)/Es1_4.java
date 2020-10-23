public class Es1_4 extends Automata{
    
    public boolean scan(String s){
        int i = 0;

        //Analyzing the input string
        while(getState() >= 0 && i < s.length()){
            //Current char to be analyzed
            final char ch = s.charAt(i++);

            //State printing (only for debugging)
            //System.out.println("State rn: " + getState());

            switch (getState()) {

                //Init state, id regognising
                case 0:
                    if ((int) ch % 2 == 0){ setState(1); }
                    else if ((int) ch % 2 == 1){ setState(2); }
                    else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' '){ 
                        setState(7); 
                    }
                    else{ setState(-1); }
                    break;
            
                //even id regognised
                case 1:
                    if(ch == ' '){
                        setState(3);
                    }
                    else if (ch >= 'A' && ch <= 'K'){
                        setState(5); 
                    }
                    else if ((ch >= 'L' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')){
                        setState(7); 
                    }
                    else if ((int) ch % 2 == 0){ setState(1); }
                    else if ((int) ch % 2 == 1){ setState(2); }
                    else{ setState(-1); }
                    break;

                //odd id regognised
                case 2:
                    if(ch == ' '){
                        setState(4);
                    }
                    else if (ch >= 'L' && ch <= 'Z'){
                        setState(5); 
                    }
                    else if ((ch >= 'A' && ch <= 'K') || (ch >= 'a' && ch <= 'z')){
                        setState(7); 
                    }
                    else if ((int) ch % 2 == 0){ setState(1); }
                    else if ((int) ch % 2 == 1){ setState(2); }
                    else{ setState(-1); }
                    break;

                //White space after even id
                case 3:
                    if(ch == ' '){
                        setState(3);
                    }
                    else if (ch >= 'A' && ch <= 'K'){
                        setState(5); 
                    }
                    else if ((ch >= 'L' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')){
                        setState(7); 
                    }
                    else{ setState(-1); }
                    break;

                //White space after odd id
                case 4:
                    if(ch == ' '){
                        setState(4);
                    }
                    else if (ch >= 'L' && ch <= 'Z'){
                        setState(5); 
                    }
                    else if ((ch >= 'A' && ch <= 'K') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')){
                        setState(7); 
                    }
                    else{ setState(-1); }
                    break;

                //Successful state
                case 5:
                    if ((ch >= 'a' && ch <= 'z')){
                        setState(5); 
                    }
                    else if(ch == ' '){
                        setState(6);
                    }
                    else if ((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')){
                        setState(7); 
                    }
                    else{ setState(-1); }
                    break;

                //Successful state with white space
                case 6:
                    if (ch >= 'A' && ch <= 'Z'){
                        setState(5); 
                    }
                    else if(ch == ' '){
                        setState(6);
                    }
                    else if ((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')){
                        setState(7); 
                    }
                    else{ setState(-1); }
                    break;

                //First letter regognised
                case 7:
                    if ((ch >= 'a' && ch <= 'z') || 
                        (ch >= 'A' && ch <= 'Z') ||
                        (ch >= '0' && ch <= '9') ||
                        ch == ' '){
                        setState(7); 
                    }
                    else{ setState(-1); }
                    break;

            }
        }

        return getState() == 5 || getState() == 6;
    }

}
