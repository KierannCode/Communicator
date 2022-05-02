package fr.orsys.communicator.security;

import fr.orsys.communicator.security.authentication.AuthenticationManager;
import fr.orsys.communicator.security.authentication.AuthenticationTokenInterceptor;
import fr.orsys.communicator.security.authorization.AuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class InterceptorRegister implements WebMvcConfigurer {
    private final AuthenticationManager<?> authenticationManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationTokenInterceptor(authenticationManager)).order(
                Ordered.HIGHEST_PRECEDENCE);
        registry.addInterceptor(new AuthorizationInterceptor(authenticationManager))
                .order(Ordered.HIGHEST_PRECEDENCE + 1);
    }
}
