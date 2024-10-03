package uz.uzum.finance.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import uz.uzum.finance.model.Income;
import uz.uzum.finance.model.UserInfo;
import uz.uzum.finance.repository.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final CustomLabelService customLabelService;

    @Transactional
    public Income createIncome(BigDecimal amount, String description, LocalDate date, List<String> customLabelNames) {
        Income income = new Income();
        income.setAmount(amount);
        income.setDescription(description);
        income.setDate(date);
        income.setCustomLabels(customLabelService.fetchCustomLabelsByNames(customLabelNames));
        return incomeRepository.save(income);
    }

    @Transactional(readOnly = true)
    public List<Income> getAllIncomes(LocalDate startDate, LocalDate endDate, List<String> customLabelNames) {
        if (customLabelNames != null && !customLabelNames.isEmpty()) {
            return incomeRepository.findByDateBetweenAndLabels(startDate, endDate, customLabelNames);
        } else {
            return incomeRepository.findByDateBetween(startDate, endDate);
        }
    }

    @Transactional
    public String deleteIncome(Long id) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Income with ID " + id + " not found"));
        incomeRepository.delete(income);
        return "Income with ID " + id + " deleted";
    }

    @Transactional
    public Income updateIncome(Long id, BigDecimal amount, String description, LocalDate date, List<String> customLabelNames) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Income with ID " + id + " not found"));

        income.setAmount(amount);
        income.setDescription(description);
        income.setDate(date);
        income.setCustomLabels(customLabelService.fetchCustomLabelsByNames(customLabelNames));

        return incomeRepository.save(income);
    }

}
