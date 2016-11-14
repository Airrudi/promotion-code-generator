package nl.ruudclaassen.jfall3.data;

import java.util.Set;

public interface CodeDao {
	public abstract boolean save(String id, Set<String> codes);
	public abstract Set<String> load(String id);
	public abstract void delete(String id);
}
