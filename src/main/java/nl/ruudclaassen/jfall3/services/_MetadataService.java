package nl.ruudclaassen.jfall3.services;

import java.util.Map;

import nl.ruudclaassen.jfall3.data.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ruudclaassen.jfall3.data.MetadataFileRepository;
import nl.ruudclaassen.jfall3.model.Metadata;

@Service
public class _MetadataService {

	@Autowired
	MetadataRepository metadataRepository;

	public void save(String id, String title, String note){}

	public void save(Metadata metadata){
		String note = metadata.getNote();
		System.out.println(note);
		//note = note.replaceAll();
		metadataRepository.save(metadata);
	}
	
	public Map<String,Metadata> load(){
		return metadataRepository.load();
	}
	
	public Metadata getMetadataById(String promoId){
		return metadataRepository.getMetadataById(promoId);
	}
}
