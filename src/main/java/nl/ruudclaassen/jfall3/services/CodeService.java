package nl.ruudclaassen.jfall3.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
	MetadataFileRepository metadataFileRepository;
	
	@Autowired
	WinnerService winnerService;
	
	public Metadata save(String title, String numberOfCodes, String note) throws InputValidationException{
		
		validateInput(title, numberOfCodes);
				
		// Remove "," to prevent csv parsing issues (delimiter)
		title = title.replaceAll(",", " ");
		note = note.replaceAll(",", " ");	
		int parsedNumberOfCodes = Integer.parseInt(numberOfCodes);

		Set<String> codes = generatorService.generateCodes(parsedNumberOfCodes);
		String id = UUID.randomUUID().toString();
		
		codeFileRepository.save(id, codes);		

		String formattedDateTime = getFormattedDate();
		Metadata metadata = new Metadata(id, title, note, formattedDateTime, parsedNumberOfCodes);
		metadataFileRepository.save(metadata);	
		
		return metadata;
	}


	private String getFormattedDate() {
		LocalDateTime dateTime = LocalDateTime.now();		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dateTime.format(formatter);		
	}


	private void validateInput(String title, String numberOfCodes) throws InputValidationException {
		List<Field> fields = new ArrayList<>();
		boolean isNumberCorrect = true;
		int parsedNumber = 0;		

		try{
			parsedNumber = Integer.parseInt(numberOfCodes);
		} catch(NumberFormatException nfe){
			isNumberCorrect = false;
		}
		
		System.out.println(parsedNumber);
		System.out.println(isNumberCorrect);
		
		if(title.equals("")){
			fields.add(Field.TITLE);
		}		
		
		if(!isNumberCorrect || parsedNumber <= 0){
			fields.add(Field.NUMBER_OF_CODES);
		}		
		
		if(!fields.isEmpty()){
			throw new InputValidationException(fields);
		}
	}
	
	public Map<String, Metadata> remove(String promoId){
		Map<String, Metadata> metadataMap = new HashMap<>();
		
		codeFileRepository.delete(promoId);
		metadataMap = metadataFileRepository.delete(promoId);
		
		return metadataMap;
	}
	
	public Metadata pickWinner(Metadata metadata){
		String promoId = metadata.getId();
		
		Set<String> codes =  codeFileRepository.load(promoId);
		String winningCode = winnerService.pickWinner(codes);
		
		metadata.setWinningCode(winningCode);
		metadataFileRepository.save(promoId, metadata);
		
		return metadata;
	}

	
	public Metadata getMetadataById(String promoId){
		return metadataFileRepository.getMetadataById(promoId);
	}
}