package core.grammar;
// Generated from Grammar.g4 by ANTLR 4.3
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#frase}.
	 * @param ctx the parse tree
	 */
	void enterFrase(@NotNull GrammarParser.FraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#frase}.
	 * @param ctx the parse tree
	 */
	void exitFrase(@NotNull GrammarParser.FraseContext ctx);
}