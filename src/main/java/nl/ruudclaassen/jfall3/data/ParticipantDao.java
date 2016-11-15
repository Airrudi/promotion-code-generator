package nl.ruudclaassen.jfall3.data;

import java.nio.file.FileAlreadyExistsException;
import java.util.Map;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;

public interface ParticipantDao {
    Map<String, Participant> delete();

    void save(Metadata metadata, Map<String, Participant> participantList) throws FileAlreadyExistsException;

    Map<String, Participant> load(Metadata metadata);
    Participant getParticipantById(Metadata metadata, String id);
}
