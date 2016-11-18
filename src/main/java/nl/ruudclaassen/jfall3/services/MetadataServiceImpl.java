package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.PromotionDao;
import nl.ruudclaassen.jfall3.general.Utilities;
import nl.ruudclaassen.jfall3.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionDao PromotionDao;

    @Override
    public Promotion getPromotionById(int id) {
        return PromotionDao.getPromotionById(id);
    }

    @Override
    public List<Promotion> getPromotions() {
        return PromotionDao.getPromotions();
    }

    @Override
    public void delete(int promoId) {
        PromotionDao.delete(promoId);
    }

    @Override
    public Promotion save(Promotion Promotion) {
        Promotion.setCreationDate(Utilities.getFormattedDate());
        return PromotionDao.save(Promotion);
    }

    @Override
    public Promotion update(Promotion Promotion) {
        return PromotionDao.update(Promotion);
    }
}
