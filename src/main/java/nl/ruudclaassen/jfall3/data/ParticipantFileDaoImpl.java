package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.exceptions.WriteFailedException;
import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

@Component
public class ParticipantFileDaoImpl implements ParticipantDao {

	private static final String HEADERS = "name,code,email";

	private static final Logger LOG = Logger.getLogger(ParticipantFileDaoImpl.class);

	@Override
	public Map<String, Participant> delete() {
		return null;
	}

	@Override
	public void save(Metadata metadata, Map<String, Participant> participantMap) {

		// TODO: Check if file already exists?
		String fileName = buildFileName(metadata.getId());
		File file = new File(fileName);

		// Overwrites existing file
		try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
			writer.println(HEADERS);

			// Print the entries
			for (Entry<String, Participant> e : participantMap.entrySet()) {
				writer.println(e.getValue());
			}

			throw new IOException("on purpose");

			// participantMap.forEach((participant)->writer.println(participant));

		} catch (IOException e) {
			throw new WriteFailedException("Schrijven mislukt");
		}
	}

	public String buildFileName(String id) {
		return "participant-" + id + ".csv";
	}

	private void buildParticipantMap(String line, Map<String, Participant> participants) {
		String[] lineArray = line.split(",");
		Participant participant = new Participant(lineArray[0], lineArray[1], lineArray[2], lineArray[3]);
		participants.put(participant.getId(), participant);
	}

	@Override
	public Map<String, Participant> load(Metadata metadata) {
		Map<String, Participant> participants = new HashMap<>();
		String fileName = buildFileName(metadata.getId());

		System.out.println("Start reading ParticipantFile from " + Paths.get(fileName).toAbsolutePath());
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.skip(1).forEach(s -> buildParticipantMap(s, participants));
		} catch (IOException e) {
			System.out.println("Failed to read ParticipantFile from " + Paths.get(fileName).toAbsolutePath());
			System.out.println("Returning empty list of participants");
			// e.printStackTrace();
		} finally {
			return participants;
		}
	}

	@Override
	public Participant getParticipantById(Metadata metadata, String id) {
		Map<String, Participant> participantMap = this.load(metadata);
		return participantMap.get(id);
	}
}
