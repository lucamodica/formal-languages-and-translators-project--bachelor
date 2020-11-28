import java.io.*;

public class Evaluator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Evaluator(Lexer l, BufferedReader br) {
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

    //Initial variable
    public void start() {
        switch(look.tag){
            case '(':
            case Tag.NUM:
                int expr_val = expr();
                match(Tag.EOF);
                System.out.println(expr_val);
                break;
        }
    }

    //Not-null expression
    private int expr() {
        int term_val, exprp_val;
        switch(look.tag){
            case '(':
            case Tag.NUM:
                term_val = term();
                exprp_val = exprp(term_val);
                break;
        }
        return exprp_val;
    }

    //Espression auxiliary
    private int exprp(int exprp_i) { 
        int term_val, exprp_val;
	    switch (look.tag) {
            case '+':
                match('+');
                term_val = term();
                exprp_val = exprp(exprp_i + term_val);
                return exprp_val;

            case '-':
                match('-');
                term_val = term();
                exprp_val = exprp(exprp_i - term_val);
                return exprp_val;

            //"exprp --> epsilon" production 
            case ')':
            case Tag.EOF:
                break;

            default: 
                error("exprp");
                
        }
        return exprp_i;
    }

    //Term, for mult and div (not null)
    private int term() {
        int fact_val = fact();
        int termp_val = termp(fact_val);
        return termp_val;
    }

    //Term auxiliary variable
    private int termp(int termp_i) {
        int fact_val, termp_val;
        switch(look.tag){
            case '*':
                match('*');
                fact_val = fact();
                termp_val = termp(termp_i * fact_val);
                return termp_i;

            case '/':
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i / fact_val);
                return termp_i;
            
            //"termp --> epsilon" production 
            case '+':
            case '-':
            case ')':
            case Tag.EOF:
                break;   
        }
        return termp_i;
    }

    //Single value/expression in parenthesis
    private int fact() {
        int fact_val = 0;
        switch(look.tag){
            case '(':
                match('(');
                int expr_val = expr();
                match(')');                                                                                                 
                return expr_val;

            case Tag.NUM:
                match(Tag.NUM);
                //Extracting numeric value from token
                String n = look.toString().substring(6,look.toString().length()-1);
                fact_val = Integer.parseInt(n);
                break;
        }
        return fact_val;
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "D:\\Luca\\Desktop\\Uni\\LFT\\lab\\L7 (Syntax-Directed Translator)\\exampleSDT.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Evaluator evaluator = new Evaluator(lex, br);
            evaluator.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}