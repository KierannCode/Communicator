package fr.orsys.communicator.repository;

import fr.orsys.communicator.model.entity.Message;
import fr.orsys.communicator.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("from Message message where (message.sender = :sender and message.recipient = :recipient) or (message.sender = :recipient and message.recipient = :sender)")
    Page<Message> findConversationBetween(User sender, User recipient, Pageable pageable);
}
