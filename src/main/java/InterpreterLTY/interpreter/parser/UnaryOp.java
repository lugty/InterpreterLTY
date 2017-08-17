package InterpreterLTY.interpreter.parser;

import InterpreterLTY.interpreter.util.Token;

/**
 * Created by lugty on 4/20/17.
 */
public class UnaryOp extends AST {
    private Token token;
    private Token op;

    private AST expr;

    public UnaryOp(Token token, AST node){
        this.token = token;
        this.op = token;
        this.expr = node;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getOp() {
        return op;
    }

    public void setOp(Token op) {
        this.op = op;
    }

    public AST getExpr() {
        return expr;
    }

    public void setExpr(AST expr) {
        this.expr = expr;
    }
}
