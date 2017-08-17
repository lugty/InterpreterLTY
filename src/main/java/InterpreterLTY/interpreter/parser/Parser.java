package InterpreterLTY.interpreter.parser;

import InterpreterLTY.interpreter.lexer.Lexer;
import InterpreterLTY.interpreter.util.Token;
import InterpreterLTY.interpreter.util.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lugty on 4/19/17.
 */
public class Parser {
    private Token currentToken;
    private Token nextToken;
    private Lexer lexer;

    public Parser(Lexer lexer) throws Exception {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
        this.nextToken = lexer.getNextToken();
    }

    private void error()throws Exception{
        throw new Exception("Invalid syntax");
    }

    private void eat(TokenTypes type) throws Exception{
        if(this.currentToken.getType() == type){
            this.currentToken = this.nextToken;
            this.nextToken = this.lexer.getNextToken();
        } else
            this.error();
    }

    /**
     * factor : PLUS factor
     * | MINUS factor
     * | INTEGER_CONST
     * | REAL_CONST
     * | LPAREN expr RPAREN
     * | variable
     *
     * @return
     * @throws Exception
     */
    private AST factor() throws Exception{
        Token token = this.currentToken;

        if(token.getType() == TokenTypes.PLUS){
            this.eat(TokenTypes.PLUS);
            AST node = new UnaryOp(token, this.factor());
            return node;
        }else if(token.getType() == TokenTypes.MINUS) {
            this.eat(TokenTypes.MINUS);
            AST node = new UnaryOp(token, this.factor());
            return node;
        }else if(token.getType() == TokenTypes.INTEGER_CONTS){
            this.eat(TokenTypes.INTEGER_CONTS);
            return new Num(token);
        }else if(token.getType() == TokenTypes.FLOAT_CONTS) {
            this.eat(TokenTypes.FLOAT_CONTS);
            return new Num(token);
        }else if(token.getType() == TokenTypes.LPAREN){
            this.eat(TokenTypes.LPAREN);
            AST node = this.expr();
            this.eat(TokenTypes.RPAREN);

            return node;
        }else{
            AST node = this.variable();
            return node;
        }
    }

    /**
     * term : factor ((MUL | INTEGER_DIV | FLOAT_DIV) factor)*
     *
     * @throws Exception
     */
    private AST term() throws Exception{
        AST node = this.factor();
        while(this.currentToken.getType().equals(TokenTypes.DIV) ||
                this.currentToken.getType().equals(TokenTypes.MUL)){
            Token token = this.currentToken;
            if(token.getType().equals(TokenTypes.MUL)){
                this.eat(TokenTypes.MUL);
            }else if(token.getType().equals(TokenTypes.DIV)){
                this.eat(TokenTypes.DIV);
            }

            node = new BinOp(node, token, this.factor());
        }
        return node;
    }

    /**
     * expr   : term ((PLUS | MINUS) term)*
     * term   : factor ((MUL | DIV) factor)*
     * factor : INTEGER
     *
     * @return
     * @throws Exception
     */
    private AST expr()throws Exception{
        AST node = this.term();
        while(this.currentToken.getType().equals(TokenTypes.PLUS) ||
                this.currentToken.getType().equals(TokenTypes.MINUS)){
            Token token = this.currentToken;
            if(token.getType().equals(TokenTypes.PLUS)){
                this.eat(TokenTypes.PLUS);
            }else if(token.getType().equals(TokenTypes.MINUS)){
                this.eat(TokenTypes.MINUS);
            }
            node = new BinOp(node, token, this.term());
        }

        return node;
    }


    /***
     * program : compound_statement DOT
     * @return
     * @throws Exception
     */
    private Compound program() throws Exception{
        Compound node =  this.compound_statement();
        return node;
    }

    /**
     * compound_statement: BEGIN statement_list END
     * @return
     * @throws Exception
     */
    private Compound compound_statement() throws Exception{
        List<AST> nodes = this.statement_list();
        Compound root = new Compound();
        for(AST node: nodes){
            root.getChildren().add(node);
        }
        return root;
    }

    /**
     * statement_list : statement
     *                | statement SEMI statement_list
     *
     * @return
     * @throws Exception
     */
    private List<AST> statement_list() throws Exception{
        AST node = this.statement();
        List<AST> results = new ArrayList<AST>();
        results.add(node);

        while (this.currentToken.getType() == TokenTypes.SEMI){
            this.eat(TokenTypes.SEMI);
            results.add(this.statement());
        }

        if(this.currentToken.getType() == TokenTypes.ID)
            this.error();

        return results;
    }

    /**
     * statement : compound_statement
     *           | assignment_statement
     *           | empty
     * @return
     * @throws Exception
     */
    private AST statement() throws Exception{
        AST node = null;

        if(this.currentToken.getType() ==  TokenTypes.INTEGER_CONTS
                || this.currentToken.getType() ==  TokenTypes.FLOAT_CONTS
                || this.currentToken.getType() ==  TokenTypes.LPAREN
                || this.currentToken.getType() ==  TokenTypes.MINUS
                || this.currentToken.getType() ==  TokenTypes.PLUS){
            node = this.expr();
        }else if(this.currentToken.getType() ==  TokenTypes.IF) {
            node = this.ifStatement();
        }else if(this.currentToken.getType() ==  TokenTypes.ID && this.nextToken.getType() ==  TokenTypes.ASSIGN){
            node = this.assignmentStatement();
        }else if(this.currentToken.getType() ==  TokenTypes.ID){
            node = this.expr();
        }else {
            node = new NoOp();
        }

        return node;
    }

    /**
     * assignment_statement : variable ASSIGN expr
     * @return
     * @throws Exception
     */
    private Assign assignmentStatement() throws Exception{
        Var left = this.variable();
        Token token = this.currentToken;
        this.eat(TokenTypes.ASSIGN);
        AST right = this.expr();

        return new Assign(left, token, right);
    }

    /**
     * variable : ID
     * @return
     */
    private Var variable() throws Exception{
        Var node = new Var(this.currentToken);
        this.eat(TokenTypes.ID);
        return node;
    }

    /**
     * if_statement:
     * @throws Exception
     */
    private IfStatement ifStatement() throws Exception{
        if(this.currentToken != null && this.currentToken.getType() == TokenTypes.IF){
            this.eat(TokenTypes.IF);
            AST condition = this.conditional();
            this.eat(TokenTypes.THEN);

            AST trueStatement = statement();
            AST falseStatement = null;

            if(this.currentToken != null && this.currentToken.getType() == TokenTypes.ELSE){
                this.eat(TokenTypes.ELSE);
                falseStatement = statement();
            }
            this.eat(TokenTypes.END);

            return new IfStatement(condition, trueStatement, falseStatement);
        }

        return null;
    }

    private ConditionalOp conditional() throws Exception{
        AST nodeLeft = this.expr();
        Token token = this.currentToken;

        if(token.getType() == TokenTypes.EQUAL ||
                token.getType() == TokenTypes.NOTEQUAL ||
                token.getType() == TokenTypes.LESS ||
                token.getType() == TokenTypes.LESSEQ ||
                token.getType() == TokenTypes.GREATER ||
                token.getType() == TokenTypes.GREATEREQ
                )
        {
            this.eat(token.getType());
            AST node = this.expr();

            return new ConditionalOp(nodeLeft, token, node);
        }

        this.error();
        return null;
    }

    /**
     * An empty production
     * @return
     */
    private NoOp empty(){
        return new NoOp();
    }

    public AST parse() throws Exception{
        Compound node = this.program();
        if(this.currentToken.getType() != TokenTypes.EOF)
            this.error();
        return node;
    }

}
