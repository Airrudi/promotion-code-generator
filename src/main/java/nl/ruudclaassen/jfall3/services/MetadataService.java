package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.model.Promotion;

import java.util.List;

public interface PromotionService {
    Promotion getPromotionById(int id);

    /**
     * Loads all the promotions
     *
     * @return Map<String, Promotion> with id and Promotion pairs
     */
    List<Promotion> getPromotions();


    void delete(int promoId);

    /**
     * Creates new promotion (generates new id etc.)
     */
    Promotion save(Promotion Promotion);

    /**
     * Updates an existing promotion (does not create a new id)
     */
    Promotion update(Promotion Promotion);
}
