package main;

import InterpreterLTY.interpreter.Interpreter;
import InterpreterLTY.interpreter.lexer.Lexer;
import InterpreterLTY.interpreter.parser.AST;
import InterpreterLTY.interpreter.parser.Parser;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lugty on 5/2/17.
 */
public class ParserLYFn {

    public static Double evaluateSentence(String sentence) throws Exception{
        return evaluate(sentence, null);
    }

    public static Double evaluateSentence(String sentence, HashMap<String, Double> varTable) throws Exception{
        return evaluate(sentence, varTable);
    }

    private static Double evaluate(String sentence, HashMap<String, Double> varTable) throws Exception{
        Lexer lexer = new Lexer(sentence);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = (varTable==null)?new Interpreter(tree):new Interpreter(tree, varTable);
        Object result = interpreter.interprete();
        if(result instanceof List){
            List<Object> responseList = (List<Object>)result;
            for(Object obj: responseList){
                if(obj != null && obj instanceof Double)
                    return (Double) obj;
            }
            throw new Exception("No expected response, parser");
        }else
            throw new Exception("No expected response, parser");
    }
}
