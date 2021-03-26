/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.grammar;

import core.dto.GeoRefInfoDTO;
import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 *
 * @author Ricardo Branco
 */
public class Grammar {
    
    private String frase;
    
    private final int MIN_MEDIUM = 10;
    private final int MAX_MEDIUM = 20;
    
    public Grammar(String frase) {
        this.frase = frase;
    }
    
    public String parse() {
        String value = "";
        String aux[] = frase.split(" ");
        
        if (Integer.valueOf(aux[1]) < MIN_MEDIUM) {
            value = "LOW";
        } else if (Integer.valueOf(aux[1]) > MIN_MEDIUM && Integer.valueOf(aux[1]) < MAX_MEDIUM) {
            value = "MEDIUM";
        } else {
            value = "HIGH";
        }
        return aux[0] + " " + value;
    }

    public String parseWithListener() throws IOException {
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(frase));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);
        ParseTree tree = parser.frase(); // parse
        ParseTreeWalker walker = new ParseTreeWalker();
        EvalListener eListener = new EvalListener();
        walker.walk(eListener, tree);
        return eListener.getResult(); // print the result
    }
}
