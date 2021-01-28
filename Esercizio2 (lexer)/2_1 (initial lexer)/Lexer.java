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

    private Token incorrectToken(char details){
        System.err.println("Erroneous character after " + details + " : "  + peek);
        return null;
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
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
            
            case '/':
                peek = ' ';
                return Token.div;
            
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
                    return incorrectToken('&');
                }
            
            //Case "||"
            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } 
                else {
                    return incorrectToken('|');
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
                String str = "";
                //Case for identifiers and keywords
                if (Character.isLetter(peek)) {

                    str += peek;
                    readch(br);
                    while(Character.isLetter(peek) || Character.isDigit(peek)){
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
                    while(Character.isLetter(peek) || Character.isDigit(peek)){
                        str += peek;
                        readch(br);
                    }

                    //Checking the last character read
                    return new NumberTok(Tag.NUM, Integer.parseInt(str));

                } 
                else {
                    return incorrectToken(peek);
                }
         }
    }
    
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "example1.txt"; // il percorso del file da leggere
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
