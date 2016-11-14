package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.ParticipantDao;
import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Mock
    private ParticipantDao participantDao;

    @Mock
    private CodeService codeService;

    @Before
    public void setUp() throws Exception {
        Set<String> codes = new HashSet<>(Arrays.asList("1234", "5678", "9012", "3456"));
        when(codeService.load("id")).thenReturn(codes);
    }

    private Metadata metadata;

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(participantDao, codeService);
    }

    @Test
    public void emptyParticipantListWillNotGetSaved() throws Exception {
        metadata = new Metadata();
        metadata.setId("id");

        Map<String, Set<Participant>> returnMap = participantService.save(metadata, new ClassPathResource("emptyParticipants.csv").getInputStream());
        verify(codeService, times(1)).load("id");

        assertEquals(3, returnMap.size());
        assertEquals(0, returnMap.get(participantService.VALID_PARTICIPANTS_LIST).size());
        assertEquals(0, returnMap.get(participantService.INVALID_PARTICIPANTS_LIST).size());
        assertEquals(0, returnMap.get(participantService.DUPLICATE_PARTICIPANTS_LIST).size());
    }

    @Test
    public void duplicateParticipantListWillBeFiltered() throws Exception {
        metadata = new Metadata();
        metadata.setId("id");

        Map<String, Set<Participant>> returnMap = participantService.save(metadata, new ClassPathResource("duplicateCodeParticipants.csv").getInputStream());
        verify(codeService, times(1)).load("id");
        verify(participantDao, times(1)).save(metadata, returnMap.get(participantService.VALID_PARTICIPANTS_LIST));

        assertEquals(3, returnMap.size());
        assertEquals(3, returnMap.get(participantService.VALID_PARTICIPANTS_LIST).size());
        assertEquals(2, returnMap.get(participantService.INVALID_PARTICIPANTS_LIST).size());
        assertEquals(2, returnMap.get(participantService.DUPLICATE_PARTICIPANTS_LIST).size());
    }

    @Test
    public void invalidParticipantCodesWillBeFiltered() throws Exception {
        metadata = new Metadata();
        metadata.setId("id");

        Map<String, Set<Participant>> returnMap = participantService.save(metadata, new ClassPathResource("invalidCodeParticipants.csv").getInputStream());
        verify(codeService, times(1)).load("id");
        verify(participantDao, times(1)).save(metadata, returnMap.get(participantService.VALID_PARTICIPANTS_LIST));

        assertEquals(3, returnMap.size());
        assertEquals(2, returnMap.get(participantService.VALID_PARTICIPANTS_LIST).size());
        assertEquals(2, returnMap.get(participantService.INVALID_PARTICIPANTS_LIST).size());
        assertEquals(0, returnMap.get(participantService.DUPLICATE_PARTICIPANTS_LIST).size());
    }

}