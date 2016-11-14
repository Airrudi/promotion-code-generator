package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Everything that belongs to participants handling
 */
public interface ParticipantService {
    /**
     * this is the valid key
     */
    String VALID_PARTICIPANTS_LIST = "valid";
    String INVALID_PARTICIPANTS_LIST = "invalid";
    String DUPLICATE_PARTICIPANTS_LIST = "duplicate";

    /**
     * Map keys are the constants defined in this interface
     *
     * @param metadata
     * @param inputParticipants the stream containing the csv participants
     * @return a map with lists for each scenario in the keys
     */
    Map<String, Set<Participant>> save(Metadata metadata, InputStream inputParticipants);

    Set<Participant> load(String promoId);

    Participant pickWinner(Metadata metadata);
    Participant getParticipantByCode(Metadata metadata);
}
