import de.koehler.parser.SimpleQueryParser;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.text.ParseException;

public class SimpleParserTest {

    @Test
    public void firstTest() throws ParseException, de.koehler.parser.ParseException {
        SimpleQueryParser parser = new SimpleQueryParser(new StringReader("Hello & JavaCC"));
        parser.expression();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void notAllowed() throws ParseException, de.koehler.parser.ParseException {
        SimpleQueryParser parser = new SimpleQueryParser(new StringReader("(Hello & JavaCC"));
        parser.expression();
    }
}
