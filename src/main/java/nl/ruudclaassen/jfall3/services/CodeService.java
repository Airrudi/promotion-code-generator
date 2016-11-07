package nl.ruudclaassen.jfall3.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import nl.ruudclaassen.jfall3.data.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ruudclaassen.jfall3.data.CodeFileRepository;
import nl.ruudclaassen.jfall3.data.MetadataFileRepository;
import nl.ruudclaassen.jfall3.exceptions.InputValidationException;
import nl.ruudclaassen.jfall3.exceptions.InputValidationException.Field;
import nl.ruudclaassen.jfall3.model.Metadata;

@Service
public class CodeService {
	
	@Autowired
	GeneratorService generatorService;
	
	@Autowired
	CodeFileRepository codeFileRepository;
	
	@Autowired
	MetadataRepository metadataRepository;
	
	@Autowired
	WinnerService winnerService;
	
	public Metadata save(Metadata metadata) throws InputValidationException{
		validateInput(metadata);
		formatMetadata(metadata);

		String id = UUID.randomUUID().toString();
		Set<String> codes = generatorService.generateCodes(metadata.getNumberOfCodes());

		metadata.setId(id);
		metadata.setCreationDate(getFormattedDate());

		codeFileRepository.save(id, codes);
		metadataRepository.save(metadata);
		
		return metadata;
	}

	private Metadata formatMetadata(Metadata metadata){

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






	private String getFormattedDate() {
		LocalDateTime dateTime = LocalDateTime.now();		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dateTime.format(formatter);		
	}


	private void validateInput(Metadata metadata) throws InputValidationException {
		List<Field> fields = new ArrayList<>();
		String title = metadata.getTitle();
		int numberOfCodes = metadata.getNumberOfCodes();
		
		if(title.equals("")){
			fields.add(Field.TITLE);
		}		
		
		if(numberOfCodes <= 0){
			fields.add(Field.NUMBER_OF_CODES);
		}		
		
		if(!fields.isEmpty()){
			throw new InputValidationException(fields);
		}
	}
	
	public Map<String, Metadata> remove(String promoId){
		Map<String, Metadata> metadataMap = new HashMap<>();
		
		codeFileRepository.delete(promoId);
		metadataMap = metadataRepository.delete(promoId);
		
		return metadataMap;
	}
	
	public Metadata pickWinner(Metadata metadata){
		String promoId = metadata.getId();
		
		Set<String> codes =  codeFileRepository.load(promoId);
		String winningCode = winnerService.pickWinner(codes);
		
		metadata.setWinningCode(winningCode);
		metadataRepository.save(metadata);
		
		return metadata;
	}

	
	public Metadata getMetadataById(
			String promoId){
		return metadataRepository.getMetadataById(promoId);
	}

	public Map<String,Metadata> load(){
		return metadataRepository.load();
	}



}