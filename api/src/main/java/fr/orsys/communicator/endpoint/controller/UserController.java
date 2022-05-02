package fr.orsys.communicator.endpoint.controller;

import fr.orsys.communicator.model.entity.User;
import fr.orsys.communicator.model.request_data.CredentialsData;
import fr.orsys.communicator.model.request_data.UserData;
import fr.orsys.communicator.model.request_data.util.Creation;
import fr.orsys.communicator.model.request_data.util.Update;
import fr.orsys.communicator.security.authentication.AuthenticationController;
import fr.orsys.communicator.security.authentication.AuthenticationManager;
import fr.orsys.communicator.security.authorization.Authorize;
import fr.orsys.communicator.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("user")
@RestController
public class UserController extends AuthenticationController<User, CredentialsData> {
    private final UserService userService;

    public UserController(AuthenticationManager<User> authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected User getUser(CredentialsData validatedCredentials) {
        return userService.getUser(validatedCredentials);
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Valid Creation<UserData> registrationData) {
        return userService.createUser(registrationData);
    }

    @Authorize(User.class)
    @PatchMapping
    public User updateUser(@RequestBody @Valid Update<UserData> updateData) {
        return userService.updateUser(updateData);
    }
}
