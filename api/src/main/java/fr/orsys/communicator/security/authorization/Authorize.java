package fr.orsys.communicator.security.authorization;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorize {
    @AliasFor("role")
    Class<?> value() default Object.class;

    @AliasFor("value")
    Class<?> role() default Object.class;

    String message() default "Forbidden access";
}