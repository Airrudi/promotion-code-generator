package nl.ruudclaassen.jfall3.services;


import java.util.Set;

public interface GeneratorService {
    public abstract Set<String> generateCodes(int requestedNumberOfCodes);
}
