package seedu.address.model.income;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Person;
import seedu.address.testutil.IncomeBuilder;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalIncomes.WORK_AT_LIHO;
import static seedu.address.testutil.TypicalIncomes.ALLOWANCE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

public class IncomeTest {
    @Test
    public void equals() {
        // same values -> returns true
        Income workAtLihoCopy = new IncomeBuilder(WORK_AT_LIHO).build();
        assertTrue(WORK_AT_LIHO.equals(workAtLihoCopy));

        // same object -> returns true
        assertTrue(WORK_AT_LIHO.equals(WORK_AT_LIHO));

        // null -> returns false
        assertFalse(WORK_AT_LIHO.equals(null));

        // different type -> returns false
        assertFalse(WORK_AT_LIHO.equals(5));

        // different income -> returns false
        assertFalse(WORK_AT_LIHO.equals(ALLOWANCE));

        // different name -> returns false
        Income editedWorkAtLiho = new IncomeBuilder(WORK_AT_LIHO).withName(VALID_NAME_BOB).build();
        assertFalse(WORK_AT_LIHO.equals(editedWorkAtLiho));

        // different value -> returns false
        editedWorkAtLiho = new IncomeBuilder(WORK_AT_LIHO).withAmount(VALID_AMOUNT).build();
        assertFalse(ALICE.equals(editedWorkAtLiho));
    }

    @Test
    public void toStringMethod() {
        String expected = Income.class.getCanonicalName() + "{name=" + WORK_AT_LIHO.getName() + ", amount=" + WORK_AT_LIHO.getAmount()
                + ", date=" + WORK_AT_LIHO.getDate() + "}";
        assertEquals(expected, WORK_AT_LIHO.toString());
    }
}
