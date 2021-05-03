package com.mongo.mongoservice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Document(value = "users")
public class User {
    @Id
    private String id;
    @Indexed(unique=true)
    private String username;
    private String firstName;
    private String lastName;
    private String city;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;
    private boolean enabled;
    private Set<String> followings;

    public User() {
    }

    public User(String id, String username, String firstName, String lastName, String city, LocalDate dateOfBirth, boolean enabled, Set<String> followings) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.enabled = enabled;
        this.followings = followings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<String> followings) {
        this.followings = followings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isEnabled() == user.isEnabled()
                && getId().equals(user.getId())
                && getUsername().equals(user.getUsername())
                && Objects.equals(getFirstName(), user.getFirstName())
                && Objects.equals(getLastName(), user.getLastName())
                && Objects.equals(getCity(), user.getCity())
                //&& getFollowers().equals(user.getFollowers())
                //&& getFollowings().equals(user.getFollowings())
                && Objects.equals(getDateOfBirth(), user.getDateOfBirth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getFirstName(), getLastName(), getCity(), /*getFollowers(), getFollowings(),*/ getDateOfBirth(), isEnabled());
    }

}
