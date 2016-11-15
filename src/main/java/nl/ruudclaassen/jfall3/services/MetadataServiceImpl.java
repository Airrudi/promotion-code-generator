package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.MetadataDao;
import nl.ruudclaassen.jfall3.model.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Service
public class MetadataServiceImpl implements MetadataService {

    @Autowired
    MetadataDao metadataDao;

    @Override
    public Metadata getMetadataById(String id) {
        return metadataDao.getMetadataById(id);
    }

    @Override
    public Map<String, Metadata> load() {
        return metadataDao.load();
    }

    @Override
    public Map<String, Metadata> delete(String id) {
        return metadataDao.delete(id);
    }

    @Override
    public void save(Metadata metadata) {
        String id = UUID.randomUUID().toString();
        metadata.setId(id);
        metadata.setCreationDate(getFormattedDate());
        metadataDao.save(metadata);
    }

    @Override
    public Map<String, Metadata> update(Metadata metadata) {
        return metadataDao.update(metadata);
    }

    private String getFormattedDate() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}
