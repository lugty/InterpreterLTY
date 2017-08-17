package InterpreterLTY.interpreter.parser;


import InterpreterLTY.interpreter.util.Token;

/**
 * Created by lugty on 4/20/17.
 */
public class Assign extends AST{
    private Token token;
    private Token op;
    private Var left;
    private AST right;

    public Assign(Var left, Token op, AST right){
        this.left = left;
        this.token = op;
        this.op = op;
        this.right = right;
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

    public Var getLeft() {
        return left;
    }

    public void setLeft(Var left) {
        this.left = left;
    }

    public AST getRight() {
        return right;
    }

    public void setRight(AST right) {
        this.right = right;
    }
}
