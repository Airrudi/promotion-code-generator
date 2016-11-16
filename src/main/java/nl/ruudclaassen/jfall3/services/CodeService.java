package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.model.Metadata;

import java.io.InputStream;
import java.util.Set;

public interface CodeService {
	public abstract void save(Metadata metadata, InputStream inputStream);

	public abstract void save(Metadata metadata);

	public Set<String> load(Metadata metadata);
}
