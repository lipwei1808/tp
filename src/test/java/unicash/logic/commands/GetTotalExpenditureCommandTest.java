package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import unicash.commons.enums.TransactionType;
import unicash.logic.commands.exceptions.CommandException;
import unicash.model.Model;
import unicash.model.ModelManager;
import unicash.model.UniCash;
import unicash.model.UserPrefs;
import unicash.model.category.Category;
import unicash.testutil.TransactionBuilder;

public class GetTotalExpenditureCommandTest {

    private static final Model BASE_MODEL = getModel();

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        var command = new GetTotalExpenditureCommand(10, null);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_negativeMonth_throwsCommandException() {
        var command = new GetTotalExpenditureCommand(-12, null);
        assertThrows(CommandException.class, () -> command.execute(BASE_MODEL));
    }

    @Test
    public void execute_monthGreaterThan12_throwsCommandException() {
        var command = new GetTotalExpenditureCommand(13, null);
        assertThrows(CommandException.class, () -> command.execute(BASE_MODEL));
    }

    @Test
    public void execute_validMonthWithOnlyOneMonth_filtersOnlyExpenses() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("income").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        var command = new GetTotalExpenditureCommand(8, null);
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(2, filteredResult.size());
        for (var result : filteredResult) {
            assertEquals(TransactionType.EXPENSE, result.getType().type);
        }
    }

    @Test
    public void execute_multipleMonthsOnly_filtersOnlySelectedMonths() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withDateTime("18-07-2001 00:00").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        var command = new GetTotalExpenditureCommand(8, null);
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(2, filteredResult.size());
        for (var result : filteredResult) {
            assertEquals(TransactionType.EXPENSE, result.getType().type);
            assertEquals(8, result.getDateTime().getDateTime().getMonthValue());
        }
    }

    @Test
    public void execute_multipleCategoriesOnly_filtersOnlySelectedCategory() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Food").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Others").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        var command = new GetTotalExpenditureCommand(8, new Category("Food"));
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(2, filteredResult.size());
        for (var result : filteredResult) {
            assertEquals(TransactionType.EXPENSE, result.getType().type);
            assertTrue(result.getCategories().asUnmodifiableObservableList()
                    .stream().anyMatch(cat -> cat.equals(new Category("Food"))));
        }
    }

    @Test
    public void execute_multipleMonthsAndCategories_filtersForSelectedMonthAndCategory() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("expense").withDateTime("23-06-2001 00:00").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Others").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        var command = new GetTotalExpenditureCommand(8, new Category("Food"));
        command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(1, filteredResult.size());
        for (var result : filteredResult) {
            assertEquals(TransactionType.EXPENSE, result.getType().type);
            assertTrue(result.getCategories().asUnmodifiableObservableList()
                    .stream().anyMatch(cat -> cat.equals(new Category("Food"))));
            assertEquals(8, result.getDateTime().getDateTime().getMonthValue());
        }
    }

    @Test
    public void execute_multipleMonthsAndCategories_returnsValidTotalExpenditure() throws CommandException {
        var model = getModel();
        model.addTransaction(new TransactionBuilder().withType("expense").withDateTime("23-06-2001 00:00").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withCategories("Others").build());
        model.addTransaction(new TransactionBuilder().withType("expense").build());
        model.addTransaction(new TransactionBuilder().withType("expense").withAmount(133.15).build());
        var command = new GetTotalExpenditureCommand(8, new Category("Food"));
        var result = command.execute(model);
        var filteredResult = model.getFilteredTransactionList();
        assertEquals(2, filteredResult.size());
        assertEquals(
                String.format(GetTotalExpenditureCommand.MESSAGE_SUCCESS, "AUGUST", 123.45 + 133.15),
                result.getFeedbackToUser()
        );
    }

    @Test
    public void toString_noInput_returnsCommandStringFormatted() {
        var command = new GetTotalExpenditureCommand(8, new Category("Food"));
        var toStringResult = command.toString();
        String expected = GetTotalExpenditureCommand.class.getCanonicalName() + "{month=8, categoryFilter=[Food]}";
        assertEquals(expected, toStringResult);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        var command = new GetTotalExpenditureCommand(8, new Category("Food"));
        assertEquals(command, command);
    }

    @Test
    public void equals_notGetTotalExpenditureCommand_returnsFalse() {
        var command = new GetTotalExpenditureCommand(8, new Category("Food"));
        assertNotEquals(command, new ClearTransactionsCommand());
    }

    @Test
    public void equals_sameMonthAndCategoryFilter_returnsTrue() {
        var command = new GetTotalExpenditureCommand(8, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, new Category("Food"));
        assertEquals(command, other);
    }

    @Test
    public void equals_sameMonthDifferentCategoryFilter_returnsFalse() {
        var command = new GetTotalExpenditureCommand(8, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, new Category("Others"));
        assertNotEquals(command, other);
    }

    @Test
    public void equals_differentMonthSameCategoryFilter_returnsFalse() {
        var command = new GetTotalExpenditureCommand(7, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, new Category("Food"));
        assertNotEquals(command, other);
    }

    @Test
    public void equals_differentMonthAndCategoryFilter_returnsFalse() {
        var command = new GetTotalExpenditureCommand(7, new Category("Food"));
        var other = new GetTotalExpenditureCommand(8, new Category("Others"));
        assertNotEquals(command, other);
    }

    private static Model getModel() {
        return new ModelManager(new UniCash(), new UserPrefs());
    }
}
