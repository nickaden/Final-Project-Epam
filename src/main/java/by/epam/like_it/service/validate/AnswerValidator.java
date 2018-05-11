package by.epam.like_it.service.validate;

import by.epam.like_it.entity.Answer;

public class AnswerValidator {

    private static final int MAX_DESCRIPTION_LENGTH=20000;

    public static boolean checkAnswerAdding(Answer answer){

        boolean valid=(answer !=null)
                && (answer.getDescription() !=null)
                && (answer.getDescription().length() > 0 && answer.getDescription().length() < MAX_DESCRIPTION_LENGTH)
                && (answer.getQuestionId() >0)
                && (answer.getOwner()!=null && UserValidator.checkUserEditing(answer.getOwner()));

        return valid;
    }

    public static boolean checkAnswerEditing(Answer answer){

        boolean valid=checkAnswerAdding(answer) && GeneralValidator.checkId(answer.getId());

        return valid;
    }
}
