import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private Parser createParser(String input) throws ParserException, LexerException {
        return new Parser(new Lexer(new StringReader(input)));
    }
    @Test
    void calculate1() {
        String input = "1";
        try {
            Parser p = createParser(input);
            assertEquals( 1, p.calculate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate2() {
        String input = "-1";
        try {
            Parser p = createParser(input);
            assertEquals( -1, p.calculate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void calculate3() {
        String input = "1+2-3+4-5+6";
        try {
            Parser p = createParser(input);
            assertEquals( 1+2-3+4-5+6, p.calculate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void calculate4() {
        String input = "1-2+3*4/2-6+7*8/4";
        try {
            Parser p = createParser(input);
            assertEquals( 1-2+3*4/2-6+7*8/4, p.calculate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void calculate5() {
        String input = "1+2-3*4^2-6*7*3^4";
        try {
            Parser p = createParser(input);
            assertEquals( 1+2-3*((int) Math.pow(4,2))-6*7*((int)Math.pow(3,4)), p.calculate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate6() {
        String input = "1^2^3^4";
        try {
            Parser p = createParser(input);
            assertEquals((int) Math.pow(1,(int)Math.pow(2,(int)Math.pow(3, 4))), p.calculate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate7() {
        String input = "-(-(-(-(-1))))";
        try {
            Parser p = createParser(input);
            assertEquals(-(-(-(-(-1)))), p.calculate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void calculate8() {
        String input = "10*(22-31)+15*302*12/2+2^7";
        try {
            Parser p = createParser(input);
            assertEquals(10*(22-31)+15*302*12/2 + (int)Math.pow(2, 7), p.calculate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}