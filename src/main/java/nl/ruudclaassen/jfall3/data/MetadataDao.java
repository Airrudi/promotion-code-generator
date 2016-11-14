package nl.ruudclaassen.jfall3.data;

import java.util.Map;
import nl.ruudclaassen.jfall3.model.Metadata;

public interface MetadataDao {
	public abstract Map<String, Metadata> load();
	public abstract Metadata save(Metadata metadata);
	public abstract Map<String, Metadata> update(Metadata metadata);
	public abstract Metadata getMetadataById(String id);
	public abstract Map<String, Metadata> delete(String id);
}
