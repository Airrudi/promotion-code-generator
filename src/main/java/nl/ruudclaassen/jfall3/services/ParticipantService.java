package nl.ruudclaassen.jfall3.services;

import java.io.InputStream;
import java.util.Map;

import nl.ruudclaassen.jfall3.model.Promotion;
import nl.ruudclaassen.jfall3.model.Participant;

/**
 * Everything that belongs to participants handling
 */
public interface ParticipantService {
    
	// TODO: CR use an enumeration rather than duplicated String values
	/**
     * this is the valid key
     */
    String VALID_PARTICIPANTS_LIST = "valid";
    String INVALID_PARTICIPANTS_LIST = "invalid";
    String DUPLICATE_PARTICIPANTS_LIST = "duplicate";

    /**
     * Map keys are the constants defined in this interface
     *
     * @param Promotion
     * @param inputParticipants the stream containing the csv participants
     * @return a map with lists for each scenario in the keys
     */
    Map<String, Map<String, Participant>> save(Promotion Promotion, InputStream inputParticipants);

    Map<String, Participant> load(Promotion Promotion);

    Participant pickWinner(Promotion Promotion);
    Participant getParticipantById(Promotion Promotion, String id);
}
