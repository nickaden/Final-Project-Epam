package by.epam.like_it.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 33L;

    public enum Role{USER,ADMIN}

    private int id;
    private String login;
    private String password;
    private String imageName;
    private Role role;
    private String name;
    private String surname;
    private String email;
    private LocalDate regDate;

    public User() {
        role=Role.USER;
    }

    public User(int id, String login, String password, LocalDate regDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.regDate = regDate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o.getClass().equals(this.getClass()))) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(regDate, user.regDate) &&
                Objects.equals(imageName, user.imageName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, login, password, role, name, surname, email, regDate, imageName);
    }
}
