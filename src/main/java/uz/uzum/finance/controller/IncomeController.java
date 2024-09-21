package uz.uzum.finance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import uz.uzum.finance.model.Income;
import uz.uzum.finance.service.IncomeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IncomeController {
    
    private final IncomeService incomeService;

    @MutationMapping
    public Income addIncome(@Argument BigDecimal amount,
                              @Argument String description,
                              @Argument LocalDate date,
                              @Argument List<String> customLabelNames) {

        return incomeService.addIncome(amount, description, date, customLabelNames);

    }

    @MutationMapping
    public Income updateIncome(@Argument Long id,
                                 @Argument BigDecimal amount,
                                 @Argument String description,
                                 @Argument LocalDate date,
                                 @Argument List<String> customLabelNames){
        return incomeService.updateIncome(id, amount, description, date, customLabelNames);
    }

    @MutationMapping
    public String deleteIncome(@Argument Long id) {
        return incomeService.deleteIncome(id);
    }

    @QueryMapping
    public List<Income> getAllIncomes(@Argument @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate StartDate,
                                      @Argument @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate EndDate,
                                      @Argument List<String> customLabelNames) {
        return incomeService.getAllIncomes(StartDate, EndDate, customLabelNames);
    }
    
}
