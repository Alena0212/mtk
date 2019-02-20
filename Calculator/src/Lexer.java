import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class Lexer {
    private Reader reader;
    int current;

    public Lexer(Reader rd) throws LexerException {
        this.reader = rd;
        try {
            current = this.reader.read();
        } catch (IOException e) {
            throw new LexerException("Problems with input" + e.getMessage());
        }
    }

    public Lexeme getNextLexeme() throws LexerException {
        try {
            while (Character.isSpaceChar(current)) {
                current = reader.read();
            }

            switch (current) {
                case '+':
                    current = reader.read();
                    return new Lexeme(LexemeType.PLUS);
                case '-':
                    current = reader.read();
                    return new Lexeme(LexemeType.MINUS);
                case '*':
                    current = reader.read();
                    return new Lexeme(LexemeType.MUL);
                case '/':
                    current = reader.read();
                    return new Lexeme(LexemeType.DIV);
                case '^':
                    current = reader.read();
                    return new Lexeme(LexemeType.POW);
                case '(':
                    current = reader.read();
                    return new Lexeme(LexemeType.BRACE_OPEN);
                case ')':
                    current = reader.read();
                    return new Lexeme(LexemeType.BRACE_CLOSE);
                default:
                    if (current < 0 || current == '\r' || current == '\n') {
                        return new Lexeme(LexemeType.EOF);//EOF reached?
                    }
                    if (Character.isDigit(current)) {
                        ArrayList<String> digits = new ArrayList<>();
                        do {
                            digits.add(Character.toString((char) current));
                            current = reader.read();
                        } while (Character.isDigit(current));
                        return new Lexeme(LexemeType.NUM, String.join("", digits));
                    }
                    throw new LexerException("Unsupported symbol");
            }
        } catch (IOException e) {
            throw new LexerException("Problems with input" + e.getMessage());
        }
    }
}
