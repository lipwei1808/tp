package seedu.address.model.income;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Income {
    private final Name name;
    private final Amount amount;
    private final LocalDate date;

    /**
     * Every field must be present and not null.
     */
    public Income(Name name, Amount amount, LocalDate date) {
        requireAllNonNull(name, amount, date);
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public Name getName() {
        return name;
    }

    public Amount getAmount() {
        return this.amount;
    }

    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Returns true if both income have the same data fields.
     * This defines a stronger notion of equality between two incomes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Income)) {
            return false;
        }

        Income otherIncome = (Income) other;
        return name.equals(otherIncome.name)
                && amount.equals(otherIncome.amount)
                && date.equals(otherIncome.date);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount, date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("amount", amount)
                .add("date", date)
                .toString();
    }

}
