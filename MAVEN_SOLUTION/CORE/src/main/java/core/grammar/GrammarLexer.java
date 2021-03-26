package core.grammar;
// Generated from Grammar.g4 by ANTLR 4.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NUM=1, VALUE=2, WS=3;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'"
	};
	public static final String[] ruleNames = {
		"NUM", "VALUE", "WS"
	};


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\5\36\b\1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\3\2\3\2\3\2\6\2\r\n\2\r\2\16\2\16\5\2\21\n\2\3\3\6\3"+
		"\24\n\3\r\3\16\3\25\3\4\6\4\31\n\4\r\4\16\4\32\3\4\3\4\2\2\5\3\3\5\4\7"+
		"\5\3\2\6\3\2\62;\3\2\63;\4\2C\\c|\5\2\13\f\17\17\"\"!\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\3\20\3\2\2\2\5\23\3\2\2\2\7\30\3\2\2\2\t\21\t\2\2"+
		"\2\n\f\t\3\2\2\13\r\t\2\2\2\f\13\3\2\2\2\r\16\3\2\2\2\16\f\3\2\2\2\16"+
		"\17\3\2\2\2\17\21\3\2\2\2\20\t\3\2\2\2\20\n\3\2\2\2\21\4\3\2\2\2\22\24"+
		"\t\4\2\2\23\22\3\2\2\2\24\25\3\2\2\2\25\23\3\2\2\2\25\26\3\2\2\2\26\6"+
		"\3\2\2\2\27\31\t\5\2\2\30\27\3\2\2\2\31\32\3\2\2\2\32\30\3\2\2\2\32\33"+
		"\3\2\2\2\33\34\3\2\2\2\34\35\b\4\2\2\35\b\3\2\2\2\7\2\16\20\25\32\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}