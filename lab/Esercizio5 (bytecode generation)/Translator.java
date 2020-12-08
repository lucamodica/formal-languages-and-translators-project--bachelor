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
    public void statlist(int lnext_statlist){
        switch(look.tag){
            case '=':
            case Tag.PRINT:
            case Tag.READ:
            case Tag.COND:
            case Tag.WHILE:
                stat(lnext_statlist);
                statlistp(lnext_statlist);
                break;
        }
    }

    //Statement list auxiliary
    public void statlistp(int lnext_statlistp){
        switch(look.tag){
            case ';':
                match(';');
                int lnext_stat = code.newLabel();
                code.emitLabel(lnext_stat);
                stat(lnext_stat);
                statlistp(lnext_statlistp);
                break;
        }
    }

    public void stat(int lnext_stat) {
        switch(look.tag) {

            //Assignement
            case '=':
                match('=');
                if (look.tag==Tag.ID) {
                    //Check if variable exists
                    int id_addr = st.lookupAddress(((Word)look).lexeme);
                    if (id_addr==-1) {
                        id_addr = count;
                        st.insert(((Word)look).lexeme,count++);
                    }
                    match(Tag.ID);
                    expr();
                    code.emit(OpCode.istore,id_addr);
                    
                }
                else{ error("Error in grammar (stat) after read( with " + look); }
                break;

            //print()
            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist();
                match(')');
                code.emit(OpCode.invokestatic,1);
                break;

            //read()
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

            //if() then else
            case Tag.COND:
                //New labels
                int true_label = code.newLabel();
                int false_label = code.newLabel();
                match(Tag.COND);
                whenlist(true_label,false_label);
                code.emitLabel(false_label);
                match(Tag.ELSE);
                stat(lnext_stat);
                break;

            //while()
            case Tag.WHILE:
                int cond_label = code.newLabel(); //conditional label
                int loop_label = code.newLabel(); //looping label
                match(Tag.WHILE);
                match('(');
                code.emitLabel(cond_label);
                bexpr(loop_label,lnext_stat);
                match(')');
                code.emitLabel(loop_label);
                int lnext = code.newLabel();
                stat(lnext);
                code.emit(OpCode.GOto,cond_label);
                code.emitLabel(lnext_stat);
                break;
                
            //Another statementlist
            case '{':
                match('{');
                statlist(lnext_stat);
                match('}');
                break;
	
        }
    }

    //when() list 
    public void whenlist(int ltrue, int lfalse){
        switch(look.tag){
            case Tag.WHEN:
                whenitem(ltrue,lfalse);
                whenlistp(ltrue,lfalse);
                break;
        }
    }

    //when() statement potentially empty
    public void whenlistp(int ltrue, int lfalse){ 
        switch(look.tag){
            case Tag.WHEN:
                whenitem(ltrue,lfalse);
                int lnext_true = code.newLabel();
                whenlistp(lnext_true,lfalse);
                break;    
        }
    }

    //when() single item
    public void whenitem(int ltrue, int lfalse){
        switch(look.tag){
            case Tag.WHEN:
                match(Tag.WHEN);
                match('(');
                bexpr(ltrue,lfalse);
                
                code.emitLabel(ltrue);
                match(')');
                match(Tag.DO);
                int lnext = code.newLabel();
                stat(lnext);
                code.emitLabel(lnext);
                break;
        }
    }

    //Binary expression
    void bexpr(int ltrue, int lfalse){
        switch(look.tag){
            //Case relational operator
            case Tag.RELOP:
                String cond = (((Word) look).lexeme);
                match(Tag.RELOP);
                switch (cond) {

                    case "==":
                        expr();
                        expr();
                        code.emit(OpCode.if_icmpeq, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;

                    case "<":
                        expr();
                        expr();
                        code.emit(OpCode.if_icmplt, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;

                    case ">":
                        expr();
                        expr();
                        code.emit(OpCode.if_icmpgt, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;

                    case "<=":
                        expr();
                        expr();
                        code.emit(OpCode.if_icmple, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;

                    case ">=":
                        expr();
                        expr();
                        code.emit(OpCode.if_icmpge, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;

                    case "<>":
                        expr();
                        expr();
                        code.emit(OpCode.if_icmpne, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;
                }
                break;
            
            //AND conditional
            case Tag.AND:
                match(Tag.AND);
                int true_label = code.newLabel();
                bexpr(true_label,lfalse);
                code.emitLabel(true_label);
                bexpr(ltrue,lfalse);
                break;

            //OR condition
            case Tag.OR:
                match(Tag.OR);
                int false_label = code.newLabel();
                bexpr(ltrue,false_label);
                code.emitLabel(false_label);
                bexpr(ltrue,lfalse);
                break;

            //NEG condition
            case '!':
                match('!');   
                bexpr(lfalse, ltrue);
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

    //Single expression
    private void expr() {
        switch(look.tag) {

            //Case plus operator
            case '+':
                match('+');
                match('(');
                exprlist();
                match(')');
                code.emit(OpCode.iadd);
                break; 

            //Case mult operator
            case '*':
                match('*');
                match('(');
                exprlist();
                match(')');
                code.emit(OpCode.imul);
                break;

            //Case minus operator
            case '-':
                match('-');
                exprlist();
                exprlist();
                code.emit(OpCode.isub);
                break;

            //Case div operator
            case '/':
                match('/');
                exprlist();
                exprlist();
                code.emit(OpCode.idiv);
                break;

            //Number value
            case Tag.NUM:
                code.emit(OpCode.ldc,((NumberTok) look).num);
                match(Tag.NUM);
                break;

            //Identifiers
            case Tag.ID:
                if (look.tag==Tag.ID) {
                    int id_addr = st.lookupAddress(((Word)look).lexeme);
                    if (id_addr==-1) {
                        id_addr = count;
                        st.insert(((Word)look).lexeme,count++);
                    }
                    match(Tag.ID);
                    expr();
                    code.emit(OpCode.iload,id_addr);
                }
                break;
           

        }
    }


    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "D:\\Luca\\Desktop\\Uni\\LFT\\lab\\Esercizio5 (bytecode generation)\\program.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }

}
