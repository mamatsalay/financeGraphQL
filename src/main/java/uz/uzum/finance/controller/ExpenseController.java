package uz.uzum.finance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import uz.uzum.finance.model.Expense;
import uz.uzum.finance.service.ExpenseService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @MutationMapping
    public Expense addExpense(@Argument BigDecimal amount,
                              @Argument String description,
                              @Argument LocalDate date,
                              @Argument List<String> customLabelNames) {

        return expenseService.addExpense(amount, description, date, customLabelNames);

    }

}
