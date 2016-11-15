package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.model.Metadata;

import java.util.Set;

public interface CodeDao {
	public abstract boolean save(String id, Set<String> codes);
	public abstract Set<String> load(Metadata metadata);
	public abstract void delete(String id);
}
