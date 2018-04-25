package com.host.core.service.validator;

import com.host.core.model.CoreModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * General validator for {@link com.host.core.model}
 *
 * @param <T> type of {@link com.host.core.model}
 * @author Anton Bondarenko
 * @version 0.1
 */
public interface ValidatorService<T extends CoreModel> {
    /**
     * Validate type
     *
     * @param type wich need to validate
     * @return {@code Set<ConstraintViolation<T>}
     */
    default Set<ConstraintViolation<T>> validate(T type) {
        return getValidator().validate(type);
    }

    /**
     * Get validator
     *
     * @return {@code Validator} {@link Validator}
     */
    default Validator getValidator() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        return vf.getValidator();
    }

    /**
     * Check type id valid
     *
     * @param type
     * @return true if and only if {@param type} is valid
     */
    default boolean isValid(T type) {
        return validate(type).size() == 0;
    }

    /**
     * Trim fields if and only if it possible
     *
     * @param type where need to trim fields
     */
    void trimFields(T type);

    default String getFieldAndValueSeparator() {
        return "=";
    }
}
