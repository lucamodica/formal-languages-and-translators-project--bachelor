import java.io.*;

public class ExtendedParser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public ExtendedParser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
	    throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF){ move();}
        } 
        else { error("syntax error"); }
    }


    //Start of the program
    void prog(){
        switch(look.tag){
            case '=':
            case Tag.PRINT:
            case Tag.READ:
            case Tag.COND:
            case Tag.WHILE:
                statlist();
                match(Tag.EOF);
                break;
        }
    }

    //Statement list
    void statlist(){
        switch(look.tag){
            case '=':
            case Tag.PRINT:
            case Tag.READ:
            case Tag.COND:
            case Tag.WHILE:
                stat();
                statlistp();
                break; 
        }
    }

    //Statement list auxiliary
    void statlistp(){
        switch(look.tag){
            case ';':
                match(';');
                stat();
                statlistp();
                break;
        }
    }

    //Single statement
    void stat(){
        switch(look.tag){

            //Assignement (penny di merda)
            case '=':
                match('=');
                match(Tag.ID);
                expr();
                break;

            //print()
            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist();
                match(')');
                break;

            //read()
            case Tag.READ:
                match(Tag.READ);
                match('(');
                match(Tag.ID);
                match(')');
                break;

            //cond
            case Tag.COND:
                match(Tag.COND);
                whenlist();
                match(Tag.ELSE);
                stat();
                break;

            //while()
            case Tag.WHILE:
                match(Tag.WHILE);
                match('(');
                bexpr();
                match(')');
                stat();
                break;

            //Other statementlist
            case '{':
                match('{');
                statlist();
                match('}');
                break;

        }
    }

    //when() list 
    void whenlist(){
        switch(look.tag){
            case Tag.WHEN:
                whenitem();
                whenlistp();
                break;
        }
    }

    //when() statement potentially empty
    void whenlistp(){ 
        switch(look.tag){
            case Tag.WHEN:
                whenitem();
                whenlistp();
                break;    
        }
    }

    //when() single item
    void whenitem(){
        switch(look.tag){
            case Tag.WHEN:
                match(Tag.WHEN);
                match('(');
                bexpr();
                match(')');
                match(Tag.DO);
                stat();
                break;
        }
    }

    //Binary expression
    void bexpr(){
        switch(look.tag){
            //Case relational operator
            case Tag.RELOP:
                match(Tag.RELOP);
                expr();
                expr();
                break;
        }
    }

    //Expression
    void expr(){
        switch(look.tag){

            //Case plus operator
            case '+':
                match('+');
                match('(');
                exprlist();
                match(')');
                break;

            //Case mult operator
            case '*':
                match('*');
                match('(');
                exprlist();
                match(')');
                break;

            //Case minus operator
            case '-':
                match('-');
                exprlist();
                exprlist();
                break;

            //Case div operator
            case '/':
                match('/');
                exprlist();
                exprlist();
                break;

            //Number value
            case Tag.NUM:
                match(Tag.NUM);
                break;

            //Identifiers
            case Tag.ID:
                match(Tag.ID);
                break;

        }
    }

    //List of exporession
    void exprlist(){
        switch(look.tag){
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr();
                exprlistp();
                break;
        }
    }

    //Expression list auxiliary
    void exprlistp(){
        switch(look.tag){
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr();
                exprlistp();
                break;
        }
    }

    
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "examples\\" + args[0] + ".txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            ExtendedParser parser = new ExtendedParser(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}