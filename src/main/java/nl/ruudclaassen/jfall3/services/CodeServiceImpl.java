package nl.ruudclaassen.jfall3.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.ruudclaassen.jfall3.data.CodeDao;
import nl.ruudclaassen.jfall3.data.MetadataDao;
import nl.ruudclaassen.jfall3.exceptions.InputValidationException;
import nl.ruudclaassen.jfall3.exceptions.InputValidationException.Field;
import nl.ruudclaassen.jfall3.model.Metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	GeneratorServiceImpl generatorService;

	@Autowired
	CodeDao codeDao;

	@Autowired
	MetadataDao metadataDao;

	@Autowired
	WinnerService winnerService;

	@Autowired
	ParticipantService participantService;

	@Autowired
	MetadataService metadataService;

	// #1
	// Create new Promotion
	// Create new codes || Upload existing codes

	// #2
	// Open existing Promotion
	// Upload participants
	// Match participant codes to existing codes (check if entered codes are valid)

	// #3
	// Create new Promotion
	// Upload existing codes
	// Upload existing participants

	@Override
	public void save(Metadata metadata, InputStream codeStream) {
		Set<String> codes = new HashSet<>();

		try (InputStreamReader inputStreamReader = new InputStreamReader(codeStream);
		        BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

			String line;
			String code;

			while ((line = bufferedReader.readLine()) != null) {
				code = line.trim();
				codes.add(code);
			}

			// TODO: Q: Validate codes (numbers and letters, 10 characters long)? Not a real problem
			// since we check participants by same list..
			metadata.setNumberOfCodes(codes.size());
			metadataService.update(metadata);
			codeDao.save(metadata, codes);

		} catch (FileAlreadyExistsException e) {
			// TODO: CR this does nothing
			e.getFile();
		} catch (IOException ioe) {
			// TODO: CR don't print stack traces
			ioe.printStackTrace();
		}
	}

	// TODO: CR: unclear method name - it is called "save" but it doesn't save anything that already
	// exists and apparently generates a bunch of codes instead
	@Override
	public void save(Metadata metadata) {
		Set<String> codes = generatorService.generateCodes(metadata.getNumberOfCodes());
		codeDao.save(metadata, codes);
	}

	// TODO:CR remove this, or move to a utility class
	private Metadata formatMetadata(Metadata metadata) {

		String title = metadata.getTitle();
		String note = metadata.getNote();

		// Remove "," to prevent csv parsing issues (delimiter)
		title = title.replaceAll(",", " ");

		// TODO: Replace by regex
		note = note.replaceAll("[\n\r]", " ").replaceAll(",", " ");

		metadata.setTitle(title);
		metadata.setNote(note);

		return metadata;
	}

	// TODO CR:
	private void validateInput(Metadata metadata) throws InputValidationException {
		List<Field> fields = new ArrayList<>();
		String title = metadata.getTitle();
		int numberOfCodes = metadata.getNumberOfCodes();

		if (title.equals("")) {
			fields.add(Field.TITLE);
		}

		if (numberOfCodes <= 0) {
			fields.add(Field.NUMBER_OF_CODES);
		}

		if (!fields.isEmpty()) {
			throw new InputValidationException(fields);
		}
	}

	public Map<String, Metadata> remove(Metadata metadata) {
		Map<String, Metadata> metadataMap = new HashMap<>();

		codeDao.delete(metadata);
		metadataMap = metadataDao.delete(metadata.getId());

		return metadataMap;
	}

	@Override
	public Set<String> load(Metadata metadata) {
		return codeDao.load(metadata);
	}

	// TODO CR: why is this on this service? should be (only) on the metadataservice
	public Metadata getMetadataById(String promoId) {
		return metadataDao.getMetadataById(promoId);
	}
}