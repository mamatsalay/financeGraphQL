package uz.uzum.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.uzum.finance.model.Expense;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e " +
            "WHERE e.date BETWEEN :startDate AND :endDate " +
            "AND SIZE(e.customLabels) = :labelCount " +
            "AND NOT EXISTS (" +
            "  SELECT cl FROM e.customLabels cl WHERE cl.name NOT IN :customLabelNames" +
            ")")
    List<Expense> findByDateBetweenAndExactLabels(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate,
                                                  @Param("customLabelNames") List<String> customLabelNames,
                                                  @Param("labelCount") Long labelCount);

    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

}