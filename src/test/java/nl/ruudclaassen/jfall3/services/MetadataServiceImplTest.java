package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.PromotionDao;
import nl.ruudclaassen.jfall3.exceptions.PromotionNotFoundException;
import nl.ruudclaassen.jfall3.model.Promotion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.ExceptionTypeFilter;

import java.io.FileNotFoundException;
import java.util.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PromotionServiceImplTest {

    @InjectMocks
    private PromotionService PromotionService = new PromotionServiceImpl();

    @Mock
    private PromotionDao PromotionDao;
    private int id;

    @Before
    public void setup(){
        id = 2;
    }

    @Test
    public void getPromotionById_shouldReturnOnePromotion() throws Exception {
        when(PromotionDao.getPromotionById(id)).thenReturn(new Promotion());

        assertThat(PromotionService.getPromotionById(id), instanceOf(Promotion.class));
        verify(PromotionDao, times(1)).getPromotionById(id);
    }

//    @Test
//    public void load_returnsAllPromotion() throws Exception {
//        List<Promotion> PromotionMap = new HashMap<>();
//        PromotionMap.put(1, new Promotion(1));
//        PromotionMap.put(2, new Promotion(2));
//        PromotionMap.put(3, new Promotion(3));
//
//        when(PromotionDao.getPromotions()).thenReturn(PromotionMap);
//        List<Promotion> ReturnedMap = PromotionService.getPromotions();
//
//        assertThat(ReturnedMap, instanceOf(Map.class));
//        assertEquals(3, ReturnedMap.size());
//        verify(PromotionDao, times(1)).getPromotions();
//    }

    @Test(expected = PromotionNotFoundException.class)
    public void findById_shouldThrowPromotionNotFoundException() throws Exception{
        when(PromotionDao.getPromotionById(id)).thenThrow(PromotionNotFoundException.class);

        PromotionService.getPromotionById(id);
        verify(PromotionDao, times(1)).getPromotionById(id);
    }

    @Test
    public void save_returnsPromotionObjectEnrichedWithIdAndCreationDate() throws Exception{

        Promotion Promotion = new Promotion();
        when(PromotionDao.save(Promotion)).thenReturn(Promotion);

        Promotion savedPromotion = PromotionService.save(Promotion);

        assertThat("save returns Promotion object", savedPromotion, instanceOf(Promotion.class));
        assertNotNull("Promotion has an id", Promotion.getId());
        assertNotNull("Promotion has a creationDate", Promotion.getCreationDate());

        verify(PromotionDao, times(1)).save(Promotion);
    }
}