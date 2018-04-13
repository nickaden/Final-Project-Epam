package by.epam.like_it.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Question implements Serializable {

    private static final long serialVersionUID = 27L;

    private int id;
    private String title;
    private String description;
    private Date creatingDate;
    boolean answered;

    public Question() {
    }

    public Date getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(Date creatingDate) {
        this.creatingDate = creatingDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return id == question.id &&
                answered == question.answered &&
                Objects.equals(title, question.title) &&
                Objects.equals(description, question.description) &&
                Objects.equals(creatingDate, question.creatingDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, description, creatingDate, answered);
    }
}
