package fr.orsys.communicator.model.validation.annotation;

import fr.orsys.communicator.model.validation.validator.EqualsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualsValidator.class)
@Repeatable(Equals.List.class)
@Documented
public @interface Equals {
    String source();
    String target();

    String message() default "'{target}' must be equal to '{source}'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Equals[] value();
    }
}
