package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.MetadataDao;
import nl.ruudclaassen.jfall3.general.Utilities;
import nl.ruudclaassen.jfall3.model.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class MetadataServiceImpl implements MetadataService {

    @Autowired
    MetadataDao metadataDao;

    @Override
    public Metadata getPromotionById(String id) {
        return metadataDao.getPromotionById(id);
    }

    @Override
    public Map<String, Metadata> getPromotions() {
        return metadataDao.getPromotions();
    }

    @Override
    public void delete(String promoId) {
        metadataDao.delete(promoId);
    }

    @Override
    public Metadata save(Metadata metadata) {
        String id = UUID.randomUUID().toString();
        metadata.setId(id);
        metadata.setCreationDate(Utilities.getFormattedDate());
        return metadataDao.save(metadata);
    }

    @Override
    public Metadata update(Metadata metadata) {
        return metadataDao.update(metadata);
    }
}
