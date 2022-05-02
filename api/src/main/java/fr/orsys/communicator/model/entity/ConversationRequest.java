package fr.orsys.communicator.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class ConversationRequest {
    @GeneratedValue
    @Id
    private Long id;

    private boolean status;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    public enum Status {
        REQUESTED, ACCEPTED, MUTED, BLOCKED
    }
}
