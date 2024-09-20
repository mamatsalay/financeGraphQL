package uz.uzum.finance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import uz.uzum.finance.model.CustomLabel;
import uz.uzum.finance.service.CustomLabelService;

@Controller
@RequiredArgsConstructor
public class CustomLabelController {

    private final CustomLabelService customLabelService;

    @MutationMapping
    public CustomLabel createCustomLabel(@Argument String name,
                                         @Argument String color) {

        return customLabelService.createCustomLabel(name, color);

    }

}
