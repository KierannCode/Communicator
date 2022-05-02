package fr.orsys.communicator.model.validation.annotation;

import fr.orsys.communicator.model.validation.validator.CharsetValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CharsetValidator.class)
@Documented
public @interface Charset {
    String alphabet();

    String message() default "Must not contain the character '%c' (%s)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
