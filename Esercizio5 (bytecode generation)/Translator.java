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

    //Moving the lexer
    private void move() { 
	    look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    //Throw an error of any kind inside the translator
    private void error(String s) { 
	    throw new Error("near line " + lex.line + ": " + s);
    }

    //Checking the current token taken from the lexer
    private void match(int t) {
	    if (look.tag == t) {
            if (look.tag != Tag.EOF){ move();}
        } 
        else { error("syntax error"); }
    }

    //Emit the print func
    private void exc_print(OpCode option){
        if(option != null){
            code.emit(option,1);
        }
    }

    //Start of the program
    public void prog() {
	    switch(look.tag){
            case '=':
            case Tag.PRINT:
            case Tag.READ:
            case Tag.COND:
            case Tag.WHILE:
            case '{':
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

            default:
                error("prog");
                
        }
    }

    //Statement list
    private void statlist(int lnext_statlist){
        switch(look.tag){
            case '=':
            case Tag.PRINT:
            case Tag.READ:
            case Tag.COND:
            case Tag.WHILE:
            case '{':
                stat(lnext_statlist);
                statlistp(lnext_statlist);
                break;

            default:
                error("statlist");
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

            case Tag.EOF:
            case '}':
                break;

            default:
                error("statlistp");
        }
    }

    //Single comment 
    public void stat(int lnext_stat) {
        int true_label;
        int false_label;
        int exit_label;
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
                    expr(null);
                    code.emit(OpCode.istore,id_addr);
                    
                }
                else{ error("Error in grammar (stat) after read( with " + look); }
                break;

            //print()
            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist(null,OpCode.invokestatic);
                match(')');
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
                true_label = code.newLabel();
                false_label = code.newLabel();
                exit_label = code.newLabel();
                match(Tag.COND);
                whenlist(true_label,false_label);
                code.emit(OpCode.GOto,exit_label);
                code.emitLabel(false_label);
                match(Tag.ELSE);
                stat(lnext_stat);
                code.emitLabel(exit_label);
                break;

            //while()
            case Tag.WHILE:
                int cond_label = code.newLabel(); //conditional label
                int loop_label = code.newLabel(); //looping label
                exit_label = code.newLabel();
                match(Tag.WHILE);
                match('(');
                code.emitLabel(cond_label);
                bexpr(loop_label,exit_label);
                match(')');
                code.emitLabel(loop_label);
                stat(lnext_stat);
                code.emit(OpCode.GOto,cond_label);
                code.emitLabel(exit_label);
                break;
                
            //Another statementlist
            case '{':
                match('{');
                statlist(lnext_stat);
                match('}');
                break;

            //error case 
            default:
                error("stat");
	
        }
    }

    //when() list 
    private void whenlist(int ltrue, int lfalse){
        switch(look.tag){
            case Tag.WHEN:
                whenitem(ltrue,lfalse);
                whenlistp(ltrue,lfalse);
                break;

            //error case
            default:
                error("whenlist");
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

            case Tag.ELSE:
                break;
                
            //error case
            default:
                error("whenlistp");
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

            //error case
            default:
                error("whenitem");
        }
    }

    //Binary expression
    public void bexpr(int ltrue, int lfalse){
        switch(look.tag){
            //Case relational operator
            case Tag.RELOP:
                String cond = (((Word) look).lexeme);
                match(Tag.RELOP);
                switch (cond) {

                    case "==":
                        expr(null);
                        expr(null);
                        code.emit(OpCode.if_icmpeq, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;

                    case "<":
                        expr(null);
                        expr(null);
                        code.emit(OpCode.if_icmplt, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;

                    case ">":
                        expr(null);
                        expr(null);
                        code.emit(OpCode.if_icmpgt, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;

                    case "<=":
                        expr(null);
                        expr(null);
                        code.emit(OpCode.if_icmple, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;

                    case ">=":
                        expr(null);
                        expr(null);
                        code.emit(OpCode.if_icmpge, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;

                    case "<>":
                        expr(null);
                        expr(null);
                        code.emit(OpCode.if_icmpne, ltrue);
                        code.emit(OpCode.GOto,lfalse);
                        break;
                }
                break;
            
            //AND condition
            case Tag.AND:
                match(Tag.AND);
                int true_label = code.newLabel();
                match('(');
                bexpr(true_label,lfalse);
                match(')');
                code.emitLabel(true_label);
                match('(');
                bexpr(ltrue,lfalse);
                match(')');
                break;

            //OR condition
            case Tag.OR:
                match(Tag.OR);
                int false_label = code.newLabel();
                match('(');
                bexpr(ltrue,false_label);
                match(')');
                code.emitLabel(false_label);
                match('(');
                bexpr(ltrue,lfalse);
                match(')');
                break;

            //NEG condition
            case '!':
                match('!');   
                bexpr(lfalse, ltrue);
                break;

            //error case
            default:
                error("bexpr");
                
        }
    }

    //List of exporession
    public void exprlist(OpCode opc, OpCode option){
        switch(look.tag){
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr(option);
                exc_print(option);
                exprlistp(opc,option);

                if(opc != null){ //Means that is not a constant or variables
                    code.emit(opc);
                }
                break;

            //error case
            default:
                error("exprlist");
        }
    }

    //Expression list auxiliary
    public boolean exprlistp(OpCode opc, OpCode option){
        switch(look.tag){
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr(option);
                exc_print(option);
                boolean start = exprlistp(opc,option); //Checking if there's something more

                if(!start && opc != null){
                    code.emit(opc);
                }
                return false;

            //<exprlistp> --> epsilon
            case ')':
                return true;

            default:
                return false;

        }
    }

    //Single expression
    private void expr(OpCode option) {
        switch(look.tag) {

            //Case plus operator
            case '+':
                match('+');
                match('(');
                exprlist(OpCode.iadd,null);
                match(')');
                break; 

            //Case mult operator
            case '*':
                match('*');
                match('(');
                exprlist(OpCode.imul,null);
                match(')');
                break;

            //Case minus operator
            case '-':
                match('-');
                expr(null);
                expr(null);
                code.emit(OpCode.isub);
                break;

            //Case div operator
            case '/':
                match('/');
                expr(null);
                expr(null);
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
                    code.emit(OpCode.iload,id_addr);
                }
                break;

            
            default:
                error("expr");

        }

    }


    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "examples\\" + args[0] + ".lft"; //+ args[0]; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }

}
