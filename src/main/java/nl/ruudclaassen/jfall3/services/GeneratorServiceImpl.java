package nl.ruudclaassen.jfall3.services;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Override
    public Set<String> generateCodes(int requestedNumberOfCodes) {
        SecureRandom random = new SecureRandom();
        Set<String> codes = new HashSet<String>();
        boolean isCodeMixed;

        while (codes.size() < requestedNumberOfCodes) {
            String code = new BigInteger(50, random).toString(32);
            isCodeMixed = false;

            if (code.matches(".*[0-9].*") && code.matches(".*[a-zA-Z].*")) {
                isCodeMixed = true;
            }

            // This generator can generate strings with a length of 9, we only want those with a length of 10
            // We only allow codes that contain a mix of letters and numbers
            if (code.length() != 10 || !isCodeMixed) {
                continue;
            }

            codes.add(code);
        }

        return codes;
    }
}
