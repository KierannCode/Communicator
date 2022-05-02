package fr.orsys.communicator.model.validation.validator;

import fr.orsys.communicator.model.validation.annotation.Equals;
import lombok.NonNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.util.Objects;

public class EqualsValidator implements ConstraintValidator<Equals, Object> {
    private String source;
    private String target;

    @Override
    public void initialize(Equals constraintAnnotation) {
        source = constraintAnnotation.source();
        target = constraintAnnotation.target();
    }

    @Override
    public boolean isValid(@NonNull Object value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(target).addConstraintViolation();
        try {
            Field sourceField = value.getClass().getDeclaredField(source);
            Field targetField = value.getClass().getDeclaredField(target);
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            Object sourceValue = sourceField.get(value);
            Object targetValue = targetField.get(value);
            return Objects.equals(targetValue, sourceValue);
        } catch (ReflectiveOperationException e) {
            throw new ValidationException(e);
        }
    }
}
