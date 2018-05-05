package by.epam.like_it.service.impl;

import by.epam.like_it.dao.DAOFactory;
import by.epam.like_it.dao.QuAnDAO;
import by.epam.like_it.entity.*;
import by.epam.like_it.exception.DAOException;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;

import java.util.ArrayList;
import java.util.List;

public class QuAnServiceImpl implements QuAnService {

    private DAOFactory DAOfactory = DAOFactory.getInstance();

    private static final String QUESTION_TYPE = "question";
    private static final String ANSWER_TYPE = "answer";
    private static final String WHITESPACES_SPLIT = " ";

    @Override
    public List<QuestionInfoBlock> getQuestions(String lang) throws ServiceException {

        List<QuestionInfoBlock> questionBlockList = new ArrayList<>();

        try {

            QuAnDAO quAnDAO = DAOfactory.getQuAnDAO();

            List<Question> questions = quAnDAO.getQuestions(lang);

            for (Question question : questions) {

                QuestionInfoBlock questionBlock = new QuestionInfoBlock();

                questionBlock.setQuestion(question);
                questionBlock.setOwner(quAnDAO.getQuestionOwner(question));
                questionBlock.setMarks(quAnDAO.getMarksByQuestion(question));
                questionBlock.setTags(quAnDAO.getTagsByQuestion(question));
                questionBlock.setAnswers(quAnDAO.getAnswersByQuestion(question));

                questionBlockList.add(questionBlock);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);

        }

        return questionBlockList;
    }


