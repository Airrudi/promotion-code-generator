package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.model.Code;
import nl.ruudclaassen.jfall3.model.Promotion;

import java.util.List;
import java.util.Set;

public interface CodeDao {

    boolean save(List<Code> codes);

    Set<String> load(Promotion Promotion);

    void delete(Promotion Promotion);
}
