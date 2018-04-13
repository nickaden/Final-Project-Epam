package by.epam.like_it.entity;

import java.io.Serializable;
import java.util.Objects;

public class Mark implements Serializable{

    public enum Type{UP,DOWN};
    private static final long serialVersionUID = 25L;

    private int id;
    private int ownerId;
    private Type type;

    public Mark() {
    }

    public Mark(int id, int ownerId, Type type) {
        this.id = id;
        this.ownerId = ownerId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return id == mark.id &&
                ownerId == mark.ownerId &&
                type == mark.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ownerId, type);
    }
}
