package fr.orsys.communicator.model.validation.annotation;

import fr.orsys.communicator.model.validation.validator.CharactersValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CharactersValidator.class)
@Repeatable(Characters.List.class)
@Documented
public @interface Characters {
    String alphabet();

    int min() default 1;

    int max() default 2147483647;

    String message() default "Must contain between {min} and {max} characters from the sequence '{alphabet}'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Characters[] value();
    }
}
