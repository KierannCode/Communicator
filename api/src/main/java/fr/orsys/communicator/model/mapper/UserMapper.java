package fr.orsys.communicator.model.mapper;

import fr.orsys.communicator.model.entity.User;
import fr.orsys.communicator.model.request_data.UserData;
import fr.orsys.communicator.model.request_data.util.Creation;
import fr.orsys.communicator.model.request_data.util.Update;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    private void setUsername(User user, String username) {
        user.setUsername(username);
    }

    private void setPassword(User user, String rawPassword) {
        user.setEncryptedPassword(passwordEncoder.encode(rawPassword));
    }

    private void setEmail(User user, String email) {
        if (StringUtils.isBlank(email)) {
            user.setEmail(null);
        } else {
            user.setEmail(email);
        }
    }

    private void setDescription(User user, String description) {
        if (StringUtils.isBlank(description)) {
            user.setDescription(null);
        } else {
            user.setDescription(description);
        }
    }

    private void setGender(User user, User.Gender gender) {
        user.setGender(gender);
    }

    public User mapUser(Creation<UserData> creationData) {
        User user = new User();

        UserData userData = creationData.getData();
        setUsername(user, userData.getUsername());
        setPassword(user, userData.getRawPassword());
        setEmail(user, userData.getEmail());
        setDescription(user, userData.getDescription());
        setGender(user, userData.getGender());

        return user;
    }

    public void mapUser(User user, Update<UserData> updateData) {
        Set<String> updatedFields = updateData.getUpdatedFields();
        UserData userData = updateData.getData();

        if (updatedFields.contains("username")) {
            setUsername(user, userData.getUsername());
        }
        if (updatedFields.contains("rawPassword")) {
            setPassword(user, userData.getRawPassword());
        }
        if (updatedFields.contains("email")) {
            setEmail(user, userData.getEmail());
        }
        if (updatedFields.contains("description")) {
            setDescription(user, userData.getDescription());
        }
        if (updatedFields.contains("gender")) {
            setGender(user, userData.getGender());
        }
    }
}
