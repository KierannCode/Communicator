package fr.orsys.communicator.model.validation.annotation;

import fr.orsys.communicator.model.validation.validator.UniqueValidator;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
public @interface Unique {
    Class<?> entity();

    String fieldName();

    Class<? extends QueryByExampleExecutor<?>> repository();

    boolean ignoreCase() default true;

    String message() default "Must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
