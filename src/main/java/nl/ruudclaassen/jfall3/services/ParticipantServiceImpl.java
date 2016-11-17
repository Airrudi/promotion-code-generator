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
import java.util.Map.Entry;

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
	public Map<String, Map<String, Participant>> save(Metadata metadata, InputStream inputParticipants) {

		Map<String, Map<String, Participant>> participantMap = new HashMap<>();

		try (InputStreamReader inputStreamReader = new InputStreamReader(inputParticipants);
		        BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

			Map<String, Participant> participants = new HashMap<>();
			String line;

			// TODO: Better way to skip first line
			bufferedReader.readLine();

			while ((line = bufferedReader.readLine()) != null) {
				String[] lineArray = line.split(",");
				if(lineArray.length == 0){continue;}

				String id = UUID.randomUUID().toString();
				Participant participant = new Participant(id, lineArray[0].trim(), lineArray[1].trim(), lineArray[2].trim());
				participants.put(participant.getId(), participant);
			}

			participantMap = this.validateCodes(metadata, participants);
			if (!participantMap.get("valid").isEmpty()) {
				participantDao.save(metadata, participantMap.get("valid"));
			}

		} catch (FileAlreadyExistsException e) {
			// TODO: CR this exception is swallowed and nothing is returned
			e.getFile();
		} catch (IOException ioe) {
			// TODO: CR don't print stack traces
			ioe.printStackTrace();
		}

		return participantMap;
	}

	@Override
	public Participant pickWinner(Metadata metadata) {
		Map<String, Participant> participants = this.load(metadata);
		Participant winningParticipant = winnerService.pickWinner(participants);

		return winningParticipant;
	}

	@Override
	public Participant getParticipantById(Metadata metadata, String id) {
		return participantDao.getParticipantById(metadata, id);
	}

	private Map<String, Map<String, Participant>> validateCodes(Metadata metadata, Map<String, Participant> participants) {
		Set<String> generatedCodes = codeService.load(metadata);
		Set<String> participantCodes = new HashSet<>();
		boolean codeIsNew;

		Map<String, Participant> validParticipants = new HashMap<>();
		Map<String, Participant> invalidParticipants = new HashMap<>();
		Map<String, Participant> participantDuplicateCode = new HashMap<>();

		for (Entry<String, Participant> e : participants.entrySet()) {
			String participantCode = e.getValue().getCode();
			codeIsNew = participantCodes.add(participantCode);

			if (generatedCodes.contains(participantCode) && codeIsNew) {
				validParticipants.put(e.getKey(), e.getValue());
			} else {
				invalidParticipants.put(e.getKey(), e.getValue());
			}

			if (!codeIsNew) {
				participantDuplicateCode.put(e.getKey(), e.getValue());
			}
		}

		// Map of map of map of participants
		Map<String, Map<String, Participant>> participantMap = new HashMap<>();
		participantMap.put(VALID_PARTICIPANTS_LIST, validParticipants);
		participantMap.put(INVALID_PARTICIPANTS_LIST, invalidParticipants);
		participantMap.put(DUPLICATE_PARTICIPANTS_LIST, participantDuplicateCode);

		metadata.setNumberOfParticipants(validParticipants.size());
		metadataService.update(metadata);

		return participantMap;
	}

	@Override
	public Map<String, Participant> load(Metadata metadata) {
		return participantDao.load(metadata);
	}
}
