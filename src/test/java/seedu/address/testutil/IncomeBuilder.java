package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.model.income.Income;
import seedu.address.model.income.Amount;
import seedu.address.model.income.Name;

/**
 * A utility class to help with building Income objects.
 */
public class IncomeBuilder {

    public static final String DEFAULT_NAME = "Work at liho";
    public static final Double DEFAULT_AMOUNT = 3.0;
    public static final LocalDate DEFAULT_DATE = LocalDate.now();

    private Name name;
    private Amount amount;
    private LocalDate date;

    /**
     * Creates a {@code IncomeBuilder} with the default details.
     */
    public IncomeBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        date = DEFAULT_DATE;
    }

    /**
     * Initializes the IncomeBuilder with the data of {@code incomeToCopy}.
     */
    public IncomeBuilder(Income incomeToCopy) {
        name = incomeToCopy.getName();
        amount = incomeToCopy.getAmount();
        date = incomeToCopy.getDate();
    }

    /**
     * Sets the {@code Name} of the {@code Income} that we are building.
     */
    public IncomeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Income} that we are building.
     */
    public IncomeBuilder withAmount(Double amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code LocalDate} of the {@code Income} that we are building.
     */
    public IncomeBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }


    public Income build() {
        return new Income(name, amount, date);
    }

}
