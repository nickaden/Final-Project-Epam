package by.epam.like_it.service.validate;

import by.epam.like_it.entity.User;

public class UserValidator {

    private static final int MIN_FIELD_LENGTH=4;

    public static boolean checkUserAdding(User user) {

        boolean valid = (user != null)
                && (user.getLogin() != null && user.getLogin().length() >= MIN_FIELD_LENGTH)
                && (user.getPassword() != null && user.getPassword().length() >= MIN_FIELD_LENGTH);

        return valid;
    }

    public static boolean checkUserEditing(User user) {

        boolean valid=checkUserAdding(user) && GeneralValidator.checkId(user.getId());

        return valid;
    }

    public static boolean checkField(String field){

        return field.length()>= MIN_FIELD_LENGTH;
    }
}
