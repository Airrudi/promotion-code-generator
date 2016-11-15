package nl.ruudclaassen.jfall3.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.ruudclaassen.jfall3.model.Participant;

import org.springframework.stereotype.Service;

// TODO: CR please consistently code to interfaces
@Service
public class WinnerService {
	
	public Participant pickWinner(Map<String, Participant> participants){
		
		// TODO: CR subtracting 1 and then adding it again does nothing
		int randomNum = (int)(Math.random() * ((participants.size() - 1) + 1));
		List<String> idList = new ArrayList<>(participants.keySet());
		String winningKey = idList.get(randomNum);

		return participants.get(winningKey);
	}
}
