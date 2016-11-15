package nl.ruudclaassen.jfall3.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;
import org.springframework.stereotype.Component;

@Component
public class CodeFileDaoImpl implements CodeDao {


	
	private String buildFileName(String id){
		return "code-" + id + ".txt";
	}

	@Override
	public boolean save(Metadata metadata, Set<String> codes) {
		String fileName = buildFileName(metadata.getId());
		File file = new File(fileName);

		try(FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
		){
			// Print every code on a new line
			for(String code : codes){
				out.println(code);
			}

			return true;
		} catch (IOException e) {
			//exception handling left as an exercise for the reader
			return false;
		}
	}

	public Set<String> load(Metadata metadata){
		String fileName = buildFileName(metadata.getId());
		Set<String> codes = new HashSet<>();		
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {	
			stream.forEach(codes::add);	
		} catch (IOException e) {	
			throw new RuntimeException("File not found");
		}
		
		return codes;
	}

	
	public void delete(Metadata metadata){
		
		try{
			String fileName = buildFileName(metadata.getId());
    		File file = new File(fileName);

    		if(file.delete()){
    			//return true;
    		}else{
    			System.out.println("Delete operation is failed.");
    			//return false;
    		}

    	}catch(Exception e){

    		e.printStackTrace();
    		//return false;
    	}
	}
}
