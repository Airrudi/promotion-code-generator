package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.model.Code;
import nl.ruudclaassen.jfall3.model.Promotion;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GeneratorServiceImpl implements GeneratorService {

	// TODO: CR replace magic numbers by constants. code is unclear and rather unintuitive
	@Override
	public List<Code> generateCodes(Promotion promotion) {
		int numberOfCodes = promotion.getNumberOfCodes();
		SecureRandom random = new SecureRandom();
		Set<String> codes = new HashSet<String>();
		List<Code> codeObjects = new ArrayList<>();

		boolean isCodeMixed;
		int startSize;

		while (codes.size() < numberOfCodes) {
			startSize = codes.size();
			String code = new BigInteger(50, random).toString(32);
			isCodeMixed = false;

			if (code.matches(".*[0-9].*") && code.matches(".*[a-zA-Z].*")) {
				isCodeMixed = true;
			}

			// This generator can generate strings with a length of 9, we only want those with a
			// length of 10
			// We only allow codes that contain a mix of letters and numbers
			if (code.length() == 10 && isCodeMixed) {
				codes.add(code);
			}

			// Code is unique if it is added to the set (Set only allows unique values)
			if(codes.size() > startSize){
				codeObjects.add(new Code(code, promotion));
			}
		}

		return codeObjects;
	}
}
