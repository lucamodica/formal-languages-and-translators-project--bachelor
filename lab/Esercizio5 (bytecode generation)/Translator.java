import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count=0;

    public Translator(Lexer l, BufferedReader br) {
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
    public void prog() {        
	    switch(look.tag){
            case '=':
            case Tag.PRINT:
            case Tag.READ:
            case Tag.COND:
            case Tag.WHILE:
                //Creating the first label
                int lnext_prog = code.newLabel();
                statlist(lnext_prog);
                code.emitLabel(lnext_prog);
                match(Tag.EOF);
                //Writing code to the FileName.j
                try {
                    code.toJasmin();
                }
                catch(java.io.IOException e) {
                    System.out.println("IO error\n");
                };
                break;
        }
    }

    //Statement list
    public void statlist(int lnext_prog){
        switch(look.tag){
            case '=':
            case Tag.PRINT:
            case Tag.READ:
            case Tag.COND:
            case Tag.WHILE:
                stat(code.newLabel());
                statlistp(lnext_prog);
                break; 
        }
    }

    //Statement list auxiliary
    void statlistp(int lnext){
        switch(look.tag){
            case ';':
                match(';');
                stat(code.newLabel());
                statlistp(lnext);
                break;
        }
    }

    public void stat( /* completare */ ) {
        switch(look.tag) {
	// ... completare ...
            case Tag.READ:
                match(Tag.READ);
                match('(');
                if (look.tag==Tag.ID) {
                    int id_addr = st.lookupAddress(((Word)look).lexeme);
                    if (id_addr==-1) {
                        id_addr = count;
                        st.insert(((Word)look).lexeme,count++);
                    }                    
                    match(Tag.ID);
                    match(')');
                    code.emit(OpCode.invokestatic,0);
                    code.emit(OpCode.istore,id_addr);   
                }
                else{ error("Error in grammar (stat) after read( with " + look); }
                break;
	// ... completare ...
        }
     }

    private void expr( /* completare */ ) {
        switch(look.tag) {
	// ... completare ...
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;
	// ... completare ...
        }
    }

// ... completare ...
}

