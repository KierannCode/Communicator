package fr.orsys.communicator.security.authentication;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CredentialsValidator implements ConstraintValidator<Credentials, Object> {
    private final ApplicationContext context;

    private final PasswordEncoder passwordEncoder;

    private Class<?> userEntity;
    private QueryByExampleExecutor<?> userRepository;

    private Object probe;
    private Field probeUsernameField;
    private Method findOneMethod;

    private String usernameFieldName;
    private String passwordFieldName;

    private Credentials.ValidationStrategy strategy;

    private String usernameErrorMessage;
    private String passwordErrorMessage;

    @Override
    public void initialize(Credentials constraintAnnotation) {
        userEntity = constraintAnnotation.userEntity();
        userRepository = context.getBean(constraintAnnotation.userRepository());
        usernameFieldName = constraintAnnotation.usernameField();
        passwordFieldName = constraintAnnotation.passwordField();

        try {
            probe = userEntity.getConstructor().newInstance();
            probeUsernameField = userEntity.getDeclaredField(usernameFieldName);
            probeUsernameField.setAccessible(true);
            findOneMethod = userRepository.getClass().getDeclaredMethod("findOne", Example.class);
        } catch (ReflectiveOperationException e) {
            throw new ValidationException(e);
        }

        strategy = constraintAnnotation.strategy();

        if (strategy == Credentials.ValidationStrategy.DISTINCTIVE) {
            usernameErrorMessage = constraintAnnotation.usernameMessage();
            passwordErrorMessage = constraintAnnotation.passwordMessage();
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (strategy == Credentials.ValidationStrategy.DISTINCTIVE) {
            context.disableDefaultConstraintViolation();
        }
        if (value == null) {
            return false;
        }

        Class<?> credentialsClass = value.getClass();
        try {
            Field usernameField = credentialsClass.getDeclaredField(usernameFieldName);
            usernameField.setAccessible(true);
            String username = (String) usernameField.get(value);
            if (username == null) {
                context.buildConstraintViolationWithTemplate(usernameErrorMessage)
                        .addPropertyNode(usernameFieldName).addConstraintViolation();
                return false;
            }

            probeUsernameField.set(probe, username);
            Optional<?> result = (Optional<?>) findOneMethod.invoke(userRepository,
                    Example.of(probe));
            if (result.isEmpty()) {
                if (strategy == Credentials.ValidationStrategy.DISTINCTIVE) {
                    context.buildConstraintViolationWithTemplate(usernameErrorMessage)
                            .addPropertyNode(usernameFieldName).addConstraintViolation();
                }
                return false;
            }
            Object user = result.get();
            Field encryptedPasswordField = userEntity.getDeclaredField(
                    "encrypted" + StringUtils.capitalize(passwordFieldName));
            encryptedPasswordField.setAccessible(true);

            Field passwordField = credentialsClass.getDeclaredField("raw" + StringUtils.capitalize(passwordFieldName));
            passwordField.setAccessible(true);
            String rawPassword = (String) passwordField.get(value);
            if (rawPassword == null) {
                context.buildConstraintViolationWithTemplate(passwordErrorMessage)
                        .addPropertyNode("raw" + StringUtils.capitalize(passwordFieldName))
                        .addConstraintViolation();
                return false;
            }
            if (!passwordEncoder.matches(rawPassword, (String) encryptedPasswordField.get(user))) {
                if (strategy == Credentials.ValidationStrategy.DISTINCTIVE) {
                    context.buildConstraintViolationWithTemplate(passwordErrorMessage)
                            .addPropertyNode("raw" + StringUtils.capitalize(passwordFieldName))
                            .addConstraintViolation();
                }
                return false;
            }
            return true;
        } catch (ReflectiveOperationException e) {
            throw new ValidationException(e);
        }
    }
}
