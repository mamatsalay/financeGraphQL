package uz.uzum.finance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.uzum.finance.model.CustomLabel;
import uz.uzum.finance.repository.CustomLabelRepository;

import java.util.List;

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

    public CustomLabel updateCustomLabel(Long id,
                                         String name,
                                         String color){
        CustomLabel customLabel = customLabelRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Label with id " + id + " not found"));

        customLabel.setName(name);
        customLabel.setColor(color);
        return customLabelRepository.save(customLabel);
    }

    //TODO что случится с записями в Income, если мы удалим метку?
    public Void deleteCustomLabel(Long id){
        CustomLabel customLabel = customLabelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Label with id " + id + " not found"));
        customLabelRepository.delete(customLabel);
        return null;
    }

    public List<CustomLabel> getAllCustomLabels(){
        return customLabelRepository.findAll();
    }

}