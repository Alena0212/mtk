public class Lexeme {
    public LexemeType type;
    public String data;

    public Lexeme(LexemeType t) {
        this.type = t;
    }

    public Lexeme(LexemeType t, String d) {
        this.type = t;
        this.data = d;
    }
}
