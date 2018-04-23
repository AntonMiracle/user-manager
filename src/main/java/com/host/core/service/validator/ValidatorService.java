package com.host.core.service.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public interface ValidatorService<T> {

    default Set<ConstraintViolation<T>> validate(T object) {
        return getValidator().validate(object);
    }

    default Validator getValidator() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        return vf.getValidator();
    }

    default boolean isValid(T object) {
        return validate(object).size() == 0;
    }

    void trimFields(T object);
}
