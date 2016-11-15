package nl.ruudclaassen.jfall3.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;
import nl.ruudclaassen.jfall3.services.ParticipantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetadataFileDaoImpl implements MetadataDao {

	private static final String METAFILE = "metafile.csv";
	private static final String HEADERS = "id,title,note,creationDate,numberOfCodes,numberOfParticipants,winnerId";

	// TODO: CR: DAO must never reference service (dependency goes the wrong way!)
	@Autowired
	ParticipantService participantService;

	// TODO: CR - do not use system.out.println; do not return something from a finally block; do
	// not use printStackTrace
	@Override
	public Map<String, Metadata> load() {
		Map<String, Metadata> metadataMap = new HashMap<>();

		System.out.println("Start reading metafile from " + Paths.get(METAFILE).toAbsolutePath());
		try (Stream<String> stream = Files.lines(Paths.get(METAFILE))) {
			stream.skip(1).forEach(s -> convertToMap(s, metadataMap));
		} catch (IOException e) {
			System.out.println("Failed to read metafile from " + Paths.get(METAFILE).toAbsolutePath()
			        + ", creating new one");
			this.createMetaFile();
			e.printStackTrace();
			// throw new RuntimeException("File not found");
		} finally {
			return metadataMap;
		}
	}

	private void createMetaFile() {
		try (FileWriter fw = new FileWriter(METAFILE, true);
		        BufferedWriter bw = new BufferedWriter(fw);
		        PrintWriter out = new PrintWriter(bw);) {
			out.println(HEADERS);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO: CR this code references a service, this is not allowed in a DAO. move to service layer
	// TODO: CR magic numbers
	private void convertToMap(String metadataLine, Map<String, Metadata> metadataMap) {
		// TODO: Fix if text contains ,
		String[] metadataArray = metadataLine.split(",");
		Participant participant = null;

		if (metadataArray.length < 7) {
			metadataArray = Arrays.copyOf(metadataArray, 7);
		}

		// Create new metadata object which is added to the metadataList
		Metadata metadata = new Metadata(metadataArray[0], metadataArray[1], metadataArray[2], metadataArray[3],
		        Integer.parseInt(metadataArray[4]), Integer.parseInt(metadataArray[5]), participant);

		// TODO: Q: Before or after metadata was created? Before you can only pass the metadata
		// id...
		if (metadataArray[6] != null) {
			participant = participantService.getParticipantById(metadata, metadataArray[6]);
			metadata.setWinner(participant);
		}

		metadataMap.put(metadata.getId(), metadata);
	}

	@Override
	public Metadata getMetadataById(String id) {
		Map<String, Metadata> metadataMap = this.load();
		return metadataMap.get(id);
	}

	@Override
	public Map<String, Metadata> delete(String promoId) {
		Map<String, Metadata> metadataMap = new HashMap<>();
		metadataMap = this.load();
		metadataMap.remove(promoId);
		this.save(metadataMap);

		// TODO: Check to see if something was removed?
		return metadataMap;
	}

	@Override
	public Metadata save(Metadata metadata) {
		try (FileWriter fileWriter = new FileWriter(METAFILE, true);
		        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		        PrintWriter printWriter = new PrintWriter(bufferedWriter);) {
			printWriter.println(metadata.toString());
		} catch (IOException e) {
			System.out.println("Error in file");
		}

		return metadata;
	}

	@Override
	public Map<String, Metadata> update(Metadata metadata) {
		Map<String, Metadata> metadataMap = this.load();

		// Keep some of the old values
		Metadata oldMetadata = metadataMap.get(metadata.getId());
		metadata.setCreationDate(oldMetadata.getCreationDate());

		if (metadata.getNumberOfParticipants() == 0) {
			metadata.setNumberOfParticipants(oldMetadata.getNumberOfParticipants());
		}

		if (metadata.getNumberOfCodes() == 0) {
			metadata.setNumberOfCodes(oldMetadata.getNumberOfCodes());
		}

		metadataMap.replace(metadata.getId(), metadata);
		this.save(metadataMap);

		return metadataMap;
	}

	public boolean save(Map<String, Metadata> metadataMap) {

		// Overwrites existing file
		try (PrintWriter writer = new PrintWriter(METAFILE, "UTF-8")) {
			writer.println(HEADERS);

			// Print the entries
			metadataMap.forEach((promoId, metadata) -> writer.println(metadata));

			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Schrijven mislukt");
			return false;
		}
	}

	public boolean save(String promoId, Metadata metadata) {
		Map<String, Metadata> metadataMap = this.load();

		metadataMap.put(promoId, metadata);
		this.save(metadataMap);

		return true;
	}

}
