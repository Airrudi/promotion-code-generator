package nl.ruudclaassen.jfall3.data;

import java.io.*;
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

		System.out.println("Start reading metafile from " + Paths.get(METAFILE).toAbsolutePath());
		try (Stream<String> stream = Files.lines(Paths.get(METAFILE))) {
			stream.skip(1).forEach(s -> convertToMap(s, metadataMap));
		} catch (IOException e) {
			System.out.println("Failed to read metafile from " + Paths.get(METAFILE).toAbsolutePath() + ", creating new one");
			this.createMetaFile();
			e.printStackTrace();
			//throw new RuntimeException("File not found");
		} finally{
			return metadataMap;
		}
	}

	private void createMetaFile(){
		try(FileWriter fw = new FileWriter(METAFILE, true);
			 BufferedWriter bw = new BufferedWriter(fw);
			 PrintWriter out = new PrintWriter(bw);
		){
			out.println(HEADERS);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	@Override
	public Metadata getMetadataById(String promoId){
		Map<String, Metadata> metadataMap = this.load();		
		return metadataMap.get(promoId);
	}

	@Override
	public Map<String, Metadata> delete(String promoId){
		Map<String, Metadata> metadataMap = new HashMap<>();
		metadataMap = this.load();		
		metadataMap.remove(promoId);
		this.save(metadataMap);		
		
		// TODO: Check to see if something was removed?
		return metadataMap;		
	}
	
	@Override
	public Metadata save(Metadata metadata){
		try(FileWriter fileWriter = new FileWriter(METAFILE, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			PrintWriter printWriter = new PrintWriter(bufferedWriter);){
			printWriter.println(metadata.toString());
		} catch (IOException e) {
			System.out.println("Error in file");
		}

		return metadata;
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

