package uz.uzum.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.uzum.finance.model.Income;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT i FROM Income i " +
            "WHERE i.date BETWEEN :startDate AND :endDate " +
            "AND SIZE(i.customLabels) = :labelCount " +
            "AND NOT EXISTS (" +
            "  SELECT cl FROM i.customLabels cl WHERE cl.name NOT IN :customLabelNames" +
            ")")
    List<Income> findByDateBetweenAndExactLabels(@Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate,
                                            @Param("customLabelNames") List<String> customLabelNames,
                                            @Param("labelCount") Long labelCount);

    List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);

}