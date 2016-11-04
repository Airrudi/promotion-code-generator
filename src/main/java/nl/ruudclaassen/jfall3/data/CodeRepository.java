package nl.ruudclaassen.jfall3.data;

import java.util.Set;

public interface CodeRepository {
	public boolean save(String id, Set<String> codes);	
	public Set<String> load(String id);
	
}
