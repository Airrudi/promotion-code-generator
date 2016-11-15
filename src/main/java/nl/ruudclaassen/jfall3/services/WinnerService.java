package nl.ruudclaassen.jfall3.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.ruudclaassen.jfall3.model.Participant;
import org.springframework.stereotype.Service;

@Service
public class WinnerService {
	
	public Participant pickWinner(Map<String, Participant> participants){
		int randomNum = (int)(Math.random() * ((participants.size() - 1) + 1));
		List<String> idList = new ArrayList<>(participants.keySet());
		String winningKey = idList.get(randomNum);

		return participants.get(winningKey);
	}
}
