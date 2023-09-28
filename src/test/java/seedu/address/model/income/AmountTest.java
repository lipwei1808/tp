package seedu.address.model.income;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class AmountTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        double[] invalidAmounts = new double[] {-3.0, -3, 0};
        for (double invalidAmount : invalidAmounts) {
            assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
        }
    }

    @Test
    public void isValidAmount() {
        // invalid name
        assertFalse(Amount.isValidAmount(-3)); // negative integer
        assertFalse(Amount.isValidAmount(-3.0)); // negative float
        assertFalse(Amount.isValidAmount(0.0)); // zero

        // valid name
        assertTrue(Amount.isValidAmount(3)); // integer
        assertTrue(Amount.isValidAmount(3.0)); // float
    }

    @Test
    public void equals() {
        Amount amount = new Amount(3.0);

        // same values -> returns true
        assertTrue(amount.equals(new Amount(3.0)));

        // same object -> returns true
        assertTrue(amount.equals(amount));

        // same monetary value -> returns true
        assertTrue(amount.equals(new Amount((double) 3)));

        // null -> returns false
        assertFalse(amount.equals(null));

        // different types -> returns false
        assertFalse(amount.equals("hi"));

        // different values -> returns false
        assertFalse(amount.equals(new Amount(4.0)));
    }
}
