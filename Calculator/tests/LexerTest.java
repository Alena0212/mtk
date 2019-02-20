import java.io.StringReader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void getNextLexeme() {
        String input = "+-*/^()42\n";
        LexemeType[] outputs = LexemeType.values();
        try {
            Lexer lexer = new Lexer(new StringReader(input));
            for (int i = 0; i < outputs.length; ++i) {
                assertEquals(outputs[i], lexer.getNextLexeme().type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}