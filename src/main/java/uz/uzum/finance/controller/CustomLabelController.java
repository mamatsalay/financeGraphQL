package uz.uzum.finance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import uz.uzum.finance.model.CustomLabel;
import uz.uzum.finance.service.CustomLabelService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomLabelController {

    private final CustomLabelService customLabelService;

    @MutationMapping
    public CustomLabel createCustomLabel(@Argument String name,
                                         @Argument String color) {
        return customLabelService.createCustomLabel(name, color);

    }

    @MutationMapping
    public CustomLabel updateCustomLabel(@Argument Long id,
                                         @Argument String name,
                                         @Argument String color) {
        return customLabelService.updateCustomLabel(id, name, color);
    }

    @MutationMapping
    public String deleteCustomLabel(@Argument Long id) {
        return customLabelService.deleteCustomLabel(id);
    }

    @QueryMapping
    public CustomLabel getCustomLabelByName(@Argument String name) {
        return customLabelService.getCustomLabelByName(name);
    }

    @QueryMapping
    public List<CustomLabel> getAllCustomLabels() {
        return customLabelService.getAllCustomLabels();
    }

}
