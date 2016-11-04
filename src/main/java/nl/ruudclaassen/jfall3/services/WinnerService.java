package nl.ruudclaassen.jfall3.services;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class WinnerService {
	
	public String pickWinner(Set<String> codes){			
		int randomNum = (int)(Math.random() * ((codes.size() - 1) + 1));
		String[] codeArray = codes.toArray(new String[codes.size()]);
		
		return codeArray[randomNum];		
	}
}
