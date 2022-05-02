package fr.orsys.communicator.service;

import fr.orsys.communicator.model.entity.User;
import fr.orsys.communicator.model.mapper.UserMapper;
import fr.orsys.communicator.model.request_data.CredentialsData;
import fr.orsys.communicator.model.request_data.UserData;
import fr.orsys.communicator.model.request_data.util.Creation;
import fr.orsys.communicator.model.request_data.util.Update;
import fr.orsys.communicator.repository.UserRepository;
import fr.orsys.communicator.security.authentication.AuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthenticationManager<User> authenticationManager;

    public User getUser(CredentialsData credentials) {
        return userRepository.findByUsername(credentials.getUsername()).orElseThrow();
    }

    public User createUser(Creation<UserData> creationData) {
        User user = userMapper.mapUser(creationData);
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Update<UserData> updateData) {
        User user = authenticationManager.getCurrentAuthenticatedUser();
        userMapper.mapUser(user, updateData);
        return userRepository.save(user);
    }
}
