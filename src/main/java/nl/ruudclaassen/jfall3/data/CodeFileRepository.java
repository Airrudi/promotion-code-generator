package nl.ruudclaassen.jfall3.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class CodeFileRepository implements CodeRepository {
	
	private String buildFileName(String id){
		return "code-" + id + ".txt";
	}
	
	public Set<String> load(String id){		
		String fileName = buildFileName(id);		
		Set<String> codes = new HashSet<>();		
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {	
			stream.forEach(codes::add);	
		} catch (IOException e) {	
			throw new RuntimeException("File not found");
		}
		
		return codes;
	}
	
	public boolean save(String id, Set<String> codes){		
		String fileName = buildFileName(id);
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
	
	public boolean delete(String promoId){				
		
		try{
			String fileName = buildFileName(promoId);
    		File file = new File(fileName);

    		if(file.delete()){
    			return true;
    		}else{
    			System.out.println("Delete operation is failed.");
    			return false;
    		}

    	}catch(Exception e){

    		e.printStackTrace();
    		return false;
    	}
	}
	
	
}
