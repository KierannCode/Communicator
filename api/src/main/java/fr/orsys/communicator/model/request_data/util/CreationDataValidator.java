package fr.orsys.communicator.model.request_data.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.Field;

public class CreationDataValidator implements ConstraintValidator<CreationData, Creation<?>> {
    @Override
    public boolean isValid(Creation<?> value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        boolean result = true;
        Object requestData = value.getData();
        try {
            for (Field field : requestData.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(CreationField.class) && field.isAnnotationPresent(Required.class)) {
                    field.setAccessible(true);
                    if (field.get(requestData) == null) {
                        Required requiredAnnotation = field.getAnnotation(Required.class);
                        context.buildConstraintViolationWithTemplate(requiredAnnotation.message()).addPropertyNode("data")
                                .addPropertyNode(field.getName()).addConstraintViolation();
                        result = false;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new ValidationException(e);
        }
        return result;
    }
}
