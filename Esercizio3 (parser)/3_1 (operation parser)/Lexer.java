import java.io.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    private Token incorrectToken(String details){
        System.err.println("Erroneous character after " + details + " : "  + peek);
        return null;
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r' || peek == '/') {

            //Division, single-line comment, multi-line comment
            if (peek == '\n'){ 
                line++; 
            }
            if(peek == '/'){
                readch(br);

                //Single-line comment
                if(peek == '/'){
                    //Read until a '\n' is found
                    readch(br);
                    while(peek != '\n'){
                        readch(br);
                    }
                    line++;
                }

                //Multi-line operator
                else if(peek == '*'){
                    //Read until "*/" is found
                    String c_close = "";
                    readch(br);
                    while(peek != (char) -1 && !c_close.equals("*/")){
                        if((peek == '*' && c_close.isEmpty()) || (peek == '/' && c_close.equals("*"))){
                            c_close += peek;
                        }
                        else{ c_close = ""; }
                        readch(br);
                    }

                    //If peek == EOF after the loop and we haven't found any "*/", this means that we got an error
                    if(!c_close.equals("*/")){
                        return incorrectToken("missing multi-line comment close");
                    }
                }

                //Division operator
                else{
                    return Token.div;
                }
            }
            else{
                readch(br);
            }

             
        }
        
        
        switch (peek) {

            //***Single caracter (token)***
            case '!':
                peek = ' ';
                return Token.not;
            
            case '(':
                peek = ' ';
                return Token.lpt;
            
            case ')':
                peek = ' ';
                return Token.rpt;
            
            case '{':
                peek = ' ';
                return Token.lpg;
            
            case '}':
                peek = ' ';
                return Token.rpg;
            
            case '+':
                peek = ' ';
                return Token.plus;
            
            case '-':
                peek = ' ';
                return Token.minus;
            
            case '*':
                peek = ' ';
                return Token.mult;
            
            case ';':
                peek = ' ';
                return Token.semicolon;
            

            //***Binary operator***
            //Case "&&"
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } 
                else {
                    return incorrectToken("&");
                }
            
            //Case "||"
            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } 
                else {
                    return incorrectToken("|");
                }
            
            //Case "==", "="
            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } 
                else{
                    return Token.assign;
                }
            
            //Case "<=", "<>", "<"
            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                } 
                else if(peek == '>'){
                    peek = ' ';
                    return Word.ne;
                }
                else{
                    return Word.lt;
                }

            //Case ">", ">="  
            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                }
                else{
                    return Word.gt;
                }


            //***End Of File***
            case (char)-1:
                return new Token(Tag.EOF);
            

            //***Identifiers, keywords, values***
            default:
                String str = ""; /* Var/num string */ 

                //Case for identifiers and keywords
                if (Character.isLetter(peek) || peek == '_') {
                    /* Bool check if a var is valid with an initial undescore */ 
                    boolean valid_underscore = false; 
                    str += peek;
                    readch(br);
                    
                    if(str.equals("_")){
                        while(!valid_underscore){
                            if(Character.isLetter(peek) || Character.isDigit(peek) || peek == '_'){
                                str += peek;
                                if(Character.isLetter(peek) || Character.isDigit(peek)){
                                    valid_underscore = true;
                                }
                                readch(br);
                            }
                            else if(!Character.isLetter(peek) && !Character.isDigit(peek)){
                                return incorrectToken("only underscore var");
                            }
                        }
                    } 

                    while(Character.isLetter(peek) || Character.isDigit(peek) || peek == '_'){
                        str += peek;
                        readch(br);
                    }

                    //Checking the last character read
                    switch(str){
                        case "cond": return Word.cond;
                        case "when": return Word.when;
                        case "then": return Word.then;
                        case "else": return Word.elsetok;
                        case "while": return Word.whiletok;
                        case "do": return Word.dotok;
                        case "seq": return Word.seq;
                        case "print": return Word.print;
                        case "read": return Word.read;
                        default: return new Word(Tag.ID,str);
                    }

                }

                //Case for numbers
                else if (Character.isDigit(peek)) {

                    str += peek;
                    readch(br);
                    while(Character.isDigit(peek)){
                        str += peek;
                        readch(br);
                    }

                    //Checking the last character read
                    if(Character.isLetter(peek) || peek == '_'){
                        return incorrectToken("illegal number pattern");
                    }
                    else{ return new NumberTok(Tag.NUM, Integer.parseInt(str)); }
                    

                } 
                else {
                    return incorrectToken("unrecognised character");
                }
         }
    }
    
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "example2.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}
