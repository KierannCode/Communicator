package fr.orsys.communicator.security.authorization;

import fr.orsys.communicator.security.authentication.AuthenticationManager;
import fr.orsys.communicator.security.exception.NotAuthenticatedException;
import fr.orsys.communicator.security.exception.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final AuthenticationManager<?> authenticationManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Authorize annotation = ((HandlerMethod) handler).getMethodAnnotation(Authorize.class);
        if (annotation == null) {
            return true;
        }

        Object user = authenticationManager.getCurrentAuthenticatedUser();
        if (user == null) {
            throw new NotAuthenticatedException();
        }
        if (!annotation.role().isAssignableFrom(user.getClass())) {
            throw new NotAuthorizedException(annotation.message());
        }

        return true;
    }
}
