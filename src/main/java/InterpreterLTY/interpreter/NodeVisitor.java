package InterpreterLTY.interpreter;

import InterpreterLTY.interpreter.parser.AST;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lugty on 4/20/17.
 */
public class NodeVisitor {
    public Object visit(AST node){
        try {
            String name = "visit"+node.getClass().getSimpleName();
            Class ctl = this.getClass();
            Method method = this.getClass().getMethod(name, AST.class);
             Object rst = method.invoke(this, node);

            return rst;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void genericVisit(AST node) throws Exception{
        throw new Exception("No visit" + node.getClass().getName() + "method");
    }
}
