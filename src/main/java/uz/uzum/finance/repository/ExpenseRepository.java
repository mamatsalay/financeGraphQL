package uz.uzum.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.uzum.finance.model.Expense;
import uz.uzum.finance.model.Income;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findExpenseById(Long id);

    @Query("SELECT e FROM Expense e JOIN e.customLabels cl " +
            "WHERE e.date BETWEEN :startDate AND :endDate " +
            "AND (:customLabelNames IS NULL OR cl.name IN :customLabelNames)")
    List<Expense> findByDateBetweenAndLabels(@Param("startDate") String startDate,
                                             @Param("endDate") String endDate,
                                             @Param("customLabelNames") List<String> customLabelNames);

}