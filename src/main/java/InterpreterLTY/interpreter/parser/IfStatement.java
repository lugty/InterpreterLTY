package InterpreterLTY.interpreter.parser;

/**
 * Created by lugty on 4/28/17.
 */
public class IfStatement extends AST {
    private AST condition;
    private AST trueStatement;
    private AST falseStatement;

    public IfStatement(AST condition, AST trueStatement, AST falseStatement) {
        this.condition = condition;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }

    public AST getCondition() {
        return condition;
    }

    public void setCondition(AST condition) {
        this.condition = condition;
    }

    public AST getTrueStatement() {
        return trueStatement;
    }

    public void setTrueStatement(AST trueStatement) {
        this.trueStatement = trueStatement;
    }

    public AST getFalseStatement() {
        return falseStatement;
    }

    public void setFalseStatement(AST falseStatement) {
        this.falseStatement = falseStatement;
    }
}
