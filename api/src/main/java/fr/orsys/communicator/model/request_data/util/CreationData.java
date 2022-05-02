package fr.orsys.communicator.model.request_data.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreationDataValidator.class)
@Documented
public @interface CreationData {
    String message() default "unused";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
