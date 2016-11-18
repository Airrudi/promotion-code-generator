package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.CodeDao;
import nl.ruudclaassen.jfall3.data.CodeFileDaoImpl;
import nl.ruudclaassen.jfall3.model.Promotion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CodeServiceImplTest {

    @InjectMocks //(subject waar je op aan het testen bent)
    private CodeServiceImpl codeService;

    @Mock //(schil er omheen en roep mij niet meer aan)
    private PromotionServiceImpl PromotionService;

    @Mock
    private CodeDao codeDao;
    private Promotion Promotion;// = new Promotion("345d4f543","Super","Note","2016-11-11 10:10",0,0,null);

    @Before
    public void setUp() throws Exception {
        Promotion = new Promotion(1);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(PromotionService, codeDao);
    }

    @Test
    public void emptyCodeListWillNotGetSaved() throws Exception {
        codeService.save(Promotion, new ClassPathResource("emptyCodes.txt").getInputStream());
    }

    @Test
    public void correctAmountOfCodesIsSaved() throws Exception {
        codeService.save(Promotion, new ClassPathResource("fiveCodes.txt").getInputStream());

        ArgumentCaptor<Set> captor = ArgumentCaptor.forClass(Set.class);
        verify(codeDao, times(1)).save(eq(Promotion), captor.capture()); // eq maakt een matcherobject van Promotion, indien matcher wordt gebruikt, dan moet andere ook een matcher zijn

        Set codes = captor.getValue();
        // Verify werkt alleen op gemockte objecten
        verify(PromotionService, times(1)).update(Promotion);

        // Werkt op 'normale' objecten (niet gemockt)
        assertEquals(5, codes.size());
        assertEquals(5, Promotion.getNumberOfCodes());
    }

    @Test
    public void load_returnsAllCodes() throws Exception {
        Set<String> codes = new HashSet<>(Arrays.asList("123ABC", "456DEF"));

        when(codeDao.load(Promotion)).thenReturn(codes);

        assertEquals("load() should return all (in this case two) values", 2, codeService.load(Promotion).size());
        verify(codeDao, times(1)).load(Promotion);
    }
}
