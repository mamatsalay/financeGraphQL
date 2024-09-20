package uz.uzum.finance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.uzum.finance.model.CustomLabel;
import uz.uzum.finance.repository.CustomLabelRepository;

@Service
@RequiredArgsConstructor
public class CustomLabelService {

    private final CustomLabelRepository customLabelRepository;

    public CustomLabel createCustomLabel(String name, String color){
        CustomLabel customLabel = new CustomLabel();
        customLabel.setName(name);
        customLabel.setColor(color);
        return customLabelRepository.save(customLabel);
    }



}