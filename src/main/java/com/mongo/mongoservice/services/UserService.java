package com.mongo.mongoservice.services;

import com.mongo.mongoservice.dto.UserDetails;
import com.mongo.mongoservice.models.User;
import com.mongo.mongoservice.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails getUser(String id) {
        return toUserDetails(getByUserId(id));
    }

    public List<UserDetails> getPageableUsers(Integer page, Integer pageLength) {
        Pageable pageable = PageRequest.of(page, pageLength);
        return userRepository.findAll(pageable).getContent().stream()
                .map(this::toUserDetails)
                .collect(Collectors.toList());
    }

    public User createUser(User user) {
        user.setFollowings(new HashSet<>());
        userRepository.save(user);
        return user;
    }

    public void createAllUsers(List<User> users) {
        users.forEach(user -> user.setFollowings(new HashSet<>()));
        userRepository.saveAll(users);
    }

    public User updateUser(String id, User updatedUser) {
        //todo: refactor this
        User currentUser = getByUserId(id);
        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setCity(updatedUser.getCity());
        currentUser.setDateOfBirth(updatedUser.getDateOfBirth());
        currentUser.setEnabled(updatedUser.isEnabled());
        userRepository.save(currentUser);
        return null;
    }

    public User deleteUser(String id) {
        userRepository.deleteById(id);
        return null;
    }

    public User getByUserId(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserDetails toUserDetails(User user) {
        return new UserDetails.Builder()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withCity(user.getCity())
                .withDateOfBirth(user.getDateOfBirth())
                .withEnabledFlag(user.isEnabled())
                .withFollowings(user.getFollowings())
                .withFollowers(calculateFollowers(user.getId()))
                .build();
    }

    private Set<String> calculateFollowers(String userId) {
        return userRepository.getFollowersById(userId).stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }

}
