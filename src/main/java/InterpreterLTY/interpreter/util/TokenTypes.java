package InterpreterLTY.interpreter.util;

/**
 * Created by lugty on 4/19/17.
 */
public enum TokenTypes {
    INTEGER("INTEGER"),
    PLUS("PLUS"),
    MINUS("MINUS"),
    DIV("DIV"),
    MUL("MUL"),
    LPAREN("("),
    RPAREN(")"),
    EOF("EOF"),

    IF("IF"),
    ELSE("ELSE"),
    THEN("THEN"),

    ID("ID"),
    BEGIN("BEGIN"),
    END("END"),

    ASSIGN("="),
    DOT("DOT"),
    SEMI("SEMI"),

    LESS("<"),
    GREATER(">"),
    LESSEQ("<="),
    GREATEREQ(">="),
    EQUAL("=="),
    NOTEQUAL("!="),

    AND("AND"),
    OR("OR"),


    INTEGER_CONTS("INTEGER_CONTS"),
    FLOAT_CONTS("FLOAT_CONTS");

    private final String name;

    TokenTypes(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
