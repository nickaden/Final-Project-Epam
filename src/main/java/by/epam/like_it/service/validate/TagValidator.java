package by.epam.like_it.service.validate;

import by.epam.like_it.entity.Tag;

public class TagValidator {

    public static boolean checkTagAdding(Tag tag) {

        boolean valid = (tag != null) && (tag.getTitle() != null && tag.getTitle().length() > 0);
        return valid;
    }

    public static boolean checkTagEditing(Tag tag) {

        boolean valid = checkTagAdding(tag) && GeneralValidator.checkId(tag.getId());
        return valid;
    }

    public static boolean checkTagString(String tags) {

        boolean valid = (tags != null);
        if (!valid) {
            return false;
        }

        String[] tagArray = tags.split(" ");
        for (String tag : tagArray) {
            if (tag.length()>20){
                return false;
            }
        }

        return valid;
    }
}
