package nl.ruudclaassen.jfall3.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.ruudclaassen.jfall3.data.MetadataFileRepository;
import nl.ruudclaassen.jfall3.model.Metadata;

@Service
public class MetadataService {
	
	@Autowired
	MetadataFileRepository metadataFileRepository;
	
	public void save(String id, String title, String note){
		
	}
	
	public Map<String,Metadata> load(){
		return metadataFileRepository.load();
	}
	
	public Metadata getMetadataById(String promoId){
		return metadataFileRepository.getMetadataById(promoId);
	}
}
