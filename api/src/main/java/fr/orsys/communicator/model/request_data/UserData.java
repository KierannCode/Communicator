package fr.orsys.communicator.model.request_data;

import fr.orsys.communicator.model.entity.User;
import fr.orsys.communicator.model.request_data.util.CreationField;
import fr.orsys.communicator.model.request_data.util.Required;
import fr.orsys.communicator.model.request_data.util.UpdateField;
import fr.orsys.communicator.model.validation.annotation.Characters;
import fr.orsys.communicator.model.validation.annotation.Charset;
import fr.orsys.communicator.model.validation.annotation.Equals;
import fr.orsys.communicator.model.validation.annotation.Unique;
import fr.orsys.communicator.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Enumeration;

@Equals(source = "rawPassword", target = "passwordConfirmation", message = "The confirmation password does not match the password you entered")
@Getter
@Setter
public class UserData {
    @Charset(alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", message = "Your username cannot contain the character '%c' (%s)")
    @Unique(entity = User.class, fieldName = "username", repository = UserRepository.class, message = "This username is not available")
    @Size(min = 4, max = 20, message = "Your username must contain between 4 and 20 characters")
    @CreationField
    @Required
    @UpdateField
    private String username;

    @Characters(alphabet = "abcdefghijklmnopqrstuvwxyz", message = "Your password must contain at least one lowercase english letter")
    @Characters(alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", message = "Your password must contain at least one uppercase english letter")
    @Characters(alphabet = "0123456789", message = "Your password must contain at least one digit")
    @Characters(alphabet = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~", message = "Your password must contain at least one special character")
    @Charset(alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~", message = "Your password cannot contain the character '%c' (%s)")
    @Size(min = 8, max = 30, message = "Your password must contain between {min} and {max} characters")
    @CreationField
    @Required
    @UpdateField
    private String rawPassword;

    private String passwordConfirmation;

    @Email(message = "Please enter a valid email address or leave blank")
    @CreationField
    @UpdateField
    private String email;

    @CreationField
    @UpdateField
    private String description;

    @CreationField
    @UpdateField
    private User.Gender gender;
}
