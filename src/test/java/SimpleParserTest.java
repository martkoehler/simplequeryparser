import de.koehler.parser.SimpleQueryParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringReader;
import java.text.ParseException;

@RunWith(JUnit4.class)
public class SimpleParserTest {

    private SimpleQueryParser parser;

    @Before
    public void setUp() {

    }

    @Test
    public void parenthesis() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(new StringReader("(a)"));
        parser.expression();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void missingParenthesis() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(new StringReader("(a"));
        parser.expression();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void emptyParenthesis() throws ParseException, de.koehler.parser.ParseException{
        parser = new SimpleQueryParser(new StringReader("()"));
        parser.expression();
    }

    @Test
    public void or() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(new StringReader("a | b"));
        parser.expression();
    }

    @Test
    public void and() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(new StringReader("a & b"));
        parser.expression();
    }

    @Test
    public void not() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(new StringReader("!c"));
        parser.expression();
    }

    @Test
    public void apostrophe() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(new StringReader("'a'"));
        parser.expression();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void missingApostrophe() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(new StringReader("'a"));
        parser.expression();
    }

    @Test
    public void expressionNot() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(new StringReader("!(a & b)"));
        parser.expression();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void illegalOperator() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(new StringReader("!(a !& b)"));
        parser.expression();
    }

    @Test
    public void complexExpression() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(new StringReader("(a & b) | !c & 'd'"));
        parser.expression();
    }

    @Test
    public void nested() throws ParseException, de.koehler.parser.ParseException{
        parser = new SimpleQueryParser(new StringReader("'('a')'"));
        parser.expression();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void nestedUnordered() throws ParseException, de.koehler.parser.ParseException{
        parser = new SimpleQueryParser(new StringReader("'('a)''"));
        parser.expression();
    }
}
