package InterpreterLTY.interpreter;

import InterpreterLTY.interpreter.parser.*;
import InterpreterLTY.interpreter.util.TokenTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lugty on 4/19/17.
 */
public class Interpreter extends NodeVisitor {
    private HashMap<String, Double> GLOBAL_SCOPE = new HashMap<String, Double>();
    private AST tree;

    public Interpreter(AST tree)throws Exception{
        this.tree = tree;
    }

    public Interpreter(AST tree, HashMap<String, Double> varTable)throws Exception{
        this.tree = tree;
        this.GLOBAL_SCOPE = varTable;
    }

    public Double visitBinOp(AST nodeAST){
        BinOp node = (BinOp)nodeAST;

        if (node.getOp().getType() == TokenTypes.PLUS){
            Double dd = (Double)this.visit(node.getLeft()) + (Double)this.visit(node.getRight());
            return dd;
        } else if (node.getOp().getType() == TokenTypes.MINUS)
            return (Double)this.visit(node.getLeft()) - (Double)this.visit(node.getRight());
        else if (node.getOp().getType() == TokenTypes.MUL)
            return (Double)this.visit(node.getLeft()) * (Double)this.visit(node.getRight());
        else if (node.getOp().getType() == TokenTypes.DIV)
            return (Double)this.visit(node.getLeft()) / (Double)this.visit(node.getRight());
        return 0.0;
    }

    public Double visitNum(AST nodeAST){
        Num node = (Num)nodeAST;
        Object valueObj = node.getValue();
        if(valueObj instanceof Integer){
            Integer vInt = (Integer)valueObj;
            return vInt.doubleValue();
        }else if(valueObj instanceof Double){
            Double vDouble = (Double) valueObj;
            return vDouble;
        }

        return (Double) node.getValue();
    }

    public Double visitUnaryOp(AST node){
        UnaryOp unaryOp = (UnaryOp) node;
        TokenTypes typeOp = unaryOp.getOp().getType();
        if(typeOp == TokenTypes.PLUS){
            return +(Double)this.visit(unaryOp.getExpr());
        }else if(typeOp == TokenTypes.MINUS){
            return -(Double)this.visit(unaryOp.getExpr());
        }
        return 0.0;
    }

    public List<Object> visitCompound(AST nodesAST){
        Compound nodes = (Compound) nodesAST;
        List<Object> responses = new ArrayList();

        for(AST node: nodes.getChildren()){
            Object res = this.visit(node);
            responses.add(res);
        }
        return responses;
    }

    public void visitAssign(AST node){
        Assign nodeVar = (Assign) node;

        String varName = nodeVar.getLeft().getValue();
        Object value = this.visit(nodeVar.getRight());
        this.GLOBAL_SCOPE.put(varName, (Double)value);
    }

    public Object visitVar(AST node) throws Exception{
        Var varNode = (Var) node;
        String varName = varNode.getValue();
        Object nodeVarFound = this.GLOBAL_SCOPE.get(varName);
        if(nodeVarFound == null)
            throw new Exception();
        else
            return nodeVarFound;
    }

    public Object visitIfStatement(AST node) throws Exception{
        IfStatement ifNode = (IfStatement) node;
        Boolean status = (Boolean) this.visit(ifNode.getCondition());
        if(status){
            Object rest = this.visit(ifNode.getTrueStatement());
            return rest;
        }else{
            Object rest = this.visit(ifNode.getFalseStatement());
            return rest;
        }
    }

    public Boolean visitConditionalOp(AST node) throws Exception{
        ConditionalOp condNode = (ConditionalOp) node;
        Double valLeft = (Double) this.visit(condNode.getLeft());
        Double valRight = (Double) this.visit(condNode.getRight());

        if(condNode.getOp().getType() ==TokenTypes.EQUAL){
            if(valLeft != null && valRight != null){
                if(valLeft.doubleValue() == valRight.doubleValue()) return true; else return false;
            }else{
                if(valLeft == valRight) return true; else return false;
            }
        }else if(condNode.getOp().getType() ==TokenTypes.NOTEQUAL){
            if(valLeft != valRight) return true; else return false;
        }else if(condNode.getOp().getType() ==TokenTypes.GREATER){
            if(valLeft > valRight) return true; else return false;
        }else if(condNode.getOp().getType() ==TokenTypes.GREATEREQ){
            if(valLeft >= valRight) return true; else return false;
        }else if(condNode.getOp().getType() ==TokenTypes.LESS){
            if(valLeft < valRight) return true; else return false;
        }else if(condNode.getOp().getType() ==TokenTypes.LESSEQ){
            if(valLeft <= valRight) return true; else return false;
        }
        return false;
    }

    public void visitNoOp(AST node){}



    public Object interprete() throws Exception{
        if(this.tree == null)
            return "";
        return this.visit(tree);

    }
}
