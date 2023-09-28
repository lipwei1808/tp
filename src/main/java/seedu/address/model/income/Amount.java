package seedu.address.model.income;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Income's amount.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(double)}
 */
public class Amount {
    public static final String MESSAGE_CONSTRAINTS =
            "Income amount should be positive number.";

    public final Double amount;

    public Amount(Double amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = amount;
    }

    /**
     * Returns true if a given amount is a non-negative value.
     */
    public static boolean isValidAmount(double amount) {
        return amount > 0.00;
    }
    @Override
    public String toString() {
        return Double.toString(this.amount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Amount)) {
            return false;
        }

        Amount otherAmount = (Amount) other;
        return this.amount == otherAmount.amount;
    }

    @Override
    public int hashCode() {
        return this.amount.hashCode();
    }
}
