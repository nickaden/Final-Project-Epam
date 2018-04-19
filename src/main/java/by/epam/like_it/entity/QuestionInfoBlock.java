package by.epam.like_it.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class QuestionInfoBlock implements Serializable {

    private static final long serialVersionUID = 29L;

    private Question question;
    private User owner;
    private List<Answer> answers;
    private List<Mark> marks;
    private List<Tag> tags;

    public QuestionInfoBlock() {
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionInfoBlock)) return false;
        QuestionInfoBlock that = (QuestionInfoBlock) o;
        return Objects.equals(question, that.question) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(answers, that.answers) &&
                Objects.equals(marks, that.marks) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {

        return Objects.hash(question, owner, answers, marks, tags);
    }
}
