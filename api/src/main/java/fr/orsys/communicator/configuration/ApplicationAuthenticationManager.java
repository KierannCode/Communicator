package fr.orsys.communicator.configuration;

import fr.orsys.communicator.model.entity.User;
import fr.orsys.communicator.security.authentication.AuthenticationManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationAuthenticationManager extends AuthenticationManager<User> {
}
