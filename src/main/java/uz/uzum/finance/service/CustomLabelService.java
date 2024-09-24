package uz.uzum.finance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzum.finance.model.CustomLabel;
import uz.uzum.finance.model.Expense;
import uz.uzum.finance.model.Income;
import uz.uzum.finance.repository.CustomLabelRepository;
import uz.uzum.finance.repository.ExpenseRepository;
import uz.uzum.finance.repository.IncomeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomLabelService {

    private final CustomLabelRepository customLabelRepository;
    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;

    @Transactional
    public CustomLabel createCustomLabel(String name, String color){
        CustomLabel customLabel = new CustomLabel();
        customLabel.setName(name);
        customLabel.setColor(color);
        return customLabelRepository.save(customLabel);
    }

    @Transactional
    public CustomLabel updateCustomLabel(Long id, String name, String color){
        CustomLabel customLabel = customLabelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Label with id " + id + " not found"));

        customLabel.setName(name);
        customLabel.setColor(color);
        return customLabelRepository.save(customLabel);
    }

    @Transactional
    public String deleteCustomLabel(Long id){
        Optional<CustomLabel> optionalLabel = customLabelRepository.findById(id);

        if(optionalLabel.isPresent()){
            CustomLabel customLabel = optionalLabel.get();

            for(Income income: new HashSet<>(customLabel.getIncomes())){
                income.getCustomLabels().remove(customLabel);
                incomeRepository.save(income);
            }

            for(Expense expense: new HashSet<>(customLabel.getExpenses())){
                expense.getCustomLabels().remove(customLabel);
                expenseRepository.save(expense);
            }

            customLabelRepository.delete(customLabel);

            return "Label with id " + id + " was deleted";
        } else {
            throw new IllegalArgumentException("Label with id " + id + " not found");
        }
    }

    public List<CustomLabel> getAllCustomLabels(){
        return customLabelRepository.findAll();
    }

    public CustomLabel getCustomLabelByName(String name){
        return customLabelRepository.findByName(name);
    }

}