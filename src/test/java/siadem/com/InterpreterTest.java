package siadem.com;

import InterpreterLTY.interpreter.Interpreter;
import InterpreterLTY.interpreter.lexer.Lexer;
import InterpreterLTY.interpreter.parser.AST;
import InterpreterLTY.interpreter.parser.Parser;
import main.ParserLYFn;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by lugty on 4/25/17.
 */
public class InterpreterTest {

    @Test
    public void testInterpreter01()throws Exception{
        String text = "5 + 10 +5 * 5 / 2";

        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = new Interpreter(tree);
        Object result = interpreter.interprete();

        System.out.println(result);
    }

    @Test
    public void testInterpreter02()throws Exception{
        String text = "IF 5 > 10  THEN 5 + 10 +5 * 5 / 2 ELSE 10+ 5 END";

        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = new Interpreter(tree);
        Object result = interpreter.interprete();

        System.out.println(result);
    }

    @Test
    public void testInterpreter03()throws Exception{
        String text = "a = 5*5; IF 5 > 10  THEN 5 + a +5 * 5 / 2 ELSE 10+ a END";

        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = new Interpreter(tree);
        Object result = interpreter.interprete();

        System.out.println(result);
    }

    @Test
    public void testInterpreter04()throws Exception{
        HashMap<String, Double> varTable = new HashMap<String, Double>();
        varTable.put("a", 10.0);

        String text = "IF 5 > 10  THEN 5 + a +5 * 5 / 2 ELSE 10+ a END";

        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = new Interpreter(tree, varTable);
        Object result = interpreter.interprete();

        System.out.println(result);
    }

    @Test
    public void testInterpreter05()throws Exception{
        HashMap<String, Double> varTable = new HashMap<String, Double>();
        varTable.put("a", 10.0);

        String text1 = "IF 5 > 10  THEN 5 + a +5 * 5 / 2 ELSE 10+ a END";
        Double resp1 = ParserLYFn.evaluateSentence(text1, varTable);
        System.out.println("********************************************");
        System.out.println(resp1);

        String text2 = "IF 5 > 10  THEN 5 + 8 +5 * 5 / 2 ELSE 10+ 8 END";
        Double resp2 = ParserLYFn.evaluateSentence(text2, varTable);
        System.out.println("********************************************");
        System.out.println(resp2);
    }

    @Test
    public void testInterpreter06()throws Exception{
        HashMap<String, Double> varTable = new HashMap<String, Double>();
        varTable.put("a", 10.0);

        String text = "IF a > 10  THEN a + a +5 * 5 / 2 ELSE 10+ a END";

        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = new Interpreter(tree, varTable);
        Object result = interpreter.interprete();

        System.out.println(result);
    }


    @Test
    public void testInterpreter07()throws Exception{
        HashMap<String, Double> varTable = new HashMap<String, Double>();
        varTable.put("SD", 10.0);
        varTable.put("HD", 10.0);

        String text = "(SD/HD)*2";

        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = new Interpreter(tree, varTable);
        Object result = interpreter.interprete();

        System.out.println(result);
    }

    @Test
    public void testInterpreter08()throws Exception{
        HashMap<String, Double> varTable = new HashMap<String, Double>();
        varTable.put("SUBE", 10.0);

        String text = "-SUBE * 10";

        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = new Interpreter(tree, varTable);
        Object result = interpreter.interprete();

        System.out.println(result);
    }

    @Test
    public void testInterpreter09()throws Exception{
        HashMap<String, Double> varTable = new HashMap<String, Double>();
        varTable.put("P011", 20000.0);
        varTable.put("SM", 1000.0);

        String text = "" +
                "IF P011 > (15*SM) THEN " +
                "   IF 10>5 THEN" +
                "       999" +
                "   ELSE" +
                "       888" +
                "   END " +
                "ELSE " +
                "   0 " +
                "END";
        //String text = "IF a > 10  THEN a + a +5 * 5 / 2 ELSE 10+ a END";

        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = new Interpreter(tree, varTable);
        Object result = interpreter.interprete();

        System.out.println(result);
    }

    @Test
    public void testInterpreter10()throws Exception{
        HashMap<String, Double> varTable = new HashMap<String, Double>();
        varTable.put("GPO", 4.0);
        varTable.put("SDO", 1000.0);
        varTable.put("DT", 15.0);

        String text = "" +
                "IF GPO==4.0 THEN " +
                "   ((SDO+P116+P199)/DT) " +
                "ELSE " +
                "   IF GPO==3.0 THEN " +
                "       (SDO/DT) " +
                "   ELSE " +
                "       0 " +
                "   END " +
                "END";
        //String text = "IF a > 10  THEN a + a +5 * 5 / 2 ELSE 10+ a END";

        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = new Interpreter(tree, varTable);
        Object result = interpreter.interprete();

        System.out.println(result);
    }

    @Test
    public void testInterpreter11()throws Exception{
        HashMap<String, Double> varTable = new HashMap<String, Double>();
        varTable.put("GPO", 4.0);
        varTable.put("SDO", 1069.62);
        varTable.put("P199", 275.8);
        varTable.put("NULL", null);
        varTable.put("P800", 10.0);
        varTable.put("P806", 10.0);

        String text = "" +
                "IF GPO==3 THEN " +
                "   (SDO+P150)*0.05*P803 " +
                "ELSE " +
                "   IF GPO==4 THEN " +
                "       IF P806==NULL THEN " +
                "           100 " +
                "       ELSE " +
                "           P800*P806 " +
                "       END " +
                "   ELSE " +
                "       IF GPO==5 THEN " +
                "           IF P199==NULL THEN " +
                "               (SDO+P150)*0.028 " +
                "           ELSE " +
                "               (SDO+P150+P199)*0.028 " +
                "           END " +
                "       ELSE " +
                "           0 " +
                "       END " +
                "   END " +
                "END";

        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        AST tree = parser.parse();

        Interpreter interpreter = new Interpreter(tree, varTable);
        Object result = interpreter.interprete();

        System.out.println(result);
    }

}
