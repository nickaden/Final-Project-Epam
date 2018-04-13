package by.epam.like_it.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Tag implements Serializable {

    private static final long serialVersionUID = 31L;

    private int id;
    private String title;
    private Map<String,String> descriptions=new HashMap<>();

    public Tag() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Map<String, String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getDescription(String lang){
        return descriptions.get(lang);
    }

    public void setDescription(String lang,String description){
        descriptions.put(lang,description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return id == tag.id &&
                Objects.equals(title, tag.title) &&
                Objects.equals(descriptions, tag.descriptions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, descriptions);
    }
}
