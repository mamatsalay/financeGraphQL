package uz.uzum.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzum.finance.model.CustomLabel;

import java.util.Optional;

public interface CustomLabelRepository extends JpaRepository<CustomLabel, Long> {

    CustomLabel findByName(String name);
}