package InterpreterLTY.interpreter.parser;

import InterpreterLTY.interpreter.util.Token;

/**
 * Created by lugty on 4/20/17.
 */
public class Num extends AST {
    private Token token;
    private Object value;

    public Num(Token token){
        this.token = token;
        this.value = token.getValue();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
