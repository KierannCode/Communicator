package fr.orsys.communicator.model.validation.validator;

import fr.orsys.communicator.model.validation.annotation.Unique;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, Object> {
    private final ApplicationContext applicationContext;

    private Object probe;
    private Field probeField;
    private Method existsMethod;

    private QueryByExampleExecutor<?> repository;

    private boolean ignoreCase;

    @Override
    public void initialize(Unique constraintAnnotation) {
        repository = applicationContext.getBean(constraintAnnotation.repository());
        ignoreCase = constraintAnnotation.ignoreCase();
        try {
            probe = constraintAnnotation.entity().getConstructor().newInstance();
            probeField = constraintAnnotation.entity().getDeclaredField(constraintAnnotation.fieldName());
            probeField.setAccessible(true);
            existsMethod = repository.getClass().getDeclaredMethod("exists", Example.class);
        } catch (ReflectiveOperationException e) {
            throw new ValidationException(e);
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            probeField.set(probe, value);
            Example<?> example;
            if (ignoreCase) {
                example = Example.of(probe, ExampleMatcher.matching().withIgnoreCase());
            } else {
                example = Example.of(probe);
            }
            return !((boolean) existsMethod.invoke(repository, example));
        } catch (ReflectiveOperationException e) {
            throw new ValidationException(e);
        }
    }
}
