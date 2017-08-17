package InterpreterLTY.interpreter.util;

/**
 * Created by lugty on 4/19/17.
 */
public class Token<T> {
    private TokenTypes type;
    private T value;

    public Token(TokenTypes type, T value){
        this.type = type;
        this.value = value;
    }

    public TokenTypes getType() {
        return type;
    }

    public void setType(TokenTypes type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
