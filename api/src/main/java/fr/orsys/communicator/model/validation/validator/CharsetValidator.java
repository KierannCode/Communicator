package fr.orsys.communicator.model.validation.validator;

import fr.orsys.communicator.model.validation.annotation.Charset;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.concurrent.atomic.AtomicBoolean;

public class CharsetValidator implements ConstraintValidator<Charset, CharSequence> {
    private String alphabet;

    @Override
    public void initialize(Charset constraintAnnotation) {
        alphabet = constraintAnnotation.alphabet();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        AtomicBoolean result = new AtomicBoolean(true);
        value.codePoints().forEach(character -> {
            AtomicBoolean allowed = new AtomicBoolean(false);
            alphabet.codePoints().forEach(allowedCharacter -> {
                if (character == allowedCharacter) {
                    allowed.set(true);
                }
            });
            if (!allowed.get()) {
                result.set(false);
                synchronized (context) {
                    context.buildConstraintViolationWithTemplate(
                            String.format(context.getDefaultConstraintMessageTemplate(), character,
                                    Character.getName(character).toLowerCase())).addConstraintViolation();
                }
            }
        });
        return result.get();
    }
}
