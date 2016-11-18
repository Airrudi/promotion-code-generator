package nl.ruudclaassen.jfall3.services;


import nl.ruudclaassen.jfall3.model.Code;
import nl.ruudclaassen.jfall3.model.Promotion;

import java.util.List;

public interface GeneratorService {
    List<Code> generateCodes(Promotion promotion);
}
