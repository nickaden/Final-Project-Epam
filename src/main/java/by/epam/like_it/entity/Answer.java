package by.epam.like_it.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Answer implements Serializable{

    private static final long serialVersionUID = 23L;

    private int id;
    private User owner;
    private int questionId;
    private Date cr_date;
    private List<Mark> marks;
    private boolean solution;
    private String description;

    public Answer() {
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Date getCr_date() {
        return cr_date;
    }

    public void setCr_date(Date cr_date) {
        this.cr_date = cr_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public boolean isSolution() {
        return solution;
    }

    public void setSolution(boolean solution) {
        this.solution = solution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return id == answer.id &&
                questionId == answer.questionId &&
                solution == answer.solution &&
                Objects.equals(owner, answer.owner) &&
                Objects.equals(cr_date, answer.cr_date) &&
                Objects.equals(marks, answer.marks) &&
                Objects.equals(description, answer.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, owner, questionId, cr_date, marks, solution, description);
    }
}
