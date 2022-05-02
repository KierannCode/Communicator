package fr.orsys.communicator.model.request_data.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.util.Set;

public class UpdateDataValidator implements ConstraintValidator<UpdateData, Update<?>> {
    @Override
    public boolean isValid(Update<?> value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        boolean result = true;
        Set<String> updatedFields = value.getUpdatedFields();
        Object requestData = value.getData();
        for (String fieldName : updatedFields) {
            try {
                Field field = requestData.getClass().getDeclaredField(fieldName);
                if (field.isAnnotationPresent(UpdateField.class) && field.isAnnotationPresent(Required.class)) {
                    field.setAccessible(true);
                    if (field.get(requestData) == null) {
                        Required requiredAnnotation = field.getAnnotation(Required.class);
                        context.buildConstraintViolationWithTemplate(requiredAnnotation.message())
                                .addPropertyNode("data")
                                .addPropertyNode(field.getName()).addConstraintViolation();
                        result = false;
                    }
                }
            } catch (NoSuchFieldException e) {
                context.buildConstraintViolationWithTemplate(String.format("Field '%s' does not exist", fieldName))
                        .addPropertyNode("updatedFields").addConstraintViolation();
                result = false;
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
        }
        return result;
    }
}
