package com.host.core;

import com.host.core.model.CoreModel;
import com.host.core.service.validator.ValidatorService;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Help testing {@link com.host.core.service.validator}
 *
 * @param <T> type of {@link com.host.core.model}
 * @author Anton Bondarenko
 * @version 0.1
 */
public interface TestHelper<T extends CoreModel> {
    /**
     * Count errors in {@param fieldName} in {@param object}
     *
     * @param validatorService which will validate {@param object}
     * @param object           which need to validate
     * @param fieldName        field of {@param object}
     * @return numbers of error in {@param fieldName}
     */
    default long countConstraintViolation(ValidatorService<T> validatorService, T object, String fieldName) {
        return validatorService.validate(object).stream()
                .filter(error -> error.getPropertyPath().toString().equals(fieldName))
                .count();
    }

    /**
     * Checking field name in {@param claz}
     *
     * @param claz where fields with name {@param name}
     * @param name of fields in {@param claz}
     * @return checking name
     */
    default String checkFieldName(Class<T> claz, String name) {
        try {
            return claz.getDeclaredField(name).getName();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Wrong field name", e);
        }
    }

    /**
     * Count fields in {@param object}
     *
     * @param object where need to calculate number of fields
     * @return number of fields in {@param object}
     */
    default long countFields(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(field -> field.getName().matches("[A-Za-z]+[A-Za-z_]*")).count();
    }

    /**
     * Set protected field with name "id"
     *
     * @param clazz type of {@param where}
     * @param where of type {@param clazz}
     * @param id    of {@param where}
     * @throws NoSuchFieldException   when id is not exist
     * @throws IllegalAccessException when access false
     */
    default void setProtectedId(Class<T> clazz, T where, Long id) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(where, id);
    }

    default String getForTestUsername1() {
        return "uf";
    }

    default String getForTestUsername2() {
        return "us";
    }

    default String getForTestGroupname1() {
        return "gf";
    }

    default String getForTestGroupname2() {
        return "gs";
    }


}
