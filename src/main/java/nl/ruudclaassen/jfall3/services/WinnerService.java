package nl.ruudclaassen.jfall3.services;

import java.util.Set;

import nl.ruudclaassen.jfall3.model.Participant;
import org.springframework.stereotype.Service;

@Service
public class WinnerService {
	
	public Participant pickWinner(Set<Participant> participants){
		int randomNum = (int)(Math.random() * ((participants.size() - 1) + 1));
		Participant[] participantArray = participants.toArray(new Participant[participants.size()]);

		return participantArray[randomNum];
	}
}
