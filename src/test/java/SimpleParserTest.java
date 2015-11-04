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

    @Test
    public void parenthesis() throws ParseException, de.koehler.parser.ParseException {
        parse("(a)");
    }


    @Test(expected = de.koehler.parser.ParseException.class)
    public void missingParenthesis() throws ParseException, de.koehler.parser.ParseException {
        parse("(a");
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void emptyParenthesis() throws ParseException, de.koehler.parser.ParseException{
        parse("()");
    }

    @Test
    public void or() throws ParseException, de.koehler.parser.ParseException {
        parse("a | b");
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void orNotComplete() throws ParseException, de.koehler.parser.ParseException {
        parse("a | ");
    }

    @Test
    public void and() throws ParseException, de.koehler.parser.ParseException {
        parse("a & b");
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void andNotComplete() throws ParseException, de.koehler.parser.ParseException {
        parse("a & ");
    }

    @Test
    public void not() throws ParseException, de.koehler.parser.ParseException {
        parse("!c");
    }

    @Test
    public void apostrophe() throws ParseException, de.koehler.parser.ParseException {
        parse("'a'");
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void missingApostrophe() throws ParseException, de.koehler.parser.ParseException {
        parse("'a");
    }

    @Test
    public void expressionNot() throws ParseException, de.koehler.parser.ParseException {
        parse("!(a & b)");
    }

    @Test(expected = de.koehler.parser.ParseException.class)
    public void illegalOperator() throws ParseException, de.koehler.parser.ParseException {
        parse("!(a !& b)");
    }

    @Test
    public void complexExpression() throws ParseException, de.koehler.parser.ParseException {
        parse("(.änders & b | 'e)') | !c & '(d'");
    }

    @Test
    public void nested() throws ParseException, de.koehler.parser.ParseException{
        parse("'('a')'");
    }

    @Test
    public void cut() throws ParseException, de.koehler.parser.ParseException{
        parse("'b'a'c'");
    }

    @Test
    public void moreAnd() throws ParseException, de.koehler.parser.ParseException{
        parse("'b' & a & 'c'");
    }

    @Test
    public void wildcardEnd() throws ParseException, de.koehler.parser.ParseException{
        parse("abc%");
    }

    @Test
    public void wildcardBegin() throws ParseException, de.koehler.parser.ParseException{
        parse("%abc");
    }

    @Test
    public void wildcard() throws ParseException, de.koehler.parser.ParseException{
        parse("%ab%c%");
    }

    @Test
    public void underscoreWildcard() throws ParseException, de.koehler.parser.ParseException{
        parse("foo_bar");
    }

    @Test
    public void beginWhitespaceWithinApostrophe() throws ParseException, de.koehler.parser.ParseException{
        parse("' abc'");
    }

    @Test
    public void endWhitespaceWithinApostrophe() throws ParseException, de.koehler.parser.ParseException{
        parse("'abc '");
    }

    @Test
    public void whitespaceWithinApostrophe() throws ParseException, de.koehler.parser.ParseException{
        parse("'ab c'");
    }

    @Test
    public void whitespace() throws ParseException, de.koehler.parser.ParseException{
        parse("ab c");
    }

    @Test
    public void empty() throws ParseException, de.koehler.parser.ParseException{
        parse("#");
    }

    @Test
    public void notEmpty() throws ParseException, de.koehler.parser.ParseException{
        parse("!#");
    }

    private void parse(String query) throws de.koehler.parser.ParseException {
        parser = new SimpleQueryParser(mock(CriteriaBuilder.class), query);
        parser.parse();
    }
}
