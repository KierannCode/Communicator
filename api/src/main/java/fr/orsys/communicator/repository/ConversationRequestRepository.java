package fr.orsys.communicator.repository;

import fr.orsys.communicator.model.entity.ConversationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRequestRepository extends JpaRepository<ConversationRequest, Long> {
}
