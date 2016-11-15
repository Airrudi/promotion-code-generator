package nl.ruudclaassen.jfall3.services;

import java.io.InputStream;
import java.util.Map;

import nl.ruudclaassen.jfall3.model.Metadata;
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
     * @param metadata
     * @param inputParticipants the stream containing the csv participants
     * @return a map with lists for each scenario in the keys
     */
    Map<String, Map<String, Participant>> save(Metadata metadata, InputStream inputParticipants);

    Map<String, Participant> load(Metadata metadata);

    Participant pickWinner(Metadata metadata);
    Participant getParticipantById(Metadata metadata, String id);
}
