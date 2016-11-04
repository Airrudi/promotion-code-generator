package nl.ruudclaassen.jfall3.data;

import java.util.Map;
import nl.ruudclaassen.jfall3.model.Metadata;

public interface MetadataRepository {
	public abstract  Map<String, Metadata> load();
	public abstract boolean save(Metadata metadata);
}
