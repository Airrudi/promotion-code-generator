package nl.ruudclaassen.jfall3.data;

import java.nio.file.FileAlreadyExistsException;
import java.util.Map;

import nl.ruudclaassen.jfall3.model.Promotion;
import nl.ruudclaassen.jfall3.model.Participant;

public interface ParticipantDao {
    Map<String, Participant> delete();

    void save(Promotion Promotion, Map<String, Participant> participantList) throws FileAlreadyExistsException;

    Map<String, Participant> load(Promotion Promotion);
    Participant getParticipantById(Promotion Promotion, String id);
}
