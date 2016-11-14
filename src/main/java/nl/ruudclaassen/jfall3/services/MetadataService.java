package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.model.Metadata;

import java.io.InputStream;
import java.util.Map;

public interface MetadataService {
    public abstract Metadata getMetadataById(String id);
    public abstract Map<String, Metadata> load();
    public abstract Map<String, Metadata> delete(String id);
    public abstract void save(Metadata metadata);
    public abstract Map<String, Metadata> update(Metadata metadata);
}
