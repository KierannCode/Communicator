package fr.orsys.communicator.security.authentication;

import fr.orsys.communicator.security.exception.TokenException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthenticationTokenInterceptor implements HandlerInterceptor {
    private final AuthenticationManager<?> authenticationManager;

    private void addAuthenticationTokenHeader(HttpServletResponse response, String authenticationToken) {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                authenticationManager.AUTHENTICATION_TOKEN_HEADER);
        response.setHeader(authenticationManager.AUTHENTICATION_TOKEN_HEADER, authenticationToken);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (((HandlerMethod) handler).getBean() instanceof ErrorController) {
            return true;
        }
        if (request.getRequestURI().endsWith("logOut")) {
            return true;
        }

        String authenticationToken = request.getHeader(authenticationManager.AUTHENTICATION_TOKEN_HEADER);
        if (StringUtils.isBlank(authenticationToken)) {
            return true;
        }

        try {
            authenticationToken = authenticationManager.renewAuthenticationToken(authenticationToken);
            addAuthenticationTokenHeader(response, authenticationToken);
            authenticationManager.bindAuthenticatedUserToCurrentThread(authenticationToken);
        } catch (TokenException e) {
            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        String authenticationToken = request.getHeader(authenticationManager.AUTHENTICATION_TOKEN_HEADER);
        if (authenticationToken != null) {
            authenticationManager.removeAuthentication(authenticationToken);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        authenticationManager.removeCurrentThreadBinding();
    }
}
