package fr.orsys.communicator.model.validation.validator;

import fr.orsys.communicator.model.validation.annotation.Characters;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.concurrent.atomic.AtomicInteger;

public class CharactersValidator implements ConstraintValidator<Characters, CharSequence> {
    private String alphabet;
    private int min;
    private int max;

    @Override
    public void initialize(Characters constraintAnnotation) {
        alphabet = constraintAnnotation.alphabet();
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        AtomicInteger count = new AtomicInteger(0);
        value.codePoints().forEach(character -> {
            for (int alphabetCharacter : alphabet.codePoints().toArray()) {
                if (character == alphabetCharacter) {
                    count.incrementAndGet();
                    break;
                }
            }
        });
        return min <= count.get() && count.get() <= max;
    }
}
