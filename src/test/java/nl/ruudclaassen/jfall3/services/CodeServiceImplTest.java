package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by OCS on 15/11/2016.
 */
public class CodeServiceImplTest {

    @Mock
    private CodeService codeService;
    private Metadata metadata;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(codeService);
    }

    @Test
    public void emptyCodeListWillNotGetSaved() throws Exception {
        codeService.save(metadata, new ClassPathResource("emptyCodes.text").getInputStream());
    }

//    @Test
//    public void correctAmountOfCodesIsSaved() throws Exception {
//        codeService.save(metadata, new ClassPathResource("fiveCodes.text").getInputStream());
//
//
//    }


}

/*@Override
    public void save(Metadata metadata, InputStream codeStream) {
        Set<String> codes = new HashSet<>();

        try (InputStreamReader inputStreamReader = new InputStreamReader(codeStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

            String line;
            String code;

            while ((line = bufferedReader.readLine()) != null) {
                code = line.trim();
                codes.add(code);
            }

            // TODO: Q: Validate codes (numbers and letters, 10 characters long)? Not a real problem since we check participants by same list..
            metadata.setNumberOfCodes(codes.size());
            metadataService.update(metadata);
            codeDao.save(metadata.getId(), codes);

        } catch (FileAlreadyExistsException e) {
            e.getFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }*/