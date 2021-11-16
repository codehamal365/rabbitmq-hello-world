package org.example.config;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigTopicValidator implements ConstraintValidator<ValueCheck, String> {

    @Autowired
    private User user;

    static List<String> actions = ImmutableList.of("pub", "sub");
    private boolean required = false;
    private String type = "";


    public void initialize(ValueCheck constraintAnnotation) {
        required = constraintAnnotation.required();
        type = constraintAnnotation.type();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            switch (type) {
                case "actions":
                    if (!actions.contains(value)) {
                        return false;
                    }
                    break;
                case "client":
                    List<String> collect = Stream.of(TypeEnum.values())
                            .map(TypeEnum::getType).collect(Collectors.toList());
                    if (!collect.contains(value)) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }

        }
        return true;
    }

}