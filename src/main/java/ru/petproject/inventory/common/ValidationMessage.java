package ru.petproject.inventory.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessage {
    public static final String FIELD_MUST_BE_NOT_NULL = "поле должно быть указано";
    public static final String FIELD_MUST_BE_NOT_BLANK = "поле должно быть указано и не должно состоять из пробелов";
    public static final String FIELD_MUST_BE_NOT_EMPTY = "поле должно быть указано и не должно быть пустым";
    public static final String FIELD_MUST_BE_POSITIVE = "значение поля должно быть положительным";
    public static final String FIELD_MUST_BE_FROM_1_TO_100 = "поле должно содержать от 1 до 100 символов";
    public static final String FIELD_MUST_BE_FROM_1_TO_1000 = "поле должно содержать от 1 до 1000 символов";
    public static final String INVALID_EMAIL = "неверный формат email";
    public static final String FIELD_MUST_BE_FROM_6_TO_100 = "поле email должно быть от 6 до 100 символов";
    public static final String DATE_MUST_BE_FUTURE_OR_PRESENT = "дата окончания эксплуатации не может быть в прошлом";
    public static final String SET_MIN_SIZE_MUST_BE_1 = "Список должен иметь хотя бы один элемент";
}
