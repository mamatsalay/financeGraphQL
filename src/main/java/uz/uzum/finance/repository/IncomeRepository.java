package uz.uzum.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.uzum.finance.model.Income;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    Optional<Income> findIncomeById(Long id);

    @Query("SELECT i FROM Income i JOIN i.customLabels cl " +
            "WHERE i.date BETWEEN :startDate AND :endDate " +
            "AND (:customLabelNames IS NULL OR cl.name IN :customLabelNames)")
    List<Income> findByDateBetweenAndLabels(@Param("startDate") String startDate,
                                            @Param("endDate") String endDate,
                                            @Param("customLabelNames") List<String> customLabelNames);

}