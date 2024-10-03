package uz.uzum.finance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzum.finance.model.Expense;
import uz.uzum.finance.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CustomLabelService customLabelService;


    @Transactional(readOnly = true)
    public List<Expense> getAllExpenses(LocalDate startDate, LocalDate endDate, List<String> customLabelNames) {
        if (customLabelNames != null && !customLabelNames.isEmpty()) {
            return expenseRepository.findByDateBetweenAndLabels(startDate, endDate, customLabelNames);
        } else {
            return expenseRepository.findByDateBetween(startDate, endDate);
        }
    }

    @Transactional
    public Expense createExpense(BigDecimal amount, String description, LocalDate date, List<String> customLabelNames) {
        Expense expense = new Expense();
        expense.setAmount(amount);
        expense.setDescription(description);
        expense.setDate(date);
        expense.setCustomLabels(customLabelService.fetchCustomLabelsByNames(customLabelNames));
        return expenseRepository.save(expense);
    }

    @Transactional
    public String deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Expense not found"));
        expenseRepository.delete(expense);
        return "Expense with id " + id + " was deleted";
    }

    @Transactional
    public Expense updateExpense(Long id, BigDecimal amount, String description, LocalDate date, List<String> customLabelNames) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense with ID " + id + " not found"));

        expense.setAmount(amount);
        expense.setDescription(description);
        expense.setDate(date);
        expense.setCustomLabels(customLabelService.fetchCustomLabelsByNames(customLabelNames));

        return expenseRepository.save(expense);
    }
}
