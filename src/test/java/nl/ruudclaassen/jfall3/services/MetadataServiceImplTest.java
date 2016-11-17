package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.MetadataDao;
import nl.ruudclaassen.jfall3.exceptions.PromotionNotFoundException;
import nl.ruudclaassen.jfall3.model.Metadata;
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
public class MetadataServiceImplTest {

    @InjectMocks
    private MetadataService metadataService = new MetadataServiceImpl();

    @Mock
    private MetadataDao metadataDao;
    private String id;

    @Before
    public void setup(){
        id = "123ABC";
    }

    @Test
    public void getPromotionById_shouldReturnOnePromotion() throws Exception {
        when(metadataDao.getPromotionById(id)).thenReturn(new Metadata());

        assertThat(metadataService.getPromotionById(id), instanceOf(Metadata.class));
        verify(metadataDao, times(1)).getPromotionById(id);
    }

    @Test
    public void load_returnsAllMetadata() throws Exception {
        Map<String, Metadata> metadataMap = new HashMap<>();
        metadataMap.put("123ABC", new Metadata("123ABC"));
        metadataMap.put("456DEF", new Metadata("456DEF"));
        metadataMap.put("789GHI", new Metadata("789GHI"));

        when(metadataDao.getPromotions()).thenReturn(metadataMap);
        Map<String, Metadata> ReturnedMap = metadataService.getPromotions();

        assertThat(ReturnedMap, instanceOf(Map.class));
        assertEquals(3, ReturnedMap.size());
        verify(metadataDao, times(1)).getPromotions();
    }

    @Test(expected = PromotionNotFoundException.class)
    public void findById_shouldThrowPromotionNotFoundException() throws Exception{
        when(metadataDao.getPromotionById(id)).thenThrow(PromotionNotFoundException.class);

        metadataService.getPromotionById(id);
        verify(metadataDao, times(1)).getPromotionById(id);
    }

    @Test
    public void save_returnsMetadataObjectEnrichedWithIdAndCreationDate() throws Exception{

        Metadata metadata = new Metadata();
        when(metadataDao.save(metadata)).thenReturn(metadata);

        Metadata savedMetadata = metadataService.save(metadata);

        assertThat("save returns metadata object", savedMetadata, instanceOf(Metadata.class));
        assertNotNull("metadata has an id", metadata.getId());
        assertNotNull("metadata has a creationDate", metadata.getCreationDate());

        verify(metadataDao, times(1)).save(metadata);
    }
}