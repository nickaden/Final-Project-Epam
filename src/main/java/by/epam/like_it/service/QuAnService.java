package by.epam.like_it.service;

import by.epam.like_it.entity.*;
import by.epam.like_it.exception.ServiceException;

import java.util.List;

public interface QuAnService {

    QuestionInfoBlock getQuestionInfoBlock(int id) throws ServiceException;

    Question getQuestion(int id) throws ServiceException;

    int addQuestion(Question question, User owner, String lang, String tags) throws ServiceException;

    void editQuestion(Question question, String tags, String lang) throws ServiceException;

    List<QuestionInfoBlock> getQuestions(String language) throws ServiceException;

    List<Tag> getTags() throws ServiceException;

    void editTag(Tag tag) throws ServiceException;

    int addTag(Tag tag) throws ServiceException;

    boolean deleteTag(Tag tag) throws ServiceException;

    Answer getAnswerById(int id) throws ServiceException;

    List<Answer> getAnswersByQuestion(Question question) throws ServiceException;

    boolean addMark(Mark mark, String typeOfMark, int id) throws ServiceException;

    List<Question> getAnsweredQuestionsByUser(User user) throws ServiceException;

    List<Question> getQuestionsByUser(User user) throws ServiceException;

    void addAnswer(Answer answer, int questionID) throws ServiceException;

    void editAnswer(Answer newAnswer, int userID) throws ServiceException;

    boolean deleteAnswer(int answerID, int userID) throws ServiceException;

    void setSolution(int questionID, int answerID) throws ServiceException;

    boolean deleteQuestion(int questionID, int userID) throws ServiceException;
}
