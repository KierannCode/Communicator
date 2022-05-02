package fr.orsys.communicator.model.request_data;

import fr.orsys.communicator.model.entity.User;
import fr.orsys.communicator.repository.UserRepository;
import fr.orsys.communicator.security.authentication.Credentials;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Credentials(userEntity = User.class, userRepository = UserRepository.class, strategy = Credentials.ValidationStrategy.DISTINCTIVE)
@Getter
@Setter
public class CredentialsData {
    private String username;

    private String rawPassword;
}