    @Override
    public int addQuestion(Question question, User owner, String lang, String tagString) throws ServiceException {

        int questionID = -1;

        List<Tag> tags = new ArrayList<>();

        try {

            QuAnDAO quAnDAO = DAOfactory.getQuAnDAO();
            String[] tagList = tagString.split(WHITESPACES_SPLIT);

            for (String tagTitle : tagList) {
                Tag tag = new Tag();
                tag.setTitle(tagTitle);

                tags.add(tag);
            }
            questionID = quAnDAO.addQuestion(question, owner, lang, tags);


        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return questionID;
    }

    @Override
    public void editQuestion(Question question, String tagString, String lang) throws ServiceException {

        List<Tag> tags = new ArrayList<>();

        QuAnDAO dao = DAOfactory.getQuAnDAO();

        String[] tagList = tagString.split(WHITESPACES_SPLIT);

        for (String tagTitle : tagList) {
            Tag tag = new Tag();
            tag.setTitle(tagTitle);

            tags.add(tag);
        }

        try {

            int langID = dao.getLanguageID(lang);
            dao.editQuestion(question, tags, langID);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean deleteQuestion(int questionID, int userID) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();
        boolean isDeleted = false;

        try {

            Question question = dao.getQuestionById(questionID);
            User owner = dao.getQuestionOwner(question);

            if (owner.getId() == userID) {
                isDeleted = dao.deleteQuestion(questionID);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);

        }
        return isDeleted;
    }


    @Override
    public QuestionInfoBlock getQuestionInfoBlock(int id) throws ServiceException {


        QuestionInfoBlock block = null;

        QuAnDAO quAnDAO = DAOfactory.getQuAnDAO();

        block = new QuestionInfoBlock();

        Question question = null;
        try {

            question = quAnDAO.getQuestionById(id);

            block.setQuestion(question);
            block.setTags(quAnDAO.getTagsByQuestion(question));
            block.setMarks(quAnDAO.getMarksByQuestion(question));
            block.setOwner(quAnDAO.getQuestionOwner(question));
            block.setAnswers(getAnswersByQuestion(question));

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return block;
    }


    public Question getQuestion(int id) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();
        try {
            return dao.getQuestionById(id);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Answer> getAnswersByQuestion(Question question) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        QuAnDAO quAnDAO = factory.getQuAnDAO();

        List<Answer> answers = null;
        try {
            answers = quAnDAO.getAnswersByQuestion(question);

            for (Answer answer : answers) {
                answer.setOwner(quAnDAO.getAnswerOwner(answer));
                answer.setMarks(quAnDAO.getMarksByAnswer(answer));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return answers;
    }


    @Override
    public Answer getAnswerById(int id) throws ServiceException {

        Answer answer = null;
        QuAnDAO dao = DAOfactory.getQuAnDAO();

        try {
            answer = dao.getAnswerById(id);

        } catch (DAOException e) {
            throw new ServiceException(e);

        }

        return answer;
    }


    @Override
    public List<QuestionInfoBlock> getAnsweredQuestionsByUser(User user) throws ServiceException {

        QuAnDAO quAnDAO = DAOfactory.getQuAnDAO();
        List<QuestionInfoBlock> blockList = new ArrayList<>();

        List<Answer> answers=new ArrayList<>();

        try {

            answers = quAnDAO.getAnswersByUser(user);

            for (Answer answer : answers) {
                Question question = quAnDAO.getQuestionByAnswer(answer);

                QuestionInfoBlock block=new QuestionInfoBlock();

                block.setQuestion(question);
                block.setTags(quAnDAO.getTagsByQuestion(question));
                block.setMarks(quAnDAO.getMarksByQuestion(question));
                block.setOwner(quAnDAO.getQuestionOwner(question));
                block.setAnswers(getAnswersByQuestion(question));

                blockList.add(block);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return blockList;
    }


    @Override
    public List<QuestionInfoBlock> getQuestionsByUser(User user) throws ServiceException {

        QuAnDAO quAnDAO = DAOfactory.getQuAnDAO();
        List<Question> questions;
        List<QuestionInfoBlock> blockList = new ArrayList<>();

        try {

            questions = quAnDAO.getQuestionsByUser(user);

            for (Question question:questions){

                QuestionInfoBlock block=new QuestionInfoBlock();

                block.setQuestion(question);
                block.setTags(quAnDAO.getTagsByQuestion(question));
                block.setMarks(quAnDAO.getMarksByQuestion(question));
                block.setOwner(quAnDAO.getQuestionOwner(question));
                block.setAnswers(getAnswersByQuestion(question));

                blockList.add(block);

            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return blockList;
    }


    @Override
    public List<Tag> getTags() throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();
        List<Tag> tags = new ArrayList<>();

        try {

            tags = dao.getTags();

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return tags;

    }


    @Override
    public void editTag(Tag tag) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();

        try {

            dao.editTag(tag);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public int addTag(Tag tag) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();
        int tagID = 0;

        try {

            tagID = dao.addTag(tag);

        } catch (DAOException e) {
            throw new ServiceException(e);

        }

        return tagID;
    }


    @Override
    public boolean deleteTag(Tag tag) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();
        boolean isDeleted = false;

        try {

            isDeleted = dao.deleteTag(tag);

        } catch (DAOException e) {
            throw new ServiceException(e);

        }

        return isDeleted;
    }

    @Override
    public void addAnswer(Answer answer, int questionID) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();

        try {

            dao.addAnswer(answer, questionID);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public void editAnswer(Answer newAnswer, int userID) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();

        try {

            if (newAnswer.getOwner().getId() == userID) {

                dao.editAnswer(newAnswer);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean deleteAnswer(int answerID, int userID) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();
        Answer answer;
        boolean isDeleted = false;

        try {

            answer = dao.getAnswerById(answerID);

            if (answer.getOwner().getId() == userID) {
                isDeleted = dao.deleteAnswer(answerID);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);

        }
        return isDeleted;
    }


    @Override
    public void setSolution(int questionID, int answerID) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();

        try {



            dao.setSolution(questionID, answerID);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean addMark(Mark mark, String typeOfMark, int id) throws ServiceException {

        DAOFactory factory = DAOFactory.getInstance();
        QuAnDAO quAnDAO = factory.getQuAnDAO();
        boolean isAdded = false;

        try {

            if (typeOfMark.equals(QUESTION_TYPE)) {
                isAdded = quAnDAO.addQuestionMark(mark, id);
            } else if (typeOfMark.equals(ANSWER_TYPE)) {
                isAdded = quAnDAO.addAnswerMark(mark, id);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return isAdded;
    }

    @Override
    public Mark getMark(String typeOfMark, int id, int ownerId) throws ServiceException {

        QuAnDAO quAnDAO=DAOfactory.getQuAnDAO();
        Mark mark= null;

        try {
            mark = quAnDAO.getMark(typeOfMark, id, ownerId);
        } catch (DAOException e) {
            throw new ServiceException();
        }

        return mark;
    }
}
