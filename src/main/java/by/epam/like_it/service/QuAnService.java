package by.epam.like_it.service;

import by.epam.like_it.entity.*;
import by.epam.like_it.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface QuAnService {

    QuestionInfoBlock getQuestionInfoBlock(int id) throws ServiceException;

    Question getQuestion(int id) throws ServiceException;

    int addQuestion(Question question, User owner, String lang, String tags) throws ServiceException;

    void editQuestion(Question question, String tags, String lang, int userId) throws ServiceException;

    List<QuestionInfoBlock> getQuestions(String language, int page) throws ServiceException;

    List<QuestionRateInfo> getPopularQuestions(String language) throws ServiceException;

    int getPageCount(String lang) throws ServiceException;

    List<Tag> getTags() throws ServiceException;

    void editTag(Tag tag) throws ServiceException;

    int addTag(Tag tag) throws ServiceException;

    boolean deleteTag(Tag tag) throws ServiceException;

    Answer getAnswerById(int id) throws ServiceException;

    List<Answer> getAnswersByQuestion(Question question) throws ServiceException;

    boolean addMark(Mark mark, String typeOfMark, int id) throws ServiceException;

    Mark getMark(String typeOfMark, int id, int ownerId) throws ServiceException;

    int getRate(String type, int id) throws ServiceException;

    List<QuestionInfoBlock> getAnsweredQuestionsByUser(User user) throws ServiceException;

    List<QuestionInfoBlock> getQuestionsByUser(User user) throws ServiceException;

    void addAnswer(Answer answer) throws ServiceException;

    void editAnswer(Answer newAnswer, int userId) throws ServiceException;

    boolean deleteAnswer(int answerId, int userId) throws ServiceException;

    void setSolution(int questionId, int answerId, User owner) throws ServiceException;

    boolean deleteQuestion(int questionId, int userId) throws ServiceException;
}
