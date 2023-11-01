package unicash.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static unicash.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static unicash.logic.commands.CommandTestUtil.INVALID_TRANSACTION_NAME_DESC;
import static unicash.logic.commands.CommandTestUtil.TRANSACTION_NAME_DESC_NUS;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import unicash.commons.util.ToStringBuilder;
import unicash.logic.UniCashMessages;
import unicash.logic.parser.exceptions.ParseException;
import unicash.model.category.Category;
import unicash.model.transaction.Location;
import unicash.model.transaction.predicates.TransactionContainsAllKeywordsPredicate;


/**
 * A class to test the FindCommandParser.
 */
public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        String emptyArgument = "";
        assertThrows(ParseException.class, () -> parser.parse(emptyArgument));

    }

    @Test
    public void parse_repeatedType_failure() {
        assertParseFailure(parser, TRANSACTION_NAME_DESC_NUS + TRANSACTION_NAME_DESC_NUS,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }


    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TRANSACTION_NAME_DESC,
                unicash.model.transaction.Name.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, INVALID_LOCATION_DESC, Location.MESSAGE_CONSTRAINTS);

        // invalid datetime
        assertParseFailure(parser, INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void sameFindCommandParser_equalsTrue() {
        FindCommandParser parser = new FindCommandParser();
        assertTrue(parser.equals(parser));
        assertTrue(parser.equals(new FindCommandParser()));

    }

    @Test
    public void equalsMethod_differentCommandTypes_returnsFalse() {
        FindCommandParser findCommandParser = new FindCommandParser();
        ListCommandParser listCommandParser = new ListCommandParser();
        assertNotEquals(listCommandParser, findCommandParser);
        assertFalse(findCommandParser.equals(listCommandParser));
    }

    @Test
    public void equalsMethod_nullInput_returnsFalse() {
        assertNotEquals(null, new FindCommandParser());
    }

    @Test
    public void toStringTest() {
        FindCommandParser findCommandParser = new FindCommandParser();
        TransactionContainsAllKeywordsPredicate findPredicate =
                new TransactionContainsAllKeywordsPredicate();

        String expected = new ToStringBuilder(new FindCommandParser())
                .add("findPredicate", findPredicate).toString();
        assertEquals(expected, findCommandParser.toString());
    }

}
