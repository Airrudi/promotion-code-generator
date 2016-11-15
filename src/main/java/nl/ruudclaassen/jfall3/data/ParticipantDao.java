package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ParticipantDao {
    Map<String, Participant> delete();

    void save(Metadata metadata, Map<String, Participant> participantList) throws FileAlreadyExistsException;

    Map<String, Participant> load(Metadata metadata);
    Participant getParticipantById(Metadata metadata, String id);
}
