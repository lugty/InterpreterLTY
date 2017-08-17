package InterpreterLTY.interpreter.parser;

import InterpreterLTY.interpreter.util.Token;

/**
 * Created by lugty on 4/28/17.
 */
public class ConditionalOp extends AST {
    private Token token;
    private Token op;
    private AST left;
    private AST right;

    public ConditionalOp(AST left, Token token, AST right){
        this.token = token;
        this.op = token;
        this.left = left;
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

    public AST getLeft() {
        return left;
    }

    public void setLeft(AST left) {
        this.left = left;
    }

    public AST getRight() {
        return right;
    }

    public void setRight(AST right) {
        this.right = right;
    }
}
