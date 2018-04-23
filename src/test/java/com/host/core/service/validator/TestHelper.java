package com.host.core.service.validator;

import java.lang.reflect.Field;
import java.util.Arrays;

public interface TestHelper<T> {

    default long countConstraintViolation(ValidatorService<T> validatorService, T object, String fieldName) {
        return validatorService.validate(object).stream()
                .filter(error -> error.getPropertyPath().toString().equals(fieldName))
                .count();
    }

    default String getField(Class<T> claz, String name) {
        try {
            return claz.getDeclaredField(name).getName();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Wrong field name", e);
        }
    }

    default long countFields(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(field -> field.getName().matches("[A-Za-z]+[A-Za-z_]*")).count();
    }

    default void setProtectedId(Class<T> clazz, T where, Long id) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(where, id);
    }

}
