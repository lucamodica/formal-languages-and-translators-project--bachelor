public class Es1_4 extends Automata{
    
    public boolean scan(String s){
        //int state = 0;
        int i = 0;

        //Analyzing the input string
        while(getState() >= 0 && i < s.length()){
            //Current char to be analyzed
            final char ch = s.charAt(i++);

            //State printing (only for debugging)
            System.out.println("State rn: " + getState());

            switch (getState()) {

                //Init state, id regognising
                case 0:
                    if ((int) ch % 2 == 0){ setState(1); }
                    else if ((int) ch % 2 == 1){ setState(2); }
                    else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' '){ 
                        setState(4); 
                    }
                    else{ setState(-1); }
                    break;
            
                //even id regognised
                case 1:
                    if ((ch >= 'A' && ch <= 'K' && ch != 'D') || (ch >= 'a' && ch <= 'k' && ch != 'd') || ch == ' '){
                        setState(5); 
                    }
                    else if(ch == 'D' || ch == 'd'){
                        setState(7);
                    }
                    else if ((ch >= 'L' && ch <= 'Z') || (ch >= 'l' && ch <= 'z')){
                        setState(4); 
                    }
                    else if ((int) ch % 2 == 0){ setState(1); }
                    else if ((int) ch % 2 == 1){ setState(2); }
                    else{ setState(-1); }
                    break;

                //odd id regognised
                case 2:
                    if ((ch >= 'A' && ch <= 'K') || (ch >= 'a' && ch <= 'k')){
                        setState(4); 
                    }
                    else if ((ch >= 'L' && ch <= 'Z') || (ch >= 'l' && ch <= 'z') || ch == ' '){
                        setState(6); 
                    }
                    else if ((int) ch % 2 == 0){ setState(1); }
                    else if ((int) ch % 2 == 1){ setState(2); }
                    else{ setState(-1); }
                    break;

                //Succesful state
                case 3:
                    if ((ch >= '0' && ch <= '9') || ch == ' '){
                        setState(4); 
                    }
                    else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                        setState(3);
                    }
                    else{ setState(-1); }
                    break;

                //Failed state
                case 4:
                    if ((ch >= '0' && ch <= '9') ||
                        (ch >= 'a' && ch <= 'z') || 
                        (ch >= 'A' && ch <= 'Z') ||
                        ch == ' '){
                        setState(4); 
                    }
                    else{ setState(-1); }
                    break;

                //Blank space after even id
                case 5:
                    if ((ch >= 'l' && ch <= 'z') || 
                        (ch >= 'L' && ch <= 'Z') ||
                        (ch >= '0' && ch <= '9')){
                        setState(4); 
                    }
                    else if ((ch >= 'A' && ch <= 'K') || (ch >= 'a' && ch <= 'k' && ch != 'd')){
                        setState(3); 
                    }
                    else if(ch == ' '){
                        setState(5);
                    }
                    else{ setState(-1); }
                    break;

                //Blank space after odd id
                case 6:
                    if ((ch >= 'a' && ch <= 'k') || 
                        (ch >= 'A' && ch <= 'K') ||
                        (ch >= '0' && ch <= '9') ||
                        ch == ' '){
                        setState(4); 
                    }
                    else if ((ch >= 'L' && ch <= 'Z') || (ch >= 'l' && ch <= 'z')){
                        setState(3); 
                    }
                    else if(ch == ' '){
                        setState(6);
                    }
                    else{ setState(-1); }
                    break;

                //First letter regognised
                case 7:
                    if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                        setState(8); 
                    }
                    else if ((ch >= '0' && ch <= '9') || ch == ' '){
                        setState(4); 
                    }
                    else{ setState(-1); }
                    break;

                //Second letter regognised
                case 8:
                    if ((ch >= '0' && ch <= '9')){
                        setState(4); 
                    }
                    else if(ch == ' '){
                        setState(8);
                    }
                    else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                        setState(3); 
                    }
                    else{ setState(-1); }
                    break;
            }
        }

        return getState() == 3;
    }

}
