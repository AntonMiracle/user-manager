package com.host.core.service.validator;

import com.host.core.model.CoreModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * General validator for {@link com.host.core.model}
 *
 * @param <T> type of {@link com.host.core.model}
 * @author Anton Bondarenko
 * @version 0.1
 */
public interface ValidatorService<T extends CoreModel> {
    String SEPARATOR = "=";

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

    default Map<String, String> convertToMap(Set<ConstraintViolation<T>> errors) {
        Map<String, String> result = new HashMap<>();
        errors.forEach(error -> result.put(error.getPropertyPath().toString(), error.getInvalidValue().toString()));
        return result;
    }

    default String convertToString(Set<ConstraintViolation<T>> errors) {
        StringBuilder text = new StringBuilder();
        Map<String, String> result = convertToMap(errors);
        result.keySet().forEach(key -> text.append(key + "=" + result.get(key) + " "));
        return text.toString();
    }
}
