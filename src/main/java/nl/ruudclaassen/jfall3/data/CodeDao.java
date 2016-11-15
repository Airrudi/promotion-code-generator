package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.model.Metadata;

import java.util.Set;

//TODO: CR: public and abstract are not needed on an interface
public interface CodeDao {
	public abstract boolean save(String id, Set<String> codes);

	public abstract Set<String> load(Metadata metadata);

	public abstract void delete(Metadata metadata);
}
