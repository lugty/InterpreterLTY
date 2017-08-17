package InterpreterLTY.interpreter.lexer;

import InterpreterLTY.interpreter.util.TokenTypes;
import InterpreterLTY.interpreter.util.Token;

import java.util.HashMap;

/**
 * Created by lugty on 4/19/17.
 */
public class Lexer {
    private String sourceCode;
    private Character currentChar;
    private int position;

    private HashMap<String, Token> RESERVED_KEYWORDS;

    public Lexer(String sourceCode){
        this.sourceCode = sourceCode;
        this.position = 0;
        this.currentChar = this.sourceCode.charAt(this.position);

        this.RESERVED_KEYWORDS = new HashMap<String, Token>();
        this.RESERVED_KEYWORDS.put("BEGIN", new Token(TokenTypes.BEGIN, "BEGIN"));
        this.RESERVED_KEYWORDS.put("END", new Token(TokenTypes.END, "END"));
        this.RESERVED_KEYWORDS.put("IF", new Token(TokenTypes.IF, "IF"));
        this.RESERVED_KEYWORDS.put("ELSE", new Token(TokenTypes.ELSE, "ELSE"));
        this.RESERVED_KEYWORDS.put("THEN", new Token(TokenTypes.THEN, "THEN"));

        this.RESERVED_KEYWORDS.put("GREATER", new Token(TokenTypes.GREATER, ">"));
        this.RESERVED_KEYWORDS.put("GREATEREQ", new Token(TokenTypes.GREATEREQ, ">="));
        this.RESERVED_KEYWORDS.put("LESS", new Token(TokenTypes.LESS, "<"));
        this.RESERVED_KEYWORDS.put("LESSEQ", new Token(TokenTypes.LESSEQ, "<="));
        this.RESERVED_KEYWORDS.put("EQUAL", new Token(TokenTypes.EQUAL, "=="));
        this.RESERVED_KEYWORDS.put("NOTEQUAL", new Token(TokenTypes.NOTEQUAL, "!="));
    }

    private void error() throws Exception{
        throw new Exception("Invalid Character");
    }

    private void advance(){
        this.position += 1;
        if(this.position > this.sourceCode.length() -1 )
            this.currentChar = null;
        else
            this.currentChar = sourceCode.charAt(this.position);
    }

    private void skipWhiteSpace(){
        while(this.currentChar != null &&  Character.isWhitespace(this.currentChar))
            this.advance();
    }

    private Token number(){
        String result = "";
        Token token = null;

        while(this.currentChar != null && Character.isDigit(this.currentChar)){
            result += this.currentChar;
            this.advance();
        }

        if(this.currentChar != null && this.currentChar == '.'){
            result += this.currentChar;
            this.advance();

            while(this.currentChar != null && Character.isDigit(this.currentChar)){
                result += this.currentChar;
                this.advance();
            }

            token = new Token(TokenTypes.FLOAT_CONTS, Double.parseDouble(result));
        }else{
            token = new Token(TokenTypes.INTEGER_CONTS, Integer.valueOf(result));
        }

        return token;
    }

    private Token _id(){
        String result = "";
        while (this.currentChar != null && Character.isLetterOrDigit(this.currentChar)){
            result += this.currentChar;
            this.advance();
        }

        Token token = this.RESERVED_KEYWORDS.get(result);
        if (token == null)
            token = new Token(TokenTypes.ID, result);

        return token;
    }

    public Token getNextToken() throws Exception{
        while(this.currentChar != null){

            if(Character.isSpaceChar(this.currentChar)){
                this.skipWhiteSpace();
                continue;
            }

            if(Character.isLetter(this.currentChar)){
                return this._id();
            }

            if(Character.isDigit(this.currentChar)){
                return this.number();
            }

            if(this.currentChar == '=' && !this.peek().equals("=")){
                this.advance();
                return new Token(TokenTypes.ASSIGN, '=');
            }

            if(this.currentChar == ';'){
                this.advance();
                return new Token(TokenTypes.SEMI, ';');
            }

            if(this.currentChar == '+'){
                this.advance();
                return new Token(TokenTypes.PLUS, '+');
            }

            if(this.currentChar == '-'){
                this.advance();
                return new Token(TokenTypes.MINUS, '-');
            }

            if(this.currentChar == '*'){
                this.advance();
                return new Token(TokenTypes.MUL, '*');
            }

            if(this.currentChar == '/'){
                this.advance();
                return new Token(TokenTypes.DIV, '/');
            }

            if(this.currentChar == '('){
                this.advance();
                return new Token(TokenTypes.LPAREN, '(');
            }

            if(this.currentChar == ')'){
                this.advance();
                return new Token(TokenTypes.RPAREN, ')');
            }

            if(this.currentChar == '.'){
                this.advance();
                return new Token(TokenTypes.DOT, '.');
            }

            if(this.currentChar == '>'){
                this.advance();
                return new Token(TokenTypes.GREATER, '>');
            }

            if(this.currentChar == '<'){
                this.advance();
                return new Token(TokenTypes.LESS, '<');
            }

            if(this.currentChar == '=' && this.peek().equals("=")){
                this.advance();
                this.advance();
                return new Token(TokenTypes.EQUAL, "==");
            }

            if(this.currentChar == '!' && this.peek().equals("=")){
                this.advance();
                this.advance();
                return new Token(TokenTypes.NOTEQUAL, "!=");
            }

            if(this.currentChar == '>' && this.peek().equals("=")){
                this.advance();
                this.advance();
                return new Token(TokenTypes.GREATEREQ, ">=");
            }

            if(this.currentChar == '<' && this.peek().equals("=")){
                this.advance();
                this.advance();
                return new Token(TokenTypes.LESSEQ, "<=");
            }


            this.error();
        }
        return new Token(TokenTypes.EOF, null);
    }

    private String peek(){
        int peek_pos = this.position + 1;
        if(peek_pos > this.sourceCode.length() -1)
            return null;
        else
            return ""+this.sourceCode.charAt(peek_pos);
    }
}
