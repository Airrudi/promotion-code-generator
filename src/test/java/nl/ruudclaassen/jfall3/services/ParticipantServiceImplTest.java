package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.ParticipantDao;
import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantServiceImplTest {

    @InjectMocks
    private ParticipantServiceImpl participantService;
    private Metadata metadata;

    @Mock
    private ParticipantDao participantDao;

    @Mock
    private MetadataService metadataService;

    @Mock
    private CodeService codeService;

    @Before
    public void setUp() throws Exception {
        metadata = new Metadata("id");
        Set<String> codes = new HashSet<>(Arrays.asList("1234", "5678", "9012", "3456"));
        when(codeService.load(metadata)).thenReturn(codes);
    }



    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(participantDao);
    }

    @Test
    public void emptyParticipantListWillNotGetSaved() throws Exception {
        Map<String, Map<String, Participant>> returnMap = participantService.save(metadata, new ClassPathResource("emptyParticipants.csv").getInputStream());

        assertEquals(3, returnMap.size());
        assertEquals(0, returnMap.get(participantService.VALID_PARTICIPANTS_LIST).size());
        assertEquals(0, returnMap.get(participantService.INVALID_PARTICIPANTS_LIST).size());
        assertEquals(0, returnMap.get(participantService.DUPLICATE_PARTICIPANTS_LIST).size());
    }

    @Test
    public void save_filterOutInvalidAndDuplicateCode() throws Exception {
        Map<String, Map<String, Participant>> returnMap = participantService.save(metadata, new ClassPathResource("invalidCodeParticipants.csv").getInputStream());
        verify(codeService, times(1)).load(metadata);
        verify(participantDao, times(1)).save(metadata, returnMap.get(participantService.VALID_PARTICIPANTS_LIST));

        assertEquals(3, returnMap.size());
        assertEquals("number of valid codes", 2, returnMap.get(participantService.VALID_PARTICIPANTS_LIST).size());
        assertEquals("number of invalid codes", 3, returnMap.get(participantService.INVALID_PARTICIPANTS_LIST).size());
        assertEquals("number of duplicate codes", 1, returnMap.get(participantService.DUPLICATE_PARTICIPANTS_LIST).size());
    }
}