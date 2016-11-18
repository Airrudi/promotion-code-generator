package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.model.Promotion;

import java.util.List;

public interface PromotionDao {
    List<Promotion> getPromotions();

    Promotion save(Promotion Promotion);

    Promotion update(Promotion Promotion);

    Promotion getPromotionById(int id);

    void delete(int promoId);
}
