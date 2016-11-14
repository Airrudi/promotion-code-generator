package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ParticipantDao {
    Map<String, Participant> delete();

    void save(Metadata metadata, Set<Participant> participantList) throws FileAlreadyExistsException;

    Set<Participant> load(String promoId);
    Participant getParticipantByCode(Metadata metadata);
}
