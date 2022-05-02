package fr.orsys.communicator.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationPasswordEncoder extends BCryptPasswordEncoder {
}
