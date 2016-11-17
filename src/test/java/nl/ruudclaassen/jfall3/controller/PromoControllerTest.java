package nl.ruudclaassen.jfall3.controller;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.services.MetadataService;
import nl.ruudclaassen.jfall3.general.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class PromoControllerTest {
    private MockMvc mockMvc;
    private Map<String, Metadata> promotions;

    @InjectMocks
    private PromoController promoController;

    @Mock
    private MetadataService metadataService;

    @Before
    public void setup(){
        // TODO: Q: How does Willem / Bas test controllers?
        mockMvc = new MockMvcBuilders().standaloneSetup(promoController).build();

        promotions = new HashMap<>();
        promotions.put("1ABC", new Metadata("1ABC"));
        promotions.put("2ABC", new Metadata("2ABC"));

        when(metadataService.getPromotions()).thenReturn(promotions);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(metadataService);
    }

    @Test
    public void promoShouldReturnAllPromotionsPage() throws Exception{

        mockMvc.perform(get("/promo/"))
            .andExpect(status().isOk())
            .andExpect(view().name(Constants.PROMOTIONS))
            .andExpect(model().attribute("promotions", promotions));

        verify(metadataService, times(1)).getPromotions();
    }

    @Test
    public void promotionRemovalShouldReturnToPromoOverview() throws Exception{

        mockMvc.perform(
            post(Constants.REDIRECT_PROMOTIONS)
                .param("promoId", "1ABC"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(Constants.PROMOTIONS))
                    .andExpect(model().attribute("promotions", promotions));

        verify(metadataService, times(1)).delete("1ABC");
        verify(metadataService, times(1)).getPromotions();
    }

}