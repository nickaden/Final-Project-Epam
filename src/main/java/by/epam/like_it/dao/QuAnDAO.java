package by.epam.like_it.dao;

import by.epam.like_it.entity.*;
import by.epam.like_it.exception.DAOException;
import by.epam.like_it.service.QuAnService;

import java.sql.Connection;
import java.util.List;

public interface QuAnDAO {

    List<Question> getQuestions(String lang, int page) throws DAOException;

    int getPageCount(String lang) throws DAOException;

    Question getQuestionById(int id) throws DAOException;

    List<Tag> getTags() throws DAOException;

    int addTag(Tag tag) throws DAOException;

    void editTag(Tag tag) throws DAOException;

    boolean deleteTag(Tag tag) throws DAOException;

    List<Question> getQuestionsByUser(User user) throws DAOException;

    List<Question> getQuestionsByTag(Tag tag) throws DAOException;

    int addQuestion(Question question, User owner, String lang, List<Tag> tags) throws DAOException;

    void editQuestion(Question question, List<Tag> tags, int langID) throws DAOException;

    boolean deleteQuestion(int id) throws DAOException;

    int getLanguageID(String lang) throws DAOException;

    List<Mark> getMarksByQuestion(Question question) throws DAOException;

    List<Mark> getMarksByUser(User user) throws DAOException;

    List<Answer> getAnswersByUser(User user) throws DAOException;

    Question getQuestionByAnswer(Answer answer) throws DAOException;

    Mark getMark(String typeOfMark, int id, int ownerId) throws DAOException;

    List<Mark> getMarksByAnswer(Answer answer) throws DAOException;

    User getQuestionOwner(Question question) throws DAOException;

    User getAnswerOwner(Answer answer) throws DAOException;

    void addAnswer(Answer answer, int questionID) throws DAOException;

    void editAnswer(Answer newAnswer) throws DAOException;

    boolean deleteAnswer(int answerID) throws DAOException;

    void setSolution(int questionID, int answerID) throws DAOException;

    List<Tag> getTagsByQuestion(Question question) throws DAOException;

    List<Answer> getAnswersByQuestion(Question question) throws DAOException;

    Answer getAnswerById(int id) throws DAOException;

    boolean addQuestionMark(Mark mark, int id) throws DAOException;

    boolean addAnswerMark(Mark mark, int id) throws DAOException;
}
