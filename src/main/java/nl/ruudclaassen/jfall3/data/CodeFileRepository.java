package nl.ruudclaassen.jfall3.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import nl.ruudclaassen.jfall3.model.Code;
import nl.ruudclaassen.jfall3.model.Participant;
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
		
		try(FileWriter fw = new FileWriter(fileName, true);
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

	public void read(String fileName){

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            // If file is successfully loaded, reset possible existing code
            List<Code> codes = new ArrayList<>();

            stream.forEach(s -> convertTextLineToCode(s, codes));

        } catch (IOException e) {
            // System.out.print("Het bestand is niet gevonden");
            throw new RuntimeException("File not found");
            // e.printStackTrace();
        }
	}

	private void convertTextLineToCode(String textLine, List<Code> codes){
		String[] textLineElements = textLine.split(",");

		String name = textLineElements[0];
		String codeCombination = textLineElements[1];
		String email = textLineElements[2];

		Participant participant = new Participant(name, email);
		Code code = new Code(codeCombination, participant);

		codes.add(code);
	}
	
	
}
