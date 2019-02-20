import java.io.InputStreamReader;
import java.io.Reader;

public class Main { //TODO: read from file
    public static void main(String args[]) {
        Reader reader = new InputStreamReader(System.in);
        try {
            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer);
            System.out.println(parser.calculate());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
