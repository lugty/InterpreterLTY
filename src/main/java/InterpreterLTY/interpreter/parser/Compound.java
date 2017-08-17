package InterpreterLTY.interpreter.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lugty on 4/20/17.
 */
public class Compound extends AST {
    private List<AST> children;

    public Compound(){
        this.children = new ArrayList<AST>();
    }

    public List<AST> getChildren() {
        return children;
    }

    public void setChildren(List<AST> children) {
        this.children = children;
    }
}
