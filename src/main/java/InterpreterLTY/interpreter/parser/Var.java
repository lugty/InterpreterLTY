package InterpreterLTY.interpreter.parser;

import InterpreterLTY.interpreter.util.Token;

/**
 * Created by lugty on 4/20/17.
 */
public class Var extends AST {
    private Token token;
    private String value;

    public Var(Token token){
        this.token = token;
        this.value = (String)token.getValue();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
