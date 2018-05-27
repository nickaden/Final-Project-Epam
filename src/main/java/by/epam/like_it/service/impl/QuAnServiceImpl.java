package by.epam.like_it.service.impl;

import by.epam.like_it.dao.DAOFactory;
import by.epam.like_it.dao.QuAnDAO;
import by.epam.like_it.entity.*;
import by.epam.like_it.exception.DAOException;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.validate.*;
import org.apache.log4j.Logger;

import java.util.*;

public class QuAnServiceImpl implements QuAnService {

    private DAOFactory DAOfactory = DAOFactory.getInstance();

    private static final String QUESTION_TYPE = "question";
    private static final String ANSWER_TYPE = "answer";
    private static final String WHITESPACES_SPLIT = " ";
    private static final String EMPTY_STRING="";
    private static final String NOT_VALID_MSG="Data is not valid";
    private static final int NOT_VALID_ID=-1;

    @Override
    public List<QuestionInfoBlock> getQuestions(String lang, int page) throws ServiceException {

        List<QuestionInfoBlock> questionBlockList = new ArrayList<>();

        if (!GeneralValidator.checkLangWithNull(lang)){
            throw new ServiceException(NOT_VALID_MSG);
        }

        try {

            QuAnDAO quAnDAO = DAOfactory.getQuAnDAO();

            int noOfPages=quAnDAO.getPageCount(lang);
            if (page>noOfPages){
                page=noOfPages;
            }

            List<Question> questions = quAnDAO.getQuestions(lang, page);

            for (Question question : questions) {

                QuestionInfoBlock questionBlock = new QuestionInfoBlock();

                questionBlock.setQuestion(question);
                questionBlock.setOwner(quAnDAO.getQuestionOwner(question));
                questionBlock.setMarks(quAnDAO.getMarksByQuestion(question));
                questionBlock.setTags(quAnDAO.getTagsByQuestion(question));
                questionBlock.setAnswers(quAnDAO.getAnswersByQuestion(question));

                questionBlockList.add(questionBlock);
            }

            Collections.reverse(questionBlockList);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return questionBlockList;
    }

    @Override
    public List<QuestionRateInfo> getPopularQuestions(String language) throws ServiceException {

        Map<Question,Integer> popular=new HashMap<>();
        List<Question> questions=new ArrayList<>();
        List<QuestionRateInfo> questionRateInfoList=new ArrayList<>();
        QuAnDAO quAnDAO=DAOfactory.getQuAnDAO();

        try {

            for (int page = 1; page <= getPageCount(language); page++) {
                questions.addAll(quAnDAO.getQuestions(language, page));
            }

            for(Question question:questions){

                List<Mark> marks=quAnDAO.getMarksByQuestion(question);
                popular.put(question,getQuestionRate(marks));
            }

            List<Map.Entry<Question,Integer>> questionList=findGreatest(popular,5);

            for (Map.Entry<Question,Integer> entry:questionList){

                QuestionRateInfo rateInfo=new QuestionRateInfo();
                rateInfo.id=entry.getKey().getId();
                rateInfo.title=entry.getKey().getTitle();
                rateInfo.rate=entry.getValue();

                questionRateInfoList.add(rateInfo);
            }

            return questionRateInfoList;

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public int getPageCount(String lang) throws ServiceException {

        int pageCount= 0;
        try {

            pageCount = DAOFactory.getInstance().getQuAnDAO().getPageCount(lang);
            return pageCount;

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int addQuestion(Question question, User owner, String lang, String tagString) throws ServiceException {

        int questionId = -1;

        if (!QuestionValidator.checkQuestionAdding(question)
                && UserValidator.checkUserEditing(owner)
                && GeneralValidator.checkLangStrict(lang)
                && TagValidator.checkTagString(tagString)){

            Logger logger=Logger.getLogger(getClass());
            logger.error(NOT_VALID_MSG);
            return questionId;
        }

        List<Tag> tags = new ArrayList<>();

        try {

            QuAnDAO quAnDAO = DAOfactory.getQuAnDAO();
            String[] tagList = tagString.split(WHITESPACES_SPLIT);

            for (String tagTitle : tagList) {
                if (!tagTitle.equals(EMPTY_STRING)){
                Tag tag = new Tag();
                tag.setTitle(tagTitle);
                tags.add(tag);
                }
            }
            questionId = quAnDAO.addQuestion(question, owner, lang, tags);


        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return questionId;
    }

    @Override
    public void editQuestion(Question question, String tagString, String lang, int userId) throws ServiceException {

        if(!QuestionValidator.checkQuestionEditing(question)
                && TagValidator.checkTagString(tagString)
                && GeneralValidator.checkLangStrict(lang)){

            Logger logger=Logger.getLogger(getClass());
            logger.warn(NOT_VALID_MSG);
            return;
        }

        List<Tag> tags = new ArrayList<>();

        QuAnDAO dao = DAOfactory.getQuAnDAO();

        String[] tagList = tagString.split(WHITESPACES_SPLIT);

        for (String tagTitle : tagList) {
            Tag tag = new Tag();
            tag.setTitle(tagTitle);

            tags.add(tag);
        }

        try {

            User owner = dao.getQuestionOwner(question);
            User user=DAOFactory.getInstance().getUserDAO().getUserById(userId);

            if (owner.getId() == userId || user.getRole() == User.Role.ADMIN) {
                int langID = dao.getLanguageID(lang);
                dao.editQuestion(question, tags, langID);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean deleteQuestion(int questionId, int userId) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();
        boolean isDeleted = false;

        if (!GeneralValidator.checkId(questionId) && GeneralValidator.checkId(userId)){

            Logger logger=Logger.getLogger(getClass());
            logger.warn(NOT_VALID_MSG);
            return false;
        }

        try {

            Question question = dao.getQuestionById(questionId);
            User owner = dao.getQuestionOwner(question);
            User user=DAOFactory.getInstance().getUserDAO().getUserById(userId);

            if (owner.getId() == userId || user.getRole() == User.Role.ADMIN) {
                isDeleted = dao.deleteQuestion(questionId);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);

        }
        return isDeleted;
    }


    @Override
    public QuestionInfoBlock getQuestionInfoBlock(int id) throws ServiceException {

        if (!GeneralValidator.checkId(id)){
            throw new ServiceException(NOT_VALID_MSG);
        }

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

        if (!GeneralValidator.checkId(id)){
            throw new ServiceException(NOT_VALID_MSG);
        }

        QuAnDAO dao = DAOfactory.getQuAnDAO();
        try {
            return dao.getQuestionById(id);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Answer> getAnswersByQuestion(Question question) throws ServiceException {

        if (! QuestionValidator.checkQuestionEditing(question)){
            throw new ServiceException(NOT_VALID_MSG);
        }

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

        if (!GeneralValidator.checkId(id)){
            throw new ServiceException(NOT_VALID_MSG);
        }

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

        if (! UserValidator.checkUserEditing(user)){
            throw new ServiceException(NOT_VALID_MSG);
        }

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

        if (! UserValidator.checkUserEditing(user)){
            throw new ServiceException(NOT_VALID_MSG);
        }

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

        if(!TagValidator.checkTagEditing(tag)){
            Logger logger=Logger.getLogger(getClass());
            logger.warn(NOT_VALID_MSG);
            return;
        }

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
        int tagId = NOT_VALID_ID;

        if(!TagValidator.checkTagAdding(tag)){

            Logger logger=Logger.getLogger(getClass());
            logger.warn(NOT_VALID_MSG);
            return tagId;
        }

        try {

            tagId = dao.addTag(tag);

        } catch (DAOException e) {
            throw new ServiceException(e);

        }

        return tagId;
    }


    @Override
    public boolean deleteTag(Tag tag) throws ServiceException {

        if(!TagValidator.checkTagEditing(tag)){
            Logger logger=Logger.getLogger(getClass());
            logger.warn(NOT_VALID_MSG);
            return false;
        }

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
    public void addAnswer(Answer answer) throws ServiceException {

        if ( !(AnswerValidator.checkAnswerAdding(answer) && GeneralValidator.checkId(answer.getQuestionId())) ){
            Logger logger=Logger.getLogger(getClass());
            logger.error(NOT_VALID_MSG);
            return;
        }

        QuAnDAO dao = DAOfactory.getQuAnDAO();

        try {

            dao.addAnswer(answer, answer.getQuestionId());

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public void editAnswer(Answer newAnswer, int userId) throws ServiceException {

        if (!AnswerValidator.checkAnswerEditing(newAnswer) && GeneralValidator.checkId(userId)){
            Logger logger=Logger.getLogger(getClass());
            logger.warn(NOT_VALID_MSG);
            return;
        }

        QuAnDAO dao = DAOfactory.getQuAnDAO();

        try {

            User user=DAOFactory.getInstance().getUserDAO().getUserById(userId);

            if (newAnswer.getOwner().getId() == userId || user.getRole()==User.Role.ADMIN) {

                dao.editAnswer(newAnswer);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean deleteAnswer(int answerId, int userId) throws ServiceException {

        QuAnDAO dao = DAOfactory.getQuAnDAO();
        Answer answer;
        boolean isDeleted = false;

        if (!GeneralValidator.checkId(answerId) && GeneralValidator.checkId(userId)){
            Logger logger=Logger.getLogger(getClass());
            logger.warn(NOT_VALID_MSG);
            return false;
        }

        try {

            answer = dao.getAnswerById(answerId);
            User user=DAOFactory.getInstance().getUserDAO().getUserById(userId);

            if (answer.getOwner().getId() == userId || user.getRole()==User.Role.ADMIN) {
                isDeleted = dao.deleteAnswer(answerId);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);

        }
        return isDeleted;
    }


    @Override
    public void setSolution(int questionId, int answerId, User owner) throws ServiceException {

        if (! (GeneralValidator.checkId(questionId) && GeneralValidator.checkId(answerId)
                && UserValidator.checkUserEditing(owner))){
            Logger logger=Logger.getLogger(getClass());
            logger.warn(NOT_VALID_MSG);
            return;
        }

        QuAnDAO dao = DAOfactory.getQuAnDAO();

        try {

            QuestionInfoBlock questionInfoBlock=getQuestionInfoBlock(questionId);
            if (owner.getId()!=questionInfoBlock.getOwner().getId())
            {
                throw new ServiceException();
            }
            dao.setSolution(questionId, answerId);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean addMark(Mark mark, String typeOfMark, int id) throws ServiceException {

        DAOFactory factory = DAOFactory.getInstance();
        QuAnDAO quAnDAO = factory.getQuAnDAO();
        boolean isAdded = false;

        if(!GeneralValidator.checkId(id)){
            Logger.getLogger(getClass()).warn(NOT_VALID_MSG);
            return false;
        }

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
    public int getRate(String type, int id) throws ServiceException {

        List<Mark> marks;

        if (type.equals(QUESTION_TYPE)){
            QuestionInfoBlock question=getQuestionInfoBlock(id);
            marks=question.getMarks();
        } else {
            Answer answer=getAnswerById(id);
            marks=answer.getMarks();
        }

        int rate=0;
        for(Mark rateMark:marks){
            if (rateMark.getType()==Mark.Type.UP){
                rate++;
            } else if (rateMark.getType()==Mark.Type.DOWN){
                rate--;
            }
        }

        return rate;
    }

    @Override
    public Mark getMark(String typeOfMark, int id, int ownerId) throws ServiceException {

        QuAnDAO quAnDAO=DAOfactory.getQuAnDAO();
        Mark mark= null;

        if (!GeneralValidator.checkId(id) && GeneralValidator.checkId(ownerId)){
            throw new ServiceException(NOT_VALID_MSG);
        }

        try {
            mark = quAnDAO.getMark(typeOfMark, id, ownerId);
        } catch (DAOException e) {
            throw new ServiceException();
        }

        return mark;
    }

    private int getQuestionRate(List<Mark> marks){

        int rate=0;

        for (Mark mark:marks){
            if(mark.getType()==Mark.Type.UP){
                rate++;
            }
            else {
                rate--;
            }
        }

        return rate;
    }

    private static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>>
    findGreatest(Map<K, V> map, int n)
    {
        Comparator<? super Map.Entry<K, V>> comparator =
                new Comparator<Map.Entry<K, V>>()
                {
                    @Override
                    public int compare(Map.Entry<K, V> e0, Map.Entry<K, V> e1)
                    {
                        V v0 = e0.getValue();
                        V v1 = e1.getValue();
                        return v0.compareTo(v1);
                    }
                };

        PriorityQueue<Map.Entry<K, V>> highest =
                new PriorityQueue<>(n, comparator);
        for (Map.Entry<K, V> entry : map.entrySet())
        {
            highest.offer(entry);
            while (highest.size() > n)
            {
                highest.poll();
            }
        }

        List<Map.Entry<K, V>> result = new ArrayList<>();
        while (highest.size() > 0)
        {
            result.add(highest.poll());
        }

        return result;
    }
}
