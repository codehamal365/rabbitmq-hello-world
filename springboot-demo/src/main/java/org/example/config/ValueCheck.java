package org.example.config;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ConfigTopicValidator.class)
public @interface ValueCheck {

    boolean required() default true;

    String type() default "";

    String message() default "topic config value is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
