package fr.orsys.communicator.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Message {
    @GeneratedValue
    @Id
    private Long id;

    @Lob
    private String content;

    private LocalDateTime timestamp;

    private Status status;

    @ManyToOne
    private ConversationRequest conversationRequest;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    public enum Status {
        SENT, BLOCKED, READ
    }
}
