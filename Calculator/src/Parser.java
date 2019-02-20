
public class Parser {
    private Lexer lexer;
    private Lexeme current;

    public Parser(Lexer lex) throws LexerException {
        this.lexer = lex;
        current = lexer.getNextLexeme();
    }

    public int calculate() throws LexerException, ParserException {
        int result = parseExpression();
        if (current.type != LexemeType.EOF) {
            throw new ParserException("Invalid expression");
        }
        return result;
    }

    private int parseExpression() throws LexerException, ParserException {
        int result = parseTerm();
        while (true) {
            if (current.type == LexemeType.PLUS) {
                current = lexer.getNextLexeme();
                result += parseTerm();
            }
            else if (current.type == LexemeType.MINUS) {
                current = lexer.getNextLexeme();
                result -= parseTerm();
            }
            else break;
        }
        return result;
    }

    private int parseTerm() throws LexerException, ParserException {
        int result = parseFactor();
        while (true) {
            if (current.type == LexemeType.MUL) {
                current = lexer.getNextLexeme();
                result *= parseFactor();
            }
            else if (current.type == LexemeType.DIV) {
                current = lexer.getNextLexeme();
                result /= parseFactor();
            }
            else break;
        }
        return result;
    }

    private int parseFactor() throws LexerException, ParserException {
        int result = parsePower();
        if (current.type == LexemeType.POW) {
            current = lexer.getNextLexeme();
            result = (int) Math.pow(result, parseFactor());
        }
        return result;
    }

    private int parsePower() throws LexerException, ParserException {
        if (current.type == LexemeType.MINUS) {
            current = lexer.getNextLexeme();
            return -parseAtom();
        }
        return parseAtom();
    }

    private int parseAtom() throws LexerException, ParserException {
        int result;
        if (current.type == LexemeType.NUM) {
            try {
                result = Integer.parseInt(current.data);
            } catch (NumberFormatException e) {
                throw new ParserException("Bad number format: " + e.getMessage());
            }
            current = lexer.getNextLexeme();
            return result;
        }
        if (current.type == LexemeType.BRACE_OPEN) {
            current = lexer.getNextLexeme();
            result = parseExpression();
            if (current.type != LexemeType.BRACE_CLOSE) {
                throw new ParserException("Missing matching brace \")\" ");
            }
            current = lexer.getNextLexeme();
            return result;
        }
        throw new ParserException("Invalid expression");
    }

}
