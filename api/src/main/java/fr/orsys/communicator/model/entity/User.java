package fr.orsys.communicator.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Transient
    private final String role = this.getClass().getSimpleName();

    @GeneratedValue
    @Id
    private Long id;
    private String username;

    @JsonIgnore
    private String encryptedPassword;

    @JsonIgnore
    private String email;

    @Lob
    private String description;
    private Gender gender;

    private LocalDateTime registrationTimestamp;

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<ConversationRequest> sentConversationRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "recipient")
    private List<ConversationRequest> receivedConversationRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @JsonIgnore
    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages;

    @PrePersist
    public void prePersist() {
        registrationTimestamp = LocalDateTime.now(ZoneOffset.UTC);
    }

    public enum Gender {
        MALE, FEMALE
    }
}
