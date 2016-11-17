package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.model.Metadata;

import java.util.Set;

public interface CodeDao {

	boolean save(Metadata metaData, Set<String> codes);

	Set<String> load(Metadata metadata);

	void delete(Metadata metadata);
}
