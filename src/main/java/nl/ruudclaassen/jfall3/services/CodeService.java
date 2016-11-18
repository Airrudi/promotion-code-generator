package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.model.Promotion;

import java.io.InputStream;
import java.util.Set;

public interface CodeService {
	public abstract void save(Promotion Promotion, InputStream inputStream);

	public abstract void save(Promotion Promotion);

	public Set<String> load(Promotion Promotion);
}
