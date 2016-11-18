package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.CodeDao;
import nl.ruudclaassen.jfall3.data.PromotionDao;
import nl.ruudclaassen.jfall3.exceptions.InputValidationException;
import nl.ruudclaassen.jfall3.exceptions.InputValidationException.Field;
import nl.ruudclaassen.jfall3.model.Code;
import nl.ruudclaassen.jfall3.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    GeneratorServiceImpl generatorService;

    @Autowired
    CodeDao codeDao;

    @Autowired
    PromotionDao PromotionDao;

    @Autowired
    WinnerService winnerService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    PromotionService PromotionService;

    // #1
    // Create new Promotion
    // Create new codes || Upload existing codes

    // #2
    // Open existing Promotion
    // Upload participants
    // Match participant codes to existing codes (check if entered codes are valid)

    // #3
    // Create new Promotion
    // Upload existing codes
    // Upload existing participants

    @Override
    public void save(Promotion Promotion, InputStream codeStream) {
        Set<String> codes = new HashSet<>();

        try (InputStreamReader inputStreamReader = new InputStreamReader(codeStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String line;
            String code;

            while ((line = bufferedReader.readLine()) != null) {
                code = line.trim();
                codes.add(code);
            }

            // TODO: Q: Validate codes (numbers and letters, 10 characters long)? Not a real problem
            // since we check participants by same list..
            if(codes.size() > 0) {
                Promotion.setNumberOfCodes(codes.size());
                PromotionService.update(Promotion);
                codeDao.save(Promotion, codes);
            }

        } catch (FileAlreadyExistsException e) {
            // TODO: CR this does nothing
            e.getFile();
        } catch (IOException ioe) {
            // TODO: CR don't print stack traces
            ioe.printStackTrace();
        }
    }

    // TODO: CR: unclear method name - it is called "save" but it doesn't save anything that already
    // exists and apparently generates a bunch of codes instead
    @Override
    public void save(Promotion Promotion) {
        List<Code> codes = generatorService.generateCodes(Promotion);
        codeDao.save(Promotion, codes);
    }

    // TODO:CR remove this, or move to a utility class
    private Promotion formatPromotion(Promotion Promotion) {

        String title = Promotion.getTitle();
        String note = Promotion.getNote();

        // Remove "," to prevent csv parsing issues (delimiter)
        title = title.replaceAll(",", " ");

        // TODO: Replace by regex
        note = note.replaceAll("[\n\r]", " ").replaceAll(",", " ");

        Promotion.setTitle(title);
        Promotion.setNote(note);

        return Promotion;
    }

    // TODO CR:
    private void validateInput(Promotion Promotion) throws InputValidationException {
        List<Field> fields = new ArrayList<>();
        String title = Promotion.getTitle();
        int numberOfCodes = Promotion.getNumberOfCodes();

        if (title.equals("")) {
            fields.add(Field.TITLE);
        }

        if (numberOfCodes <= 0) {
            fields.add(Field.NUMBER_OF_CODES);
        }

        if (!fields.isEmpty()) {
            throw new InputValidationException(fields);
        }
    }

//    public Map<String, Promotion> remove(Promotion Promotion) {
//        Map<String, Promotion> PromotionMap = new HashMap<>();
//
//        codeDao.delete(Promotion);
//        PromotionDao.delete(Promotion.getId());
//
//        return PromotionMap;
//    }

    @Override
    public Set<String> load(Promotion Promotion) {
        return codeDao.load(Promotion);
    }
}