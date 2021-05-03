package com.mongo.mongoservice.services;

import com.mongo.mongoservice.dao.UserDetails;
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
        return userRepository.findById(id).map(this::toUserDetails).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDetails> getAllUsers(Integer page, Integer pageLength) {
        Pageable pageable = PageRequest.of(page, pageLength);
        return userRepository.findAll(pageable).getContent().stream()
                .map(this::toUserDetails)
                .collect(Collectors.toList());
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    public void createAllUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    public User updateUser(String id, User user) {
        return null;
    }

    public User deleteUser(String id) {
        userRepository.deleteById(id);
        userRepository.deleteAll();
        return null;
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
        return new HashSet<>();
    }

}
