package fr.orsys.communicator.security.authentication;

import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CredentialsValidator.class)
@Documented
public @interface Credentials {
    Class<?> userEntity();

    Class<? extends QueryByExampleExecutor<?>> userRepository();

    ValidationStrategy strategy() default ValidationStrategy.GLOBAL;

    String usernameField() default "username";

    String passwordField() default "password";

    String usernameMessage() default "Invalid {usernameField}";

    String passwordMessage() default "Invalid {passwordField}";

    String message() default "Invalid credentials";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    enum ValidationStrategy {
        GLOBAL, DISTINCTIVE
    }
}
