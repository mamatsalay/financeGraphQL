package uz.uzum.finance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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
    public Expense createExpense(@Argument BigDecimal amount,
                                 @Argument String description,
                                 @Argument LocalDate date,
                                 @Argument List<String> customLabelNames) {

        return expenseService.createExpense(amount, description, date, customLabelNames);

    }

    @MutationMapping
    public Expense updateExpense(@Argument Long id,
                                 @Argument BigDecimal amount,
                                 @Argument String description,
                                 @Argument LocalDate date,
                                 @Argument List<String> customLabelNames){
        return expenseService.updateExpense(id, amount, description, date, customLabelNames);
    }

    @MutationMapping
    public String deleteExpense(@Argument Long id) {
        return expenseService.deleteExpense(id);
    }

    @QueryMapping
    public List<Expense> getAllExpenses(@Argument @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate StartDate,
                                        @Argument @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate EndDate,
                                        @Argument List<String> customLabelNames) {
        return expenseService.getAllExpenses(StartDate, EndDate, customLabelNames);
    }

}
