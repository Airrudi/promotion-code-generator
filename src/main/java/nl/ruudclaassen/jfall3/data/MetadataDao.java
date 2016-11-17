package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.model.Metadata;

import java.util.Map;

public interface MetadataDao {
    Map<String, Metadata> getPromotions();

    Metadata save(Metadata metadata);

    Metadata update(Metadata metadata);

    Metadata getPromotionById(String id);

    void delete(String promoId);
}
