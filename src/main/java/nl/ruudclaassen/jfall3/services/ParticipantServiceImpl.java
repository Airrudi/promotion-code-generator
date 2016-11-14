package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.ParticipantDao;
import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;


@Service
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    ParticipantDao participantDao;

    @Autowired
    CodeService codeService;

    @Autowired
    WinnerService winnerService;

    @Autowired
    MetadataService metadataService;

    @Override
    public Map<String, Set<Participant>> save(Metadata metadata, InputStream inputParticipants) {

        Map<String, Set<Participant>> participantMap = new HashMap<>();

        try (InputStreamReader inputStreamReader = new InputStreamReader(inputParticipants);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

            Set<Participant> participants = new HashSet<>();
            String line;

            // TODO: Better way to skip first line
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] lineArray = line.split(",");
                Participant participant = new Participant(lineArray[0].trim(), lineArray[1].trim(), lineArray[2].trim());
                participants.add(participant);
            }

            participantMap = this.validateCodes(metadata, participants);
            if (!participantMap.get("valid").isEmpty()) {
                participantDao.save(metadata, participantMap.get("valid"));
            }

        } catch (FileAlreadyExistsException e) {
            e.getFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return participantMap;
    }

    @Override
    public Participant pickWinner(Metadata metadata) {
        String promoId = metadata.getId();

        //Set<String> codes = this.load(promoId);
        Set<Participant> participants = this.load(promoId);
        Participant winningParticipant= winnerService.pickWinner(participants);

//        metadata.setWinningCode(winningParticipant.getCode());
//        metadataService.save(metadata);

        return winningParticipant;
    }

    @Override
    public Participant getParticipantByCode(Metadata metadata) {
        return participantDao.getParticipantByCode(metadata);
    }

    private Map<String, Set<Participant>> validateCodes(Metadata metadata, Set<Participant> participants) {
        Set<String> generatedCodes = codeService.load(metadata.getId());
        Set<String> participantCodes = new HashSet<>();
        boolean codeIsNew;

        Set<Participant> validParticipants = new HashSet<>();
        Set<Participant> invalidParticipants = new HashSet<>();
        Set<Participant> participantDuplicateCode = new HashSet<>();

        for (Participant participant : participants) {
            String participantCode = participant.getCode();
            codeIsNew = participantCodes.add(participantCode);

            if (generatedCodes.contains(participantCode) && codeIsNew) {
                validParticipants.add(participant);
            } else {
                invalidParticipants.add(participant);
            }

            if (!codeIsNew) {
                participantDuplicateCode.add(participant);
            }
        }

        Map<String, Set<Participant>> participantMap = new HashMap<>();
        participantMap.put("valid", validParticipants);
        participantMap.put("invalid", invalidParticipants);
        participantMap.put("duplicate", participantDuplicateCode);

        metadata.setNumberOfParticipants(validParticipants.size());
        metadataService.update(metadata);

        return participantMap;
    }

    @Override
    public Set<Participant> load(String promoId) {
        return participantDao.load(promoId);
    }

}
