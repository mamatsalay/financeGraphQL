package uz.uzum.finance.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import uz.uzum.finance.model.CustomLabel;
import uz.uzum.finance.model.Income;
import uz.uzum.finance.repository.CustomLabelRepository;
import uz.uzum.finance.repository.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    // TODO custom label можеть лежать в кэше, чтобы не делать запросы к БД
    private final CustomLabelRepository customLabelRepository;

    @Transactional(readOnly = true)
    public List<Income> getAllIncomes(LocalDate startDate,
                                      LocalDate endDate,
                                      List<String> customLabelNames) {
        if (customLabelNames != null && !customLabelNames.isEmpty()) {
            // TODO в написании кода стремиться к тому что мы не кастим и делать это в том случае когда это необходимо
            Long labelCount = (long) customLabelNames.size();
            return incomeRepository.findByDateBetweenAndExactLabels(startDate, endDate, customLabelNames, labelCount);
        } else {
            return incomeRepository.findByDateBetween(startDate, endDate);
        }
    }

    @Transactional
    public Income addIncome(BigDecimal amount, String description, LocalDate date, List<String> customLabelNames) {
        Income income = new Income();
        income.setAmount(amount);
        income.setDescription(description);
        income.setDate(date);
        income.setCustomLabels(fetchCustomLabelsByNames(customLabelNames));
        return incomeRepository.save(income);
    }

    @Transactional
    public String deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Income with ID " + id + " not found"));
        incomeRepository.delete(income);
        return "Income with ID " + id + " deleted";
    }

    @Transactional
    public Income updateIncome(Long id, BigDecimal amount, String description, LocalDate date, List<String> customLabelNames) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Income with ID " + id + " not found"));

        // Update basic fields
        income.setAmount(amount);
        income.setDescription(description);
        income.setDate(date);

        // Fetch and update labels
        Set<CustomLabel> customLabels = fetchCustomLabelsByNames(customLabelNames);
        income.setCustomLabels(customLabels);

        return incomeRepository.save(income);
    }

    // TODO: сколько запросов к БД будет выполнено в худшем случае?
    private Set<CustomLabel> fetchCustomLabelsByNames(List<String> customLabelNames) {
        return customLabelNames.stream()
                .map(customLabelRepository::findByName)
                .collect(Collectors.toSet());
    }

}
