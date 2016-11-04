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
import org.springframework.stereotype.Component;
import nl.ruudclaassen.jfall3.model.Metadata;


@Component
public class MetadataFileRepository implements MetadataRepository {
	
	private static final String METAFILE = "metafile.csv";
	private static final String HEADERS = "id,title,note,creationDate,numberOfCodes,winningCode";
	
	@Override
	public Map<String, Metadata> load(){			
		Map<String, Metadata> metadataMap = new HashMap<>();
		
		try (Stream<String> stream = Files.lines(Paths.get(METAFILE))) {			
			stream.skip(1).forEach(s -> convertToMap(s, metadataMap));				
		} catch (IOException e) {//			
			throw new RuntimeException("File not found");
		}
		
		return metadataMap;
	}

	private void convertToMap(String metadataLine, Map<String, Metadata> metadataMap) {
		// TODO: Fix if text contains ,
		String[] metadataArray = metadataLine.split(",");
		
		if(metadataArray.length < 6){
			metadataArray = Arrays.copyOf(metadataArray,metadataArray.length + 1);
		}		
		
		
		// Create new metadata object which is added to the metadataList
		Metadata metadata = new Metadata(metadataArray[0], metadataArray[1], metadataArray[2], metadataArray[3], Integer.parseInt(metadataArray[4]), metadataArray[5]);
		metadataMap.put(metadata.getId(), metadata);
	}
	
	public Metadata getMetadataById(String promoId){
		Map<String, Metadata> metadataMap = this.load();		
		return metadataMap.get(promoId);
	}
	
	public Map<String, Metadata> delete(String promoId){
		Map<String, Metadata> metadataMap = new HashMap<>();
		metadataMap = this.load();		
		metadataMap.remove(promoId);
		this.save(metadataMap);		
		
		// TODO: Check to see if something was removed?
		return metadataMap;		
	}
	
	@Override
	public boolean save(Metadata metadata){

		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(METAFILE, true))){
			bufferedWriter.append(metadata.toString());			
			return true;
		} catch (IOException e) {
			System.out.println("Error in file");
			return false;
		}
	}
	
	public boolean save(Map<String, Metadata> metadataMap){		
		
		// Overwrites existing file
		try (PrintWriter writer = new PrintWriter(METAFILE, "UTF-8")){
			writer.println(HEADERS);		
				
			// Print the entries
			metadataMap.forEach((promoId, metadata)->writer.println(metadata));
			
			return true;			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Schrijven mislukt");
			return false;
		}	
	}
	
	
	public boolean save(String promoId, Metadata metadata){
		Map<String, Metadata> metadataMap = this.load();
		
		metadataMap.put(promoId, metadata);		
		this.save(metadataMap);
		
		return true;
	}

}

