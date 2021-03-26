/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.grammar;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javafx.util.Pair;

/**
 *
 * @author Ricardo Branco
 */
public class EvalListener extends GrammarBaseListener {
    
    private final int MIN_MEDIUM = 20;
    private final int MAX_MEDIUM = 30;

    private final Stack<String> stack = new Stack<>();

    public String getResult() {
        return stack.peek();
    }

    @Override
    public void enterFrase(GrammarParser.FraseContext ctx) {
        String value = "";
        if (Integer.valueOf(ctx.NUM().getText()) < MIN_MEDIUM) {
            value = "LOW";
        } else if (Integer.valueOf(ctx.NUM().getText()) > MIN_MEDIUM && Integer.valueOf(ctx.NUM().getText()) < MAX_MEDIUM) {
            value = "MEDIUM";
        } else {
            value = "HIGH";
        }
        
        stack.push(ctx.VALUE().getText() + value);
    }
}
