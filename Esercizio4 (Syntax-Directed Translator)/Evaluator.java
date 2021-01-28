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
            
            default:
                error("start");
        }
    }

    //Not-null expression
    private int expr() {
        int exprp_val = 0;
        switch(look.tag){
            case '(':
            case Tag.NUM:
                int term_val = term();
                exprp_val = exprp(term_val);
                break;

            default:
                error("expr");
        }
        return exprp_val;
    }

    //Espression auxiliary
    private int exprp(int exprp_i) { 
        int term_val, exprp_val = exprp_i;
	    switch (look.tag) {
            case '+':
                match('+');
                term_val = term();
                exprp_val = exprp(exprp_i + term_val);
                break;

            case '-':
                match('-');
                term_val = term();
                exprp_val = exprp(exprp_i - term_val);
                break;

            //"exprp --> epsilon" production 
            case ')':
            case Tag.EOF:
                break;

            default:
                error("exprp");
                
        }
        return exprp_val;
    }

    //Term, for mult and div (not null)
    private int term() {
        int termp_val = 0;
        switch(look.tag){
            case '(':
            case Tag.NUM:
                int fact_val = fact();
                termp_val = termp(fact_val);
                break;

            default:
                error("term");
        }
        return termp_val;
    }

    //Term auxiliary variable
    private int termp(int termp_i) {
        int fact_val, termp_val = termp_i;
        switch(look.tag){
            case '*':
                match('*');
                fact_val = fact();
                termp_val = termp(termp_i * fact_val);
                break;

            case '/':
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i / fact_val);
                break;
            
            //"termp --> epsilon" production 
            case '+':
            case '-':
            case ')':
            case Tag.EOF:
                break; 
                
            default:
                error("termp");
        }
        return termp_val;
    }

    //Single value/expression in parenthesis
    private int fact() {
        int fact_val = 0;
        switch(look.tag){
            case '(':
                match('(');
                fact_val = expr();
                match(')');                                                                                                 
                break;

            case Tag.NUM:
                //Extracting numeric value from token
                NumberTok n = (NumberTok) look;
                fact_val = n.num;
                //String n = look.toString().substring(6,look.toString().length()-1);
                //fact_val = Integer.parseInt(n);
                match(Tag.NUM);
                break;

            default:
                error("fact");
        }
        return fact_val;
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "exampleSDT.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Evaluator evaluator = new Evaluator(lex, br);
            evaluator.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}