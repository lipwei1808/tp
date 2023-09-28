package seedu.address.testutil;

import seedu.address.model.income.Income;

import java.time.LocalDate;

public class TypicalIncomes {
    public static final Income WORK_AT_LIHO = new IncomeBuilder().withName("Work at liho")
            .withAmount(300.0)
            .withDate(LocalDate.of(2023, 9, 31))
            .build();

    public static final Income ALLOWANCE = new IncomeBuilder().withName("Allowance")
            .withAmount(200.0)
            .withDate(LocalDate.of(2023, 10, 31))
            .build();
}
