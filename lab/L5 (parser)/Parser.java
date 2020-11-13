import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
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

    public void start() {
        switch(look.tag){
            case '(':
            case :
                expr();
                break;
            
            case Token(Tag.EOF):
                match(Tag.EOF);
                break;
        }
    }

    private void expr() {
	    switch(look.tag){
            case '(':
            case :
                term();
                exprp();
                break;
        }
    }

    private void exprp() {
	    switch (look.tag) {
            case '+':
                match(Token.plus.tag);
                term();
                exprp();
                break;
            case '-':
                match(Token.minus.tag);
                term();
                exprp();
                break;
            case ')':
                break;
        }
    }

    private void term() {
        
    }

    private void termp() {
        
    }

    private void fact() {
        // ... completare ...
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "...path..."; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}