package com.mongo.mongoservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Set;

public class UserDetails {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String city;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;
    private boolean enabled;
    private Set<String> followings;
    private Set<String> followers;

    private UserDetails(String id, String username, String firstName, String lastName, String city, LocalDate dateOfBirth, boolean enabled, Set<String> followings, Set<String> followers) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.enabled = enabled;
        this.followings = followings;
        this.followers = followers;
    }

    public static class Builder {
        private String id;
        private String username;
        private String firstName;
        private String lastName;
        private String city;
        private LocalDate dateOfBirth;
        private boolean enabled;
        private Set<String> followers;
        private Set<String> followings;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder withEnabledFlag(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder withFollowers(Set<String> followers) {
            this.followers = followers;
            return this;
        }

        public Builder withFollowings(Set<String> followings) {
            this.followings = followings;
            return this;
        }

        public UserDetails build() {
            return new UserDetails(id, username, firstName, lastName, city, dateOfBirth, enabled, followers, followings);
        }
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<String> getFollowings() {
        return followings;
    }

    public Set<String> getFollowers() {
        return followers;
    }

}
