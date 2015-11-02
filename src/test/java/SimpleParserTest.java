import de.koehler.parser.SimpleQueryParser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.StringReader;
import java.text.ParseException;

import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class SimpleParserTest {

    private SimpleQueryParser parser;

    @Before
    public void setUp() {

    }

    @Test
    public void parenthesis() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "(a)");
        parser.parse();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void missingParenthesis() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "(a");
        parser.parse();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void emptyParenthesis() throws ParseException, de.koehler.parser.ParseException{
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "()");
        parser.parse();
    }

    @Test
    public void or() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "a | b");
        parser.parse();
    }

    @Test
    public void and() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "a & b");
        parser.parse();
    }

    @Test
    public void not() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "!c");
        parser.parse();
    }

    @Test
    public void apostrophe() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "'a'");
        parser.parse();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void missingApostrophe() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "'a");
        parser.parse();
    }

    @Test
    public void expressionNot() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "!(a & b)");
        parser.parse();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void illegalOperator() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "!(a !& b)");
        parser.parse();
    }

    @Test
    public void complexExpression() throws ParseException, de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "(a & b) | !c & 'd'");
        parser.parse();
    }

    @Test
    public void nested() throws ParseException, de.koehler.parser.ParseException{
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "'('a')'");
        parser.parse();
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void nestedUnordered() throws ParseException, de.koehler.parser.ParseException{
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), "'('a)''");
        parser.parse();
    }
}
