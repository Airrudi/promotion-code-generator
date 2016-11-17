package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.model.Metadata;

import java.io.InputStream;
import java.util.Map;

public interface MetadataService {
    Metadata getPromotionById(String id);

    /**
     * Loads all the promotions
     *
     * @return Map<String, Metadata> with id and metadata pairs
     */
    Map<String, Metadata> getPromotions();


    void delete(String promoId);

    /**
     * Creates new promotion (generates new id etc.)
     */
    Metadata save(Metadata metadata);

    /**
     * Updates an existing promotion (does not create a new id)
     */
    Metadata update(Metadata metadata);
}
