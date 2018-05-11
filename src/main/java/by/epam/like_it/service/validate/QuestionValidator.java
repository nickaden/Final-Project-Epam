package by.epam.like_it.service.validate;

import by.epam.like_it.entity.Question;

public class QuestionValidator {

    private static final int MAX_TITLE_LENGTH = 1024;

    private static final int MAX_DESCRIPTION_LENGTH = 20000;

    public static boolean checkQuestionAdding(Question question) {

        boolean valid =(question != null)
                && (question.getTitle() != null)
                && (question.getDescription() != null)
                && (question.getTitle().length() > 0 && question.getTitle().length() < MAX_TITLE_LENGTH)
                && (question.getDescription().length() > 0 && question.getDescription().length() < MAX_DESCRIPTION_LENGTH);

        return valid;
    }

    public static boolean checkQuestionEditing(Question question) {

        boolean valid=checkQuestionAdding(question) && GeneralValidator.checkId(question.getId()) ;

        return valid;
    }
}
